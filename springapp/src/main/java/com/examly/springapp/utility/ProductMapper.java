package com.examly.springapp.utility;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.model.Product;

public class ProductMapper {
    public static ProductDTO mapToProduct(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setCategory(product.getCategory());
        productDTO.setBrand(product.getBrand());
        productDTO.setCoverImage(product.getCoverImage());
        return productDTO;
    } 
}
