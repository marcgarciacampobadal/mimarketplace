const ProductService = {
  async getAll() {
    try {
      const res = await fetch("http://localhost:8080/api/productos");
      if (!res.ok) throw new Error("Error al obtener productos");
      const data = await res.json();
      return { data };
    } catch (error) {
      console.error("❌ Error en ProductService:", error);
      return { data: [] };
    }
  }
};

export default ProductService;
