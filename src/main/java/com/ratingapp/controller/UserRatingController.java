package com.ratingapp.controller;

import com.ratingapp.exception.NotAllowedMultipleReviewsException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.UserRating;
import com.ratingapp.repository.UserRatingRepository;
import com.ratingapp.service.UserRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class UserRatingController {

    private UserRatingService userRatingService;

    public UserRatingController (UserRatingService userRatingService){
        this.userRatingService = userRatingService;
    }

    @GetMapping
    public List<UserRating> getAllUserRatings(){
        return userRatingService.getAllUserRatings();
    }

    @PostMapping
    public ResponseEntity<UserRating> createUserRating(@Valid @RequestBody UserRating userRating, Errors errors, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request){

        if (getAllUserRatings().stream().anyMatch(rating -> rating.getShop() == userRating.getShop() && rating.getUser().getId() == userRating.getUser().getId())){
            throw new NotAllowedMultipleReviewsException();
        }

        if (errors.hasErrors()){
            throw new ValidationErrorsException(errors);
        }

        UserRating newUserRating = userRatingService.createUserRating(userRating);

        return ResponseEntity.created(uriComponentsBuilder.fromUriString(request.getRequestURL().toString()).pathSegment("{id}").build(newUserRating.getId()))
                             .body(newUserRating);
    }

}
