package com.adproa3.microservice.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private String productDescription;
    private int productStock;
    private double productPrice;
    private double productDiscount;
    private int productDiscountDaysLeft;
    private String productPictureUrl;
}