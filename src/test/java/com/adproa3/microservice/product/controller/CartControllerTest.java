package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;
import com.adproa3.microservice.product.model.tempModel.Product;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    void testGetCartById_CatchBlock() {
        String userId = "user123";

        when(cartService.findByUserId(userId)).thenThrow(new RuntimeException("Failed to get cart"));

        CompletableFuture<ResponseEntity<?>> response = cartController.getCartById(userId);

        verify(cartService, times(1)).findByUserId(userId);
        verifyNoMoreInteractions(cartService);
        verifyNoInteractions(cartObservable);
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
    void testAddProductToCart_CatchBlock() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        int quantity = 2;

        when(cartService.addProductToCart(userId, productId, quantity)).thenThrow(new RuntimeException("Failed to add product to cart"));

        CompletableFuture<ResponseEntity<?>> response = cartController.addProductToCart(userId, productId, quantity);

        verify(cartService, times(1)).addProductToCart(userId, productId, quantity);
        verifyNoMoreInteractions(cartService);
        verifyNoInteractions(cartObservable);
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

    @Test
    void testRemoveProductFromCart_CatchBlock() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();

        when(cartService.removeProductFromCart(userId, productId)).thenThrow(new RuntimeException("Failed to remove product from cart"));

        CompletableFuture<ResponseEntity<?>> response = cartController.removeProductFromCart(userId, productId);

        verify(cartService, times(1)).removeProductFromCart(userId, productId);
        verifyNoMoreInteractions(cartService);
        verifyNoInteractions(cartObservable);
    }

    @Test
    void testClearCart_CatchBlock() {
        String userId = "user123";

        when(cartService.clearCart(userId)).thenThrow(new RuntimeException("Failed to clear cart"));

        CompletableFuture<ResponseEntity<?>> response = cartController.clearCart(userId);

        verify(cartService, times(1)).clearCart(userId);
        verifyNoMoreInteractions(cartService);
        verifyNoInteractions(cartObservable);
    }

    @Test
    void testClearCart() {
        String userId = "user123";
        Cart clearedCart = new Cart(userId);
        when(cartService.clearCart(userId)).thenReturn(clearedCart);

        CompletableFuture<ResponseEntity<?>> response = cartController.clearCart(userId);

        verify(cartService, times(1)).clearCart(userId);
        verify(cartObservable, times(1)).notifyObservers(clearedCart);
        verifyNoMoreInteractions(cartService);
        verifyNoMoreInteractions(cartObservable);
    }

    @Test
    void testCheckout() {
        String userId = "user123";
        String name = "John Doe";
        String address = "123 Main Street";

        Order order = new Order();
        when(cartService.checkout(userId, name, address)).thenReturn(order);

        CompletableFuture<ResponseEntity<?>> response = cartController.checkout(userId, name, address);

        verify(cartService, times(1)).checkout(userId, name, address);
        verify(cartObservable, times(1)).notifyObservers(order);
        verifyNoMoreInteractions(cartService);
        verifyNoMoreInteractions(cartObservable);
    }

    @Test
    void testCheckout_CatchBlock() {
        String userId = "user123";
        String name = "John Doe";
        String address = "123 Main Street";

        when(cartService.checkout(userId, name, address)).thenThrow(new RuntimeException("Checkout failed"));

        CompletableFuture<ResponseEntity<?>> response = cartController.checkout(userId, name, address);

        verify(cartService, times(1)).checkout(userId, name, address);
        verifyNoMoreInteractions(cartService);
        verifyNoInteractions(cartObservable);
    }
}
