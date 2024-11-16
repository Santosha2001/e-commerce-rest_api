package com.ecommerce.controllers;

import com.ecommerce.exceptions.CategoryAlreadyExistException;
import com.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.models.Category;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all-categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category addCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", addCategory));
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryId(@PathVariable Long id) {
        try {
            Category categoryById = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", categoryById));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryName}/category")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable String categoryName) {
        try {
            Category categoryByName = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found", categoryByName));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryId(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryId(id);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updateCategory = categoryService.upCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Success", updateCategory));
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
