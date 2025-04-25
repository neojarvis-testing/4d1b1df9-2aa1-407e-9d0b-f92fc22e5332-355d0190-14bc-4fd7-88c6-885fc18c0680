package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.dto.ReviewDTO;

public interface ReviewService {
    ReviewDTO addReview(ReviewDTO reviewDTO);
    ReviewDTO getReviewById(long reviewId);
    boolean deleteReviewById(long reviewId);
    List<ReviewDTO> getAllReviews();
    List<ReviewDTO> getReviewsByUserId(long userId);
    List<ReviewDTO> getReviewsByProductId(long productId);
   
}
