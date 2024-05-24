package com.enoca.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enoca.ecommerce.dto.CustomerRequestDto;
import com.enoca.ecommerce.dto.CustomerResponseDto;
import com.enoca.ecommerce.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDto>> getCustomer() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody CustomerRequestDto customerDto) {
        return ResponseEntity.ok(customerService.save(customerDto));
    }

    @DeleteMapping("/customers")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted");

    }

}