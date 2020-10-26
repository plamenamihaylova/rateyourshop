package com.ratingapp.service.impl;

import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Rating;
import com.ratingapp.repository.RatingRepository;
import com.ratingapp.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository){
       this.ratingRepository = ratingRepository;
    }


    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Rating with ID %d does not exist.",id)));
    }

    @Override
    public Rating createRating(Rating rating) {
        ratingRepository.saveAndFlush(rating);
        return rating;
    }

    @Override
    public Long count() {
        return ratingRepository.count();
    }
}
