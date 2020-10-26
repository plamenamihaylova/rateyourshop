package com.ratingapp.service;

import com.ratingapp.model.Category;
import com.ratingapp.model.Shop;

import java.util.List;


public interface ShopService {

    List<Shop> getAllShops();
    Shop findById(Long id);
    List<Shop> findByName(String shopName);
    List<Shop> findByCategory(String categoryName);
    Shop createShop(Shop shop);
    Shop updateShop(Shop shop);
    Shop deleteShop(Long id);
}
