import type { CreateOrderRequest, OrderResponse, ApiResponse } from '../types';
import orderApi from './order.api';

export const orderService = {
  createOrder: async (orderData: CreateOrderRequest): Promise<ApiResponse<OrderResponse>> => {
    const response = await orderApi.post<ApiResponse<OrderResponse>>('/orders', orderData);
    return response.data;
  },

  getAllOrders: async (): Promise<ApiResponse<OrderResponse[]>> => {
    const response = await orderApi.get<ApiResponse<OrderResponse[]>>('/orders');
    return response.data;
  },
};
