package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProductDTO {
    private long productId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String productName;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be positive")
    private int stockQuantity;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Cover Image cannot be blank")
    private String coverImage;
}
