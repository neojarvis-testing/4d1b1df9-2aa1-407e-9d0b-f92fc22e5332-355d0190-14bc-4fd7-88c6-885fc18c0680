import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-myorder',
  templateUrl: './myorder.component.html',
  styleUrls: ['./myorder.component.css']
})
export class MyorderComponent implements OnInit {
  orders: Order[] = [];
  selectedOrderItems: any[] = []; // Holds items for the selected order
  selectedOrder: Order | null = null; // Holds the selected order for cancellation
  isLoading: boolean = true;
  isItemsLoading: boolean = false; // Loading state for order items
  isTrackingLoading: boolean = false; // Loading state for order tracking
  errorMessage: string = '';
  itemsErrorMessage: string = '';
  trackingErrorMessage: string = '';
  
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    const userId = this.getUserIdFromLocalStorage();
    if (userId) {
      this.getUserOrders(userId);
    } else {
      this.errorMessage = 'User ID not found. Please log in.';
      this.isLoading = false;
    }
  }

  // Fetch orders for the user
  getUserOrders(userId: number): void {
    this.orderService.getOrdersByUserId(userId).subscribe(
      (data: Order[]) => {
        this.orders = data;
        this.isLoading = false;
      },
      error => {
        this.errorMessage = 'Failed to fetch user orders. Please try again later.';
        this.isLoading = false;
      }
    );
  }

  // Retrieve user ID from local storage
  getUserIdFromLocalStorage(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null;
  }

  // Fetch items for a specific order
  viewOrderItems(orderId: number): void {
    this.isItemsLoading = true;
    this.itemsErrorMessage = '';
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

  // Determine the badge class based on order status
  getStatusBadgeClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'pending': return 'bg-warning text-dark';
      case 'accepted': return 'bg-success';
      case 'dispatched': return 'bg-primary';
      case 'out for delivery': return 'bg-info';
      case 'delivered': return 'bg-secondary';
      case 'cancelled': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }

  // Open cancel order modal and store the selected order
  openCancelOrder(order: Order): void {
    if (order.orderStatus.toLowerCase() !== 'cancelled') {
      this.selectedOrder = order;
    }
  }

  // Cancel the order and update its status
  cancelOrder(): void {
    if (!this.selectedOrder) {
      return;
    }

    // Update order status to 'Cancelled'
    this.selectedOrder.orderStatus = 'CANCELLED';

    // Call service to update the order in backend
    this.orderService.updateOrderStatus(this.selectedOrder.orderId, 'CANCELLED').subscribe(
      () => {
        // Reflect changes in the orders list
        this.orders = this.orders.map(order => 
          order.orderId === this.selectedOrder!.orderId ? { ...order, orderStatus: 'CANCELLED' } : order
        );

        // Clear selected order
        this.selectedOrder = null;
      },
      error => {
        this.errorMessage = 'Failed to cancel the order. Please try again.';
      }
    );
  }
}
