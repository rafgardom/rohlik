package com.test.rohlik.common.repository;

import com.test.rohlik.common.domain.ProductXOrder;
import com.test.rohlik.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductXOrderRepository extends JpaRepository<ProductXOrder, UUID> {

    @Query("select pxo from ProductXOrder pxo where pxo.order.id = ?1")
    List<ProductXOrder> findProductsByOrder(UUID orderId);
}
