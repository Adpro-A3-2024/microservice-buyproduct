package com.adproa3.microservice.product.repository;

import com.adproa3.microservice.product.model.tempModel.Order;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository @Generated
public interface OrderRepository extends JpaRepository<Order, UUID> {
}