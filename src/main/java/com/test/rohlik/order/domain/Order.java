package com.test.rohlik.order.domain;

import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.order.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private UUID id;
    @Column(columnDefinition = "boolean default false")
    private boolean paid;

    @Embedded
    private BaseAuditory baseAuditory;

    public Order(OrderDTO dto) {
        this.id = dto.getId();
        this.baseAuditory = dto.getBaseAuditory();
    }
}
