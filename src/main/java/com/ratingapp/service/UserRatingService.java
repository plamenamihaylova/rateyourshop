package com.ratingapp.service;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface UserRatingService {
    List<UserRating> findAllUserRatings();
    UserRating findById(Long id) throws NotFoundEntityException;
    List<UserRating> findByUserId(Long userId) throws NotFoundEntityException;
    List<UserRating> findByShop(Shop shop) throws NotFoundEntityException;
    List<UserRating> findByShopAndRating(Shop shop, int rating) throws NotFoundEntityException;
    UserRating createUserRating(UserRating userRating) throws DataIntegrityViolationException, InvalidEntityDataException;
    UserRating updateUserRating(UserRating userRating) throws DataIntegrityViolationException, InvalidEntityDataException, NotFoundEntityException;
    UserRating deleteUserRating(Long id) throws NotFoundEntityException;
    Long count();
}
