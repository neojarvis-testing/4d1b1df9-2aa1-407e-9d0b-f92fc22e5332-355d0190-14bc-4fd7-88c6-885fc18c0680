import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order.model';
import { Observable } from 'rxjs';
import { Api } from '../api-urls';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  
  // Constructor method to inject HttpClient service
  constructor(private http:HttpClient) { }

  /**
   * Method to place a new order.
   * @returns Observable containing the response
   */
  placeOrder(order:Order):Observable<any>{
    console.log(order)
    return this.http.post<any>(`${Api.apiUrl}/api/orders`,order)
  }

  /**
   * Method to fetch an order by its ID.
   * @returns Observable containing the fetched order object
   */
  getOrderById(orderId:number):Observable<Order>{
    return this.http.get<Order>(`${Api.apiUrl}/api/orders/${orderId}`)
  }

  /**
   * Method to fetch all orders.
   * @returns Observable containing an array of all orders
   */
  getAllOrders():Observable<Order[]>{
    return this.http.get<Order[]>(`${Api.apiUrl}/api/orders`)
  }

  /**
   * Method to fetch orders by user ID.
   * @returns Observable containing an array of orders for the given user
   */
  getOrdersByUserId(userId:number):Observable<Order[]>{
    return this.http.get<Order[]>(`${Api.apiUrl}/api/orders/user/${userId}`)
  }

  /**
   * Method to update an existing order.
   * @returns Observable containing the updated order object
   */
  updateOrder(orderId:number,order:Order):Observable<Order>{
    return this.http.put<Order>(`${Api.apiUrl}/api/orders/${orderId}`,order)
  }

  /**
   * Method to delete an order by its ID.
   * @returns Observable containing void (no return data)
   */
  deleteOrder(orderId:number):Observable<void>{
    return this.http.delete<void>(`${Api.apiUrl}/api/orders/${orderId}`)
  }

}
