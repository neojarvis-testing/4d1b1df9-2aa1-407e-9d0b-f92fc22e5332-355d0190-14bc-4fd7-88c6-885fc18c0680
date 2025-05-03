import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Api } from 'src/app/api-urls';
import { Product } from 'src/app/models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
 
declare var bootstrap: any;
 
@Component({
  selector: 'app-userviewproduct',
  templateUrl: './userviewproduct.component.html',
  styleUrls: ['./userviewproduct.component.css']
})
export class UserviewproductComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  ApiUrl: string = Api.apiUrl;
  searchText: string = '';
  selectedCategory: string = '';
  selectedProduct: Product | null = null;
  currentPage: number = 1;
  itemsPerPage: number = 8;
 
  categories: string[] = ["Home Appliances", "Toys", "Fashion", "Electronics", "Books", "Furniture", "Beauty"];
 
  constructor(private router: Router, private productService: ProductService, private cartService: CartService) {}
 
  ngOnInit(): void {
    this.getAllProducts();
  }
 
  onPageChange(page: number): void {
    this.currentPage = page;
  }
 
  getAllProducts() {
    this.productService.getAllProducts().subscribe(
      (data) => {
        this.products = data;
        this.searchProducts();
      },
      (error) => {
        console.error("❌ Failed to fetch products:", error);
      }
    );
  }
 
  navigateToReview(productId: number) {
    this.router.navigate(['/review', productId]);
  }
 
  navigateToMyReview() {
    this.router.navigate(['/viewReview']);
  }
 
  getImageUrl(imageName: string): string {
    return `${this.ApiUrl}/${imageName}`;
  }
 
  addToCart(product: Product): void {
    if (!product.quantity || product.quantity < 1) {
      this.showModal('quantityErrorModal');
      return;
    }
   
    if (product.quantity > product.stockQuantity) {
      this.showModal('stockWarningModal');
      return;
    }
 
    this.cartService.addToCart(product, product.quantity); // ✅ Fix: Adds product to cart instantly
    this.selectedProduct = product;
    this.showModal('cartConfirmModal'); // ✅ Shows confirmation pop-up
  }
 
  confirmAddToCart() {
    this.hideModal('cartConfirmModal'); // ✅ Hide pop-up after confirmation
  }
 
  showModal(modalId: string) {
    setTimeout(() => {
      const modalElement = document.getElementById(modalId);
      if (modalElement) {
        const modal = new bootstrap.Modal(modalElement);
        modal.show();
      } else {
        console.error(`❌ Modal with ID ${modalId} not found.`);
      }
    }, 100);
  }
 
  hideModal(modalId: string) {
    const modalElement = document.getElementById(modalId);
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      modal?.hide();
    }
  }
 
  searchProducts(): void {
    this.filteredProducts = this.products.filter((product) => {
      const matchesCategory = this.selectedCategory ? product.category === this.selectedCategory : true;
      const matchesSearch = this.searchText.trim() ? JSON.stringify(product).toLowerCase().includes(this.searchText.toLowerCase()) : true;
      return matchesCategory && matchesSearch;
    });
  }
}