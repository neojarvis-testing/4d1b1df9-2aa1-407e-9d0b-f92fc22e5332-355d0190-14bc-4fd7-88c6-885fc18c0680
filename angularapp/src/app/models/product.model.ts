// Represents a Product entity with details about products
export interface Product{

    // Unique identifier for the product (optional)
    productId?:number;

    // Name of the product
    productName:string;
    
    // Description of the product
    description:string;
    
    // Price of the product
    price:number;
    
    // Quantity of the product available in stock
    stockQuantity:number;
    
    // Category under which the product falls
    category:string;
    
    // Brand name of the product
    brand:string;
    
    // URL or path to the cover image of the product
    coverImage:string;
}