package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.CategoryDTO;
import com.mph.salelaptop.model.Category;
import com.mph.salelaptop.repository.CategoryRepository;
import com.mph.salelaptop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? convertToDto(category) : null;
    }

    @Override
    public CategoryDTO getCategoryByName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        return category != null ? convertToDto(category) : null;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setCategoryName(categoryDTO.getCategoryName());
            Category updatedCategory = categoryRepository.save(existingCategory);
            return convertToDto(updatedCategory);
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDto(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        // Manually map categoryId because entity Category use categoryId instead of id.
        categoryDTO.setCategoryId(category.getCategoryId());
        return categoryDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        // Manually map categoryId because entity Category use categoryId instead of id.
        category.setCategoryId(categoryDTO.getCategoryId());
        return category;
    }
}