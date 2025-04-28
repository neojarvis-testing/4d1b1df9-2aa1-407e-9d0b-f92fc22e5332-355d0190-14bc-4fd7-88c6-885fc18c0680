package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.service.ProductServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "API for managing products")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    /*Handles post mapping */
    @PostMapping

    @Operation(summary = "Update product by ID", description = "Updates the product with the specified ID and returns the updated product object")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) //@Valid handles validations
    {
        ProductDTO saved=productService.addProduct(productDTO); //calls service method and passes the user data
        return ResponseEntity.status(201).body(saved);
    }

     /*Handles get mapping, returns product with corresponding id */
    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Returns the product with the specified ID")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long productId){
        ProductDTO productDTO=productService.getProductById(productId);
        return ResponseEntity.status(200).body(productDTO);
    }

    /*Handles delete mapping, deletes product with corresponding id */
    /*Handles delete mapping, deletes product with corresponding id and returns a message*/
    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product by ID", description = "Deletes the product with the specified ID and returns a success message")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable long productId) {
       boolean result = productService.deleteProductById(productId);
       if(result)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(200).body(false);
    }

     /*Handles get mapping, returns all products */
    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products=productService.getAllProducts();
        return ResponseEntity.status(200).body(products);
    }
    
    /*Handles put mapping, updates a product */
    @PutMapping("/{productId}")
    @Operation(summary = "Update product by ID", description = "Updates the product with the specified ID and returns the updated product object")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long productId,  @RequestBody ProductDTO productDTO){
        ProductDTO saved=productService.updateProduct(productId,productDTO); //calls service method and passes the user data
        return ResponseEntity.status(201).body(saved);
    }
}
