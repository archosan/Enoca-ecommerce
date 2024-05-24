package com.enoca.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.enoca.ecommerce.dto.ProductCreateRequestDto;
import com.enoca.ecommerce.dto.ProductResponseDto;
import com.enoca.ecommerce.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toProductResponseDto(Product product);

    Product toProduct(ProductCreateRequestDto productCreateRequestDto);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> products);
}
