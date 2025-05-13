'use client';

import React, { useState } from "react";
import { useUser } from "@/context/UserContext";
import CarritoModal from "./CarritoModal";
import useCarrito from "./useCarrito";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function Header() {
  const { email, logout } = useUser();
  const { carrito } = useCarrito();
  const [mostrarCarrito, setMostrarCarrito] = useState(false);
  const router = useRouter();

  const cantidadTotal = carrito.items.reduce(
    (acc: number, item: any) => acc + item.cantidad,
    0
  );

  return (
    <>
      <header style={{
        backgroundColor: 'white',
        boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
        padding: '1rem 1.5rem',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
      }}>
        <Link href="/home" style={{
          fontSize: '1.5rem',
          fontWeight: 'bold',
          color: 'var(--color-primario)',
          textDecoration: 'none'
        }}>
          ICEBERG - the modern Marketplace
        </Link>

        <nav style={{ display: 'flex', alignItems: 'center', gap: '1rem', fontWeight: 500 }}>
          <Link href="/home" style={navLinkStyle}>Productos</Link>
          <Link href="/productos" style={navLinkStyle}>¡Lo más destacado! 🔥</Link>

          <button
            onClick={() => setMostrarCarrito(prev => !prev)}
            style={{
              fontSize: '1.5rem',
              color: '#374151',
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              position: 'relative'
            }}
          >
            🛒
            {cantidadTotal > 0 && (
              <span style={{
                position: 'absolute',
                top: '-8px',
                right: '-8px',
                backgroundColor: '#ef4444',
                color: 'white',
                fontSize: '0.75rem',
                borderRadius: '9999px',
                padding: '0 5px'
              }}>
                {cantidadTotal}
              </span>
            )}
          </button>

          {email && (
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginLeft: '1rem' }}>
              <span style={{ fontSize: '0.875rem', color: '#4b5563' }}>Hola, {email}</span>
              <button
                onClick={() => {
                  logout();
                  router.push('/login');
                }}
                style={{
                  fontSize: '0.875rem',
                  backgroundColor: '#BFE0E2',
                  color: 'white',
                  padding: '0.5rem 0.75rem',
                  border: 'none',
                  borderRadius: '0.75rem',
                  cursor: 'pointer',
                  transition: 'background 0.3s'
                }}
              >
                Cerrar sesión
              </button>
            </div>
          )}
        </nav>
      </header>

      {mostrarCarrito && (
        <CarritoModal onClose={() => setMostrarCarrito(false)} />
      )}
    </>
  );
}

const navLinkStyle = {
  color: '#374151',
  textDecoration: 'none',
  transition: 'color 0.3s',
} as React.CSSProperties;
