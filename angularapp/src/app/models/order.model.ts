import { OrderItem } from "./order-item.model";

// Represents an Order entity containing details about user orders
export interface Order{

    // Unique identifier for the order (optional)
    orderId?:number;

    // The date when the order was placed
    orderDate:string;

    // Current status of the order (e.g., Pending, Shipped, Delivered)
    orderStatus:string;

    // Shipping address for the order
    shippingAddress:string;

    // Billing address for the order
    billingAddress:string;

    // Total amount for the order (calculated from order items)
    totalAmount:number;

    // User who placed the order
    userId:number;

    // List of items included in the order
    orderItems:OrderItem[];
}