package com.test.rohlik.product.dto;

import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private UUID id;
    private String name;
    private long stock;
    private double price;

    private BaseAuditory baseAuditory;

    public ProductDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.stock = product.getStock();
        this.price = product.getPrice();
        this.baseAuditory = product.getBaseAuditory();
    }
}
