import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';
 
@Component({
  selector: 'app-orderplaced',
  templateUrl: './orderplaced.component.html',
  styleUrls: ['./orderplaced.component.css']
})
export class OrderplacedComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedOrderItems: any[] = [];
  selectedOrder: Order | null = null;
  isLoading: boolean = true;
  isItemsLoading: boolean = false;
  errorMessage: string = '';
  itemsErrorMessage: string = '';
  searchQuery: string = '';
  sortOrder: 'asc' | 'desc' = 'asc';
  orderStatusOptions = ['CONFIRMED', 'DISPATCHED', 'DELIVERED'];
 
  isLoadingPagenation: boolean = true;
  currentPage: number = 1;
  itemsPerPage: number = 8;
 
  constructor(private readonly orderService: OrderService) {}
 
  ngOnInit(): void {
    this.getAllOrders();
  }
 
  onPageChange(page: number): void {
    this.currentPage = page;
  }
 
  // Fetch all orders
  getAllOrders(): void {
    this.orderService.getAllOrders().subscribe(
      (data: Order[]) => {
        this.orders = data;
        this.filteredOrders = data;
        this.isLoading = false;
      },
      error => {
        this.errorMessage = 'Failed to fetch orders. Please try again later.';
        this.isLoading = false;
      }
    );
  }
 
  // Update order status (Pass selected status properly)
  updateOrderStatus(order: Order): void {
    if (!order) return;
 
    const updatedOrder = { ...order, orderStatus: order.orderStatus }; // Ensure selected status is sent
 
    this.orderService.updateOrder(order.orderId, updatedOrder).subscribe(
      (updatedOrderResponse) => {
        order.orderStatus = updatedOrderResponse.orderStatus; // Correctly reflect the updated status
        console.log(`Order status updated successfully: ${updatedOrderResponse.orderStatus}`);
      },
      (error) => {
        this.errorMessage = 'Failed to update order status. Please try again.';
        console.error('Error updating order status:', error);
      }
    );
  }
 
  // Disable statuses dynamically
  shouldDisableStatus(currentStatus: string, statusOption: string): boolean {
    const statusHierarchy = {
      'CONFIRMED': ['CONFIRMED'],
      'DISPATCHED': ['CONFIRMED', 'DISPATCHED'],
      'DELIVERED': ['CONFIRMED', 'DISPATCHED', 'DELIVERED']
    };
 
    return statusHierarchy[currentStatus]?.includes(statusOption) || false;
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
 
  // Sort orders by date
  sortByDate(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.filteredOrders.sort((a, b) => {
      const dateA = new Date(a.orderDate).getTime();
      const dateB = new Date(b.orderDate).getTime();
      return this.sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
 
  // View order items for a specific order
  viewOrderItems(orderId: number): void {
    this.isItemsLoading = true;
    this.orderService.getOrderItemByOrderId(orderId).subscribe(
      (items: any[]) => {
        this.selectedOrderItems = items;
        this.isItemsLoading = false;
      },
      error => {
        this.itemsErrorMessage = 'Failed to fetch order items. Please try again later.';
        this.isItemsLoading = false;
      }
    );
  }
 
  // Track order status for a selected order
  trackStatus(order: Order): void {
    this.selectedOrder = order;
  }
}