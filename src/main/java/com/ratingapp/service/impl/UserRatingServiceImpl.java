package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Shop;
import com.ratingapp.model.User;
import com.ratingapp.model.UserRating;
import com.ratingapp.model.dto.ReviewerDTO;
import com.ratingapp.repository.*;
import com.ratingapp.service.UserRatingService;
import com.ratingapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRatingServiceImpl(UserRatingRepository userRatingRepository,
                                 ShopRepository shopRepository,
                                 UserService userService, ModelMapper modelMapper){
        this.userRatingRepository = userRatingRepository;
        this.shopRepository = shopRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserRating> findAllUserRatings() {
        return userRatingRepository.findAll();
    }

    @Override
    public UserRating findById(Long id) throws NotFoundEntityException {
        return userRatingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Review with ID %d does not exist.", id)));
    }

    @Override
    public List<UserRating> findByUserId(Long userId) throws NotFoundEntityException {
        List<UserRating> result = userRatingRepository.findByUser(userService.findById(userId));
        if (result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("User with ID %d has not rated anything.", userId));
        }
        return result;
    }

    @Override
    public List<UserRating> findByShop(Shop shop) throws NotFoundEntityException {

        // will get the shop from the shop repository
        List<UserRating> result =  userRatingRepository.findByShop(shop);
        if (result == null) {
            throw new NotFoundEntityException(String.format("Review for shop with ID %d does not exist.", shop));
        }

        return result;
    }

    @Override
    public List<UserRating> findByShopAndRating(Shop shop, int rating) throws NotFoundEntityException {
        List<UserRating> result = userRatingRepository.findByShopAndRating(shop,rating);
        if (result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("Review for shop with ID %d and rating %d does not exist.", shop.getId(), rating));
        }
        return result;
    }

    @Override
    public UserRating createUserRating(UserRating userRating) throws DataIntegrityViolationException, InvalidEntityDataException {
        userRating.setCreated(LocalDateTime.now());
        userRating.setModified(LocalDateTime.now());
        areDependentPropertiesCorrect(userRating);
        userRatingRepository.saveAndFlush(userRating);
        return userRating;
    }

    @Override
    public UserRating updateUserRating(UserRating userRating) throws DataIntegrityViolationException, InvalidEntityDataException, NotFoundEntityException {
       // todo to check if the id given exists  see the controller also

        //UserRating result = findById(userRating.getId());
        areDependentPropertiesCorrect(userRating);
        userRating.setModified(LocalDateTime.now());
        return userRatingRepository.save(userRating);
    }

    @Override
    public UserRating deleteUserRating(Long id) throws NotFoundEntityException {
        UserRating result = findById(id);
        userRatingRepository.delete(result);
        return result;
    }

    @Override
    public Long count() {
        return userRatingRepository.count();
    }

    private void areDependentPropertiesCorrect(UserRating userRating){
        if (userService.findAllUsers().stream().noneMatch(user -> user.getId().equals(userRating.getUser().getId()))){
            throw new NotFoundEntityException(String.format("User with ID %d does not exist.", userRating.getUser().getId()));
        }
        else if (shopRepository.findAll().stream().noneMatch(shop -> shop.getId().equals(userRating.getShop().getId()))){
            throw new NotFoundEntityException(String.format("Shop with ID %d does not exist.", userRating.getShop().getId()));
        }
    }
}
