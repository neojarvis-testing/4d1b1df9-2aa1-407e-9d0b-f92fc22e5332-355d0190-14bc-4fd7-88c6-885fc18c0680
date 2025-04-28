package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class OrderItemDTO {
    
    private Long orderItemId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

}
