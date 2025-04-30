import { Product } from "./product.model";

import { User } from "./user.model";
 
// Represents a Review entity with details about reviews
export interface Review {
 
    // Optional: Auto-assigned unique identifier
    reviewId?: number; 
 
    // Required: Add character limit/validation
    reviewText: string; 
 
    // Required: Rating within a defined range
    rating:number;
 
    // Use Date type for better timestamp handling
    date: string; 
 
   // Required: Associated with user who wrote the review
   userId: number; 
   
   // Required: Associated product being reviewed
   productId: number;
}
 