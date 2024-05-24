package com.enoca.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enoca.ecommerce.dto.OrderResponseDto;
import com.enoca.ecommerce.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getOrderByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersForCustomer(customerId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("/orders/{customerId}")
    public ResponseEntity<OrderResponseDto> placeOrder(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.placeOrder(customerId));
    }

}
