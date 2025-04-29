import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-orderplaced',
  templateUrl: './orderplaced.component.html',
  styleUrls: ['./orderplaced.component.css']
})
export class OrderplacedComponent implements OnInit {

  orders: Order[] = []; // Full list of orders
  filteredOrders: Order[] = []; // Orders after filtering
  isLoading: boolean = true; // Show loading state
  errorMessage: string = ''; // Error message (if any)
  searchQuery: string = ''; // Search query input by user

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.getAllOrders(); // Fetch all orders on initialization
  }

  // Fetch all orders
  getAllOrders(): void {
    this.orderService.getAllOrders().subscribe(
      (data: Order[]) => {
        console.log('API response:', data); // Debug log
        this.orders = data; // Store fetched orders
        this.filteredOrders = data; // Initialize filtered orders
        this.isLoading = false; // Turn off loading state
      },
      error => {
        console.error('Error fetching all orders:', error); // Log error
        this.errorMessage = 'Failed to fetch orders. Please try again later.';
        this.isLoading = false; // Turn off loading state
      }
    );
  }

  // Filter orders based on search query
  onSearch(): void {
    const query = this.searchQuery.toLowerCase().trim();
    this.filteredOrders = this.orders.filter(order =>
      order.orderId.toString().includes(query) ||
      order.orderStatus.toLowerCase().includes(query) ||
      order.shippingAddress.toLowerCase().includes(query) ||
      order.billingAddress.toLowerCase().includes(query)
    );
  }
}