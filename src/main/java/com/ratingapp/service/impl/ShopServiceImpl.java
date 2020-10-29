package com.ratingapp.service.impl;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Shop;
import com.ratingapp.repository.CategoryRepository;
import com.ratingapp.repository.ShopRepository;
import com.ratingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public List<Shop> findAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop findById(Long id) throws NotFoundEntityException {
        return shopRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Shop with ID %d does not exist.", id)));
    }

    @Override
    public Shop findByName(String shopName) throws NotFoundEntityException {
        Shop result = shopRepository.findByShopNameIgnoreCase(shopName);
        if(result == null){
            throw new NotFoundEntityException(String.format("Shop with name '%s' does not exist.", shopName)) ;
        }
        return result;
    }

    @Override
    public List<Shop> findByCategory(String categoryName) throws NotFoundEntityException {
        List<Shop> result = shopRepository.findByCategory(categoryRepository.findByNameIgnoreCase(categoryName));
        if(result == null || result.isEmpty()){
            throw new NotFoundEntityException(String.format("Shop with category '%s' does not exist.", categoryName)) ;
        }
        return result;
    }

    @Override
    public Shop createShop(Shop shop) throws DataIntegrityViolationException, InvalidEntityDataException {
        areDependentPropertiesCorrect(shop);
        shopRepository.saveAndFlush(shop);
        return shop;
    }

    @Override
    public Shop updateShop(Shop shop) throws DataIntegrityViolationException, InvalidEntityDataException, NotFoundEntityException {
        findById(shop.getId());
        areDependentPropertiesCorrect(shop);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop deleteShop(Long id) throws NotFoundEntityException {
        Shop result = findById(id);
        shopRepository.delete(result);
        return result;
    }

    @Override
    public Long count() {
        return shopRepository.count();
    }

    private void areDependentPropertiesCorrect (Shop shop) {
        if (categoryRepository.findAll().stream().noneMatch(category -> category.getId().equals(shop.getCategory().getId()))){
            throw new NotFoundEntityException(String.format("Category with ID %d does not exist.", shop.getCategory().getId()));
        }
    }
}
