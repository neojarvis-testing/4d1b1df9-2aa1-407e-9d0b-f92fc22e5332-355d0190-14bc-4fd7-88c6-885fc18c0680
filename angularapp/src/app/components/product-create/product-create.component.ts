import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  
  addProductForm:FormGroup
  constructor(private formBuilder:FormBuilder,private productService:ProductService,private router:Router) { 
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

  ngOnInit(): void {}

  showPopupMsg(title: string, message: string) {
    alert(`${title}: ${message}`); // Simple alert popup
  }

  createProduct() {
    console.log(this.addProductForm.valid)
    console.log(this.addProductForm.value)
  //  if(this.addProductForm.valid)
    {
      this.productService.addProduct(this.addProductForm.value).subscribe((data)=>{
      console.log(data)
      this.showPopupMsg("Success", "Product added Successfully!!!");
      alert("Product added Successfully!!!");
      this.router.navigate(['/viewproduct']);
    }, 
    (error)=>{
        console.log(JSON.stringify(error))
        this.showPopupMsg("Error", "Product Failed to add!!!");
    })
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
