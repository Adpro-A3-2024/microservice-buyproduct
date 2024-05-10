package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface CartService {
    Cart findByUserId(String userId);
    Cart saveCart(Cart cart);
    Cart addProductToCart(String userId, UUID productId, int quantity);
    Cart removeProductFromCart(String userId, UUID productId);
    Cart clearCart(String userId);
    double getTotalPrice(String userId);
    Cart updateProductQuantity(String userId, UUID productId, int newQuantity);
    Map<UUID, Integer> getAllProductsInCart(String userId);
    boolean isCartEmpty(String userId);
}
