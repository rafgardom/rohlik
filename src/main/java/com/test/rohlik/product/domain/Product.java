package com.test.rohlik.product.domain;

import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private long stock;
    private double price;

    @Embedded
    private BaseAuditory baseAuditory;

    public Product(ProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.stock = dto.getStock();
        this.price = dto.getPrice();
        this.baseAuditory = dto.getBaseAuditory() != null ? dto.getBaseAuditory() : new BaseAuditory();
    }
}
