package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Rating;
import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;
import com.ratingapp.repository.*;
import com.ratingapp.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class UserRatingServiceImpl  implements UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final ShopRepository shopRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRatingServiceImpl(UserRatingRepository userRatingRepository,
                                 ShopRepository shopRepository,
                                 RatingRepository ratingRepository,
                                 UserRepository userRepository){
        this.userRatingRepository = userRatingRepository;
        this.shopRepository = shopRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRating> findAllUserRatings() {
        return userRatingRepository.findAll();
    }

    @Override
    public UserRating findById(Long id) {
        return userRatingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Review with ID %d does not exist.", id)));
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
    public UserRating createUserRating(UserRating userRating) throws DataIntegrityViolationException {
        if (shopRepository.findAll().stream().noneMatch(shop -> shop.getId().equals(userRating.getShop().getId()))){
            throw new NotFoundEntityException(String.format("Shop with ID %d does not exist.", userRating.getShop().getId()));
        }
        if (ratingRepository.findAll().stream().noneMatch(rating -> rating.getId().equals(userRating.getRating().getId()))){
            throw new NotFoundEntityException(String.format("Rating with ID %d does not exist.", userRating.getRating().getId()));
        }
        if (userRepository.findAll().stream().noneMatch(user -> user.getId().equals(userRating.getUser().getId()))){
            throw new NotFoundEntityException(String.format("User with ID %d does not exist.", userRating.getUser().getId()));
        }
        userRatingRepository.saveAndFlush(userRating);
        return userRating;
    }

    @Override
    public UserRating updateUserRating(UserRating userRating) throws InvalidEntityDataException, NotFoundEntityException {
        UserRating result = findById(userRating.getId());

        if(!result.getCreated().equals(userRating.getCreated())) {
            throw new InvalidEntityDataException(String.format("Review date of creation: %s cannot be modified.",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(result.getCreated())));
        }
        userRating.setModified(LocalDateTime.now());
        return userRating;
    }

    @Override
    public UserRating deleteUserRating(Long id) {
        UserRating result = findById(id);
        userRatingRepository.delete(result);
        return result;
    }
}
