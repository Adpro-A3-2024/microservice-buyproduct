package com.adproa3.microservice.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Generated
public class Product {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @NotNull
    private UUID productId;

    @NotNull(message = "Nama produk tidak boleh bernilai null.")
    @NotBlank(message = "Nama produk tidak boleh kosong.")
    private String productName;

    @NotNull(message = "Harga produk tidak boleh bernilai null.")
    @NotBlank(message = "Harga produk tidak boleh kosong.")
    private double productPrice;

    @NotNull(message = "Stok tidak boleh bernilai null.")
    @NotBlank(message = "Stok tidak boleh kosong.")
    private int productStock;

    private double productDiscount = 0.0;
    private int productDiscountDaysLeft = 0;
    private String productPictureUrl;

    public Product(String productName, double productPrice, int productStock, double productDiscount, int productDiscountDaysLeft, String productPictureUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDiscount = productDiscount;
        this.productDiscountDaysLeft = productDiscountDaysLeft;
        this.productPictureUrl = productPictureUrl;
    }
}