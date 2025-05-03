package com.examly.springapp.utility;

import com.examly.springapp.dto.ReviewDTO;
import com.examly.springapp.model.Review;

public class ReviewMapper {

    private ReviewMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static ReviewDTO convertToReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setUserId(review.getUser().getUserId());
        reviewDTO.setProductId(review.getProduct().getProductId());
        return reviewDTO;
    }

    public static Review convertToReviewEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        review.setDate(reviewDTO.getDate());
        return review;
    }
}
