package com.adproa3.microservice.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter @Data
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId;

    private UUID cartId;
    private String userId;
    private double totalPrice;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<String, Integer> productsInCart = new HashMap<>();

    public Cart(String userId) {
        this.userId = userId;
        generateId();
    }

    @PrePersist
    public void generateId() {
        if (this.cartId == null) {
            this.cartId = UUID.randomUUID();
        }
    }
}
