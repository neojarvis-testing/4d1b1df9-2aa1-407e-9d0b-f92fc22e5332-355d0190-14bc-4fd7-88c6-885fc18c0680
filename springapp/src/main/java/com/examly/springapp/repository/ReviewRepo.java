package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("select review from Review review where review.user.userId=?1")
    List<Review> findByUserId(long userId);

    @Query("select review from Review review where review.product.productId=?1")
    List<Review> findByProductId(long productId);

}
