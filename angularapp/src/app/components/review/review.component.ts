import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from 'src/app/models/review.model';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  addReviewForm: FormGroup;
  productId: number;
  userId: number;
  showSuccessPopup: boolean = false;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly reviewService: ReviewService,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) {
    this.addReviewForm = this.formBuilder.group({
      reviewText: ['', [Validators.required, Validators.pattern(/^[A-Za-z0-9 ]{3,300}$/)]],
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.productId = +params['productId']; // Get productId from route
    });
    this.userId = +localStorage.getItem('userId'); // Get userId from local storage
  }

  setRating(star: number): void {
    this.addReviewForm.controls['rating'].setValue(star);
  }

  submitReview(): void {
    if (this.addReviewForm.valid) {
      const review: Review = {
        reviewText: this.addReviewForm.value.reviewText,
        rating: this.addReviewForm.value.rating,
        date: new Date().toISOString().split('T')[0], // Format date as 'YYYY-MM-DD' for LocalDate compatibility
        userId: this.userId, // Replace with actual user data if needed
        productId: this.productId // Replace with actual product data if needed
      };
      this.reviewService.addReview(review).subscribe(
        (data) => {
          console.log(data);
          this.showSuccessPopup = true;
          setTimeout(() => this.showSuccessPopup = false, 3000); // Hide popup after 3 seconds
          this.addReviewForm.reset();
         // this.router.navigate(['/viewReview']);
        },
        (error) => {
          console.error(error);
          alert('Failed to add review.');
        }
      );
    }
  }
}
