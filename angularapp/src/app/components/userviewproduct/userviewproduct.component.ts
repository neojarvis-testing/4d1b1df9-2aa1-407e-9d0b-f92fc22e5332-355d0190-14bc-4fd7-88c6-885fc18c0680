import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Api } from 'src/app/api-urls';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-userviewproduct',
  templateUrl: './userviewproduct.component.html',
  styleUrls: ['./userviewproduct.component.css']
})
export class UserviewproductComponent implements OnInit {

  products:Product[]=[]
  ApiUrl:string=Api.apiUrl
  constructor(private router:Router,private productService:ProductService) { }

  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getAllProducts().subscribe((data)=>{
      this.products=data
    })
  }

  navigateToReview(productId:number){
    this.router.navigate(['/review',productId])
  }

  navigateToMyReview(){
    this.router.navigate(['/viewReview'])
  }

  getImageUrl(imageName: string): string {
    let a = this.ApiUrl+"/"+imageName
    console.log(a)
    a='https://8080-fdefbfaaafbdebbfaadbececcfeddbcfdcfcc.premiumproject.examly.io/1746002370897_Image.jpg'
    console.log(a)
    return `${this.ApiUrl}/${imageName}`;
  }
}
