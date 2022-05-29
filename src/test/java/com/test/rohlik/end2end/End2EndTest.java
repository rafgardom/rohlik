//package com.test.rohlik.end2end;
//
//import com.test.rohlik.common.dto.JsonResponseDTO;
//import com.test.rohlik.common.dto.ProductXOrderDTO;
//import com.test.rohlik.order.domain.Order;
//import com.test.rohlik.order.dto.OrderDTO;
//import com.test.rohlik.order.service.OrderService;
//import com.test.rohlik.product.domain.Product;
//import com.test.rohlik.product.dto.ProductDTO;
//import com.test.rohlik.product.service.ProductService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//@Rollback
//public class End2EndTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    public void createUpdateDeleteProductTest() throws Exception {
//
//        //Create products
//
//        ProductDTO productDTO1 = new ProductDTO(null, "Test product 1", 7, 31.99, null);
//        ProductDTO productDTO2 = new ProductDTO(null, "Test product 2", 4, 9.5, null);
//
//        ResponseEntity<JsonResponseDTO> createProduct1 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/create", productDTO1, JsonResponseDTO.class);
//        ResponseEntity<JsonResponseDTO> createProduct2 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/create", productDTO2, JsonResponseDTO.class);
//
//        Assert.assertSame(createProduct1.getStatusCode(), HttpStatus.OK);
//        Assert.assertSame(createProduct2.getStatusCode(), HttpStatus.OK);
//
//        //Update products
//
//        List<Product> products = this.productService.findAll();
//        products.forEach(x -> {
//            x.setName(x.getName() + " Update");
//            this.restTemplate.put("http://localhost:" + port + "/api/v1/product", x);
//        });
//
//        products = this.productService.findAll();
//        products.forEach(x -> {
//            Assert.assertTrue(x.getName().contains("Update"));
//            Assert.assertNotNull(x.getBaseAuditory().getUpdatedDate());
//        });
//
//        products.forEach(x -> {
//            this.restTemplate.delete("http://localhost:" + port + "/api/v1/product/" + x.getId());
//        });
//
//        products = this.productService.findAll();
//        products.forEach(x -> {
//            Assert.assertNotNull(x.getBaseAuditory().getEndDate());
//        });
//    }
//
//    @Test
//    public void createPayCancelOrderTest() throws Exception {
//
//        //Create products
//
//        ProductDTO productDTO1 = new ProductDTO(null, "Test product 1", 7, 31.99, null);
//        ProductDTO productDTO2 = new ProductDTO(null, "Test product 2", 4, 9.5, null);
//
//        ResponseEntity<JsonResponseDTO> createProduct1 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/create", productDTO1, JsonResponseDTO.class);
//        ResponseEntity<JsonResponseDTO> createProduct2 = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/create", productDTO2, JsonResponseDTO.class);
//
//        Assert.assertSame(createProduct1.getStatusCode(), HttpStatus.OK);
//        Assert.assertSame(createProduct2.getStatusCode(), HttpStatus.OK);
//
//        //Create order
//        List<ProductXOrderDTO> productList = new ArrayList<>();
//        this.productService.findAll().forEach(x -> {
//            productList.add(new ProductXOrderDTO(x.getId(), 3));
//        });
//        OrderDTO orderDTO = new OrderDTO(null, null, productList);
//        ResponseEntity<JsonResponseDTO> createOrder = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order/create", orderDTO, JsonResponseDTO.class);
//
//        Assert.assertSame(createOrder.getStatusCode(), HttpStatus.OK);
//
//        //Pay order
//        Order order = this.orderService.findAll().stream().findFirst().orElse(null);
//        ResponseEntity<JsonResponseDTO> payOrder = this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/order/" + order.getId() + "/pay", orderDTO, JsonResponseDTO.class);
//
//        Assert.assertSame(payOrder.getStatusCode(), HttpStatus.OK);
//
//        //Cancel order
//        this.restTemplate.delete("http://localhost:" + port + "/api/v1/order/" + order.getId());
//    }
//}
