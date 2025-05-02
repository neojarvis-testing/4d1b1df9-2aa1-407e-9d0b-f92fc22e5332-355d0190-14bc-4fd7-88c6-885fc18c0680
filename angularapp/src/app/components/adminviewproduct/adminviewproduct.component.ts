import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Api } from 'src/app/api-urls';
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
  ApiUrl:string=Api.apiUrl

  constructor(private readonly productService:ProductService,private readonly router:Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getAllProducts().subscribe((data)=>{
         this.products=data;
         console.log(this.products)
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

  getImageUrl(imageName: string): string {
    let a = this.ApiUrl+"/"+imageName
    console.log(a)
    a='https://8080-fdefbfaaafbdebbfaadbececcfeddbcfdcfcc.premiumproject.examly.io/1746002370897_Image.jpg'
    console.log(a)
    return `${this.ApiUrl}/${imageName}`;
  }
}
