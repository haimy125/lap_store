package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.BrandDTO;
import com.mph.salelaptop.model.Brand;
import com.mph.salelaptop.repository.BrandRepository;
import com.mph.salelaptop.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        return brand != null ? convertToDto(brand) : null;
    }

    @Override
    public BrandDTO getBrandByName(String brandName) {
        Brand brand = brandRepository.findByBrandName(brandName);
        return brand != null ? convertToDto(brand) : null;
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = convertToEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return convertToDto(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand != null) {
            existingBrand.setBrandName(brandDTO.getBrandName());
            Brand updatedBrand = brandRepository.save(existingBrand);
            return convertToDto(updatedBrand);
        }
        return null;
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    private BrandDTO convertToDto(Brand brand) {
        BrandDTO brandDTO = modelMapper.map(brand, BrandDTO.class);
        //Map brandId to id because BrandDTO have brandId and Brand has id
        brandDTO.setBrandId(brand.getBrandId());
        return brandDTO;
    }

    private Brand convertToEntity(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        //Map id to brandId because BrandDTO have brandId and Brand has id
        brand.setBrandId(brandDTO.getBrandId());
        return brand;
    }
}