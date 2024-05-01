package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }

    @Override
    public Cart findCartById(String cartId) {
        return cartRepository.findCartById(cartId);
    }

    @Override
    public Cart addProductToCart(String cartId, Product product, int quantity) {
        return cartRepository.addProductToCart(cartId, product, quantity);
    }

    @Override
    public Cart removeProductFromCart(String cartId, Product product) {
        return cartRepository.removeProductFromCart(cartId, product);
    } 
}
