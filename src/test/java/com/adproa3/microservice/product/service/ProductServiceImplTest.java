package com.adproa3.microservice.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    List<Product> products;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        products.add(createProduct1());
        products.add(createProduct2());
    }

    Product createProduct1() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Gojo Satoru 2x2");
        product.setProductDescription("A picture of Gojo Satoru size 2x2 cm");
        product.setProductPrice(1000000);
        product.setProductStock(50);
        product.setProductDiscount(0);
        product.setProductDiscountDaysLeft(0);
        product.setProductPictureUrl("https://url.com");
        productRepository.create(product);
        return product;
    }

    Product createProduct2() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product.setProductName("Gojo Satoru 2x3");
        product.setProductDescription("A picture of Gojo Satoru size 2x3 cm");
        product.setProductPrice(1500000);
        product.setProductStock(10);
        product.setProductDiscount(0);
        product.setProductDiscountDaysLeft(0);
        product.setProductPictureUrl("https://url2.com");
        productRepository.create(product);
        return product;
    }

    @Test
    void testCreateProduct() {
        Product product = createProduct1();

        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);

        assertEquals("Gojo Satoru 2x2", createdProduct.getProductName());
        assertEquals("A picture of Gojo Satoru size 2x2 cm", createdProduct.getProductDescription());
        assertEquals(1000000, createdProduct.getProductPrice());
        assertEquals(50, createdProduct.getProductStock());
        assertEquals(0, createdProduct.getProductDiscount());
        assertEquals(0, createdProduct.getProductDiscountDaysLeft());
        assertEquals("https://url.com", createdProduct.getProductPictureUrl());
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(products.iterator());
        List<Product> foundProducts = productService.findAll();

        assertEquals(2, foundProducts.size());
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");

        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);
        Product foundProduct = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(foundProduct);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", foundProduct.getProductId());
    }

    @Test
    void testUpdateProduct() {
        Product product = createProduct1();

        product.setProductName("Updated Product Name");
        product.setProductStock(10);

        when(productRepository.update(product.getProductId(), product)).thenReturn(product);
        Product updatedProduct = productService.update(product.getProductId(), product);

        assertEquals("Updated Product Name", updatedProduct.getProductName());
        assertEquals(10, updatedProduct.getProductStock());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductStock(5);

        when(productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);
        Product deletedProduct = productService.deleteProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(deletedProduct);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", deletedProduct.getProductId());
    }
}
