package com.ratingapp.service;

import com.ratingapp.model.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAllRatings();
    Rating findById(Long id);
    Rating createRating(Rating rating);
    Long count();
}
