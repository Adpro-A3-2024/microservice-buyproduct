package com.adproa3.microservice.product.observer;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;

public class CartUpdateNotification implements Observer {
    public void update(Object object) {
        if (object instanceof Cart cart) {
            System.out.println("Keranjang belanja telah diperbarui. Total harga: " + cart.getTotalPrice());
        }
        if (object instanceof Order order) {
            System.out.println("Order telah dibuat. Total harga: " + order.getTotalPrice());
        }
    }
}