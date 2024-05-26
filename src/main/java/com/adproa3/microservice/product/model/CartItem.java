//package com.adproa3.microservice.product.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonValue;
//import com.adproa3.microservice.product.model.tempModel.Order;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "cart_item")
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter @Getter
//public class CartItem {
//    @Id
//    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
//    @NotNull
//    private UUID cartItemId;
//
//    private UUID productId;
//    private String productName;
//    private double productPrice;
//    private int productStock;
//    private double productDiscount = 0.0;
//    private int productDiscountDaysLeft = 0;
//    private String productPictureUrl;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    @JsonIgnore // To prevent back-reference serialization
//    private Order orderId;
//
//    public CartItem(UUID productId, String productName, double productPrice, int productStock, double productDiscount, int productDiscountDaysLeft, String productPictureUrl) {
//        this.productId = productId;
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.productStock = productStock;
//        this.productDiscount = productDiscount;
//        this.productDiscountDaysLeft = productDiscountDaysLeft;
//        this.productPictureUrl = productPictureUrl;
//    }
//
//    public void setOrder(Order order) {
//    }
//}
