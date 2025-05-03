import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Review } from '../models/review.model';
import { Observable } from 'rxjs';
import { Api } from '../api-urls';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  // Constructor to inject the HttpClient dependency for making HTTP calls
constructor(private readonly http: HttpClient) { }
 
/**
   * Method to add a new review
   * Sends a POST request to the API endpoint with the review data
   */
addReview(review: Review): Observable<Review> {
return this.http.post<Review>(`${Api.apiUrl}/api/reviews`, review);
}
 
  getReviewsById(reviewId: number): Observable<Review> {
  return this.http.get<Review>(`${Api.apiUrl}/api/reviews/${reviewId}`);
  }
/**
   * Method to fetch all reviews
   * Sends a GET request to the API endpoint
   */
getAllReviews(): Observable<Review[]> {
   return this.http.get<Review[]>(`${Api.apiUrl}/api/reviews`);
}
 
/**
   * Method to get all reviews submitted by a specific user
   * Sends a GET request to the API endpoint with the user ID
   */
getReviewsByUserId(userId: number): Observable<Review[]> {
   return this.http.get<Review[]>(`${Api.apiUrl}/api/reviews/user/${userId}`);
}
 
/**
   * Method to fetch all reviews associated with a specific product
   * Sends a GET request to the API endpoint with the product ID
   */
getReviewsByProductId(productId: number): Observable<Review[]> {
   return this.http.get<Review[]>(`${Api.apiUrl}/api/reviews/product/${productId}`);
}
 
/**
   * Method to delete a review by its ID
   * Sends a DELETE request to the API endpoint with the review ID
   */
deleteReview(reviewId: number): Observable<void> {
   return this.http.delete<void>(`${Api.apiUrl}/api/reviews/${reviewId}`);
} 
}
