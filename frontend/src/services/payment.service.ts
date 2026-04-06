import type { ApiResponse } from '../types';
import paymentApi from './payment.api';

export const paymentService = {
  createPaymentIntent: async (orderId: string, amount: number): Promise<ApiResponse<{ id: string; clientSecret: string }>> => {
    const response = await paymentApi.post<ApiResponse<{ id: string; clientSecret: string }>>('/payments/create-intent', { orderId, amount });
    return response.data;
  },

  processPayment: async (paymentData: any): Promise<ApiResponse<{ success: boolean; transactionId: string }>> => {
    const response = await paymentApi.post<ApiResponse<{ success: boolean; transactionId: string }>>('/payments/process', paymentData);
    return response.data;
  },

  getPaymentStatus: async (paymentId: string): Promise<ApiResponse<{ status: string }>> => {
    const response = await paymentApi.get<ApiResponse<{ status: string }>>(`/payments/${paymentId}`);
    return response.data;
  },
};
