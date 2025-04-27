package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ProductDTO;

public interface ProductService {
    public ProductDTO addProduct(ProductDTO productDTO);
    public ProductDTO getProductById(long productId);
    public List<ProductDTO> getAllProducts();
    public ProductDTO updateProduct(long productId, ProductDTO productDTO);
    public boolean deleteProductById(long productId);
}
