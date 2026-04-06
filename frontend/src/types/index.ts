export interface User {
  id: string;
  username: string;
  email: string;
  token?: string;
}

export interface Food {
  id: string;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  category: string;
}

export interface CartItem {
  food: Food;
  quantity: number;
}

export interface Order {
  id: string;
  userId: string;
  items: CartItem[];
  totalAmount: number;
  status: 'PENDING' | 'PREPARING' | 'DELIVERING' | 'COMPLETED' | 'CANCELLED';
  address: string;
  phone: string;
  createdAt: string;
}
