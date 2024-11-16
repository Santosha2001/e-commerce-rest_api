package com.ecommerce.services.product;

import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.request.AddProductRequest;
import com.ecommerce.request.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(AddProductRequest productRequest) {
        // check if the product is found in the db
        // if yes, set it has a new product for category
        // if no, save it has new category
        // then set has new category
        Category category = Optional.ofNullable(categoryRepository.findCategoryByName(productRequest.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productRequest.setCategory(category);
        return productRepository.save(createProductByCategory(productRequest, category));
    }

    @Override
    public Product createProductByCategory(AddProductRequest productRequest, Category category) {

        return new Product(
                productRequest.getName(),
                productRequest.getBrand(),
                productRequest.getPrice(),
                productRequest.getDescription(),
                productRequest.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(productRepository::delete, () -> new ProductNotFoundException("Product not found by id: " + productId));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, Long id) {
        // if product find by id
        // update existing product and save
        // else, throw exception
        return productRepository.findById(id)
                .map(eistingProduct -> updateExistingProduct(eistingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    //update existing product
    @Override
    public Product updateExistingProduct(Product existingProduct, ProductUpdateRequest updateProductRequest) {
        existingProduct.setName(updateProductRequest.getName());
        existingProduct.setInventory(updateProductRequest.getInventory());
        existingProduct.setBrand(updateProductRequest.getBrand());
        existingProduct.setDescription(updateProductRequest.getDescription());
        existingProduct.setPrice(updateProductRequest.getPrice());

        //if category exist update
        Category category = categoryRepository.findCategoryByName(updateProductRequest.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getAllProductsByName(String name) {

        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandsAndName(String brand, String name) {
        return null;
    }
}
