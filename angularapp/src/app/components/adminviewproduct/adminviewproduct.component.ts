import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-adminviewproduct',
  templateUrl: './adminviewproduct.component.html',
  styleUrls: ['./adminviewproduct.component.css']
})
export class AdminviewproductComponent implements OnInit {

  products:Product[]=[];
  searchText:string='';
  filteredProduct:Product[]=[]

  constructor(private productService:ProductService,private router:Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getAllProducts().subscribe((data)=>{
      this.products=data;
      this.filteredProduct=data;
    })
  }
  deleteProduct(productId:number){
    if(confirm("Are you sure you want to delete product")){
      this.productService.deleteProduct(productId).subscribe(()=>{
        console.log("1")
        this.getAllProducts();
        console.log("2")
      },
      (error)=>{
        console.log(error)
      })
    }
  }

  updateProduct(productId:number){
    this.router.navigate(['/updateProduct',productId])
  } 

  search(){
    if(this.searchText=='')
      return this.filteredProduct;
    else
    this.filteredProduct=this.products.filter((data)=>JSON.stringify(data).toLowerCase().includes(this.searchText.toLowerCase()))
  }
}
