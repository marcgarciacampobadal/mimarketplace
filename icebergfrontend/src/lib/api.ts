import axios from "axios";

const api = axios.create({ 
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("Error en la API:", error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export default api; 