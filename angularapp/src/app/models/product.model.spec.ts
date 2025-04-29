import { Product } from './product.model';

describe('Product Model', () => {

  fit('Frontend_Product_model_should_create_an_instance', () => {
    // Create a sample Product object
    const product: Product = {
      productName: 'Sample Product',
      description: 'This is a sample product description.',
      price: 99.99,
      stockQuantity: 50,
      category: 'Electronics',
      brand: 'Sample Brand',
      coverImage: '/9j/4AAQSkZJRgABAQEAAAAAAAD/4QBsRXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAExAAIAAAAiAAAAMgAA...'
    };

    expect(product).toBeTruthy();
    expect(product.productId).toBeUndefined(); // productId is optional
    expect(product.productName).toBe('Sample Product');
    expect(product.description).toBe('This is a sample product description.');
    expect(product.price).toBe(99.99);
    expect(product.stockQuantity).toBe(50);
    expect(product.category).toBe('Electronics');
    expect(product.brand).toBe('Sample Brand');
    expect(product.coverImage).toBe('/9j/4AAQSkZJRgABAQEAAAAAAAD/4QBsRXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAExAAIAAAAiAAAAMgAA...');
  });

});
