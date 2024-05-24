package com.enoca.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enoca.ecommerce.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
