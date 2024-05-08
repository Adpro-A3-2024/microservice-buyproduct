package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;

import java.util.Map;


public interface CartService {
    public Cart findByUserId(String userId);
    public Cart saveCart(Cart cart);
    public Cart addProductToCart(String userId, String productId, int quantity);
    public Cart removeProductFromCart(String userId, String productId);
    public Cart clearCart(String userId);
    public double getTotalPrice(String userId);
    public Cart updateProductQuantity(String userId, String productId, int newQuantity);
    public Map<String, Integer> getAllProductsInCart(String userId);
    public boolean isCartEmpty(String userId);
}
