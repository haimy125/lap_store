package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.ProductDTO;
import com.mph.salelaptop.model.Brand;
import com.mph.salelaptop.model.Category;
import com.mph.salelaptop.model.Product;
import com.mph.salelaptop.repository.BrandRepository;
import com.mph.salelaptop.repository.CategoryRepository;
import com.mph.salelaptop.repository.ProductRepository;
import com.mph.salelaptop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository; // Cần để lấy Category từ ID

    @Autowired
    private BrandRepository brandRepository; // Cần để lấy Brand từ ID

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? convertToDto(product) : null;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setQuantityInStock(productDTO.getQuantityInStock());
            existingProduct.setImageUrl(productDTO.getImageUrl());

            // Retrieve Category and Brand from the database based on categoryId and brandId
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
            if (category == null) {
                throw new IllegalArgumentException("Category with id " + productDTO.getCategoryId() + " not found");
            }
            existingProduct.setCategory(category);

            Brand brand = brandRepository.findById(productDTO.getBrandId()).orElse(null);
            if (brand == null) {
                throw new IllegalArgumentException("Brand with id " + productDTO.getBrandId() + " not found");
            }
            existingProduct.setBrand(brand);

            Product updatedProduct = productRepository.save(existingProduct);
            return convertToDto(updatedProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        // Manually map productId because entity Product use productId instead of id.
        productDTO.setProductId(product.getProductId());

        // Manually map categoryId and brandId because relationship
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        productDTO.setBrandId(product.getBrand().getBrandId());

        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        // Manually map productId because entity Product use productId instead of id.
        product.setProductId(productDTO.getProductId());

        // Retrieve Category and Brand from the database based on categoryId and brandId
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category with id " + productDTO.getCategoryId() + " not found");
        }
        product.setCategory(category);

        Brand brand = brandRepository.findById(productDTO.getBrandId()).orElse(null);
        if (brand == null) {
            throw new IllegalArgumentException("Brand with id " + productDTO.getBrandId() + " not found");
        }
        product.setBrand(brand);

        return product;
    }
}