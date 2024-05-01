package com.adproa3.microservice.product.repository;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
    private List<Cart> cartData = new ArrayList<>();

    public Cart create(Cart cart) {
        if (cart.getCartId() == null) {
            UUID uuid = UUID.randomUUID();
            cart.setCartId(uuid.toString());
        }
        cartData.add(cart);
        return cart;
    }

    public Cart findCartById(String cartId) {
        for (Cart cart : cartData) {
            if (cart.getCartId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }
    
    public Cart addProductToCart(String cartId, Product product, int quantity) {
        for (Cart cart : cartData) {
            if (cart.getCartId().equals(cartId)) {
                if (cart.getProducts().containsKey(product)) {
                    cart.getProducts().replace(product, cart.getProducts().get(product) + quantity);
                } else {
                    cart.getProducts().put(product, quantity);
                }
                recalculateTotalPrice(cart);
                return cart;
            }
        }
        return null;
    }

    public Cart removeProductFromCart(String cartId, Product product) {
        for (Cart cart : cartData) {
            if (cart.getCartId().equals(cartId)) {
                if (cart.getProducts().containsKey(product)) {
                    cart.getProducts().remove(product);
                }
                recalculateTotalPrice(cart);
                return cart;
            }
        }
        return null;
    }

    private void recalculateTotalPrice(Cart cart) {
        double totalPrice = 0;
        for (Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            totalPrice += entry.getKey().getProductPrice() * entry.getValue();
        }
        cart.setTotalPrice(totalPrice);
    }
}