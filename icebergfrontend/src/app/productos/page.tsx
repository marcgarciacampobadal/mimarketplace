'use client';

import React, { useEffect, useState } from 'react';
import ProductService from '@/services/productService';
import CarritoModal from '@/components/CarritoModal';
import ProductCard from '@/components/ProductCard';

export default function ProductosDestacados() {
  const [productos, setProductos] = useState<any[]>([]);
  const [mostrarCarrito, setMostrarCarrito] = useState(false);

  useEffect(() => {
    const fetchProductos = async () => {
      const { data } = await ProductService.getAll();
      const ultimos6 = data.slice(-6);
      setProductos(ultimos6);
    };
    fetchProductos();
  }, []);

  const getImageForProduct = (name: string) => {
    const map: { [key: string]: string } = {
      manzana: '/manzana.jpeg',
      leche: '/leche.jpeg',
      pintalabios: '/pintalabios.jpeg',
      jersey: '/jersey.jpeg',
      ps5: '/ps5.jpeg',
      cera: '/cera.jpeg',
      fuentegato: '/fuentegato.jpeg',
      peluche: '/peluche.jpeg',
      pantalon: '/pantalon.jpeg',
      zapatos: '/zapatos.jpeg',
      patinete: '/patinete.jpeg',
      jet: '/jet.jpeg',
      lavadora: '/lavadora.jpeg',
      guantillas: '/guantillas.jpeg',
      espinilleras: '/espinilleras.jpeg',
      proteinawhey: '/proteinawhey.jpeg',
      kimono: '/kimono.jpeg',
      recambios: '/recambios.jpeg',

    };

    const key = name.toLowerCase().replace(/\s/g, '');
    return map[key] || '/default.jpg';
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1 style={{ color: 'var(--color-primario)', textAlign: 'center', marginBottom: '2rem' }}>
        Productos Destacados
      </h1>

      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))',
          gap: '2rem',
        }}
      >
        {productos.map((producto) => (
          <ProductCard
            key={producto.id}
            id={producto.id}
            nombre={producto.nombre}
            precio={producto.precio}
            image={getImageForProduct(producto.nombre)}
          />
        ))}
      </div>

      {mostrarCarrito && (
        <CarritoModal onClose={() => setMostrarCarrito(false)} />
      )}
    </div>
  );
}
