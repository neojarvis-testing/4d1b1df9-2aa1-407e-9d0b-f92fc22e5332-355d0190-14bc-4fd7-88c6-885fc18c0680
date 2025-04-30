package com.examly.springapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private static final String UPLOAD_DIR = "./Images";

    /*Handles post mapping */
    @PostMapping(consumes = {"multipart/form-data"})

    @Operation(summary = "Update product by ID", description = "Updates the product with the specified ID and returns the updated product object")
    public ResponseEntity<?> addProduct(
        @RequestParam("productData") String productData, // JSON as String
        @RequestParam("coverImage") MultipartFile coverImage) {

    if (coverImage.isEmpty()) {
        return ResponseEntity.badRequest().body("Image file is required.");
    }

    try {
        // Convert JSON String to ProductDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productData, ProductDTO.class);

        // Ensure directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Save image file
        String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
        Path filePath = Path.of(UPLOAD_DIR, fileName);
        Files.copy(coverImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Set file path while keeping `coverImage`
        productDTO.setCoverImage(fileName);

        // Save product in database and return the object
        ProductDTO savedProduct = productService.addProduct(productDTO);
        return ResponseEntity.ok(savedProduct);
    } catch (IOException e) {
        return ResponseEntity.internalServerError().body("Error saving image file.");
    }
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
    @PutMapping(value = "/{productId}", consumes = {"multipart/form-data"})
@Operation(summary = "Update product by ID", description = "Updates the product with the specified ID and returns the updated product object")
public ResponseEntity<?> updateProduct(
    @PathVariable long productId,
    @RequestParam("productData") String productData, // JSON as String
    @RequestParam(value = "coverImage", required = false) MultipartFile coverImage // Image file (optional)
) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productData, ProductDTO.class);

        if (coverImage != null && !coverImage.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
            Path filePath = Path.of(UPLOAD_DIR, fileName);
            Files.copy(coverImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            productDTO.setCoverImage(fileName); // Store the new image path
        }

        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(updatedProduct);

    } catch (IOException e) {
        return ResponseEntity.internalServerError().body("Error updating product.");
    }
}
}
