package com.adproa3.microservice.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter @Data
public class Cart {
    private String cartId;
    private String userId;
    private Map<Product, Integer> products;
    private double totalPrice;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Cart(String userId) {
        this.userId = userId;
        this.products = new HashMap();
        this.totalPrice = 0;
    }
}
