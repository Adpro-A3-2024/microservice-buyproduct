package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.annotation.Timed;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartObservable cartObservable;

    @Timed
    @Async
    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<?>> getCartById(@PathVariable String userId) {
        try {
            Cart cart = cartService.findByUserId(userId);
            if (cart != null) {
                return CompletableFuture.completedFuture(ResponseEntity.ok(cart));
            } else {
                return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
            }
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to get cart: " + e.getMessage()));
        }
    }

    @Timed
    @Async
    @PostMapping("/{userId}/addProduct")
    public CompletableFuture<ResponseEntity<?>> addProductToCart(@PathVariable String userId, @RequestBody UUID productId, @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);
            cartObservable.notifyObservers(updatedCart);
            return CompletableFuture.completedFuture(ResponseEntity.ok(updatedCart));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to add product to cart: " + e.getMessage()));
        }
    }


    @Async
    @DeleteMapping("/{userId}/removeProduct")
    public CompletableFuture<ResponseEntity<?>> removeProductFromCart(@PathVariable String userId, @RequestBody UUID productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(userId, productId);
            cartObservable.notifyObservers(updatedCart);
            return CompletableFuture.completedFuture(ResponseEntity.ok(updatedCart));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage()));
        }
    }
}