import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Api } from 'src/app/api-urls';
import { Product } from 'src/app/models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
 
@Component({
  selector: 'app-userviewproduct',
  templateUrl: './userviewproduct.component.html',
  styleUrls: ['./userviewproduct.component.css']
})
export class UserviewproductComponent implements OnInit {
   
  products: Product[] = [];
  filteredProducts: Product[] = [];
  ApiUrl: string = Api.apiUrl;
  quantity: number = 1; // Default quantity value
  searchText: string = ''; // Stores search text
  selectedCategory: string = ''; // Stores selected category
  categories: string[] = ["Home Appliances", "Toys", "Fashion", "Electronics", "Books", "Furniture", "Beauty"];
   
     constructor(private readonly router: Router, private readonly productService: ProductService, private readonly cartService: CartService) { }
   
     ngOnInit(): void {
     this.getAllProducts();
     }
   
   
  getAllProducts() {
  this.productService.getAllProducts().subscribe((data) => {
   this.products = data;
   // Initialize quantity for each product
   this.products.forEach(product => product.quantity);
   });
  }
   
   
     navigateToReview(productId: number) {
     this.router.navigate(['/review', productId]);
     }
   
     navigateToMyReview() {
     this.router.navigate(['/viewReview']);
     }
   
     getImageUrl(imageName: string): string {
     let a = this.ApiUrl + "/" + imageName;
     console.log(a);
     a = 'https://8080-fdefbfaaafbdebbfaadbececcfeddbcfdcfcc.premiumproject.examly.io/1746002370897_Image.jpg';
     console.log(a);
     return `${this.ApiUrl}/${imageName}`;
     }
   
     addToCart(product: Product): void {
      if (product.quantity && product.quantity > 0) {
        if (product.quantity <= product.stockQuantity) {
          this.cartService.addToCart(product, product.quantity);
          alert('Product added to cart!');
        } else {
          alert('Quantity exceeds available stock.');
        }
      } else {
        alert('Please enter a valid quantity.');
      }
    }
    searchProducts(): void {
      this.filteredProducts = this.products.filter((product) => {
        const matchesCategory = this.selectedCategory ? product.category === this.selectedCategory : true;
        const matchesSearch = this.searchText.trim() ? JSON.stringify(product).toLowerCase().includes(this.searchText.toLowerCase()) : true;
        return matchesCategory && matchesSearch;
      });
   
      if (this.filteredProducts.length === 0) {
        console.warn("No products available for the selected criteria.");
      }
    }
   
  }
     
