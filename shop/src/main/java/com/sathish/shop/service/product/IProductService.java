package com.sathish.shop.service.product;

import com.sathish.shop.model.Product;
import com.sathish.shop.request.AddProductRequest;

import java.util.List;


public interface IProductService{
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(AddProductRequest product, Long id);   
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);
}
