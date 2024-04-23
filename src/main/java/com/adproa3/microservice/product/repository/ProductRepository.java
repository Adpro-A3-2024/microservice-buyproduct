package com.adproa3.microservice.product.repository;

import com.adproa3.microservice.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid.toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product update(String id, Product updateProduct) {
        for (Product product : productData) {
            if (updateProduct.getProductId().equals(id)) {
                product.setProductName(updateProduct.getProductName());
                product.setProductDescription(updateProduct.getProductDescription());
                product.setProductPrice(updateProduct.getProductPrice());
                product.setProductDiscount(updateProduct.getProductDiscount());
                product.setProductDiscountDaysLeft(updateProduct.getProductDiscountDaysLeft());
                product.setProductStock(updateProduct.getProductStock());
                product.setProductPictureUrl(updateProduct.getProductPictureUrl());
                return product;
            }
        }
        return null;
    }

    public Product delete(String productId) {
        Product deletedProduct = findById(productId);
        productData.remove(deletedProduct);
        return deletedProduct;
    }
}
