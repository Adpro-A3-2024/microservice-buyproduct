package com.adproa3.microservice.product.model.tempModel.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;

import java.util.UUID;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Generated
public class SetProductDiscountDTO {
    private UUID productId;
    private double discount;
    private int discountDays;
}