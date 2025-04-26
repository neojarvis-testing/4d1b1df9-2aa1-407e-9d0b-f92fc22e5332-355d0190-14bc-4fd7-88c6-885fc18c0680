package com.examly.springapp.dto;

import lombok.Data;
@Data
public class OrderItemDTO {
    private Long orderItemId;
    private long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
