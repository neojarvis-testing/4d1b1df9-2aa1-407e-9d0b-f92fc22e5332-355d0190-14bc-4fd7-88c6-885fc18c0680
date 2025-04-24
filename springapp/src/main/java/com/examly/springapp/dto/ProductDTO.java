package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    
    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String productName;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be positive")
    private int stockQuantity;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Cover Image cannot be blank")
    private String coverImage;
    
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    public ProductDTO() {
    }
    public ProductDTO(String productName, String description, double price, 
        int stockQuantity, String category, String brand, String coverImage){
            
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.brand = brand;
        this.coverImage = coverImage;
    }
}
