package com.adproa3.microservice.product.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class Cart {
    private String cartId;
    private String userId;
    private List<Product> products;
}
