package com.ecommerce.controllers;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.Product;
import com.ecommerce.request.AddProductRequest;
import com.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.convertToListOfDtos(allProducts);

        return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
    }

    @GetMapping("/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product productById = productService.getProductById(productId);
            ProductDto convertedProduct = productService.convertToDto(productById);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product addProduct = productService.createProduct(product);
            ProductDto convertedProduct = productService.convertToDto(addProduct);
            return ResponseEntity.ok(new ApiResponse("Add Product Success", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-product-by-id")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest updateRequest, @RequestParam Long productId) {
        try {
            Product updateProduct = productService.updateProduct(updateRequest, productId);
            ProductDto convertedProduct = productService.convertToDto(updateProduct);
            return ResponseEntity.ok(new ApiResponse("Product Update Success", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Product Update Success", productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-by/brand-and-name")
    public ResponseEntity<ApiResponse> getAllProductsByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            List<Product> allProductsByBrandAndName = productService.getAllProductsByBrandAndName(brand, productName);
            List<ProductDto> convertedProducts = productService.convertToListOfDtos(allProductsByBrandAndName);

            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-category-and-brand")
    public ResponseEntity<ApiResponse> getAllProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> productsByCategoryAndBrand = productService.getAllProductsByCategoryAndBrand(category, brand);
            List<ProductDto> convertedProducts = productService.convertToListOfDtos(productsByCategoryAndBrand);

            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-name/")
    public ResponseEntity<ApiResponse> getAllProductsByName(@RequestParam String name) {
        try {
            List<Product> productsByName = productService.getAllProductsByName(name);
            List<ProductDto> convertedProducts = productService.convertToListOfDtos(productsByName);

            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-brand/")
    public ResponseEntity<ApiResponse> getAllProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> productsByBrand = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.convertToListOfDtos(productsByBrand);

            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-category/")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@RequestParam String category) {
        try {
            List<Product> productsByCategory = productService.getAllProductsByCategory(category);
            List<ProductDto> convertedProducts = productService.convertToListOfDtos(productsByCategory);

            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-count-by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsCountByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandsAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product Count", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
