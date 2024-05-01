package com.adproa3.microservice.product.observer;

import com.adproa3.microservice.product.model.Cart;

public class CartUpdateNotification implements Observer {
    @Override
    public void update(Cart cart) {
        // Kirim ke pengguna
        System.out.println("Keranjang belanja telah diperbarui. Total harga: " + cart.getTotalPrice());
    }
}