package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

     private String reviewText;
     private Integer rating;
    private LocalDate date;

     @ManyToOne
     @JoinColumn(name = "userId")
     private User user;

     @ManyToOne
     @JoinColumn(name = "productId")
     private Product product;
}
