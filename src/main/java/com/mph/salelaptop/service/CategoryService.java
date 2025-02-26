package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryById(Long id);
    CategoryDTO getCategoryByName(String categoryName);
    List<CategoryDTO> getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}