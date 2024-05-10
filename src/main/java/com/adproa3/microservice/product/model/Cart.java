package com.adproa3.microservice.product.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table(name = "carts")
@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    private UUID cartId;

    private String userId;
    private double totalPrice;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id", columnDefinition = "uuid")
    @Column(name = "quantity")
    private Map<UUID, Integer> productsInCart = new HashMap<>();

    public Cart(String userId) {
        this.setCartId(UUID.randomUUID());
        this.userId = userId;
        this.setProductsInCart(new HashMap<>());
        this.setTotalPrice(0);
    }
}
