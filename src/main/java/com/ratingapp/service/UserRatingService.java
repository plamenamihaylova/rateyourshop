package com.ratingapp.service;

import com.ratingapp.model.Rating;
import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;

import java.util.List;

public interface UserRatingService {
    List<UserRating> getAllUserRatings();
    UserRating findById(Long id);
    List<UserRating> findByShop(Shop shop);
    List<UserRating> findByRating(Rating rating);
    List<UserRating> findByUser(User user);
    UserRating createUserRating(UserRating userRating);
    UserRating updateUserRating(UserRating userRating);
    UserRating deleteUserRating(Long id);
}
