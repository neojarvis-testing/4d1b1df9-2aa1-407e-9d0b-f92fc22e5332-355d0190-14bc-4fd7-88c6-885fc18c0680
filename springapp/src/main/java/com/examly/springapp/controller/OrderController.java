package com.examly.springapp.controller;
 
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.service.OrderService;
import com.examly.springapp.service.OrderStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "API for managing orders")
@RequestMapping("/api/orders")
public class OrderController {
 
    private final OrderService orderService;
    
/**
     * Handles PATCH requests to update the status of an existing order by its ID.
     * Returns the updated OrderDTO.
     */
    

@PatchMapping("/{orderId}/status")
@Operation(summary = "Update order status by ID", description = "Updates the status of the order with the specified ID and returns the updated order object")
 public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable long orderId, @RequestBody Map<String, String> statusUpdate) {
 String status = statusUpdate.get("status");
 OrderStatus orderStatus = OrderStatus.valueOf(status);
 OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
 return ResponseEntity.status(200).body(updatedOrder);
 }



    /**
     * Handles POST requests to add a new order.
     * Validates the request body and returns the saved OrderDTO.
     */
    @PostMapping
    @Operation(summary = "Add a new order", description = "Creates a new order and returns the saved order object")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO savedOrder = orderService.addOrder(orderDTO);
        return ResponseEntity.status(201).body(savedOrder);
    }
 
    /**
     * Handles GET requests to retrieve an order by its ID.
     * Returns the OrderDTO if found, otherwise throws an exception.
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Returns the order with the specified ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.status(200).body(orderDTO);
    }
 
    /**
     * Handles GET requests to retrieve orders by user ID.
     * Returns a list of OrderDTOs.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user ID", description = "Returns a list of orders for the specified user ID")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("orderItem/{orderId}")
    @Operation(summary = "Get orderItems by order ID", description = "Returns a list of orderItems for the specified order ID")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemByOrderId(@PathVariable long orderId) {
        List<OrderItemDTO> orders = orderService.getOrderItemByOrderId(orderId);
        return ResponseEntity.status(200).body(orders);
    } 
 
    /**
     * Handles GET requests to retrieve all orders.
     * Returns a list of OrderDTOs.
     */
    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }
 
    /**
     * Handles PUT requests to update an existing order by its ID.
     * Returns the updated OrderDTO.
     */
    @PutMapping("/{orderId}")
    @Operation(summary = "Update order by ID", description = "Updates the order with the specified ID and returns the updated order object")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable long orderId, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.status(200).body(updatedOrder);
    }
 
    /**
     * Handles DELETE requests to remove an existing order by its ID.
     * Returns a confirmation message or status.
     */
    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete order by ID", description = "Deletes the order with the specified ID and returns a confirmation message")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable long orderId) {
        boolean result = orderService.deleteOrderById(orderId);
        if (result)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }
}