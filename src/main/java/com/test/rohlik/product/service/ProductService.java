package com.test.rohlik.product.service;

import com.test.rohlik.common.domain.ProductXOrder;
import com.test.rohlik.order.domain.Order;
import com.test.rohlik.order.util.OrderAction;
import com.test.rohlik.product.domain.Product;
import com.test.rohlik.product.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product findById(UUID id);
    List<Product> findAll();
    Product createProduct(ProductDTO productDTO);
    void updateProductStatus(Order order, List<String> errorList, OrderAction orderAction);
    void deleteProduct(UUID productId);
    Product update(ProductDTO productDTO);
    void saveAllProductXOrder(List<ProductXOrder> productXOrderList);
}
