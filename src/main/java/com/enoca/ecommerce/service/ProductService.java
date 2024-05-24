package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.ProductCreateRequestDto;
import com.enoca.ecommerce.dto.ProductResponseDto;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.CartItem;
import com.enoca.ecommerce.entity.Product;
import com.enoca.ecommerce.mapper.ProductMapper;
import com.enoca.ecommerce.repository.CartRepository;
import com.enoca.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDto saveProduct(ProductCreateRequestDto productCreateDto) {

        Product product = productMapper.toProduct(productCreateDto);
        product = productRepository.save(product);

        return productMapper.toProductResponseDto(product);

    }

    public List<ProductResponseDto> getAllProducts() {
        return productMapper.toProductResponseDtoList(productRepository.findAll());
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productMapper.toProductResponseDto(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductCreateRequestDto productCreateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        checkCartsForProductAvailability(id, productCreateDto.getStock());
        product.setProductName(productCreateDto.getProductName());
        product.setPrice(productCreateDto.getPrice());
        product.setStock(productCreateDto.getStock());

        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        cartRepository.findAll().forEach(cart -> {
            cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(id));
            cartRepository.save(cart);
        });
        productRepository.delete(product);
    }

    private void checkCartsForProductAvailability(Long productId, int newStock) {

        List<Cart> carts = cartRepository.findAll();

        carts.forEach(cart -> {
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.forEach(cartItem -> {
                if (cartItem.getProduct().getId().equals(productId) && cartItem.getQuantity() > newStock) {
                    throw new IllegalArgumentException("Not enough stock for product: " + productId);
                }
            });
        });

    }

}
