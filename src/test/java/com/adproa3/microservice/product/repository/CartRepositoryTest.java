package com.adproa3.microservice.product.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUserId() {
        String userId = "user-1";
        Cart cart = new Cart(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        Cart foundCart = cartService.findByUserId(userId);

        assertNotNull(foundCart);
        assertEquals(userId, foundCart.getUserId());
    }
}
