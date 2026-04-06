export * from './auth';

export interface User {
  id: string;
  username: string;
  email: string;
  token?: string;
}

export interface Food {
  id: string | number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  quantity: number;
  category?: string;
}

export interface CreateFoodRequest {
  name: string;
  price: number;
  description: string;
  imageUrl: string;
  quantity: number;
}

export interface InventoryResponse {
  id: number;
  name: string;
  quantity: number;
  inStock: boolean;
}

export type FoodResponse = Food;

export interface CartItem {
  food: Food;
  quantity: number;
}

export interface OrderItemRequest {
  foodId: number;
  quantity: number;
}

export interface CreateOrderRequest {
  userId: number;
  items: OrderItemRequest[];
}

export interface OrderItemResponse {
  foodId: number;
  foodName: string;
  quantity: number;
  price: number;
}

export interface OrderResponse {
  id: number;
  userId: number;
  items: OrderItemResponse[];
  status: 'PENDING' | 'PREPARING' | 'DELIVERING' | 'COMPLETED' | 'CANCELLED';
}
