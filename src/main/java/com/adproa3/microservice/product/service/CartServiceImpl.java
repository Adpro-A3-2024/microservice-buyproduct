package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.repository.CartRepository;
import com.adproa3.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart findByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart addProductToCart(String userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart(userId);
        }
        Map<UUID, Integer> productsInCart = cart.getProductsInCart();
        productsInCart.put(productId, productsInCart.getOrDefault(productId, 0) + quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(String userId, UUID productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Map<UUID, Integer> productsInCart = cart.getProductsInCart();
            productsInCart.remove(productId);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public Cart clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getProductsInCart().clear();
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public double getTotalPrice(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        double totalPrice = 0;
        if (cart != null) {
            Map<UUID, Integer> productsInCart = cart.getProductsInCart();
            for (Map.Entry<UUID, Integer> entry : productsInCart.entrySet()) {
                Product product = productRepository.getReferenceById(entry.getKey());
                totalPrice += product.getProductPrice() * entry.getValue();
            }
        }
        return totalPrice;
    }

    @Override
    public Cart updateProductQuantity(String userId, UUID productId, int newQuantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Map<UUID, Integer> productsInCart = cart.getProductsInCart();
            if (productsInCart.containsKey(productId)) {
                productsInCart.put(productId, newQuantity);
                return cartRepository.save(cart);
            }
        }
        return null;
    }

    @Override
    public Map<UUID, Integer> getAllProductsInCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return cart.getProductsInCart();
        }
        return null;
    }

    @Override
    public boolean isCartEmpty(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cart == null || cart.getProductsInCart().isEmpty();
    }
}
