package com.adproa3.microservice.product.observable;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Order;
import com.adproa3.microservice.product.observer.Observer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class CartObservableTest {

    @Test
    void testNotifyObservers_Cart() {
        Cart cart = new Cart();

        Observer cartObserver1 = Mockito.mock(Observer.class);
        Observer cartObserver2 = Mockito.mock(Observer.class);

        CartObservable cartObservable = new CartObservable();

        cartObservable.addObserver(cartObserver1);
        cartObservable.addObserver(cartObserver2);

        cartObservable.notifyObservers(cart);

        verify(cartObserver1, times(1)).update(cart);
        verify(cartObserver2, times(1)).update(cart);
    }

    @Test
    void testNotifyObservers_Order() {
        Order order = new Order();

        Observer orderObserver1 = Mockito.mock(Observer.class);
        Observer orderObserver2 = Mockito.mock(Observer.class);

        CartObservable cartObservable = new CartObservable();

        cartObservable.addObserver(orderObserver1);
        cartObservable.addObserver(orderObserver2);

        cartObservable.notifyObservers(order);

        verify(orderObserver1, times(1)).update(order);
        verify(orderObserver2, times(1)).update(order);
    }

    @Test
    void testRemoveObserver() {
        Observer observer = Mockito.mock(Observer.class);

        CartObservable cartObservable = new CartObservable();
        cartObservable.addObserver(observer);

        cartObservable.removeObserver(observer);

        cartObservable.notifyObservers(new Cart());

        verify(observer, never()).update(any());
    }
}
