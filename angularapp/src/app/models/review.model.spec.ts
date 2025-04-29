import { Review } from './review.model';
import { User } from './user.model';
import { Product } from './product.model';

describe('Review Model', () => {

  fit('Frontend_Review_model_should_create_an_instance', () => {
    // Create a sample User object
    const user: User = {
      userId: 1,
      email: 'user@example.com',
      password: 'password123',
      username: 'exampleUser',
      mobileNumber: '1234567890',
      userRole: 'USER'
    };

    // Create a sample Product object
    const product: Product = {
      productId: 1,
      productName: 'Sample Product',
      description: 'This is a sample product description.',
      price: 99.99,
      stockQuantity: 50,
      category: 'Electronics',
      brand: 'Sample Brand',
      coverImage: '/9j/4AAQSkZJRgABAQEAAAAAAAD/4QBsRXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAExAAIAAAAiAAAAMgAA...'
    };

    // Create a sample Review object
    const review: Review = {
      reviewText: 'Great product, highly recommend!',
      rating: 5,
      date: '2024-11-10',
      user: user,
      product: product
    };

    expect(review).toBeTruthy();
    expect(review.reviewText).toBe('Great product, highly recommend!');
    expect(review.rating).toBe(5);
    expect(review.date).toBe('2024-11-10');
  });

});
