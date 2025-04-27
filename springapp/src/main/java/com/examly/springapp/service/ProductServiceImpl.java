package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.exceptions.ProductNotFoundException;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.utility.ProductMapper;

import jakarta.validation.Valid;

/*Acts a service layer we write business logic */
@Service
public class ProductServiceImpl implements ProductService{

    /* injects the repository to handle database(DB) operations */
    @Autowired 
    ProductRepo productRepo;
    
    /*
     *  method to add new product takes DTO as input
     *  converts incoming DTO to an entity using mapper class to ensure we are storing in DB format
     *  save entity to database
     *  converts saved entity back to DTO and returns to controller
     */
    public ProductDTO addProduct(ProductDTO productDTO)
    {
        Product product=ProductMapper.mapToProductEntity(productDTO);
        Product saved=productRepo.save(product); 
        return ProductMapper.mapToProductDTO(saved); 
    }

    /*
     * method to get a product by given id
     * if product is not found throw an exception
     */
    public ProductDTO getProductById(long productId) {
        Product product=productRepo.findById(productId).orElse(null);
        if(product==null)
            throw new ProductNotFoundException("Product with the given id "+productId+" not found.");
        return ProductMapper.mapToProductDTO(product);
    }
    
    /*
     * method to delete a product by given id
     * if product is not found throw an exception saying product with id doesnot exists
     * otherwise delete a given product
     */
    public boolean deleteProductById(long productId) {
        Product existingProduct=productRepo.findById(productId).orElse(null);
        if(existingProduct==null) 
            throw new ProductNotFoundException("Product with the given id not found");
        productRepo.delete(existingProduct);
        return true;
    }

    /*method to get all products */
    public List<ProductDTO> getAllProducts() {
        List<Product> products=productRepo.findAll();
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
     }

    /*
     * method to update a product with the given id
     * If the product does not exist, throw a custom exception
     * Otherwise Update product with the fields if provided in the DTO
     */
    public ProductDTO updateProduct(long productId, ProductDTO productDTO) {
        Product existingProduct=productRepo.findById(productId).orElse(null);
        if(existingProduct==null) 
            throw new ProductNotFoundException("Product with the given id not found");
        if(productDTO.getProductName()!=null)
            existingProduct.setProductName(productDTO.getProductName());
        if(productDTO.getPrice()!=0)
            existingProduct.setPrice(productDTO.getPrice());
        if(productDTO.getCategory()!=null)
            existingProduct.setCategory(productDTO.getCategory());
        if(productDTO.getStockQuantity()!=0)
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        if(productDTO.getBrand()!=null)
            existingProduct.setBrand(productDTO.getBrand());
        if(productDTO.getCoverImage()!=null)
            existingProduct.setCoverImage(productDTO.getCoverImage());
        if(productDTO.getDescription()!=null)
            existingProduct.setDescription(productDTO.getDescription());

       productRepo.save(existingProduct);
       productDTO = ProductMapper.mapToProductDTO(existingProduct);
       return productDTO;
    }

}
