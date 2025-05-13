'use client';
import React, { useState } from 'react';
import { useCarrito } from '@/context/CarritoContext';
import CarritoModal from './CarritoModal';

export default function CarritoIcon() {
  const { carrito } = useCarrito();
  const [isOpen, setIsOpen] = useState(false);

  const totalItems = carrito.items.reduce(
    (acc, item) => acc + item.cantidad,
    0
  );

  return (
    <div style={{ position: 'fixed', top: 20, right: 20, zIndex: 1000 }}>
      <button onClick={() => setIsOpen(!isOpen)} className="relative text-2xl">
        🛒
        {totalItems > 0 && (
          <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
            {totalItems}
          </span>
        )}
      </button>

      {isOpen && <CarritoModal onClose={() => setIsOpen(false)} />}
    </div>
  );
}
