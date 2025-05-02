import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
  orderStatusOptions = ['Pending', 'Accepted', 'Dispatched', 'Out For Delivery', 'Delivered'];

  constructor(private readonly orderService: OrderService) {}

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
  updateOrderStatus(order: Order, newStatus: string): void {
    if (!order) return; // Ensure order is valid
  
    // Preserve existing order fields while updating status
    const updatedOrder = { 
      ...order, 
      orderStatus: newStatus 
    };
  
    this.orderService.updateOrder(order.orderId, updatedOrder).subscribe(
      (updatedOrderResponse) => {
        // Update UI with the response from backend
        order.orderStatus = updatedOrderResponse.orderStatus;
        console.log(`Order status updated successfully: ${updatedOrderResponse.orderStatus}`);
      },
      (error) => {
        this.errorMessage = 'Failed to update order status. Please try again.';
        console.error('Error updating order status:', error);
      }
    );
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
        this.cdr.detectChanges();
      },
      error => {
        this.errorMessage = 'Failed to cancel the order. Please try again.';
      }
    );
  }
}
// import { Component, OnInit } from '@angular/core';
// import { Order } from 'src/app/models/order.model';
// import { OrderService } from 'src/app/services/order.service';

// @Component({
//   selector: 'app-myorder',
//   templateUrl: './myorder.component.html',
//   styleUrls: ['./myorder.component.css']
// })
// export class MyorderComponent implements OnInit {
//   orders: Order[] = [];
//   selectedOrderItems: any[] = []; // Holds items for the selected order
//   selectedOrder: Order | null = null; // Holds the selected order for cancellation
//   isLoading: boolean = true;
//   isItemsLoading: boolean = false; // Loading state for order items
//   isTrackingLoading: boolean = false; // Loading state for order tracking
//   errorMessage: string = '';
//   itemsErrorMessage: string = '';
//   trackingErrorMessage: string = '';
//   orderStatusOptions = ['PROCESSING', 'CONFIRMED', 'DISPATCHED', 'DELIVERED', 'CANCELLED']; // Status options aligned with backend enum

//   constructor(private orderService: OrderService) {}

//   ngOnInit(): void {
//     const userId = this.getUserIdFromLocalStorage();
//     if (userId) {
//       this.getUserOrders(userId);
//     } else {
//       this.errorMessage = 'User ID not found. Please log in.';
//       this.isLoading = false;
//     }
//   }

//   // Fetch orders for the user
//   getUserOrders(userId: number): void {
//     this.orderService.getOrdersByUserId(userId).subscribe(
//       (data: Order[]) => {
//         this.orders = data;
//         this.isLoading = false;
//       },
//       error => {
//         this.errorMessage = 'Failed to fetch user orders. Please try again later.';
//         this.isLoading = false;
//       }
//     );
//   }

//   // Retrieve user ID from local storage
//   getUserIdFromLocalStorage(): number | null {
//     const userId = localStorage.getItem('userId');
//     return userId ? parseInt(userId, 10) : null;
//   }

//   // Fetch items for a specific order
//   viewOrderItems(orderId: number): void {
//     this.isItemsLoading = true;
//     this.itemsErrorMessage = '';
//     this.orderService.getOrderItemByOrderId(orderId).subscribe(
//       (items: any[]) => {
//         this.selectedOrderItems = items;
//         this.isItemsLoading = false;
//       },
//       error => {
//         this.itemsErrorMessage = 'Failed to fetch order items. Please try again later.';
//         this.isItemsLoading = false;
//       }
//     );
//   }

//   // Determine the badge class based on order status
//   getStatusBadgeClass(status: string): string {
//     switch (status.toUpperCase()) {
//       case 'PROCESSING': return 'bg-warning text-dark';
//       case 'CONFIRMED': return 'bg-success';
//       case 'DISPATCHED': return 'bg-primary';
//       case 'DELIVERED': return 'bg-secondary';
//       case 'CANCELLED': return 'bg-danger';
//       default: return 'bg-secondary';
//     }
//   }

//   // Open cancel order modal and store the selected order
//   openCancelOrder(order: Order): void {
//     if (order.orderStatus !== 'CANCELLED') {
//       this.selectedOrder = order;
//     }
//   }

//   // Update order status (Admin functionality)
//   updateOrderStatus(order: Order, newStatus: string): void {
//     if (!order) return;

//     const updatedOrder = { 
//       ...order, 
//       orderStatus: newStatus 
//     };

//     this.orderService.updateOrder(order.orderId, updatedOrder).subscribe(
//       () => {
//         order.orderStatus = newStatus; // Reflect UI changes
//       },
//       error => {
//         this.errorMessage = 'Failed to update order status. Please try again.';
//       }
//     );
//   }

//   // Cancel an order and update its status
//   cancelOrder(): void {
//     if (!this.selectedOrder) {
//       return;
//     }

//     this.updateOrderStatus(this.selectedOrder, 'CANCELLED');
//     this.selectedOrder = null;
//   }
// }
