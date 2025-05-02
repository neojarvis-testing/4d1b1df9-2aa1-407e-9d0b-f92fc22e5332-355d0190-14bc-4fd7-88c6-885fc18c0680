package com.examly.springapp.service;
 
import java.util.List;
import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.dto.OrderItemDTO;
 
public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(long orderId);
    List<OrderDTO> getOrdersByUserId(long userId);
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrder(long orderId, OrderDTO orderDTO);
    List<OrderItemDTO> getOrderItemByOrderId(long orderId);
    boolean deleteOrderById(long orderId);
    OrderDTO updateOrderStatus(long orderId, OrderStatus orderStatus);
    
}