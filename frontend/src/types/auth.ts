export type Role = 'USER' | 'ADMIN';

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

export interface AuthResponse {
  token: string;
}

export interface UserResponse {
  id: number;
  username: string;
  role: Role;
}

export interface LoginRequest {
  email: string;
  password:  string;
}

export interface RegisterRequest {
  email: string;
  password:  string;
  confirmPassword: string;
}
