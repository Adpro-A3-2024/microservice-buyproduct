package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@EnableAsync
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartObservable cartObservable;

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

    @Async
    @PostMapping("/{userId}/add-product")
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
    @DeleteMapping("/{userId}/remove-product")
    public CompletableFuture<ResponseEntity<?>> removeProductFromCart(@PathVariable String userId, @RequestBody UUID productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(userId, productId);
            cartObservable.notifyObservers(updatedCart);
            return CompletableFuture.completedFuture(ResponseEntity.ok(updatedCart));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage()));
        }
    }

    @Async
    @DeleteMapping("/{userId}/clear")
    public CompletableFuture<ResponseEntity<?>> clearCart(@PathVariable String userId) {
        try {
            Cart clearedCart = cartService.clearCart(userId);
            cartObservable.notifyObservers(clearedCart);
            return CompletableFuture.completedFuture(ResponseEntity.ok(clearedCart));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to clear cart: " + e.getMessage()));
        }
    }

    @Async
    @PostMapping("/{userId}/checkout")
    public CompletableFuture<ResponseEntity<?>> checkout(@PathVariable String userId, @RequestParam String name, @RequestParam String address) {
        try {
            Order order = cartService.checkout(userId, name, address);
            cartObservable.notifyObservers(order);
            return CompletableFuture.completedFuture(ResponseEntity.ok(order));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Failed to checkout: " + e.getMessage()));
        }
    }
}