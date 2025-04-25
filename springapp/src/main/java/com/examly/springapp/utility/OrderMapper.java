package com.examly.springapp.utility;

import java.util.stream.Collectors;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.model.Order;
import com.examly.springapp.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setBillingAddress(order.getBillingAddress());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setOrderItems(order.getOrderItems().stream()
            .map(OrderMapper::convertToOrderItemDTO)
            .collect(Collectors.toList()));
        return orderDTO;
    }

    public static OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        orderItemDTO.setProductName(orderItem.getProduct().getProductName());
        orderItemDTO.setProductId(orderItem.getProduct().getProductId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        return orderItemDTO;
    }
}

