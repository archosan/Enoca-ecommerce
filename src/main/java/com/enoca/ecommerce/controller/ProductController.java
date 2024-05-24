package com.enoca.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enoca.ecommerce.dto.ProductCreateRequestDto;
import com.enoca.ecommerce.dto.ProductResponseDto;
import com.enoca.ecommerce.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> saveProduct(
            @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return ResponseEntity.ok(productService.saveProduct(productCreateRequestDto));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
            @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productCreateRequestDto));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
