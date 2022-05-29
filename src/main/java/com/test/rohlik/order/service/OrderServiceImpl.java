package com.test.rohlik.order.service;

import com.test.rohlik.common.ErrorMessage;
import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.common.domain.ProductXOrder;
import com.test.rohlik.common.dto.ProductXOrderDTO;
import com.test.rohlik.common.exception.OrderException;
import com.test.rohlik.order.domain.Order;
import com.test.rohlik.order.dto.OrderDTO;
import com.test.rohlik.order.repository.OrderRepository;
import com.test.rohlik.order.util.OrderAction;
import com.test.rohlik.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Value("${order-cancel-time}")
    private Long orderCancelTime;

    // CRUD operations

    public Order findById(UUID id) {
        Order order = this.orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new OrderException(ErrorMessage.ORDER_NOT_FOUND);
        }
        return order;
    }

    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    private Order save(Order order) {
        if (order.getBaseAuditory() == null) {
            order.setBaseAuditory(new BaseAuditory());
        }
        return this.orderRepository.save(order);
    }

    // Service operations
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order result = new Order(orderDTO);
        result.setId(null);
        result = this.save(result);
        List<ProductXOrder> productXOrderToSave = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        orderDTO.setProductXOrderDTOList(this.verifyOrderCreation(orderDTO));
        for (ProductXOrderDTO productXOrderDTO : orderDTO.getProductXOrderDTOList()) {
            ProductXOrder productXOrder = new ProductXOrder();
            productXOrder.setOrder(result);
            productXOrder.setQuantity(productXOrderDTO.getQuantity());
            productXOrder.setProduct(this.productService.findById(productXOrderDTO.getProductId()));
            productXOrderToSave.add(productXOrder);
        }
        this.productService.saveAllProductXOrder(productXOrderToSave);
        this.productService.updateProductStatus(result, errorList, OrderAction.CREATE_ORDER);
        return result;
    }

    @Transactional
    public void cancelOrder(Order order) {
        order.getBaseAuditory().setEndDate(LocalDateTime.now());
        List<String> errorList = new ArrayList<>();
        this.productService.updateProductStatus(order, errorList, OrderAction.CANCEL_ORDER);
        this.save(order);
    }

    @Transactional
    public void payOrder(UUID orderId) {
        Order order = this.findById(orderId);
        if (order.getBaseAuditory().getEndDate() != null) {
            throw new OrderException(ErrorMessage.ORDER_NOT_FOUND);
        }
        if (order.isPaid()) {
            throw new OrderException(ErrorMessage.ORDER_ALREADY_PAID);
        }
        //Verify the order was created less than 30min ahead
        LocalDateTime now = LocalDateTime.now();
        now = now.minusMinutes(orderCancelTime);
        if (order.getBaseAuditory().getCreatedDate().isBefore(now)) {
            this.cancelOrder(order);
        } else {
            order.setPaid(true);
            this.save(order);
        }

    }

    /**
     * Join multiple product appearance of an order.
     *
     * @param orderDTO
     * @return
     */
    private List<ProductXOrderDTO> verifyOrderCreation(OrderDTO orderDTO) {
        List<ProductXOrderDTO> result = new ArrayList<>();
        for (ProductXOrderDTO i : orderDTO.getProductXOrderDTOList()) {
            long totalQuantity = 0;
            for (ProductXOrderDTO n : orderDTO.getProductXOrderDTOList()) {
                if (i.getProductId().equals(n.getProductId())) {
                    totalQuantity += n.getQuantity();
                }
            }
            if (!result.contains(i)) {
                i.setQuantity(totalQuantity);
                result.add(i);
            }

        }
        return result;
    }
}
