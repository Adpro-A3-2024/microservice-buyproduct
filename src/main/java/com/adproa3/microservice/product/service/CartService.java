package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;


public interface CartService {
    public Cart create(Cart cart);
    public Cart findCartById(String cartId);
    public Cart addProductToCart(String cartId, Product product, int quantity);
    public Cart removeProductFromCart(String cartId, Product product);
}
