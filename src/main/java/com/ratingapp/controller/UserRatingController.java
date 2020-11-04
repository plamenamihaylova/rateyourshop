package com.ratingapp.controller;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.Shop;
import com.ratingapp.model.UserRating;
import com.ratingapp.model.dto.ReviewerDTO;
import com.ratingapp.model.dto.UserRatingDTO;
import com.ratingapp.service.ShopService;
import com.ratingapp.service.UserRatingService;
import com.ratingapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reviews")
public class UserRatingController {

    private final UserRatingService userRatingService;
    private final ShopService shopService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRatingController(UserRatingService userRatingService,
                                ShopService shopService, UserService userService, ModelMapper modelMapper){
        this.userRatingService = userRatingService;
        this.shopService = shopService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserRatingDTO> getAllUserRatings(@RequestParam (name = "userId", required = false) Long userId){
        if (userId != null){
            return userRatingService.findByUserId(userId)
                    .stream().map(this::convertToUserRatingDTO)
                    .collect(Collectors.toList());
        }
        return userRatingService.findAllUserRatings()
                .stream().map(this::convertToUserRatingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/shop/{id}")
    public List<UserRatingDTO> getAllUserRatingsByShop(@PathVariable Long id){ // TO DO = find instead of get
        Shop searched = shopService.findById(id);
        return userRatingService.findByShop(searched)
                .stream().map(this::convertToUserRatingDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UserRating> createUserRating(@Valid @RequestBody UserRating userRating,
                                                       Errors errors,
                                                       HttpServletRequest request){

        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        UserRating newUserRating = userRatingService.createUserRating(userRating);

        return ResponseEntity.created(
                                UriComponentsBuilder.fromUriString(
                                        request.getRequestURL().toString()).pathSegment("{id}")
                                        .build(newUserRating.getId()))
                             .body(newUserRating);
    }

    @PutMapping("{id}")
    public UserRating updateUserRating(@PathVariable Long id,
                                       @Valid @RequestBody UserRating userRating,
                                       Errors errors){

        userService.validateLoggedUser(userRatingService.findById(id).getUser());

        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        // web specify validation business layer doesn't care about urls
        if (!id.equals(userRating.getId())) {
            throw new InvalidEntityDataException(
                    String.format("URL ID %d differs from body entity ID %d", id, userRating.getId())
            );
        }

        return userRatingService.updateUserRating(userRating);
    }

    @DeleteMapping("{id}")
    public UserRating deleteUserRating(@PathVariable Long id){
        return userRatingService.deleteUserRating(id);
    }

    private UserRatingDTO convertToUserRatingDTO (UserRating userRating) {
        return modelMapper.map(userRating, UserRatingDTO.class);
    }

    private UserRating convertToUserRatingEntity (UserRatingDTO userRatingDTO) {
        return modelMapper.map(userRatingDTO, UserRating.class);
    }
}
