package com.mph.salelaptop.repository;

import com.mph.salelaptop.model.Brand;
import com.mph.salelaptop.model.Category;
import com.mph.salelaptop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByCategoryAndBrand(Category category, Brand brand);
     List<Product> findByProductNameContaining(String keyword);
}