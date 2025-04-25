package com.examly.springapp.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
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
 
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
 
    @Autowired
    private OrderService orderService;
 
    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        /**
         * Handles POST requests to add a new order.
         * Validates the request body and returns the saved OrderDTO.
         */
        OrderDTO savedOrder = orderService.addOrder(orderDTO);
        return ResponseEntity.status(201).body(savedOrder);
    }
 
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId) {
        /**
         * Handles GET requests to retrieve an order by its ID.
         * Returns the OrderDTO if found, otherwise throws an exception.
         */
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.status(200).body(orderDTO);
    }
 
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable long userId) {
        /**
         * Handles GET requests to retrieve orders by user ID.
         * Returns a list of OrderDTOs.
         */
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(200).body(orders);
    }
 
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        /**
         * Handles GET requests to retrieve all orders.
         * Returns a list of OrderDTOs.
         */
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }
 
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable long orderId, @Valid @RequestBody OrderDTO orderDTO) {
        /**
         * Handles PUT requests to update an existing order by its ID.
         * Returns the updated OrderDTO.
         */
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.status(200).body(updatedOrder);
    }
 
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long orderId) {
        /**
         * Handles DELETE requests to delete an order by its ID.
         * Returns a 204 status if successful, otherwise throws an exception.
         */
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(204).build();
    }
}