import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  
  addProductForm: FormGroup;
  selectedFile!: File; // Stores selected image file
  isEditing: boolean = false;
  successMessage: string = '';
  productId!: number; // Ensure productId is stored properly

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) { 
    this.addProductForm = this.formBuilder.group({
      productName: ['', [Validators.required]],
      description: ['', [Validators.required,Validators.pattern(/^[A-Za-z0-9 ]{3,300}$/)]],
      price: ['', [Validators.required, Validators.min(1)]],
      stockQuantity: ['', [Validators.required, Validators.pattern(/^[1-9]\d*$/)]],
      category: ['', [Validators.required]],
      brand: ['', [Validators.required]],
      coverImage: [''] 
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.productId = Number(params['productId']);
      console.log("Extracted product ID:", this.productId); 

      if (this.productId && !isNaN(this.productId)) {
        this.isEditing = true;
        this.loadProduct(this.productId);
      }
    });
  }

  loadProduct(productId: number) {
    this.productService.getProductById(productId).subscribe((data) => {
      this.addProductForm.patchValue(data);
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  createProduct() {
    const formData = new FormData();
    const productData = JSON.stringify(this.addProductForm.value);
    formData.append('productData', productData);
    
    if (this.selectedFile) {
      formData.append('coverImage', this.selectedFile);
    }

    if (this.isEditing) {
      console.log("Product ID before update:", this.productId);

      if (!this.productId || isNaN(this.productId)) {
        console.error("Invalid product ID.");
        return;
      }

      this.productService.updateProduct(this.productId, formData).subscribe(() => {
        this.successMessage = 'Product updated successfully!';
        this.router.navigate(['/view-product']);
      }, () => {
        this.successMessage = 'Failed to update product.';
      });

    } else {
      this.productService.addProduct(formData).subscribe(() => {
        this.successMessage = 'Product added successfully!';
        this.router.navigate(['/view-product']);
      }, () => {
        this.successMessage = 'Failed to add product.';
      });
    }
  }
}