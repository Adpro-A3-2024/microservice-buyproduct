package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartObservable cartObservable;

    @InjectMocks
    private CartController cartController;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("Test Product", 10.0, 100, 0.0, 0, "");
    }

    @Test
    void testGetCartById() {
        String userId = "user1";
        Cart cart = new Cart(userId);
        when(cartService.findByUserId(userId)).thenReturn(cart);

        CompletableFuture<ResponseEntity<?>> response = cartController.getCartById(userId);

        assertEquals(ResponseEntity.ok(cart), response.join());
        verify(cartService, times(1)).findByUserId(userId);
    }

    @Test
    void testGetCartByIdNotFound() {
        String userId = "user1";
        when(cartService.findByUserId(userId)).thenReturn(null);

        CompletableFuture<ResponseEntity<?>> response = cartController.getCartById(userId);

        assertEquals(ResponseEntity.notFound().build(), response.join());
        verify(cartService, times(1)).findByUserId(userId);
    }

    @Test
    void testAddProductToCart() {
        String userId = "user1";
        UUID productId = product.getProductId();
        int quantity = 2;
        Cart cart = new Cart(userId);
        Map<UUID, Integer> productsInCart = new HashMap<>();
        productsInCart.put(productId, quantity);
        cart.setProductsInCart(productsInCart);
        when(cartService.addProductToCart(userId, productId, quantity)).thenReturn(cart);

        CompletableFuture<ResponseEntity<?>> response = cartController.addProductToCart(userId, productId, quantity);

        assertEquals(ResponseEntity.ok(cart), response.join());
        verify(cartService, times(1)).addProductToCart(userId, productId, quantity);
        verify(cartObservable, times(1)).notifyObservers(cart);
    }

    @Test
    void testRemoveProductFromCart() {
        String userId = "user1";
        UUID productId = product.getProductId();
        Cart cart = new Cart(userId);
        when(cartService.removeProductFromCart(userId, productId)).thenReturn(cart);

        CompletableFuture<ResponseEntity<?>> response = cartController.removeProductFromCart(userId, productId);

        assertEquals(ResponseEntity.ok(cart), response.join());
        verify(cartService, times(1)).removeProductFromCart(userId, productId);
        verify(cartObservable, times(1)).notifyObservers(cart);
    }
}
