package com.ratingapp.repository;

import com.ratingapp.model.Category;
import com.ratingapp.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByShopNameIgnoreCase(String shopName);
    List<Shop> findByCategory(Category category);
}
