package com.ratingapp.service.impl;

import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Rating;
import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;
import com.ratingapp.repository.*;
import com.ratingapp.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserRatingServiceImpl  implements UserRatingService {

    private UserRatingRepository userRatingRepository;
    private ShopRepository shopRepository;
    private RatingRepository ratingRepository;
    private UserRepository userRepository;
//    private CommentRepository commentRepository;

    @Autowired
    public UserRatingServiceImpl(UserRatingRepository userRatingRepository,
                                 ShopRepository shopRepository,
                                 RatingRepository ratingRepository,
                                 UserRepository userRepository){
        this.userRatingRepository = userRatingRepository;
        this.shopRepository = shopRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
//        this.commentRepository = commentRepository;
    }

    @Override
    public List<UserRating> getAllUserRatings() {
        return userRatingRepository.findAll();
    }

    @Override
    public UserRating findById(Long id) {
        return userRatingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("User rating with ID %d does not exist.", id)));
    }

    @Override
    public List<UserRating> findByShop(Shop shop) {
      //  userRatingRepository.findByShop(shopRepository.findByShopNameIgnoreCase(shop.getShopName()));

        return null;
    }

    @Override
    public List<UserRating> findByRating(Rating rating) {
        return null;
    }

    @Override
    public List<UserRating> findByUser(User user) {
        return null;
    }

    @Override
    public UserRating createUserRating(UserRating userRating) {
        if (shopRepository.findAll().stream().noneMatch(shop -> shop.getId() == userRating.getShop().getId())){
            throw new NotFoundEntityException(String.format("Shop with ID %d does not exist.", userRating.getShop().getId()));
        }
        if (ratingRepository.findAll().stream().noneMatch(rating -> rating.getId() == userRating.getRating().getId())){
            throw new NotFoundEntityException(String.format("Rating with ID %d does not exist.", userRating.getRating().getId()));
        }
        if (userRepository.findAll().stream().noneMatch(user -> user.getId() == userRating.getUser().getId())){
            throw new NotFoundEntityException(String.format("User with ID %d does not exist.", userRating.getUser().getId()));
        }
        userRatingRepository.saveAndFlush(userRating);
        return userRating;
    }


    /*
            if (categoryRepository.findAll().stream().noneMatch(c -> c.getId() == shop.getCategory().getId() )){
            throw new NotFoundEntityException(String.format("Category with ID %d does not exist.", shop.getCategory().getId()));
        }
        shopRepository.saveAndFlush(shop);
        return shop;
    *
     */

    @Override
    public UserRating updateUserRating(UserRating userRating) {
        return null;
    }

    @Override
    public UserRating deleteUserRating(Long id) {
        UserRating result = userRatingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("User rating with ID %d does not exist.", id)));
        userRatingRepository.delete(result);
        return result;
    }
}
