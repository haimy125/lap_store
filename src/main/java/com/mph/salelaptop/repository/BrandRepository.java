package com.mph.salelaptop.repository;

import com.mph.salelaptop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
}