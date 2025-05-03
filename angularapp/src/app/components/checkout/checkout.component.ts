import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
 checkoutForm: FormGroup;

 constructor(
 private readonly cartService: CartService,
 private readonly orderService: OrderService,
 private readonly fb: FormBuilder
 ) { }

 ngOnInit(): void {
 this.cartItems = this.cartService.getCartItems();
 this.userId = +localStorage.getItem('userId'); // Retrieve user ID from local storage

 this.checkoutForm = this.fb.group({
 shippingAddress: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9,-\\s]+$')]],
 billingAddress: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9,-\\s]+$')]]
 });
 }

 placeOrder(): void {
 if (this.checkoutForm.invalid) {
 this.checkoutForm.markAllAsTouched();
 return;
 }

 const order: Order = {
 orderDate: new Date().toISOString().split('T')[0],
 orderStatus: 'PROCESSING',
 shippingAddress: this.checkoutForm.value.shippingAddress,
 billingAddress: this.checkoutForm.value.billingAddress,
 totalAmount: this.getTotalPrice(),
 userId: this.userId,
 orderItems: this.cartItems
 };

 this.orderService.placeOrder(order).subscribe(
 (response) => {
 console.log('Order placed successfully:', response);
 alert('Order placed successfully!');
 this.cartService.clearCart();
 this.cartItems = []; // Clear the cart items
 this.checkoutForm.reset(); // Reset the form
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


