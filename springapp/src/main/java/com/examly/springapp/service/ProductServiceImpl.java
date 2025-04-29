package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.exceptions.ProductNotFoundException;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.utility.ProductMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*Acts a service layer we write business logic */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepo productRepo;
    
    /*
     *  method to add new product takes DTO as input
     *  converts incoming DTO to an entity using mapper class to ensure we are storing in DB format
     *  save entity to database
     *  converts saved entity back to DTO and returns to controller
     */
    @Override
    public ProductDTO addProduct(ProductDTO productDTO)
    {
        log.info("Adding new product: {}", productDTO);
        Product product=ProductMapper.mapToProductEntity(productDTO);
        Product saved=productRepo.save(product); 
        log.info("Product added successfully: {}", saved);
        return ProductMapper.mapToProductDTO(saved); 
    }

    /*
     * method to get a product by given id
     * if product is not found throw an exception
     */
    @Override
    public ProductDTO getProductById(long productId) {
        log.info("Retrieving product with ID: {}", productId);
        Product product=productRepo.findById(productId).orElse(null);
        if(product==null){
            log.error("Product with ID: {} not found", productId);
            throw new ProductNotFoundException("Product with the given id "+productId+" not found.");
        }
        log.info("Product retrieved successfully: {}", product);
        return ProductMapper.mapToProductDTO(product);
    }
    
    /*
     * method to delete a product by given id
     * if product is not found throw an exception saying product with id doesnot exists
     * otherwise delete a given product
     */
    @Override
    public boolean deleteProductById(long productId) {
        log.info("Attempting to delete product with ID: {}", productId);
        Product existingProduct=productRepo.findById(productId).orElse(null);
        if(existingProduct==null){
            log.error("Product with ID: {} not found", productId);
            throw new ProductNotFoundException("Product with the given id not found");
        } 
        productRepo.delete(existingProduct);
        log.info("Product with ID: {} deleted successfully", productId);
        return true;
    }

  
    /*method to get all products */
    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Retrieving all products...");
        List<Product> products=productRepo.findAll();
        log.info("Total products retrieved: {}", products.size());
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
     }

    /*
     * method to update a product with the given id
     * If the product does not exist, throw a custom exception
     * Otherwise Update product with the fields if provided in the DTO
     */
    @Override
    public ProductDTO updateProduct(long productId, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", productId);
        Product existingProduct=productRepo.findById(productId).orElse(null);
        if(existingProduct==null){
            log.error("Product with ID: {} not found", productId);
            throw new ProductNotFoundException("Product with the given id not found");
        }
        log.info("Existing product found: {}", existingProduct);

        if(productDTO.getProductName()!=null){
            log.debug("Updating product name to: {}", productDTO.getProductName());
            existingProduct.setProductName(productDTO.getProductName());
        }
        if(productDTO.getPrice()!=0){
            log.debug("Updating product price to: {}", productDTO.getPrice());
            existingProduct.setPrice(productDTO.getPrice());
        }
        if(productDTO.getCategory()!=null){
            log.debug("Updating product category to: {}", productDTO.getCategory());
            existingProduct.setCategory(productDTO.getCategory());
        }
        if(productDTO.getStockQuantity()!=0){
            log.debug("Updating product stock quantity to: {}", productDTO.getStockQuantity());
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }
        if(productDTO.getBrand()!=null){
            log.debug("Updating product brand to: {}", productDTO.getBrand());
            existingProduct.setBrand(productDTO.getBrand());
        }
        if(productDTO.getCoverImage()!=null){
            log.debug("Updating product cover image");
            existingProduct.setCoverImage(productDTO.getCoverImage());
        }
        if(productDTO.getDescription()!=null){
            log.debug("Updating product description");
            existingProduct.setDescription(productDTO.getDescription());
        }

       productRepo.save(existingProduct);
       log.info("Product updated successfully: {}", existingProduct);
       productDTO = ProductMapper.mapToProductDTO(existingProduct);
       return productDTO;
    }

}
