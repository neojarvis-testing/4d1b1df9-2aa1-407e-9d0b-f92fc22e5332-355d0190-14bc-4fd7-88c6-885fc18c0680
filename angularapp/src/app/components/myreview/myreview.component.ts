import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { Review } from 'src/app/models/review.model';
import { ProductService } from 'src/app/services/product.service';
import { ReviewService } from 'src/app/services/review.service';
 
@Component({
  selector: 'app-myreview',
  templateUrl: './myreview.component.html',
  styleUrls: ['./myreview.component.css']
})
export class MyreviewComponent implements OnInit {
  reviews: Review[] = [];
  filteredReviews: Review[] = [];
  sortDirection: string = 'asc';
  searchRating: string | null = null;
  selectedProduct: Product | null = null;
 
  constructor(
    private readonly reviewService: ReviewService,
    private  readonly productService: ProductService,
    private readonly router: Router
  ) { }
 
  ngOnInit(): void {
    this.loadReviews();
  }
 
  loadReviews(): void {
    this.reviewService.getAllReviews().subscribe(
      (data: Review[]) => {
        this.reviews = data;
        this.filteredReviews = [...this.reviews]; // Initialize filteredReviews
      },
      (error) => {
        console.error(error);
        alert('Failed to load reviews.');
      }
    );
  }
 
  sortReviews(): void {
    this.filteredReviews.sort((a, b) => {
      const dateA = new Date(a.date).getTime();
      const dateB = new Date(b.date).getTime();
      return this.sortDirection === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
 
  filterReviews(): void {
    if (this.searchRating === null || this.searchRating === '') {
      this.filteredReviews = [...this.reviews];
    } else {
      this.filteredReviews = this.reviews.filter(review => review.rating.toString() === this.searchRating);
    }
    this.sortReviews(); // Ensure sorting is applied after filtering
  }
 
  onSortChange(direction: string): void {
    this.sortDirection = direction;
    this.sortReviews();
  }
 
  onRatingChange(rating: string | null): void {
    this.searchRating = rating;
    this.filterReviews();
  }
 
  viewProduct(productId: number): void {
    this.productService.getProductById(productId).subscribe(
      (data) => {
        this.selectedProduct = data;
        console.log(this.selectedProduct);
      },
      (error) => {
        console.error(error);
        alert('Failed to load product.');
      }
    );
  }
 
  closeModal(): void {
    this.selectedProduct = null;
  }
 
  deleteReview(reviewId: number): void {
    // Show confirmation alert
    const isConfirmed = confirm('Are you sure you want to delete this review?');
  
    if (isConfirmed) {
      this.reviewService.deleteReview(reviewId).subscribe(
        () => {
          this.reviews = this.reviews.filter(review => review.reviewId !== reviewId);
          this.filteredReviews = this.filteredReviews.filter(review => review.reviewId !== reviewId);
        },
        (error) => {
          console.error(error);
          alert('Failed to delete review.');
        }
      );
    }
  }

}