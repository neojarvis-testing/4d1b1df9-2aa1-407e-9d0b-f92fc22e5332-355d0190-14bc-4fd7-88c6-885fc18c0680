package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;

import com.examly.springapp.service.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDTO {
  
    private Long orderId;

    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;

    @NotBlank(message = "Billing address cannot be blank")
    private String billingAddress;

    @NotNull(message = "Total amount cannot be null")
    private Double totalAmount;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemDTO> orderItems;

    
}


