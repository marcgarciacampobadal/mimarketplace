'use client';

import React from 'react';
import { useCarrito } from '@/context/CarritoContext';
import { useRouter } from 'next/navigation';

export default function CarritoModal({ onClose }: { onClose: () => void }) {
  const { carrito, eliminarProducto, finalizarCompra, actualizarCantidad, total } = useCarrito();
  const router = useRouter();

  const handleFinalizar = async () => {
    const orden = await finalizarCompra();
    onClose();
    router.push(`/checkout/${orden.id}?total=${total}`);
  };

  return (
    <div
      style={{
        position: 'fixed',
        inset: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.4)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 9999,
        fontFamily: 'Poppins, sans-serif',
      }}
    >
      <div
        style={{
          background: 'white',
          borderRadius: '1rem',
          boxShadow: '0 8px 30px rgba(0, 0, 0, 0.15)',
          padding: '1.5rem',
          width: '100%',
          maxWidth: '500px',
          position: 'relative',
        }}
      >
        <button
          onClick={onClose}
          style={{
            position: 'absolute',
            top: '1rem',
            right: '1rem',
            background: 'none',
            border: 'none',
            fontSize: '1.5rem',
            color: '#999',
            cursor: 'pointer',
          }}
        >
          ✕
        </button>

        <h2 style={{ fontSize: '1.5rem', marginBottom: '1rem', color: 'var(--color-primario)' }}>
          🛒 Tu carrito
        </h2>

        {carrito.items.length === 0 ? (
          <p style={{ color: '#6b7280', textAlign: 'center' }}>El carrito está vacío.</p>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            {carrito.items.map((item: any) => (
              <div
                key={item.producto.id}
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  borderBottom: '1px solid #e5e7eb',
                  paddingBottom: '0.5rem',
                }}
              >
                <div>
                  <p style={{ fontWeight: '600' }}>{item.producto.nombre}</p>
                  <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>
                    {item.cantidad} x €{item.producto.precio}
                  </p>

                  <div style={{ marginTop: '0.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    <button
                      onClick={() => actualizarCantidad(item.producto.id, item.cantidad - 1)}
                      style={{
                        padding: '0.3rem 0.7rem',
                        borderRadius: '0.5rem',
                        backgroundColor: '#f3f4f6',
                        border: 'none',
                        cursor: 'pointer',
                      }}
                      disabled={item.cantidad <= 1}
                    >
                      -
                    </button>
                    <span>{item.cantidad}</span>
                    <button
                      onClick={() => actualizarCantidad(item.producto.id, item.cantidad + 1)}
                      style={{
                        padding: '0.3rem 0.7rem',
                        borderRadius: '0.5rem',
                        backgroundColor: '#f3f4f6',
                        border: 'none',
                        cursor: 'pointer',
                      }}
                    >
                      +
                    </button>
                  </div>
                </div>

                <button
                  onClick={() => eliminarProducto(item.producto.id)}
                  style={{
                    background: 'none',
                    border: 'none',
                    color: 'var(--color-error)',
                    fontSize: '0.85rem',
                    cursor: 'pointer',
                  }}
                >
                  Eliminar
                </button>
              </div>
            ))}

            <div style={{ textAlign: 'right', fontWeight: 'bold', fontSize: '1.2rem' }}>
              Total: €{total.toFixed(2)}
            </div>

            <button
              onClick={handleFinalizar}
              style={{
                backgroundColor: '#3b82f6',
                color: 'white',
                padding: '0.75rem',
                borderRadius: '1rem',
                border: 'none',
                cursor: 'pointer',
                fontSize: '1rem',
                marginTop: '1rem',
              }}
            >
              Finalizar compra
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
