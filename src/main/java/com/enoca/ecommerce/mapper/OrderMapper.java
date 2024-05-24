package com.enoca.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.enoca.ecommerce.dto.OrderItemResponseDto;
import com.enoca.ecommerce.dto.OrderResponseDto;
import com.enoca.ecommerce.entity.Order;
import com.enoca.ecommerce.entity.OrderItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponseDto toOrderResponseDto(Order order);

    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

    List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders);

    List<OrderItemResponseDto> toOrderItemResponseDtoList(List<OrderItem> orderItems);
}
