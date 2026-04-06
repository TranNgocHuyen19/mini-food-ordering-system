import type { ApiResponse, FoodResponse, CreateFoodRequest, InventoryResponse } from '../types';
import foodApi from './food.api';

export const foodService = {
  getAllFoods: async (): Promise<ApiResponse<FoodResponse[]>> => {
    const response = await foodApi.get<ApiResponse<FoodResponse[]>>('/foods');
    return response.data;
  },

  getFoodById: async (id: number): Promise<ApiResponse<FoodResponse>> => {
    const response = await foodApi.get<ApiResponse<FoodResponse>>(`/foods/${id}`);
    return response.data;
  },

  createFood: async (foodData: CreateFoodRequest): Promise<ApiResponse<FoodResponse>> => {
    const response = await foodApi.post<ApiResponse<FoodResponse>>('/foods', foodData);
    return response.data;
  },

  updateFood: async (id: number, foodData: Partial<CreateFoodRequest>): Promise<ApiResponse<FoodResponse>> => {
    const response = await foodApi.put<ApiResponse<FoodResponse>>(`/foods/edit/${id}`, foodData);
    return response.data;
  },

  deleteFood: async (id: number): Promise<ApiResponse<void>> => {
    const response = await foodApi.delete<ApiResponse<void>>(`/foods/delete/${id}`);
    return response.data;
  },

  checkStock: async (id: number): Promise<ApiResponse<InventoryResponse>> => {
    const response = await foodApi.get<ApiResponse<InventoryResponse>>(`/foods/${id}/stock`);
    return response.data;
  },

  updateQuantity: async (id: number, quantity: number): Promise<ApiResponse<FoodResponse>> => {
    const response = await foodApi.patch<ApiResponse<FoodResponse>>(`/foods/edit/${id}/quantity`, null, {
      params: { quantity }
    });
    return response.data;
  },

  getFoodsByCategory: async (category: string): Promise<ApiResponse<FoodResponse[]>> => {
    const response = await foodApi.get<ApiResponse<FoodResponse[]>>(`/foods/category/${category}`);
    return response.data;
  },
};
