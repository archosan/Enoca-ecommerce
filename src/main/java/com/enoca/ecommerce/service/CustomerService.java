package com.enoca.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoca.ecommerce.dto.CartResponseDto;
import com.enoca.ecommerce.dto.CustomerRequestDto;
import com.enoca.ecommerce.dto.CustomerResponseDto;
import com.enoca.ecommerce.entity.Customer;
import com.enoca.ecommerce.mapper.CustomerMapper;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.repository.CartRepository;
import com.enoca.ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerResponseDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerResponseDto dto = customerMapper.toCustomerResponseDto(customer);
                    calculateTotalPrice(dto.getCart());
                    return dto;
                })
                .collect(Collectors.toList());

    }

    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));

        CustomerResponseDto dto = customerMapper.toCustomerResponseDto(customer);
        calculateTotalPrice(dto.getCart());
        return dto;
    }

    public CustomerResponseDto save(CustomerRequestDto customerRequestDto) {

        Cart cart = new Cart();

        Customer customer = new Customer();
        customer.setUsername(customerRequestDto.getUsername());
        customer.setCart(cart);

        cart.setCustomer(customer);

        customerRepository.save(customer);
        cartRepository.save(cart);

        return customerMapper.toCustomerResponseDto(customer);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }

    private void calculateTotalPrice(CartResponseDto cart) {
        Double totalPrice = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
        cart.setTotalPrice(totalPrice);
    }
}
