package com.adproa3.microservice.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table(name = "carts")
@Entity
@Getter @Setter
@NoArgsConstructor
public class Cart {
    @Id
    private UUID cartId;

    private String userId;
    private double totalPrice;

    @ElementCollection
    @CollectionTable(name = "isi_cart", joinColumns = @JoinColumn(name = "cart_id"))
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