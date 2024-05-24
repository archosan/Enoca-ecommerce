package com.enoca.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enoca.ecommerce.dto.CartResponseDto;
import com.enoca.ecommerce.service.CartService;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart/{customerId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @PostMapping("/cart/{customerId}/empty")
    public ResponseEntity<String> emptyCart(@PathVariable Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.ok("Cart is emptied");
    }

    @PostMapping("/cart/{customerId}/add-product/{productId}")
    public ResponseEntity<CartResponseDto> addProductToCart(@PathVariable Long productId, @PathVariable Long customerId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(productId, customerId, quantity));
    }

    @PostMapping("/cart/{customerId}/remove-product/{productId}")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable Long productId,
            @PathVariable Long customerId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId, customerId, quantity));

    }

    @PutMapping("/cart/{customerId}")
    public ResponseEntity<CartResponseDto> updateCart(@PathVariable Long customerId, @RequestParam Long productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCart(customerId, productId, quantity));
    }
}
