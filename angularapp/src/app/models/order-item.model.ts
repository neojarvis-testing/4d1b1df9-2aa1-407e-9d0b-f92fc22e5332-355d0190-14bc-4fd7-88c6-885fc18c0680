import { Product } from "./product.model";

// Represents an individual item in an order, linked to a specific product
export interface OrderItem{

    // Unique identifier for the order item (optional)
    orderItemId?:number;

    // The product associated with the order item
    productId:number;

    productName:string;

    // Quantity of the product ordered
    quantity:number;

    // Total price of the product for the given quantity
    price:number;
}
