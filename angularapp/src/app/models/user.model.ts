// Represents a User entity with details about users
export interface User {
 
    // Optional: Automatically assigned by the system
    userId?: number; 
 
    // Required: Must follow email format validation
    email: string; 
 
    //Required:Must be provided while registering and logging in
    password: string; 

    //Required:Must be provided while registering
    //confirmPassword:string;
 
    // Required: Unique identifier for the user
    username: string; 
 
    // Required: Include proper format validation
    mobileNumber: string;
 
    // Specify the user role "ADMIN" | "USER"
    userRole?: string;
}