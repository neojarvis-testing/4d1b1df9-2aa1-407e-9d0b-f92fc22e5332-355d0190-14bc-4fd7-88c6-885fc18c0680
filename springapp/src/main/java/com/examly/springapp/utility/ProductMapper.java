package com.examly.springapp.utility;

import com.examly.springapp.dto.ProductDTO;
import com.examly.springapp.model.Product;

public class ProductMapper {
    public static ProductDTO mapToProductDTO(Product product){
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
    public static Product mapToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(productDTO.getCategory());
        product.setBrand(productDTO.getBrand());
        product.setCoverImage(productDTO.getCoverImage());
        return product;
    }
}
