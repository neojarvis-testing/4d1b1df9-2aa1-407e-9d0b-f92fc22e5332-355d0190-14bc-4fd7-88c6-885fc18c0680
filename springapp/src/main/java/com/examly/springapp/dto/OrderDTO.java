package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;


@Data
public class OrderDTO {
    private Long orderId;
    private LocalDate orderDate;
    private String orderStatus;
    private String shippingAddress;
    private String billingAddress;
    private Double totalAmount;
    private Long userId;
    private List<OrderItemDTO> orderItems;

}


