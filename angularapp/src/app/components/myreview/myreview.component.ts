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
  searchRating: number | null = null;
  selectedProduct: Product;
 
  constructor(
    private reviewService: ReviewService,
    private productService: ProductService,
    private router: Router
  ) { }
 
  ngOnInit(): void {
 
    this.loadReviews();
  }
 
  loadReviews(): void {
    this.reviewService.getAllReviews().subscribe(
      (data: Review[]) => {
        this.reviews = data;
        this.applyFilters();
      },
      (error) => {
        console.error(error);
        alert('Failed to load reviews.');
      }
    );
  }
 
  applyFilters(): void {
    this.filteredReviews = this.reviews
      .filter(review => this.searchRating == null || review.rating === this.searchRating)
      .sort((a, b) => {
        const dateA = new Date(a.date).getTime();
        const dateB = new Date(b.date).getTime();
        return this.sortDirection === 'asc' ? dateA - dateB : dateB - dateA;
      });
  }
 
  onSortChange(direction: string): void {
    this.sortDirection = direction;
    this.applyFilters();
  }
 
  onRatingChange(rating: number): void {
    this.searchRating = rating;
    this.applyFilters();
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
 
}
