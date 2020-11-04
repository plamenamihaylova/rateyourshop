package com.ratingapp.repository;

import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
    List<UserRating> findByShop(Shop shop);
    List<UserRating> findByUser(User user);
    List<UserRating> findByShopAndRating(Shop shop, int rating);
    List<UserRating> findByRatingGreaterThanEqual(double rating);
}
