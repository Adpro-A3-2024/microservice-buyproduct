package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    Product findById(String productId);
    Product update(String productId, Product product);
    Product deleteProductById(String productId);
}