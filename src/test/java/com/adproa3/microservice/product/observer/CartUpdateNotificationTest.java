package com.adproa3.microservice.product.observer;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartUpdateNotificationTest {

    @Test
    void testUpdate_CartObject() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Cart cart = new Cart();
        cart.setTotalPrice(200.0);

        CartUpdateNotification notification = new CartUpdateNotification();
        notification.update(cart);

        String expectedOutput = "Keranjang belanja telah diperbarui. Total harga: 200.0";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
        System.setOut(System.out);
    }

    @Test
    void testUpdate_OrderObject() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Order order = new Order();
        order.setTotalPrice(300.0);

        CartUpdateNotification notification = new CartUpdateNotification();

        notification.update(order);

        String expectedOutput = "Order telah dibuat. Total harga: 300.0";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
        System.setOut(System.out);
    }
}