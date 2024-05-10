package com.adproa3.microservice.product.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

public class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        this.cart = new Cart();
        this.cart.setCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        this.cart.setUserId("user-1");
        this.cart.setTotalPrice(0);
        this.cart.setProductsInCart(new HashMap<>());
    }

    @Test
    void testGetCartId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.cart.getCartId());
    }

    @Test
    void testGetUserId() {
        assertEquals("user-1", this.cart.getUserId());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(0, this.cart.getTotalPrice());
    }

    @Test
    void testGetProductInCart() {
        assertEquals(new HashMap<>(), this.cart.getProductsInCart());
    }

}