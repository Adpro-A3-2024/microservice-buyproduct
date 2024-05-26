package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Cart;
import com.adproa3.microservice.product.model.tempModel.Product;
import com.adproa3.microservice.product.model.tempModel.Order;
import com.adproa3.microservice.product.repository.CartRepository;
import com.adproa3.microservice.product.repository.ProductRepository;
import com.adproa3.microservice.product.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUserId_CartExists() {
        String userId = "user123";
        Cart expectedCart = new Cart(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(expectedCart);

        Cart actualCart = cartService.findByUserId(userId);
        assertEquals(expectedCart, actualCart);
    }

    @Test
    void testFindByUserId_CartDoesNotExist() {
        String userId = "user456";
        when(cartRepository.findByUserId(userId)).thenReturn(null);

        Cart actualCart = cartService.findByUserId(userId);
        assertNull(actualCart);
    }

    @Test
    void testFindByUserId_InvalidUserId() {
        Cart actualCart = cartService.findByUserId(null);
        assertNull(actualCart);
    }

    @Test
    void testSaveCart_ValidCart() {
        Cart cart = new Cart("user123");
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart savedCart = cartService.saveCart(cart);
        assertEquals(cart, savedCart);
    }

    @Test
    void testSaveCart_NullCart() {
        Cart savedCart = cartService.saveCart(null);
        assertNull(savedCart);
    }

    @Test
    void testAddProductToCart_CartFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        int quantity = 2;

        Cart cart = new Cart(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getProductsInCart().size());
        assertTrue(updatedCart.getProductsInCart().containsKey(productId));
        assertEquals(quantity, updatedCart.getProductsInCart().get(productId));
    }

    @Test
    void testAddProductToCart_CartNotFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        int quantity = 2;

        when(cartRepository.findByUserId(userId)).thenReturn(null);
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getProductsInCart().size());
        assertTrue(updatedCart.getProductsInCart().containsKey(productId));
        assertEquals(quantity, updatedCart.getProductsInCart().get(productId));
    }

    @Test
    void testDeleteProductFromCart_CartFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();

        Cart cart = new Cart(userId);
        Map<UUID, Integer> productsInCart = new HashMap<>();
        productsInCart.put(productId, 2);
        cart.setProductsInCart(productsInCart);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart updatedCart = cartService.deleteProductFromCart(userId, productId);

        assertNotNull(updatedCart);
        assertTrue(updatedCart.getProductsInCart().isEmpty());
    }

    @Test
    void testDeleteProductFromCart_CartNotFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();

        when(cartRepository.findByUserId(userId)).thenReturn(null);

        when(cartRepository.save(any())).thenThrow(new RuntimeException("Save should not be called"));

        Cart updatedCart = cartService.deleteProductFromCart(userId, productId);

        assertNull(updatedCart);
    }

    @Test
    void testDeleteProductFromCart_EmptyCart() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        Cart cart = new Cart(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        Cart updatedCart = cartService.deleteProductFromCart(userId, productId);
        assertNull(updatedCart);
    }

    @Test
    void testClearCart_CartFound() {
        String userId = "user123";

        Cart cart = new Cart(userId);
        Map<UUID, Integer> productsInCart = new HashMap<>();
        productsInCart.put(UUID.randomUUID(), 2);
        cart.setProductsInCart(productsInCart);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart clearedCart = cartService.clearCart(userId);

        assertNotNull(clearedCart);
        assertTrue(clearedCart.getProductsInCart().isEmpty());
    }

    @Test
    void testClearCart_CartNotFound() {
        String userId = "user123";

        when(cartRepository.findByUserId(userId)).thenReturn(null);

        when(cartRepository.save(any())).thenThrow(new RuntimeException("Save should not be called"));

        Cart clearedCart = cartService.clearCart(userId);

        assertNull(clearedCart);
    }

    @Test
    void testGetTotalPrice_NonEmptyCart() {
        String userId = "user123";
        Cart cart = new Cart(userId);
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        int quantity1 = 2;
        int quantity2 = 3;
        double price1 = 10.0;
        double price2 = 20.0;
        cart.getProductsInCart().put(productId1, quantity1);
        cart.getProductsInCart().put(productId2, quantity2);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(productRepository.getReferenceById(productId1)).thenReturn(new Product("Product 1", price1, 10, 0, 0, ""));
        when(productRepository.getReferenceById(productId2)).thenReturn(new Product("Product 2", price2, 10, 0, 0, ""));

        double totalPrice = cartService.getTotalPrice(userId);
        assertEquals(quantity1 * price1 + quantity2 * price2, totalPrice);
    }

    @Test
    void testGetTotalPrice_EmptyCart() {
        String userId = "user123";
        Cart cart = new Cart(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        double totalPrice = cartService.getTotalPrice(userId);
        assertEquals(0, totalPrice);
    }

    @Test
    void testGetTotalPrice_CartIsNull() {
        String userId = "user123";
        when(cartRepository.findByUserId(userId)).thenReturn(null);

        double totalPrice = cartService.getTotalPrice(userId);
        assertEquals(0, totalPrice);
    }

    @Test
    void testUpdateProductQuantity_CartFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        int newQuantity = 5;

        Cart cart = new Cart(userId);
        Map<UUID, Integer> productsInCart = new HashMap<>();
        productsInCart.put(productId, 2);
        cart.setProductsInCart(productsInCart);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart updatedCart = cartService.updateProductQuantity(userId, productId, newQuantity);

        assertNotNull(updatedCart);
        assertEquals(newQuantity, updatedCart.getProductsInCart().get(productId));
    }

    @Test
    void testUpdateProductQuantity_CartNotFound() {
        String userId = "user123";
        UUID productId = UUID.randomUUID();
        int newQuantity = 5;

        when(cartRepository.findByUserId(userId)).thenReturn(null);

        when(cartRepository.save(any())).thenThrow(new RuntimeException("Save should not be called"));

        Cart updatedCart = cartService.updateProductQuantity(userId, productId, newQuantity);

        assertNull(updatedCart);
    }

    @Test
    void testGetAllProductsInCart_CartExists() {
        String userId = "user123";
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        int quantity1 = 3;
        int quantity2 = 5;
        Cart cart = new Cart(userId);
        cart.getProductsInCart().put(productId1, quantity1);
        cart.getProductsInCart().put(productId2, quantity2);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        Map<UUID, Integer> productsInCart = cartService.getAllProductsInCart(userId);

        assertNotNull(productsInCart);
        assertEquals(2, productsInCart.size());
        assertEquals(quantity1, productsInCart.get(productId1));
        assertEquals(quantity2, productsInCart.get(productId2));
    }

    @Test
    void testGetAllProductsInCart_CartDoesNotExist() {
        String userId = "user123";
        when(cartRepository.findByUserId(userId)).thenReturn(null);

        Map<UUID, Integer> productsInCart = cartService.getAllProductsInCart(userId);
        assertNull(productsInCart);
    }

    @Test
    void testIsCartEmpty_CartExistsWithProducts() {
        String userId = "user123";
        Cart cart = new Cart(userId);
        cart.getProductsInCart().put(UUID.randomUUID(), 1); // Adding a product to the cart
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        boolean isEmpty = cartService.isCartEmpty(userId);
        assertFalse(isEmpty);
    }

    @Test
    void testIsCartEmpty_CartExistsWithoutProducts() {
        String userId = "user123";
        Cart cart = new Cart(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        boolean isEmpty = cartService.isCartEmpty(userId);
        assertTrue(isEmpty);
    }

    @Test
    void testIsCartEmpty_CartDoesNotExist() {
        String userId = "user123";
        when(cartRepository.findByUserId(userId)).thenReturn(null);

        boolean isEmpty = cartService.isCartEmpty(userId);
        assertTrue(isEmpty);
    }

    @Test
    void testCheckout_CartFoundAndNotEmpty() {
        String userId = "user123";
        String name = "John Doe";
        String address = "123 Main Street";

        Cart cart = new Cart(userId);
        Map<UUID, Integer> productsInCart = new HashMap<>();
        UUID productId = UUID.randomUUID();
        int quantity = 2;
        productsInCart.put(productId, quantity);
        cart.setProductsInCart(productsInCart);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        Order savedOrder = new Order(userId, name, address, 20.0, productsInCart); // Assuming total price is 20.0
        when(orderRepository.save(any())).thenReturn(savedOrder);

        Product product = new Product(productId, "Test Product", 10.0, 100, 0, 0, "https://localhost");
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        when(cartRepository.save(any())).thenReturn(cart);

        Order order = cartService.checkout(userId, name, address);

        assertNotNull(order);
        assertEquals(userId, order.getUserId());
        assertEquals(name, order.getName());
        assertEquals(address, order.getAddress());
        assertEquals(20.0, order.getTotalPrice());
        assertEquals(productsInCart, order.getProducts());
    }

    @Test
    void testCheckout_CartNotFound() {
        String userId = "user123";
        String name = "John Doe";
        String address = "123 Main Street";

        when(cartRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> cartService.checkout(userId, name, address));
    }

    @Test
    void testCheckout_CartEmpty() {
        String userId = "user123";
        String name = "John Doe";
        String address = "123 Main Street";

        Cart cart = new Cart(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        assertThrows(IllegalStateException.class, () -> cartService.checkout(userId, name, address));
    }
}
