package com.test.rohlik.product.controller;

import com.test.rohlik.common.dto.JsonResponseDTO;
import com.test.rohlik.common.exception.ProductException;
import com.test.rohlik.product.domain.Product;
import com.test.rohlik.product.dto.ProductDTO;
import com.test.rohlik.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<JsonResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = this.productService.createProduct(productDTO);
            ProductDTO result = new ProductDTO(product);
            return new ResponseEntity(new JsonResponseDTO(result, null), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method does not really remove the product as Product is linked to Order. In case of real removal,
     * Order information could potentially be lost
     *
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<JsonResponseDTO> deleteProduct(@PathVariable UUID productId) {
        try {
            this.productService.deleteProduct(productId);
            return new ResponseEntity(new JsonResponseDTO("Product was removed", null), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<JsonResponseDTO> updateProduct(@RequestBody ProductDTO product) {
        try {
            Product result = this.productService.update(product);
            return new ResponseEntity(new JsonResponseDTO(result, null), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof ProductException) {
                return new ResponseEntity(new JsonResponseDTO(null, ProductException.prettifyErrors(((ProductException) e).getErrorMessageList())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(new JsonResponseDTO(null, e.getMessage() != null ? e.getMessage() : e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
