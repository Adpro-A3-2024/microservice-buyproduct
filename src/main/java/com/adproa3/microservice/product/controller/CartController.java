package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    private CartObservable cartObservable;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartById(@PathVariable String userId) {
        try {
            Cart cart = cartService.findByUserId(userId);
            if (cart != null) {
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get cart: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}/addProduct")
    public ResponseEntity<?> addProductToCart(@PathVariable String userId, @RequestBody UUID productId, @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);
            cartObservable.notifyObservers(updatedCart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add product to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/removeProduct")
    public ResponseEntity<?> removeProductFromCart(@PathVariable String userId, @RequestBody UUID productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(userId, productId);
            cartObservable.notifyObservers(updatedCart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage());
        }
    }
}