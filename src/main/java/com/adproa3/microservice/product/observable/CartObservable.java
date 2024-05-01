package com.adproa3.microservice.product.observable;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.observer.Observer;

import java.util.ArrayList;
import java.util.List;

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
}