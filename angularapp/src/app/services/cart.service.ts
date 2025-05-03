import { Injectable } from '@angular/core';
import { OrderItem } from '../models/order-item.model';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

 
private cartItems: OrderItem[] = [];
  private readonly cartSubject = new BehaviorSubject<OrderItem[]>(this.cartItems);

  constructor() { }

  addToCart(product: Product, quantity: number): void {
    const existingItem = this.cartItems.find(item => item.productId === product.productId);
    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      const orderItem: OrderItem = {
        productId: product.productId,
        productName: product.productName,
        quantity: quantity,
        price: product.price
      };
      this.cartItems.push(orderItem);
    }
    this.cartSubject.next(this.cartItems);
  }

  removeFromCart(productId: number): void {
    this.cartItems = this.cartItems.filter(item => item.productId !== productId);
    this.cartSubject.next(this.cartItems);
  }

  getCartItems(): OrderItem[] {
    return [...this.cartItems];
  }

  clearCart(): void {
    this.cartItems = [];
    this.cartSubject.next(this.cartItems);
  }


}
