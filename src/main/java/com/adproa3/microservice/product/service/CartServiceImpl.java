package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.repository.CartRepository;
import com.adproa3.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart findByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart addProductToCart(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart(userId);
        }
        Map<String, Integer> productsInCart = cart.getProductsInCart();
        productsInCart.put(productId, productsInCart.getOrDefault(productId, 0) + quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Map<String, Integer> productsInCart = cart.getProductsInCart();
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
            Map<String, Integer> productsInCart = cart.getProductsInCart();
            for (Map.Entry<String, Integer> entry : productsInCart.entrySet()) {
                Product product = productRepository.findById(entry.getKey());
                if (product != null) {
                    totalPrice += product.getProductPrice() * entry.getValue();
                }
            }
        }
        return totalPrice;
    }

    @Override
    public Cart updateProductQuantity(String userId, String productId, int newQuantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Map<String, Integer> productsInCart = cart.getProductsInCart();
            if (productsInCart.containsKey(productId)) {
                productsInCart.put(productId, newQuantity);
                return cartRepository.save(cart);
            }
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAllProductsInCart(String userId) {
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
