package com.adproa3.microservice.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Gojo Satoru 2x2");
        this.product.setProductDescription("A picture of Gojo Satoru");
        this.product.setProductPrice(1000000);
        this.product.setProductStock(20);
        this.product.setProductDiscount(30);
        this.product.setProductDiscountDaysLeft(7);
        this.product.setProductPictureUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2G7MHJ7ZWbTEmd1pUpsJ4KU25uDJvJNW0XzpF58PnrA&s");
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Gojo Satoru 2x2", this.product.getProductName());
    }

    @Test
    void testGetProductDescription() {
        assertEquals("A picture of Gojo Satoru", this.product.getProductDescription());
    }

    @Test
    void testGetProductStock() {
        assertEquals(20, this.product.getProductStock());
    }

    @Test
    void testGetProductDiscount() {
        assertEquals(30, this.product.getProductDiscount());
    }

    @Test
    void testGetProductDiscountDaysLeft() {
        assertEquals(7, this.product.getProductDiscountDaysLeft());
    }

    @Test
    void testGetProductPrice() {
        assertEquals(1000000, this.product.getProductPrice());
    }

    @Test
    void testGetProductPictureUrl() {
        assertEquals("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2G7MHJ7ZWbTEmd1pUpsJ4KU25uDJvJNW0XzpF58PnrA&s", this.product.getProductPictureUrl());
    }
}
