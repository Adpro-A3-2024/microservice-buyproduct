package com.adproa3.microservice.product.controller;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.observable.CartObservable;
import com.adproa3.microservice.product.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartObservable cartObservable;

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody Cart cart) {
        try {
            Cart createdCart = cartService.create(cart);
            return ResponseEntity.ok(createdCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create cart: " + e.getMessage());
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable String cartId) {
        try {
            Cart cart = cartService.findCartById(cartId);
            if (cart != null) {
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get cart: " + e.getMessage());
        }
    }

    @PostMapping("/{cartId}/addProduct")
    public ResponseEntity<?> addProductToCart(@PathVariable String cartId, @RequestBody Product product, @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.addProductToCart(cartId, product, quantity);
            cartObservable.notifyObservers(updatedCart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add product to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}/removeProduct")
    public ResponseEntity<?> removeProductFromCart(@PathVariable String cartId, @RequestBody Product product) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(cartId, product);
            cartObservable.notifyObservers(updatedCart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove product from cart: " + e.getMessage());
        }
    }
}