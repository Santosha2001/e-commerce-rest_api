package com.ecommerce.services.category;

import com.ecommerce.exceptions.CategoryAlreadyExistException;
import com.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.models.Category;
import com.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        // if category not exist by name
        // then, save
        // if exist throw exception
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new CategoryAlreadyExistException("Category already exist"));
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found by id: " + categoryId));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category upCategory(Category category, Long categoryId) {
        // if category exist by id,
        // then update old existing category
        // if no, throw exception
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new CategoryNotFoundException("Category not found by id: " + categoryId));
    }

    @Override
    public void deleteCategoryId(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository::delete, () -> new CategoryNotFoundException("Category not found by id: " + categoryId));

    }
}
