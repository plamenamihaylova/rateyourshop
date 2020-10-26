package com.ratingapp.controller;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.Shop;
import com.ratingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    @Qualifier("defaultValidator")
    @Autowired
    Validator validator;

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService){
        this.shopService = shopService;
    }

    @GetMapping
    public List<Shop> getAllShops(@RequestParam (name = "category", required = false) String category){
        if (category != null){
            return shopService.findByCategory(category);
        }
        else {
            return shopService.getAllShops();
        }

    }

    @GetMapping("/name/{name}")
    public List<Shop> getShopsByName(@PathVariable String name) throws EntityNotFoundException {
        return shopService.findByName(name);
    }

    @GetMapping("{id}")
    public Shop getShopById(@PathVariable Long id) throws EntityNotFoundException {
        return shopService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Shop> createNewShop(@Valid @RequestBody Shop shop, Errors errors, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request){
        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        Shop newShop = shopService.createShop(shop);
        return ResponseEntity
                .created(
                        uriComponentsBuilder.fromUriString(request.getRequestURL().toString()).pathSegment("{id}").build(newShop.getId()))
                .body(newShop);
    }

    @PutMapping("{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable Long id, @Valid @RequestBody Shop shop, Errors errors, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request){

        if(errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }
        if (!id.equals(shop.getId())){
            throw new InvalidEntityDataException(
                    String.format("Url ID %d differs from entity's body ID %d", id, shop.getId()));
        }
        Shop updatedShop = shopService.updateShop(shop);
        return ResponseEntity
                .created(
                        uriComponentsBuilder.fromUriString(request.getRequestURL().toString()).pathSegment("{id}").build(updatedShop.getId()))
                .body(updatedShop);
    }

    @DeleteMapping("{id}")
    public Shop deleteShop(@PathVariable Long id){
        return shopService.deleteShop(id);
    }
}
