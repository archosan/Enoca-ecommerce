package com.enoca.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoca.ecommerce.dto.OrderResponseDto;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.CartItem;
import com.enoca.ecommerce.entity.Customer;
import com.enoca.ecommerce.entity.Order;
import com.enoca.ecommerce.entity.OrderItem;
import com.enoca.ecommerce.mapper.OrderMapper;
import com.enoca.ecommerce.repository.CartRepository;
import com.enoca.ecommerce.repository.CustomerRepository;
import com.enoca.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderResponseDto placeOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Cart cart = customer.getCart();

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());

        Double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum();

        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        OrderResponseDto dto = orderMapper.toOrderResponseDto(order);

        dto.setTotalPrice(totalPrice);

        return dto;

    }

    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        OrderResponseDto dto = orderMapper.toOrderResponseDto(order);

        calculateTotalPrice(dto);

        return dto;
    }

    public List<OrderResponseDto> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        List<OrderResponseDto> dtos = orders.stream()
                .map(orderMapper::toOrderResponseDto)
                .collect(Collectors.toList());

        dtos.forEach(this::calculateTotalPrice);

        return dtos;

    }

    private void calculateTotalPrice(OrderResponseDto order) {
        Double totalPrice = order.getItems().stream()
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity()).sum();
        order.setTotalPrice(totalPrice);
    }

}
