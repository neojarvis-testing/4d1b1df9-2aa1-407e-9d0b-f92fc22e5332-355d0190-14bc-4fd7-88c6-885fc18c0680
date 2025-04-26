package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.utility.ProductMapper;

import jakarta.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired //injects the repository to handle database(DB) operations
    ProductRepo productRepo;
    
    /*
     * 
     */
    public ProductDTO addProduct(ProductDTO productDTO) //method to add new product takes DTO as input
    {
        Product product=ProductMapper.mapToProductEntity(productDTO); //converts incoming DTO to an entity using mapper class to ensure we are storing in DB format
        Product saved=productRepo.save(product); //save entity to database
        return ProductMapper.mapToProductDTO(saved); // converts saved entity back to DTO and returns to controller
    }


    public ProductDTO getProductById(long productId) {
        Product product=productRepo.findById(productId).orElse(null); // throw an exception if product is not found
        return ProductMapper.mapToProductDTO(product);
    }
    
    public boolean deleteProductById(long productId){
        Product product=productRepo.findById(productId).orElse(null);
        if(product==null){
            return false; // throw an exception saying product with id doesnot exists
        }
        productRepo.delete(product);
        return true;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products=productRepo.findAll();
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
     }


    public ProductDTO updateProduct(long productId, ProductDTO productDTO) {
        Product existingProduct=productRepo.findById(productId).orElse(null);
        if(existingProduct==null) //throw validation
            return null;
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
