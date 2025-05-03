import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { Order } from 'src/app/models/order.model';
 
declare var bootstrap: any;
 
@Component({
  selector: 'app-myorder',
  templateUrl: './myorder.component.html',
  styleUrls: ['./myorder.component.css']
})
export class MyorderComponent implements OnInit {
  orders: Order[] = [];
  selectedOrderItems: any[] = [];
  selectedOrder: Order | null = null;
  isLoading: boolean = true;
  isItemsLoading: boolean = false;
  isTrackingLoading: boolean = false;
  errorMessage: string = '';
  itemsErrorMessage: string = '';
  trackingErrorMessage: string = '';
  orderStatusOptions = ['Pending', 'Accepted', 'Dispatched', 'Out For Delivery', 'Delivered'];
 
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
 
  getUserIdFromLocalStorage(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null;
  }
 
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
 
  openCancelOrder(order: Order): void {
    if (order.orderStatus.toLowerCase() !== 'cancelled') {
      this.selectedOrder = order;
    }
  }
 
  cancelOrder(): void {
    if (!this.selectedOrder) {
      return;
    }
    if (this.selectedOrder.orderStatus.toLowerCase() === 'delivered') {
      alert('Delivered orders cannot be canceled.');
      return;
    }
   
    this.selectedOrder.orderStatus = 'Cancelled';
    this.orderService.updateOrderStatus(this.selectedOrder.orderId, 'Cancelled').subscribe(
      () => {
        // Reflect changes in the orders list
        this.orders = this.orders.map(order =>
          order.orderId === this.selectedOrder!.orderId ? { ...order, orderStatus: 'Cancelled' } : order
        );
        this.selectedOrder = null;
       
        this.hideModal('cancelOrderModal'); // ✅ Close the modal after cancellation
      },
      error => {
        this.errorMessage = 'Failed to cancel the order. Please try again.';
      }
    );
  }
 
  hideModal(modalId: string): void {
    const modalElement = document.getElementById(modalId);
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      modal?.hide();
    }
  }  
 
  isStepActive(currentStatus: string, orderStatus: string): boolean {
    return this.orderStatusOptions.indexOf(currentStatus) <= this.orderStatusOptions.indexOf(orderStatus);
  }
}