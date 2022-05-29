package com.test.rohlik.product.service;

import com.test.rohlik.common.ErrorMessage;
import com.test.rohlik.common.domain.BaseAuditory;
import com.test.rohlik.common.domain.ProductXOrder;
import com.test.rohlik.common.exception.ProductException;
import com.test.rohlik.common.repository.ProductXOrderRepository;
import com.test.rohlik.order.domain.Order;
import com.test.rohlik.order.util.OrderAction;
import com.test.rohlik.product.domain.Product;
import com.test.rohlik.product.dto.ProductDTO;
import com.test.rohlik.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductXOrderRepository productXOrderRepository;

    // CRUD operations

    public Product findById(UUID id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    private Product save(Product product) {
        if (product.getBaseAuditory() == null) {
            product.setBaseAuditory(new BaseAuditory());
        }
        return this.productRepository.save(product);
    }

    private List<Product> saveAll(List<Product> products) {
        return this.productRepository.saveAll(products);
    }

    private void delete(Product product) {
        this.productRepository.delete(product);
    }

    // Service operations
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product result = new Product(productDTO);
        result.setId(null);
        return this.save(result);
    }

    @Transactional
    public void updateProductStatus(Order order, List<String> errors, OrderAction orderAction) {
        List<ProductXOrder> productXOrderList = this.productXOrderRepository.findProductsByOrder(order.getId());
        List<Product> productsToSave = new ArrayList<>();
        if (orderAction == OrderAction.CREATE_ORDER) {
            productXOrderList.forEach(x -> {
                Product product = this.findById(x.getProduct().getId()); //Get product at the moment
                if (product.getStock() < x.getQuantity() || product.getBaseAuditory().getEndDate() != null) {
                    errors.add(ErrorMessage.PRODUCT_UNAVAILABLE + product.getName());
                } else {
                    product.setStock(product.getStock() - x.getQuantity());
                    productsToSave.add(product);
                }
            });
            this.saveAll(productsToSave);
            if (!errors.isEmpty()) {
                errors.add(0, ErrorMessage.PRODUCT_STOCK_HEADER_ERROR);
            }
        } else if (orderAction == OrderAction.CANCEL_ORDER) {
            productXOrderList.forEach(x -> {
                Product product = this.findById(x.getProduct().getId()); //Get product at the moment
                product.setStock(product.getStock() + x.getQuantity());
                productsToSave.add(product);
            });
            this.saveAll(productsToSave);
        }
        if (!errors.isEmpty()) {
            throw new ProductException(errors);
        }
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = this.findById(productId);
        if (product == null || product.getBaseAuditory().getEndDate() != null) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            product.getBaseAuditory().setEndDate(LocalDateTime.now());
            this.save(product);
        }
    }

    @Transactional
    public Product update(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        if (product.getId() == null) {
            throw new ProductException(List.of(ErrorMessage.PRODUCT_PARAMETERS_NOT_VALID));
        } else if (product.getBaseAuditory().getEndDate() != null) {
            throw new ProductException(List.of(ErrorMessage.PRODUCT_NOT_FOUND));
        }
        return this.save(product);
    }

    public void saveAllProductXOrder(List<ProductXOrder> productXOrderList) {
        this.productXOrderRepository.saveAll(productXOrderList);
    }

}
