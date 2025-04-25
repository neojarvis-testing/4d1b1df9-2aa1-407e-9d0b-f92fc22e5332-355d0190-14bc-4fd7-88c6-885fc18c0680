package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ReviewDTO;
import com.examly.springapp.model.Review;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.repository.ProductRepo;
import com.examly.springapp.repository.ReviewRepo;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private UserRepo userRepository;

    
    /** 
     * Converts ReviewDTO to Review entity, saves it to the repository, 
     * and returns the saved ReviewDTO.
     */

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return mapToDTO(savedReview);
    }

    
    /** 
     * Retrieves a Review by its ID, throws an exception if not found, 
     * and converts it to ReviewDTO.
     */

    @Override
    public ReviewDTO getReviewById(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        return mapToDTO(review);
    }

    
    /** 
     * Deletes a Review by its ID if it exists, returns true if successful, 
     * false otherwise.
     */

    @Override
    public boolean deleteReviewById(long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
    
    /** 
     * Retrieves all Reviews, converts them to ReviewDTOs, 
     * and returns the list.
     */

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    /** 
     * Retrieves Reviews by user ID, converts them to ReviewDTOs, 
     * and returns the list.
     */

    @Override
    public List<ReviewDTO> getReviewsByUserId(long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    /** 
     * Retrieves Reviews by product ID, converts them to ReviewDTOs, 
     * and returns the list.
     */
    @Override
    public List<ReviewDTO> getReviewsByProductId(long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReviewDTO mapToDTO(Review review) {
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
        Review review = new Review();
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        review.setDate(reviewDTO.getDate());
        
        Product product = productRepository.findById(reviewDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        review.setProduct(product);
        review.setUser(user);
        return review;
    }
}
