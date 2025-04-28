package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ReviewDTO;
import com.examly.springapp.service.ReviewServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    
    
    /**
     * Handles POST requests to add a new review.
     * Validates the request body and returns the saved ReviewDTO.
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO saved = reviewService.addReview(reviewDTO);
        return ResponseEntity.status(201).body(saved);
    }

    
    /**
     * Handles GET requests to retrieve a review by its ID.
     * Returns the ReviewDTO with the specified ID.
     */
    @GetMapping("/{reviewId}") // Returns review with corresponding id
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable long reviewId) {
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
        return ResponseEntity.status(200).body(reviewDTO);
    }

    
    /**
     * Handles DELETE requests to remove a review by its ID.
     * Returns true if the review was deleted, false if not found.
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Boolean> deleteReviewById(@PathVariable long reviewId) {
        boolean result = reviewService.deleteReviewById(reviewId);
        if (result)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }

    
    /**
     * Handles GET requests to retrieve all reviews.
     * Returns a list of all ReviewDTOs.
     */
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.status(200).body(reviews);
    }

    
    /**
     * Handles GET requests to retrieve reviews by user ID.
     * Returns a list of ReviewDTOs for the specified user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.status(200).body(reviews);
    }

    
    /**
     * Handles GET requests to retrieve reviews by product ID.
     * Returns a list of ReviewDTOs for the specified product.
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.status(200).body(reviews);
    }

}
