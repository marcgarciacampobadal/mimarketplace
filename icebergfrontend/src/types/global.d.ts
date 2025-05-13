// @ts-nocheck
type AxiosInstance = import('axios').AxiosInstance;

declare module '@/lib/api' {
  const api: AxiosInstance;
  export default api;
}

declare module '@/services/productService' {
  const ProductService: {
    getAll: () => Promise<any>;
  };
  export default ProductService;
}