package com.ecommerce.services.category;

import com.ecommerce.models.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    Category getCategoryById(Long categoryId);

    Category getCategoryByName(String categoryName);

    List<Category> getAllCategories();

    Category upCategory(Category category, Long categoryId);

    void deleteCategoryId(Long categoryId);
}
