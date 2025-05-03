import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { Review } from 'src/app/models/review.model';
import { ProductService } from 'src/app/services/product.service';
import { ReviewService } from 'src/app/services/review.service';
 
declare var bootstrap: any;
 
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
  selectedReviewId: number | null = null;
 
  constructor(
    private readonly reviewService: ReviewService,
    private readonly productService: ProductService,
    private readonly router: Router
  ) {}
 
  ngOnInit(): void {
    this.loadReviews();
  }
 
  loadReviews(): void {
    this.reviewService.getAllReviews().subscribe(
      (data: Review[]) => {
        this.reviews = data;
        this.filteredReviews = [...this.reviews];
      },
      (error) => {
        console.error(error);
      }
    );
  }
 
  onSortChange(direction: string): void {
    this.sortDirection = direction;
    this.sortReviews();
  }
 
  onRatingChange(rating: string | null): void {
    this.searchRating = rating;
    this.filterReviews();
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
    this.sortReviews();
  }
 
  viewProduct(productId: number): void {
    this.productService.getProductById(productId).subscribe(
      (data) => {
        this.selectedProduct = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }
 
  closeModal(): void {
    this.selectedProduct = null;
  }
 
  deleteReview(reviewId: number): void {
    this.selectedReviewId = reviewId;
    this.showModal('confirmDeleteModal');
  }
 
  confirmDeleteReview(): void {
    if (this.selectedReviewId) {
      this.reviewService.deleteReview(this.selectedReviewId).subscribe(
        () => {
          this.reviews = this.reviews.filter(review => review.reviewId !== this.selectedReviewId);
          this.filteredReviews = this.filteredReviews.filter(review => review.reviewId !== this.selectedReviewId);
         
          this.hideModal('confirmDeleteModal');
        },
        (error) => {
          console.error(error);
          this.showModal('deleteErrorModal');
        }
      );
    }
  }
 
  showModal(modalId: string): void {
    const modalElement = document.getElementById(modalId);
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    } else {
      console.error(`❌ Modal with ID ${modalId} not found.`);
    }
  }
 
  hideModal(modalId: string): void {
    const modalElement = document.getElementById(modalId);
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      modal?.hide();
    }
  }
}