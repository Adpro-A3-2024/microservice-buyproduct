package com.adproa3.microservice.product.observable;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;
import com.adproa3.microservice.product.observer.Observer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartObservable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Cart cart) {
        for (Observer observer : observers) {
            observer.update(cart);
        }
    }

    public void notifyObservers(Order order) {
        for (Observer observer : observers) {
            observer.update(order);
        }
    }
}
