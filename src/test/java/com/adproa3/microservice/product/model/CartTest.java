package com.adproa3.microservice.product.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
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

    @Test
    public void testConstructors() {
        // Test default constructor
        Cart cart = new Cart();
        Assertions.assertNull(cart.getCartId());
        Assertions.assertNull(cart.getUserId());
        Assertions.assertEquals(0, cart.getTotalPrice());
        Assertions.assertTrue(cart.getProductsInCart().isEmpty());

        // Test constructor with userId
        Cart userCart = new Cart("user123");
        Assertions.assertNotNull(userCart.getCartId());
        Assertions.assertEquals("user123", userCart.getUserId());
        Assertions.assertEquals(0, userCart.getTotalPrice());
        Assertions.assertTrue(userCart.getProductsInCart().isEmpty());
    }

    @Test
    public void testGettersAndSetters() {
        Cart cart = new Cart();
        UUID cartId = UUID.randomUUID();
        Map<UUID, Integer> productsInCart = new HashMap<>();
        productsInCart.put(UUID.randomUUID(), 1);

        // Test setters
        cart.setCartId(cartId);
        cart.setUserId("user456");
        cart.setTotalPrice(200.0);
        cart.setProductsInCart(productsInCart);

        // Test getters
        Assertions.assertEquals(cartId, cart.getCartId());
        Assertions.assertEquals("user456", cart.getUserId());
        Assertions.assertEquals(200.0, cart.getTotalPrice());
        Assertions.assertEquals(productsInCart, cart.getProductsInCart());
    }
}