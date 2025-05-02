package com.examly.springapp.service;

public enum OrderStatus {
        PROCESSING,
        CONFIRMED,
        DISPATCHED,
        DELIVERED,
        CANCELLED;

        public static OrderStatus fromString(String status) {
                try {
                    return OrderStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid order status: " + status);
                }
                }
}
    
