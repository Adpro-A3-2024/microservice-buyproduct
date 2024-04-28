package com.adproa3.microservice.product.repository;

import com.adproa3.microservice.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
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
    void testCreateAndFind() {
        Product product = createProduct1();

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductDescription(), savedProduct.getProductDescription());
        assertEquals(product.getProductPrice(), savedProduct.getProductPrice());
        assertEquals(product.getProductStock(), savedProduct.getProductStock());
        assertEquals(product.getProductDiscount(), savedProduct.getProductDiscount());
        assertEquals(product.getProductDiscountDaysLeft(), savedProduct.getProductDiscountDaysLeft());
        assertEquals(product.getProductPictureUrl(), savedProduct.getProductPictureUrl());

    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = createProduct1();
        Product product2 = createProduct2();

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdProduct() {
        Product product = createProduct1();

        Product foundProduct = productRepository.findById(product.getProductId());
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
        assertEquals(product.getProductDescription(), foundProduct.getProductDescription());
        assertEquals(product.getProductPrice(), foundProduct.getProductPrice());
        assertEquals(product.getProductStock(), foundProduct.getProductStock());
        assertEquals(product.getProductDiscount(), foundProduct.getProductDiscount());
        assertEquals(product.getProductDiscountDaysLeft(), foundProduct.getProductDiscountDaysLeft());
        assertEquals(product.getProductPictureUrl(), foundProduct.getProductPictureUrl());
    }

    @Test
    void testEditProduct() {
        Product product = createProduct1();

        Product updatedProduct = getUpdatedProduct();
        productRepository.update(product.getProductId(), updatedProduct);

        Product retrievedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(retrievedProduct);
        assertEquals("Gojo Satoru 2x3", retrievedProduct.getProductName());
        assertEquals("A picture of Gojo Satoru size 2x3 cm", retrievedProduct.getProductDescription());
        assertEquals(1500000, retrievedProduct.getProductPrice());
        assertEquals(10, retrievedProduct.getProductStock());
        assertEquals(0, retrievedProduct.getProductDiscount());
        assertEquals(0, retrievedProduct.getProductDiscountDaysLeft());
        assertEquals("https://url2.com", retrievedProduct.getProductPictureUrl());
    }

    private Product getUpdatedProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Gojo Satoru 2x3");
        updatedProduct.setProductDescription("A picture of Gojo Satoru size 2x3 cm");
        updatedProduct.setProductPrice(1500000);
        updatedProduct.setProductStock(10);
        updatedProduct.setProductDiscount(0);
        updatedProduct.setProductDiscountDaysLeft(0);
        updatedProduct.setProductPictureUrl("https://url2.com");
        return updatedProduct;
    }

    @Test
    void testFindByIdProductIfDoesNotExist() {
        createProduct1();

        createProduct2();

        String randomId = UUID.randomUUID().toString();

        Product findedProduct = productRepository.findById(randomId);
        assertNull(findedProduct);
    }

    @Test
    void testDeleteAndFindByIdProduct() {
        Product product = createProduct1();

        Product deletedProduct = productRepository.delete(product.getProductId());
        assertEquals(product.getProductId(), deletedProduct.getProductId());
        assertEquals(product.getProductName(), deletedProduct.getProductName());
        assertEquals(product.getProductDescription(), deletedProduct.getProductDescription());
        assertEquals(product.getProductPrice(), deletedProduct.getProductPrice());
        assertEquals(product.getProductStock(), deletedProduct.getProductStock());
        assertEquals(product.getProductDiscount(), deletedProduct.getProductDiscount());
        assertEquals(product.getProductDiscountDaysLeft(), deletedProduct.getProductDiscountDaysLeft());
        assertEquals(product.getProductPictureUrl(), deletedProduct.getProductPictureUrl());

        Product deletedProductIfSearch = productRepository.findById(product.getProductId());
        assertNull(deletedProductIfSearch);
    }

    @Test
    void testDeleteProductIfEmpty() {
        String randomId = UUID.randomUUID().toString();

        Product deletedProduct = productRepository.delete(randomId);
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProductIfDoesNotExist() {
        createProduct1();

        createProduct2();

        String randomId = UUID.randomUUID().toString();

        Product findedProduct = productRepository.delete(randomId);
        assertNull(findedProduct);
    }
}
