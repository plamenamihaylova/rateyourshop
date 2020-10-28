package com.ratingapp.service;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Shop;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;


public interface ShopService {

    List<Shop> findAllShops();
    Shop findById(Long id) throws NotFoundEntityException;
    List<Shop> findByName(String shopName) throws NotFoundEntityException;
    List<Shop> findByCategory(String categoryName) throws NotFoundEntityException;
    Shop createShop(Shop shop) throws DataIntegrityViolationException, InvalidEntityDataException;
    Shop updateShop(Shop shop) throws DataIntegrityViolationException, InvalidEntityDataException, NotFoundEntityException;
    Shop deleteShop(Long id) throws NotFoundEntityException;
}
