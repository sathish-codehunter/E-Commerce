package com.sathish.shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sathish.shop.exceptions.ProductNotFoundException;
import com.sathish.shop.model.Product;
import com.sathish.shop.request.AddProductRequest;
import com.sathish.shop.response.ApiResponse;
import com.sathish.shop.service.product.IProductService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Found", products));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product products = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Found Successfully", products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product Created", theProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody AddProductRequest request, @PathVariable Long id){
        try {
            Product theProduct = productService.updateProduct(request, id);
            return ResponseEntity.ok(new ApiResponse("Update Successfully", theProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted", id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brand, @PathVariable String name){
        try {
            List<Product> theProduct = productService.getProductByBrandAndName(brand, name);
            if(theProduct.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
