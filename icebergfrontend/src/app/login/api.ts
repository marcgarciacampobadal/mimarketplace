import { mockAuth } from '@/mocks/auth';

export const mockAPI = {
  getProfile: (token: string) => {
    const user = mockAuth.validateToken(token);
    if (!user) return { error: "No autorizado" };
    return { data: user };
  },

  getProducts: (token: string) => {
    const isValid = mockAuth.validateToken(token);
    if (!isValid) return { error: "No autorizado" };
    
    return { data: [
      { id: 1, name: "Producto 1", price: 10 },
      { id: 2, name: "Producto 2", price: 20 }
    ]};
  }
};