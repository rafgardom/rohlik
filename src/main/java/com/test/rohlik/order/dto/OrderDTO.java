package com.test.rohlik.order.dto;

import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.common.dto.ProductXOrderDTO;
import com.test.rohlik.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private UUID id;
    private BaseAuditory baseAuditory;

    private List<ProductXOrderDTO> productXOrderDTOList;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.baseAuditory = order.getBaseAuditory();
    }
}
