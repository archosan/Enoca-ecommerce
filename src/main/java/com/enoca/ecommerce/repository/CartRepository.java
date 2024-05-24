package com.enoca.ecommerce.repository;

import org.springframework.stereotype.Repository;
import com.enoca.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
