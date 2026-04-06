import type { Order } from '../types';
import api from './api';

export const authService = {
  login: async (email: string, password: string) => {
    const response = await api.post('/auth/login', { email, password });
    return response.data;
  },

  register: async (username: string, email: string, password: string) => {
    const response = await api.post('/auth/register', { username, email, password });
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('token');
  },
};

export const foodService = {
  getAllFoods: async () => {
    const response = await api.get('/foods');
    return response.data;
  },

  getFoodById: async (id: string) => {
    const response = await api.get(`/foods/${id}`);
    return response.data;
  },
};

export const orderService = {
  createOrder: async (orderData: Partial<Order>) => {
    const response = await api.post('/orders', orderData);
    return response.data;
  },

  getUserOrders: async () => {
    const response = await api.get('/orders/my-orders');
    return response.data;
  },
};
