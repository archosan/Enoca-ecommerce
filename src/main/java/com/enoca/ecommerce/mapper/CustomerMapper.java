package com.enoca.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.enoca.ecommerce.dto.CustomerResponseDto;
import com.enoca.ecommerce.entity.Customer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponseDto toCustomerResponseDto(Customer customer);
}
