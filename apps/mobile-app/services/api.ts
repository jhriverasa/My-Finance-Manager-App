import axios from 'axios';

const api = axios.create({
  baseURL: 'http://10.0.2.2:8080/api/auth',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    return Promise.reject(error.response?.data?.message || 'An error occurred');
  }
);

export const authApi = {
  login: (email: string, password: string) => 
    api.post('/login', { email, password }),
  
  register: (email: string, password: string, firstName: string, lastName: string) =>
    api.post('/register', { email, password, firstName, lastName }),
  
  refreshToken: (refreshToken: string) =>
    api.post('/refresh', { refreshToken }),
  
  validateToken: (token: string) =>
    api.get(`/validate?token=${token}`),
};

export default api;