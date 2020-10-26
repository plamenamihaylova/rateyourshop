package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Category;
import com.ratingapp.model.Shop;
import com.ratingapp.repository.CategoryRepository;
import com.ratingapp.repository.ShopRepository;
import com.ratingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CategoryRepository categoryRepository){
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop findById(Long id) {
        Shop shop =  shopRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Shop with ID %d does not exist.", id)));
        return shop;
    }

    @Override
    public List<Shop> findByName(String shopName) {
        List<Shop> result = shopRepository.findByShopNameIgnoreCase(shopName);
        if(result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("Shop with name '%s' does not exist.", shopName)) ;
        }
        return result;
    }

    @Override
    public List<Shop> findByCategory(String categoryName) {
        List<Shop> result = shopRepository.findByCategory(categoryRepository.findByNameIgnoreCase(categoryName));
        if(result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("Shop with category '%s' does not exist.", categoryName)) ;
        }
        return result;
    }

    @Override
    public Shop createShop(Shop shop) {
        if (categoryRepository.findAll().stream().noneMatch(c -> c.getId() == shop.getCategory().getId() )){
            throw new NotFoundEntityException(String.format("Category with ID %d does not exist.", shop.getCategory().getId()));
        }
        shopRepository.saveAndFlush(shop);
        return shop;
    }

    @Override
    public Shop updateShop(Shop shop) throws InvalidEntityDataException, NotFoundEntityException {
        Shop result = shopRepository.findById(shop.getId()).orElseThrow(() -> new NotFoundEntityException(String.format("Shop with ID %d does not exist.", shop.getId())));
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop deleteShop(Long id) {
        Shop result = shopRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Shop with ID %d does not exist.", id)));
        shopRepository.delete(result);
        return result;
    }
}
