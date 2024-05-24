package com.enoca.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoca.ecommerce.dto.CartResponseDto;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.CartItem;
import com.enoca.ecommerce.entity.Customer;
import com.enoca.ecommerce.entity.Product;
import com.enoca.ecommerce.mapper.CartMapper;
import com.enoca.ecommerce.repository.CartRepository;
import com.enoca.ecommerce.repository.CustomerRepository;
import com.enoca.ecommerce.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartMapper cartMapper;

    public CartResponseDto getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();

        CartResponseDto responseDto = cartMapper.toCartResponseDto(cart);

        Double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        responseDto.setTotalPrice(totalPrice);

        return responseDto;

    }

    public void emptyCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();
        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

    public CartResponseDto addProductToCart(Long productId, Long customerId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();

        CartItem existingCartItem = findCardItemInCart(customerId, productId);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cart.getCartItems().add(cartItem);

        }

        Double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);

        cartRepository.save(cart);

        CartResponseDto responseDto = cartMapper.toCartResponseDto(cart);

        responseDto.setTotalPrice(totalPrice);

        return responseDto;
    }

    public CartResponseDto removeProductFromCart(Long customerId, Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();

        CartItem existingCartItem = findCardItemInCart(customerId, productId);

        if (existingCartItem == null) {
            throw new IllegalArgumentException("Product not found in cart");
        }

        if (existingCartItem.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough quantity in cart");
        }

        existingCartItem.setQuantity(existingCartItem.getQuantity() - quantity);

        if (existingCartItem.getQuantity() == 0) {
            cart.getCartItems().remove(existingCartItem);
        }

        product.setStock(product.getStock() + quantity);

        Double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        productRepository.save(product);

        cartRepository.save(cart);

        CartResponseDto responseDto = cartMapper.toCartResponseDto(cart);

        responseDto.setTotalPrice(totalPrice);

        return responseDto;
    }

    public CartResponseDto updateCart(Long customerId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();

        CartItem existingCartItem = findCardItemInCart(customerId, productId);

        if (existingCartItem == null) {
            throw new IllegalArgumentException("Product not found in cart");
        }

        if (existingCartItem.getQuantity() < product.getStock()) {
            throw new IllegalArgumentException("Not enough quantity in cart");
        }

        existingCartItem.setQuantity(quantity);

        if (existingCartItem.getQuantity() == 0) {
            cart.getCartItems().remove(existingCartItem);
        }

        product.setStock(product.getStock() + quantity);
        Double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cartRepository.save(cart);

        CartResponseDto responseDto = cartMapper.toCartResponseDto(cart);

        responseDto.setTotalPrice(totalPrice);

        return responseDto;
    }

    public CartItem findCardItemInCart(Long cartId, Long productId) {

        Cart requestedCart = cartRepository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("Cart not found id:" + cartId));

        return requestedCart.getCartItems().stream()
                .filter(
                        item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

    }

}
