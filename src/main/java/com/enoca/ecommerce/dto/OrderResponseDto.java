package com.enoca.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;

    private List<OrderItemResponseDto> items;

    private double totalPrice;

}
