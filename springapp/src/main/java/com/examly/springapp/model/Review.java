package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(name = "review_text")
     private String reviewText;

     @Column(name = "rating")
     private Integer rating;

     @Column(name = "review_date")
    private LocalDate date;

     @ManyToOne
     @JoinColumn(name = "userId")
     private User user;

     @ManyToOne
     @JoinColumn(name = "productId")
     private Product product;
}
