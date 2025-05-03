import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Api } from 'src/app/api-urls';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';
 
declare var bootstrap: any; // Required for Bootstrap modal control
 
@Component({
  selector: 'app-adminviewproduct',
  templateUrl: './adminviewproduct.component.html',
  styleUrls: ['./adminviewproduct.component.css']
})
export class AdminviewproductComponent implements OnInit {
 
  products: Product[] = [];
  searchText: string = '';
  filteredProduct: Product[] = [];
  ApiUrl: string = Api.apiUrl;
 
  isLoading: boolean = true;
  currentPage: number = 1;
  itemsPerPage: number = 8;
 
  selectedCategory: string = '';
  categories: string[] = ["Home Appliances", "Toys", "Fashion", "Electronics", "Books", "Furniture", "Beauty"];
 
  selectedProductId: number = 0; // Store product ID for deletion
 
  constructor(private readonly productService: ProductService, private readonly router: Router) { }
 
  ngOnInit(): void {
    this.getAllProducts();
  }
 
  getAllProducts() {
    this.productService.getAllProducts().subscribe((data) => {
      this.products = data;
      this.filteredProduct = data;
    });
  }
 
  openDeleteModal(productId: number) {
    this.selectedProductId = productId;
    const modalElement = document.getElementById('deleteConfirmModal');
 
    //  Ensure modal is visible and accessible
    modalElement?.removeAttribute("aria-hidden");
 
    const modal = new bootstrap.Modal(modalElement!);
    modal.show();
  }
 
  confirmDelete() {
    this.productService.deleteProduct(this.selectedProductId).subscribe(
      () => {
        console.log(` Product ID ${this.selectedProductId} deleted successfully`);
        this.getAllProducts(); // Refresh product list
 
        //  Close the modal after successful deletion
        const modalElement = document.getElementById('deleteConfirmModal');
        const modal = bootstrap.Modal.getInstance(modalElement!);
        modal?.hide();
      },
      (error) => {
        console.error(` Error deleting product ID ${this.selectedProductId}`, error);
        if (error.status === 404) {
          alert(`Product with ID ${this.selectedProductId} not found.`);
        } else {
          alert(`Error deleting product: ${error.message}`);
        }
      }
    );
  }
 
  updateProduct(productId: number) {
    this.router.navigate(['/updateProduct', productId]);
  }
 
  search() {
    this.filteredProduct = this.searchText === ''
      ? this.products
      : this.products.filter((data) =>
          JSON.stringify(data).toLowerCase().includes(this.searchText.toLowerCase())
        );
  }
 
  filterByCategory(): void {
    this.filteredProduct = this.selectedCategory
      ? this.products.filter(p => p.category === this.selectedCategory)
      : [...this.products];
  }
 
  onPageChange(page: number): void {
    this.currentPage = page;
  }
 
  getImageUrl(imageName: string): string {
    return `${this.ApiUrl}/${imageName}`;
  }
}