package com.adproa3.microservice.product.service;


import com.adproa3.microservice.product.model.tempModel.DTO.SetProductDiscountDTO;
import com.adproa3.microservice.product.model.tempModel.Product;
import lombok.Generated;

import java.util.List;
import java.util.UUID;
@Generated
public interface ProductService {
    public Product createProduct(Product product);
    public List<Product> getAllProducts();
    public Product editProduct(Product product);
    public Product findOneById(UUID id);
    public List<Product> searchProducts(String query);
    public Product setProductDiscount(SetProductDiscountDTO setProductDiscountDTO);
}