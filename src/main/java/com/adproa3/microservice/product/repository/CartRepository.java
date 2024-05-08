package com.adproa3.microservice.product.repository;

import com.adproa3.microservice.product.model.Cart;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(String userId);
}