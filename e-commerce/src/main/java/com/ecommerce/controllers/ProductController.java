package com.ecommerce.controllers;

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
        return ResponseEntity.ok(new ApiResponse("Success", allProducts));
    }

    @GetMapping("/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product productById = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Success", productById));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product addProduct = productService.createProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add Product Success", addProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest updateRequest, @PathVariable Long productId) {
        try {
            Product updateProduct = productService.updateProduct(updateRequest, productId);
            return ResponseEntity.ok(new ApiResponse("Product Update Success", updateProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
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

            if (allProductsByBrandAndName.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", allProductsByBrandAndName));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-by/category-and-brand")
    public ResponseEntity<ApiResponse> getAllProductsByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        try {
            List<Product> productsByCategoryAndBrand = productService.getAllProductsByCategoryAndBrand(category, brand);

            if (productsByCategoryAndBrand.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productsByCategoryAndBrand));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-by/{name}/")
    public ResponseEntity<ApiResponse> getAllProductsByName(@PathVariable String name) {
        try {
            List<Product> productsByName = productService.getAllProductsByName(name);

            if (productsByName.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productsByName));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-by/{brand}/")
    public ResponseEntity<ApiResponse> getAllProductsByBrand(@PathVariable String brand) {
        try {
            List<Product> productsByBrand = productService.getProductsByBrand(brand);

            if (productsByBrand.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productsByBrand));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products-by/{category}/")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable String category) {
        try {
            List<Product> productsByCategory = productService.getAllProductsByCategory(category);

            if (productsByCategory.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productsByCategory));
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
