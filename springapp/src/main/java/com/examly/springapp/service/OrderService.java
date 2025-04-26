package com.examly.springapp.service;
 
import java.util.List;
import com.examly.springapp.dto.OrderDTO;
 
public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(long orderId);
    List<OrderDTO> getOrdersByUserId(long userId);
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrder(long orderId, OrderDTO orderDTO);
    boolean deleteOrderById(long orderId);
}