import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  
  addProductForm:FormGroup
  isEditing:boolean=false;
  successMessage:string='';
  product:Product={productName:'',description:'',price:0,stockQuantity:0,category:'',brand:'',coverImage:''}

  
  constructor(private formBuilder:FormBuilder,private productService:ProductService,private router:Router,private route:ActivatedRoute) { 
    this.addProductForm = this.formBuilder.group({
      productName : ['',[Validators.required, Validators.pattern(/^[a-zA-Z0-9 ]{3,20}$/)]],
      description : ['',[Validators.required, Validators.pattern(/^[a-zA-Z0-9\s.,!?()-]{3,500}$/)]],
      price : ['',[Validators.required, Validators.min(1)]],
      stockQuantity : ['',[Validators.required, Validators.min(0)]],
      category : ['',[Validators.required, Validators.pattern(/^[a-zA-Z0-9 ]{3,20}$/)]],
      brand:['',[Validators.required,Validators.pattern(/^[a-zA-Z0-9 ]{3,20}$/)]],
      coverImage : ['',[Validators.required]]
    });
  }
  

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const productId = params['productId'];
      console.log('Editing product ID:', productId);
      if (productId) {
        this.isEditing = true;
        this.loadProduct(productId);
      }
    });
  }

  loadProduct(productId: number) {
    this.productService.getProductById(productId).subscribe((data) => {
      console.log('Fetched product:', data); // Debug log
      this.product = data; // Assign the fetched product
      this.addProductForm.patchValue(this.product); // Populate form
    });
  }
  showPopupMsg(title: string, message: string) {
    alert(`${title}: ${message}`); // Simple alert popup
  }

  createProduct() {
    if (this.addProductForm.valid) {
      if (this.isEditing) {
        this.product = { productId: this.product.productId, ...this.addProductForm.value };
        this.productService.updateProduct(this.product.productId, this.product).subscribe(() => {
          this.successMessage = 'Product updated successfully!';
          this.router.navigate(['/viewproduct']);
        }, (error) => {
          console.error('Error updating product:', error);
          this.successMessage = 'Failed to update product.';
        });
      } else {
        this.product = { ...this.addProductForm.value };
        this.productService.addProduct(this.product).subscribe(() => {
          this.successMessage = 'Product added successfully!';
          this.router.navigate(['/viewproduct']);
        }, (error) => {
          console.error('Error adding product:', error);
          this.successMessage = 'Failed to add product.';
        });
      }
    }
  }

  // Store the selected file (if needed for further processing/upload)
  handleFileChange(event: any) {
    // Get the file from input
    const file = event.target.files[0];
    if (file) {
      const formData = new FormData();
      // Attach file directly
      formData.append('coverImage', file);
      // Optional: store file name if needed
      this.addProductForm.patchValue({ coverImage: file.name });
    }
  }
}
