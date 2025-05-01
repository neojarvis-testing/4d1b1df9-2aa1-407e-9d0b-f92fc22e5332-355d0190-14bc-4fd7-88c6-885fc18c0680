import { Component, OnInit } from '@angular/core';
import { OrderItem } from 'src/app/models/order-item.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {


  cartItems: OrderItem[] = [];
  cartItem: number;

    constructor(private cartService: CartService) { }
  
    ngOnInit(): void {
      this.cartItems = this.cartService.getCartItems();
    }
  
    removeFromCart(productId: number): void {
      this.cartService.removeFromCart(productId);
      this.cartItems = this.cartService.getCartItems(); // Refresh the cart items
    }
  
    clearCart(): void {
      this.cartService.clearCart();
      this.cartItems = [];
    }
  
    getTotalPrice(): number {
      return this.cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    }
  

}
