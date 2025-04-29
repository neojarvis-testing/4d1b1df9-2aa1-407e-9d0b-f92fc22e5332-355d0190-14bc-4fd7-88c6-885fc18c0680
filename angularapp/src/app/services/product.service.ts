import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Observable } from 'rxjs';
import { Api } from '../api-urls';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  addProduct(product:Product):Observable<Product>{
    console.log(product)
    return this.http.post<Product>(`${Api.apiUrl}/api/products`,product)
  }

  getAllProducts():Observable<Product[]>{
    return this.http.get<Product[]>(`${Api.apiUrl}/api/products`)
  }

  getProductById(productId:number):Observable<Product>{
    return this.http.get<Product>(`${Api.apiUrl}/api/products/${productId}`)
  }

  updateProduct(productId:number,product:Product):Observable<Product>{
    return this.http.put<Product>(`${Api.apiUrl}/api/products/${productId}`,product)
  }

  deleteProduct(productId:number):Observable<void>{
    return this.http.delete<void>(`${Api.apiUrl}/api/products/${productId}`)
  }
  constructor(private http:HttpClient) { }
}
