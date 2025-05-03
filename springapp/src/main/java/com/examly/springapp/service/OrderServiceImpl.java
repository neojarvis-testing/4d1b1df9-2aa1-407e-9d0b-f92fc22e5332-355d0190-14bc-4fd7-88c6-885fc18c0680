package com.examly.springapp.service;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Service;
 
import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.exceptions.OrderNotFoundException;
import com.examly.springapp.exceptions.ProductNotFoundException;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.model.Order;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.OrderRepo;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.repository.UserRepo;
 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
 
    private final OrderRepo orderRepository;
 
    private final ProductRepo productRepository;
    private final UserRepo userRepository;
 
    
@Override
public OrderDTO updateOrderStatus(long orderId, OrderStatus status) {
Order order = orderRepository.findById(orderId)
.orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));
order.setOrderStatus(status);
Order updatedOrder = orderRepository.save(order);
return mapToDTO(updatedOrder);
}
 
    
    /**
     * Adds a new order.
     * Validates the order items' stock quantity and updates the product stock.
     */
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        log.info("Entering addOrder method with OrderDTO: {}", orderDTO);
        Order order = mapToEntity(orderDTO);
        double totalAmount = calculateTotalAmount(orderDTO.getOrderItems());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        log.info("Order added successfully, Total Amount: {}", totalAmount);
 
        // Check if order item quantity is less than product stock quantity
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            if (itemDTO.getQuantity() > product.getStockQuantity()) {
                log.error("Insufficient stock for product: {}", product.getProductName());
                throw new ProductNotFoundException("Insufficient stock for product: " + product.getProductName());
            }
        }
 
        // Update product stock quantity
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }
        log.info("Exiting addOrder method");
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }
 
    
    /**
     * Retrieves an order by its ID. Throws an exception if not found, 
     * and converts it to orderDTO.
     */
    @Override
    public OrderDTO getOrderById(long orderId) {
        log.info("Retrieving order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() ->{
        log.error("Order with ID: {} not found", orderId);
        throw new OrderNotFoundException("Order not found");
        });
        log.info("Order retrieved successfully with ID: {}", orderId);
        return mapToDTO(order);
    }
 
    
    /**
     * Retrieves Reviews by user ID, converts them to orderDTOs, 
     * and returns the list.
     */
    @Override
    public List<OrderDTO> getOrdersByUserId(long userId) {
        log.info("Retrieving orders for user with ID: {}", userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        log.info("Number of orders retrieved for user with ID {}: {}", userId, orders.size());
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
 
    public List<OrderItemDTO> getOrderItemByOrderId(long orderId) {
        log.info("Retrieving orders for order with ID: {}", orderId);
        List<OrderItem> orders = orderRepository.findByOrderId(orderId);
        log.info("Number of orders retrieved for user with ID {}: {}", orderId, orders.size());
        return orders.stream().map(this::mapToOrderItemDTO).collect(Collectors.toList());
    }
 
    
    /**
     * Retrieves all orders, converts them to orderDTOs, 
     * and returns the list.
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        log.info("Retrieving all orders...");
        List<Order> orders = orderRepository.findAll();
        log.info("Total orders retrieved: {}", orders.size());
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
 
    
    /**
     * Updates an existing order by its ID.
     * If the order does not exist, throw a custom exception
     * Otherwise Update order with the fields if provided in the DTO
     */
     @Override
public OrderDTO updateOrder(long orderId, OrderDTO orderDTO) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order not found"));
 
    if (orderDTO.getShippingAddress() != null) {
        order.setShippingAddress(orderDTO.getShippingAddress());
    }
    if (orderDTO.getBillingAddress() != null) {
        order.setBillingAddress(orderDTO.getBillingAddress());
    }
    if (orderDTO.getOrderStatus() != null) {
        order.setOrderStatus(orderDTO.getOrderStatus()); // Allow status update
    }
 
    orderRepository.save(order);
    return mapToDTO(order);
}
 
 
    /**
     * Deletes an order by its ID.
     * @return True if the order was deleted, false if not found.
     */
    @Override
    public boolean deleteOrderById(long orderId) {
        log.info("Deleting order with ID: {}", orderId);
        if (orderRepository.existsById(orderId)){
            log.info("Order with ID: {} exists. Proceeding with deletion.", orderId);
            orderRepository.deleteById(orderId);
            log.info("Order with ID: {} deleted successfully.", orderId);
            return true;
        }
        log.warn("Order with ID: {} does not exist. Deletion aborted.", orderId);
        return false;
    }
 
    
    /**
     * Calculates the total amount for the order items.
     * @return The total amount.
     */
    private double calculateTotalAmount(List<OrderItemDTO> orderItems) {
        log.info("Calculating total amount for order items...");
        double total = 0;
        for (OrderItemDTO item : orderItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            total += product.getPrice() * item.getQuantity();
            log.info("Added item: {}, Quantity: {}, Price: {}", product.getProductName(), item.getQuantity(), product.getPrice());
        }
        log.info("Total amount calculated: {}", total);
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
     order.setOrderStatus(orderDTO.getOrderStatus());
     order.setShippingAddress(orderDTO.getShippingAddress());
     order.setBillingAddress(orderDTO.getBillingAddress());
     User user = userRepository.findById(orderDTO.getUserId())
     .orElseThrow(() -> new UserNotFoundException("User not found"));
     order.setUser(user);
     List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
     .map(this::mapToOrderItemEntity)
     .map(orderItem -> {
     orderItem.setOrder(order);
     return orderItem;
     })
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
     orderItemDTO.setPrice(orderItem.getProduct().getPrice());
     return orderItemDTO;
}
     private OrderItem mapToOrderItemEntity(OrderItemDTO orderItemDTO) {
     OrderItem orderItem = new OrderItem();
     orderItem.setQuantity(orderItemDTO.getQuantity());
     orderItem.setPrice(orderItemDTO.getPrice());
     Product product = productRepository.findById(orderItemDTO.getProductId())
     .orElseThrow(() -> new ProductNotFoundException("Product not found"));
     orderItem.setProduct(product);
     return orderItem;
     }
    }