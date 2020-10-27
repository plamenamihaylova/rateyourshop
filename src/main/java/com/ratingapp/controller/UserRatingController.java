package com.ratingapp.controller;

import com.ratingapp.exception.NotAllowedMultipleReviewsException;
import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.UserRating;
import com.ratingapp.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserRatingService userRatingService;

    @Autowired
    public UserRatingController (UserRatingService userRatingService){
        this.userRatingService = userRatingService;
    }

    @GetMapping
    public List<UserRating> getAllUserRatings(){
        return userRatingService.findAllUserRatings();
    }

    @PostMapping
    public ResponseEntity<UserRating> createUserRating(@Valid @RequestBody UserRating userRating,
                                                       Errors errors,
                                                       HttpServletRequest request){

        if (hasUserRated(userRating)){
            throw new NotAllowedMultipleReviewsException();
        }

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

    private boolean hasUserRated(UserRating userRating) {
        return getAllUserRatings().stream().anyMatch(rating -> rating.getShop().equals(userRating.getShop())
                && rating.getUser().getId().equals(userRating.getUser().getId()));
    }
}
