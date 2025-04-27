package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.model.Order;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.OrderRepo;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = mapToEntity(orderDTO);
        double totalAmount = calculateTotalAmount(orderDTO.getOrderItems());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("Confirmed");

        // Check if order item quantity is less than product stock quantity
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (itemDTO.getQuantity() > product.getStockQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
            }
        }

        // Update product stock quantity
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrder(long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setBillingAddress(orderDTO.getBillingAddress());
        order.setTotalAmount(calculateTotalAmount(orderDTO.getOrderItems()));

        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
            .map(this::mapToOrderItemEntity)
            .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    @Override
    public boolean deleteOrderById(long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    private double calculateTotalAmount(List<OrderItemDTO> orderItems) {
        double total = 0;
        for (OrderItemDTO item : orderItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            total += product.getPrice() * item.getQuantity();
        }
        return total;
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setBillingAddress(order.getBillingAddress());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setOrderItems(order.getOrderItems().stream()
            .map(this::mapToOrderItemDTO)
            .collect(Collectors.toList()));
        return orderDTO;
    }

    private Order mapToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderStatus("Processing");
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setBillingAddress(orderDTO.getBillingAddress());

        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
            .map(this::mapToOrderItemEntity)
            .map(orderItem -> { orderItem.setOrder(order); return orderItem; })
            .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        orderItemDTO.setProductId(orderItem.getProduct().getProductId());
        orderItemDTO.setProductName(orderItem.getProduct().getProductName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        return orderItemDTO;
    }

    private OrderItem mapToOrderItemEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        Product product = productRepository.findById(orderItemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        orderItem.setProduct(product);
        return orderItem;
    }
}
