package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}