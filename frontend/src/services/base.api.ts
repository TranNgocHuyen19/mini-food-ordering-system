import axios from 'axios';
import type { AxiosInstance } from 'axios';

/**
 * Tạo một instance axios với các cấu hình chung
 * @param baseURL URL cơ sở cho service
 * @returns AxiosInstance
 */
export const createServiceInstance = (baseURL: string): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // Interceptor cho request để đính kèm JWT token
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token');
      if (token && config.headers) {
        config.headers.Authorization = `Bearer ${token}`;
        console.log(`[Service Request] ${config.baseURL}${config.url} with token: ${token.substring(0, 10)}...`);
      } else {
        console.warn(`[Service Request] No token found for ${config.url}`);
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // Interceptor cho response để xử lý lỗi chung (nếu cần)
  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      // Xử lý lỗi tập trung tại đây (ví dụ: logout nếu 401)
      if (error.response?.status === 401) {
        // localStorage.removeItem('token');
        // window.location.href = '/login';
      }
      return Promise.reject(error);
    }
  );

  return instance;
};
