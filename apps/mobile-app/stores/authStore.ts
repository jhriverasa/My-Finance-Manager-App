import { create } from 'zustand';
import * as SecureStore from 'expo-secure-store';
import { authApi } from '../services/api';

interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
}

interface AuthState {
  user: User | null;
  accessToken: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
  
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string, firstName: string, lastName: string) => Promise<void>;
  logout: () => void;
  refreshTokens: () => Promise<void>;
  clearError: () => void;
  loadStoredTokens: () => Promise<void>;
}

const useAuthStore = create<AuthState>((set, get) => ({
  user: null,
  accessToken: null,
  refreshToken: null,
  isAuthenticated: false,
  isLoading: false,
  error: null,

  login: async (email: string, password: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await authApi.login(email, password);
      const { accessToken, refreshToken } = response.data;
      
      await SecureStore.setItemAsync('accessToken', accessToken);
      await SecureStore.setItemAsync('refreshToken', refreshToken);
      
      set({ 
        accessToken, 
        refreshToken, 
        isAuthenticated: true, 
        isLoading: false 
      });
    } catch (error: any) {
      set({ error: error as string, isLoading: false });
      throw error;
    }
  },

  register: async (email: string, password: string, firstName: string, lastName: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await authApi.register(email, password, firstName, lastName);
      const { accessToken, refreshToken } = response.data;
      
      await SecureStore.setItemAsync('accessToken', accessToken);
      await SecureStore.setItemAsync('refreshToken', refreshToken);
      
      set({ 
        accessToken, 
        refreshToken, 
        isAuthenticated: true, 
        isLoading: false 
      });
    } catch (error: any) {
      set({ error: error as string, isLoading: false });
      throw error;
    }
  },

  logout: async () => {
    await SecureStore.deleteItemAsync('accessToken');
    await SecureStore.deleteItemAsync('refreshToken');
    
    set({
      user: null,
      accessToken: null,
      refreshToken: null,
      isAuthenticated: false,
      error: null
    });
  },

  refreshTokens: async () => {
    try {
      const currentRefreshToken = get().refreshToken;
      if (!currentRefreshToken) {
        throw new Error('No refresh token available');
      }

      const response = await authApi.refreshToken(currentRefreshToken);
      const { accessToken, refreshToken } = response.data;
      
      await SecureStore.setItemAsync('accessToken', accessToken);
      await SecureStore.setItemAsync('refreshToken', refreshToken);
      
      set({ accessToken, refreshToken });
    } catch (error) {
      get().logout();
      throw error;
    }
  },

  clearError: () => set({ error: null }),

  loadStoredTokens: async () => {
    try {
      const accessToken = await SecureStore.getItemAsync('accessToken');
      const refreshToken = await SecureStore.getItemAsync('refreshToken');
      
      if (accessToken && refreshToken) {
        set({ accessToken, refreshToken, isAuthenticated: true });
      }
    } catch (error) {
      console.error('Error loading stored tokens', error);
    }
  }
}));

export default useAuthStore;