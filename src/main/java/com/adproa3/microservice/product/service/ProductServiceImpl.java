package com.adproa3.microservice.product.service;

import com.adproa3.microservice.product.model.DTO.SetProductDiscountDTO;
import com.adproa3.microservice.product.model.Product;
import com.adproa3.microservice.product.repository.ProductRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service @Generated
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product editProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product findOneById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByProductNameContainingIgnoreCase(query);
    }

    @Override
    public Product setProductDiscount(SetProductDiscountDTO setProductDiscountDTO) {
        Product productToBeEdited = productRepository.getReferenceById(setProductDiscountDTO.getProductId());
        productToBeEdited.setProductDiscount(setProductDiscountDTO.getDiscount());
        productToBeEdited.setProductDiscountDaysLeft(setProductDiscountDTO.getDiscountDays());
        productRepository.save(productToBeEdited);
        return productToBeEdited;
    }
}