package com.ratingapp.controller;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.Shop;
import com.ratingapp.model.UserRating;
import com.ratingapp.model.dto.ShopDTO;
import com.ratingapp.model.dto.UserRatingDTO;
import com.ratingapp.service.ShopService;
import com.ratingapp.service.UserRatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    private final ShopService shopService;
    private final UserRatingService userRatingService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopController(ShopService shopService, UserRatingService userRatingService, ModelMapper modelMapper) {
        this.shopService = shopService;
        this.userRatingService = userRatingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ShopDTO> findAllShops(@RequestParam(name = "category", required = false) List<String> category,
                                      @RequestParam(name = "name", required = false) String name) throws EntityNotFoundException {

        if (category != null && name != null) {
            List<ShopDTO> shopByName = shopService.findByName(name)
                                        .stream().map(this::convertToShopDTO)
                                        .collect(Collectors.toList());

            List<ShopDTO> shopsByCategories = getShopsByCategories(category);

            if (!shopsByCategories.contains(name)) {
                shopsByCategories.addAll(shopByName);
            }
            return shopsByCategories;

        }
        else if (category != null){
            return getShopsByCategories(category);

        }
        else if (name != null) {
            return shopService.findByName(name)
                    .stream().map(this::convertToShopDTO)
                    .collect(Collectors.toList());
        }
        else{
             return shopService.findAllShops()
                     .stream().map(this::convertToShopDTO)
                     .collect(Collectors.toList());
        }
    }

    @GetMapping("{id}")
    public ShopDTO findShopById(@PathVariable Long id) throws EntityNotFoundException {
        return convertToShopDTO(shopService.findById(id));
    }

    @GetMapping("/{shopId}/reviews")
    public List<UserRatingDTO> getAllReviewsForShop(@PathVariable Long shopId){
        Shop searched = shopService.findById(shopId);
        return userRatingService.findByShop(searched)
                .stream().map(this::convertToUserRatingDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{shopId}/reviews/{rating}") // get all reviews for a specific shop with a specific rating
    public List<UserRatingDTO> getAllReviewsForShopWithSpecificRating(@PathVariable (name = "shopId", required = true) Long shopId,
                                                                      @PathVariable (name = "rating", required = true) int rating){

        Shop searchedShop = shopService.findById(shopId);
        return userRatingService.findByShopAndRating(searchedShop, rating)
                .stream().map(this::convertToUserRatingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rating/{ratingValue}")
    public List<ShopDTO> getShopsWithSpecificAverageRating(@PathVariable (name = "ratingValue") Double ratingValue){
        return shopService.findByRatingAverage(ratingValue)
                .stream().map(this::convertToShopDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Shop> createNewShop(@Valid @RequestBody Shop shop,
                                              Errors errors,
                                              HttpServletRequest request) {
        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }

        Shop newShop = shopService.createShop(shop);
        return ResponseEntity
                .created(
                        UriComponentsBuilder.fromUriString(
                                request.getRequestURL().toString()).pathSegment("{id}")
                                .build(newShop.getId()))
                .body(newShop);
    }

    @PutMapping("{id}")
    public Shop updateShop(@PathVariable Long id,
                           @Valid @RequestBody Shop shop,
                           Errors errors) {

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
        if (!id.equals(shop.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID %d differs from entity's body ID %d", id, shop.getId()));
        }

        return shopService.updateShop(shop);
    }

    @DeleteMapping("{id}")
    public Shop deleteShop(@PathVariable Long id) {
        return shopService.deleteShop(id);
    }

    private ShopDTO convertToShopDTO(Shop shop) {
        return modelMapper.map(shop, ShopDTO.class);
    }

    private Shop convertToShopEntity(ShopDTO shopDTO) {
        return modelMapper.map(shopDTO, Shop.class);
    }

    private UserRatingDTO convertToUserRatingDTO(UserRating userRating) {
        return modelMapper.map(userRating, UserRatingDTO.class);
    }

    private UserRating convertToUserRatingEntity(UserRatingDTO userRatingDTO) {
        return modelMapper.map(userRatingDTO, UserRating.class);
    }

    private List<ShopDTO> getShopsByCategories(List<String> category){
        List<ShopDTO> result = new ArrayList<>();
        category.forEach(c -> shopService.findByCategory(c).stream().map(this::convertToShopDTO).collect(Collectors.toList()).forEach(shop -> result.add(shop)));
        return result;
    }
}
