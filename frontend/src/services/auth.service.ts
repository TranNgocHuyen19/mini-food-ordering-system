import type { LoginRequest, RegisterRequest, AuthResponse, UserResponse, ApiResponse } from '../types';
import userApi from './user.api';

export const authService = {
  login: async (request: LoginRequest): Promise<ApiResponse<AuthResponse>> => {
    const response = await userApi.post<ApiResponse<AuthResponse>>('/users/login', request);
    return response.data;
  },

  register: async (request: RegisterRequest): Promise<ApiResponse<AuthResponse>> => {
    const response = await userApi.post<ApiResponse<AuthResponse>>('/users/register', request);
    return response.data;
  },

  getMe: async (): Promise<ApiResponse<UserResponse>> => {
    const response = await userApi.get<ApiResponse<UserResponse>>('/users/me');
    return response.data;
  },

  logout: async (): Promise<ApiResponse<void>> => {
    const response = await userApi.post<ApiResponse<void>>('/users/logout');
    localStorage.removeItem('token');
    return response.data;
  },
};
