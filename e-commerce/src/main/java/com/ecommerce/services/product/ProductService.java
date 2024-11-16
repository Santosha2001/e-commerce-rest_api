package com.ecommerce.services.product;

import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.request.AddProductRequest;
import com.ecommerce.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    Product createProduct(AddProductRequest productRequest);

    Product createProductByCategory(AddProductRequest productRequest, Category category);

    Product getProductById(Long productId);

    void deleteProduct(Long productId);

    Product updateProduct(ProductUpdateRequest product, Long id);

    Product updateExistingProduct(Product existingProduct, ProductUpdateRequest updateProductRequest);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getAllProductsByCategoryAndBrand(String category, String brand);

    List<Product> getAllProductsByName(String name);

    List<Product> getAllProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandsAndName(String brand, String name);

}
