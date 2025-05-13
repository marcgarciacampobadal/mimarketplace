'use client';

import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';
import { useUser } from '@/context/UserContext';

interface Producto {
  id: number;
  nombre: string;
  precio: number;
}

interface CarritoItem {
  producto: Producto;
  cantidad: number;
}

interface Carrito {
  items: CarritoItem[];
}

interface CarritoContextType {
  carrito: Carrito;
  total: number;
  agregarProducto: (productoId: number, cantidad?: number) => Promise<void>;
  eliminarProducto: (productoId: number) => Promise<void>;
  finalizarCompra: () => Promise<any>;
  refrescarCarrito: () => Promise<void>;
  actualizarCantidad: (productoId: number, cantidad: number) => Promise<void>;
}

const CarritoContext = createContext<CarritoContextType | undefined>(undefined);

export const CarritoProvider = ({ children }: { children: ReactNode }) => {
  const { uid, token } = useUser();
  const [carrito, setCarrito] = useState<Carrito>({ items: [] });

  const refrescarCarrito = async () => {
    if (!uid || !token) return;
    try {
      const res = await fetch(`http://localhost:8080/api/carrito/${uid}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) {
        const data = await res.json();
        setCarrito(data);
      }
    } catch (err) {
      console.error('Error al cargar carrito:', err);
    }
  };

  useEffect(() => {
    refrescarCarrito();
  }, [uid, token]);

  const agregarProducto = async (productoId: number, cantidad = 1) => {
    if (!uid || !token) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/carrito/${uid}/agregar?productoId=${productoId}&cantidad=${cantidad}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!res.ok) throw new Error('No se pudo añadir el producto');
      await refrescarCarrito();
    } catch (err) {
      console.error('Error al añadir producto:', err);
    }
  };

  const eliminarProducto = async (productoId: number) => {
    if (!uid || !token) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/carrito/${uid}/eliminar?productoId=${productoId}`,
        {
          method: 'DELETE',
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error('No se pudo eliminar el producto');
      await refrescarCarrito();
    } catch (err) {
      console.error('Error al eliminar producto:', err);
    }
  };

  const actualizarCantidad = async (productoId: number, cantidad: number) => {
    if (!uid || !token || cantidad < 1) return;
    try {
      await fetch(
        `http://localhost:8080/api/carrito/${uid}/actualizar?productoId=${productoId}&cantidad=${cantidad}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
      await refrescarCarrito();
    } catch (err) {
      console.error('Error al actualizar cantidad:', err);
    }
  };

  const finalizarCompra = async () => {
    if (!uid || !token) return;
    const res = await fetch(`http://localhost:8080/api/carrito/${uid}/finalizar`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
    const orden = await res.json();
    setCarrito({ items: [] });
    return orden;
  };

  const total = carrito.items.reduce(
    (acc, item) => acc + item.producto.precio * item.cantidad,
    0
  );

  return (
    <CarritoContext.Provider
      value={{
        carrito,
        total,
        agregarProducto,
        eliminarProducto,
        finalizarCompra,
        refrescarCarrito,
        actualizarCantidad,
      }}
    >
      {children}
    </CarritoContext.Provider>
  );
};

export const useCarrito = () => {
  const context = useContext(CarritoContext);
  if (!context)
    throw new Error('useCarrito debe usarse dentro de un CarritoProvider');
  return context;
};
