package com.examly.springapp.controller;
 
import java.util.List;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.dto.OrderDTO;
import com.examly.springapp.service.OrderService;
 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
 
    private final OrderService orderService;
 
    /**
     * Handles POST requests to add a new order.
     * Validates the request body and returns the saved OrderDTO.
     */
    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO savedOrder = orderService.addOrder(orderDTO);
        return ResponseEntity.status(201).body(savedOrder);
    }
 
    /**
     * Handles GET requests to retrieve an order by its ID.
     * Returns the OrderDTO if found, otherwise throws an exception.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.status(200).body(orderDTO);
    }
 
    /**
     * Handles GET requests to retrieve orders by user ID.
     * Returns a list of OrderDTOs.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
 
    /**
     * Handles GET requests to retrieve all orders.
     * Returns a list of OrderDTOs.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }
 
    /**
     * Handles PUT requests to update an existing order by its ID.
     * Returns the updated OrderDTO.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable long orderId, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.status(200).body(updatedOrder);
    }
 
    /**
     * Handles DELETE requests to remove an existing order by its ID.
     * Returns a confirmation message or status.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable long orderId) {
        boolean result = orderService.deleteOrderById(orderId);
        if (result)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }
}