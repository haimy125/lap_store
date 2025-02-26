package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO getBrandById(Long id);
    BrandDTO getBrandByName(String brandName);
    List<BrandDTO> getAllBrands();
    BrandDTO createBrand(BrandDTO brandDTO);
    BrandDTO updateBrand(Long id, BrandDTO brandDTO);
    void deleteBrand(Long id);
}