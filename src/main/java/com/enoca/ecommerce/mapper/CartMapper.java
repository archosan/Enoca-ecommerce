package com.enoca.ecommerce.mapper;

import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.enoca.ecommerce.dto.CartItemResponseDto;
import com.enoca.ecommerce.dto.CartResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartResponseDto toCartResponseDto(Cart cart);

    CartItemResponseDto toCartItemResponseDto(Product product);
}
