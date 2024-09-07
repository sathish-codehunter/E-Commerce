package com.sathish.shop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sathish.shop.exceptions.ProductNotFoundException;
import com.sathish.shop.model.Category;
import com.sathish.shop.model.Product;
import com.sathish.shop.repository.CategoryRepository;
import com.sathish.shop.repository.ProductRepository;
import com.sathish.shop.request.AddProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository product;
    private final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
        .orElseGet(()->{
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
                            
        request.setCategory(category);
        return product.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
        );
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return product.countByBrandAndName(brand, name);
    }

    @Override
    public void deleteProductById(Long id) {
        product.findById(id)
        .ifPresentOrElse(product::delete,
         ()-> {throw new ProductNotFoundException("Product Not Found");});
    }

    @Override
    public List<Product> getAllProducts() {
        return product.findAll();
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return product.findByBrandAndName(brand,name);
    }

    @Override
    public Product getProductById(Long id) {
        return product.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> getProductByName(String name) {
        return product.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return product.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return product.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return product.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public Product updateProduct(AddProductRequest request, Long id) {
        return product.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(product::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
    }

    private Product updateExistingProduct(Product existingProduct, AddProductRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        
        Category category = categoryRepository.findByName(request.getCategory());
        existingProduct.setCategory(category);
        return existingProduct;
    }
    
}
