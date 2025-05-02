package com.examly.springapp.service;

public enum OrderStatus {

   CONFIRMED,
   PROCESSING,
   PENDING,
   ACCEPTED,
   DISPATCHED,
   OUT_FOR_DELIVERY,
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
