'use client';
import React from 'react';

export default function Carrito() {
  return (
    <div className="container">
      <h2 style={{ textAlign: 'center', color: 'var(--color-primario)' }}>Tu carrito</h2>
      <div className="card" style={{ padding: '1.5rem', maxWidth: '600px', margin: '0 auto' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
          <div>
            <p><strong>Producto X</strong></p>
            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>Cantidad: 2</p>
          </div>
          <p style={{ color: 'var(--color-primario)', fontWeight: 'bold' }}>20€</p>
        </div>
        <button style={{ width: '100%' }}>Ir al pago</button>
      </div>
    </div>
  );
}
