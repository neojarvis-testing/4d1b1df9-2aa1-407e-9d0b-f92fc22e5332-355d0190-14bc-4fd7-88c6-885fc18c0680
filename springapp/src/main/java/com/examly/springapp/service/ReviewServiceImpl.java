package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ReviewDTO;
import com.examly.springapp.exceptions.ProductNotFoundException;
import com.examly.springapp.exceptions.ReviewNotFoundException;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.model.Review;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.repository.ReviewRepo;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepository;

    private final ProductRepo productRepository;

    private final UserRepo userRepository;

    
    /** 
    * Converts ReviewDTO to Review entity, saves it to the repository, 
    * and returns the saved ReviewDTO.
    */

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        log.info("Adding a new review: {}", reviewDTO);
        Review review = mapToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        log.info("Review added successfully: {}", savedReview);
        return mapToDTO(savedReview);
    }

    
    /** 
     * Retrieves a Review by its ID, throws an exception if not found, 
     * and converts it to ReviewDTO.
     */

    @Override
    public ReviewDTO getReviewById(long reviewId) {
        log.info("Retrieving review with ID: {}", reviewId);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->{ 
        log.error("Review with ID: {} not found", reviewId);
        return new ReviewNotFoundException("Review not found");
    });
        log.info("Review retrieved successfully: {}", review);
        return mapToDTO(review);
    }

    
    /** 
     * Deletes a Review by its ID if it exists, returns true if successful, 
     * false otherwise.
     */

    public boolean deleteReviewById(long reviewId) {
    log.info("Attempting to delete review with ID: {}", reviewId);
    Review review = reviewRepository.findById(reviewId).orElse(null);
    if (review == null) {
        log.warn("Review with ID: {} not found", reviewId);
        return false; // throw an exception saying review with id does not exist
    }
    reviewRepository.delete(review);
    log.info("Review with ID: {} deleted successfully", reviewId);
    return true;
}
    
    /** 
     * Retrieves all Reviews, converts them to ReviewDTOs, 
     * and returns the list.
     */

    @Override
    public List<ReviewDTO> getAllReviews() {
        log.info("Retrieving all reviews...");
        List<Review> reviews = reviewRepository.findAll();
        log.info("Total reviews retrieved: {}", reviews.size());
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    /** 
     * Retrieves Reviews by user ID, converts them to ReviewDTOs, 
     * and returns the list.
     */

    @Override
    public List<ReviewDTO> getReviewsByUserId(long userId) {
        log.info("Retrieving reviews for user with ID: {}", userId);
        List<Review> reviews = reviewRepository.findByUserId(userId);
        log.info("Number of reviews retrieved for user ID {}: {}", userId, reviews.size());
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    /** 
     * Retrieves Reviews by product ID, converts them to ReviewDTOs, 
     * and returns the list.
     */
    @Override
    public List<ReviewDTO> getReviewsByProductId(long productId) {
        log.info("Retrieving reviews for product with ID: {}", productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        log.info("Number of reviews retrieved for product ID {}: {}", productId, reviews.size());
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReviewDTO mapToDTO(Review review) {
        log.debug("Mapping Review entity to ReviewDTO...");
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setProductId(review.getProduct().getProductId());
        reviewDTO.setUserId(review.getUser().getUserId());
        return reviewDTO;
    }

    private Review mapToEntity(ReviewDTO reviewDTO) {
        log.debug("Mapping ReviewDTO to Review entity...");
        Review review = new Review();
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        review.setDate(reviewDTO.getDate());
        
        Product product = productRepository.findById(reviewDTO.getProductId()).orElseThrow(() ->{
        log.error("Product with ID: {} not found", reviewDTO.getProductId());
        return new ProductNotFoundException("Product not found");
        });

        User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() ->{
        log.error("User with ID: {} not found", reviewDTO.getUserId());
        return new UserNotFoundException("User not found");
        });
        
        review.setProduct(product);
        review.setUser(user);
        log.debug("Review entity mapped successfully: {}", review);
        return review;
    }
}
