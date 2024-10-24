
export interface AccountListResponse {
    SELLER: Seller[]; 
    ADMIN: Admin[];
    USER: User[];
}

export interface Seller {
    id: number;
    username: string;
    email: string;
    password: string; 
    role: string;
    createdDate: string;
    active: boolean;
  }
  
  export interface Admin {
    id: number;
    username: string;
    email: string;
    password: string; 
    role: string;
    createdDate: string;
    active: boolean;
  }
  
  export interface User {
    id: number;
    username: string;
    email: string;
    password: string; 
    role: string;
    createdDate: string;
    active: boolean;
  }
  
  