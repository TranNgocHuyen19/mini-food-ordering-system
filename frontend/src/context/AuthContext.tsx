import React, { createContext, useContext, useState } from 'react';
import type { UserResponse, LoginRequest, RegisterRequest } from '../types';
import { authService } from '../services';

interface AuthContextType {
  user: UserResponse | null;
  loading: boolean;
  login: (request: LoginRequest) => Promise<void>;
  register: (request: RegisterRequest) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserResponse | null>(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });
  const [loading] = useState(false);

  const login = async (request: LoginRequest) => {
    const response = await authService.login(request);
    if (response.success) {
      localStorage.setItem('token', response.data.token);
      const userResponse = await authService.getMe();
      if (userResponse.success) {
        setUser(userResponse.data);
        localStorage.setItem('user', JSON.stringify(userResponse.data));
      }
    } else {
        throw new Error(response.message);
    }
  };

  const register = async (request: RegisterRequest) => {
    const response = await authService.register(request);
    if (response.success && response.data.token) {
      localStorage.setItem('token', response.data.token);
      const userResponse = await authService.getMe();
      if (userResponse.success) {
        setUser(userResponse.data);
        localStorage.setItem('user', JSON.stringify(userResponse.data));
      }
    } else if (!response.success) {
      throw new Error(response.message);
    }
  };

  const logout = async () => {
    try {
      await authService.logout();
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      setUser(null);
      localStorage.removeItem('user');
      localStorage.removeItem('token');
    }
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
