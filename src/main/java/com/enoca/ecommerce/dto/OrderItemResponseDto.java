package com.enoca.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long id;

    private int quantity;

    private double priceAtOrder;

    private double price;

}
