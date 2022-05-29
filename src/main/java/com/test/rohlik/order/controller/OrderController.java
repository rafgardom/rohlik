package com.test.rohlik.order.controller;

import com.test.rohlik.common.dto.JsonResponseDTO;
import com.test.rohlik.common.exception.OrderException;
import com.test.rohlik.common.exception.ProductException;
import com.test.rohlik.order.domain.Order;
import com.test.rohlik.order.dto.OrderDTO;
import com.test.rohlik.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<JsonResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order order = this.orderService.createOrder(orderDTO);
            return new ResponseEntity(new JsonResponseDTO(order, null), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof OrderException) {
                return new ResponseEntity(new JsonResponseDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<JsonResponseDTO> cancelOrder(@PathVariable UUID orderId) {
        try {
            Order order = this.orderService.findById(orderId);
            this.orderService.cancelOrder(order);
            return new ResponseEntity(new JsonResponseDTO("Order successfully cancelled", null), HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof OrderException) {
                return new ResponseEntity(new JsonResponseDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<JsonResponseDTO> payOrder(@PathVariable UUID orderId) {
        try {
            this.orderService.payOrder(orderId);
            return new ResponseEntity(new JsonResponseDTO("Order successfully paid", null), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof OrderException) {
                return new ResponseEntity(new JsonResponseDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
