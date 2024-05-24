package com.enoca.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long id;

    private List<CartItemResponseDto> cartItems;

    private double totalPrice;
}
