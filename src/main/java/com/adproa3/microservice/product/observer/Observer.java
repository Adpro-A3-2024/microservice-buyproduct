package com.adproa3.microservice.product.observer;

import com.adproa3.microservice.product.model.Cart;

public interface Observer {
    void update(Cart cart);
}
