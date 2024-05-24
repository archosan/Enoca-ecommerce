package com.enoca.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long id;

    private String username;

    private CartResponseDto cart;

}
