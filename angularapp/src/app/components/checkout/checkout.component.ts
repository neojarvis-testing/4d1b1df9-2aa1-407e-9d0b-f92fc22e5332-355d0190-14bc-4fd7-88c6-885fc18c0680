import { Component, OnInit } from '@angular/core';
import { OrderItem } from 'src/app/models/order-item.model';
import { Order } from 'src/app/models/order.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {


  
cartItems: OrderItem[] = [];
  userId: number;
  shippingAddress: string = '';
  billingAddress: string = '';

  constructor(private cartService: CartService, private orderService: OrderService) { }

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.userId = +localStorage.getItem('userId'); // Retrieve user ID from local storage
  }

  placeOrder(): void {
    if (!this.shippingAddress || !this.billingAddress) {
      alert('Please enter both shipping and billing addresses.');
      return;
    }

    const order: Order = {
      orderDate: new Date().toISOString().split('T')[0],
      orderStatus: 'PROCESSING',
      shippingAddress: this.shippingAddress,
      billingAddress: this.billingAddress,
      totalAmount: this.getTotalPrice(),
      userId: this.userId,
      orderItems: this.cartItems
    };

    this.orderService.placeOrder(order).subscribe(
      (response) => {
        console.log('Order placed successfully:', response);
        alert('Order placed successfully!');
        this.cartService.clearCart();
      },
      (error) => {
        console.error('Failed to place order:', error);
        alert('Failed to place order.');
      }
    );
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


