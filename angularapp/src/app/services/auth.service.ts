import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { Login } from '../models/login.model';
import { Api } from '../api-urls';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Constructor to inject the HttpClient dependency for making HTTP calls
  constructor(private http:HttpClient) { }

  /**
   * Method to register a new user
   * Sends a POST request to the API endpoint with the user details
   */
  register(user:User):Observable<any>{
    console.log(user);
    return this.http.post<any>(`${Api.apiUrl}/api/register`,user);
  }

  /**
   * Method to log in a user
   * Sends a POST request to the API endpoint with login details (email and password)
   */
  login(login:Login):Observable<any>{
    return this.http.post<any>(`${Api.apiUrl}/api/login`,login);
  }

  /**
   * Method to check if the logged-in user has an admin role
   * Reads the 'userRole' value from localStorage and checks if it equals 'ADMIN'
   */
  isAdmin():boolean{
    let role=localStorage.getItem('userRole');
    return role=='ADMIN';
  }

  /**
   *  Method to check if the logged-in user has a user role
   * Reads the 'userRole' value from localStorage and checks if it equals 'USER'
   */
  isUser():boolean{
    let role=localStorage.getItem('userRole');
    return role=='USER';
  }

  /**
   * Method to check if there is a currently logged-in user
   * Verifies if 'userRole' exists in localStorage
   */
  isLoggedUser():boolean{
    let role=localStorage.getItem('userRole');
    return role!=null && role!='';
  }

  /**
   * Method to log out the current user
   * Removes 'userId', 'userRole', and 'token' from localStorage to clear session data
   */
  loggedOut():void{
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
  }
}
