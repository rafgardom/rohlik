package com.test.rohlik.common.domain;

import com.test.rohlik.order.domain.Order;
import com.test.rohlik.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class ProductXOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_x_order_id")
    private UUID id;
    @Column(nullable = false)
    private long quantity;

    @Embedded
    private BaseAuditory baseAuditory;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(targetEntity = Order.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public ProductXOrder(){
        this.baseAuditory = new BaseAuditory();
    }
}
