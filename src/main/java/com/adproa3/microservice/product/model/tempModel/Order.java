package com.adproa3.microservice.product.model.tempModel;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "temp_orders")
@Data
@NoArgsConstructor
@Generated
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    private String userId;
    private String name;
    private String address;
    private double totalPrice;

    @ElementCollection
    @CollectionTable(name = "temp_order_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id", columnDefinition = "uuid")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;

    public Order(String userId, String name, String address, double totalPrice, Map<UUID, Integer> products) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.totalPrice = totalPrice;
        this.products = products;
    }
}