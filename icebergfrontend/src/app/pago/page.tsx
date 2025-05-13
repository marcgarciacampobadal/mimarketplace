'use client';
import React from 'react';
import { useRouter } from 'next/navigation';

export default function Pago() {
  const router = useRouter();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const confirmado = window.confirm('¡Pago finalizado con éxito! ¿Volver al inicio?');
    if (confirmado) {
      router.push('/home');
    }
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '1.5rem',
      }}
    >
      <form
        onSubmit={handleSubmit}
        className="card"
        style={{
          padding: '2rem',
          maxWidth: '500px',
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          gap: '1.2rem',
          backgroundColor: 'white',
          borderRadius: '1rem',
          boxShadow: '0 8px 20px rgba(0,0,0,0.05)',
        }}
      >
        <h2 style={{ color: 'var(--color-primario)', textAlign: 'center' }}>Pago</h2>

        <input
          type="text"
          placeholder="Nombre en la tarjeta"
          required
          style={{ padding: '0.75rem', borderRadius: '0.75rem', border: '1px solid #ccc' }}
        />

        <input
          type="text"
          placeholder="Número de tarjeta"
          required
          style={{ padding: '0.75rem', borderRadius: '0.75rem', border: '1px solid #ccc' }}
        />

        <div style={{ display: 'flex', gap: '1rem' }}>
          <input
            type="text"
            placeholder="MM/AA"
            required
            style={{ flex: 1, padding: '0.75rem', borderRadius: '0.75rem', border: '1px solid #ccc' }}
          />
          <input
            type="text"
            placeholder="CVV"
            required
            style={{ flex: 1, padding: '0.75rem', borderRadius: '0.75rem', border: '1px solid #ccc' }}
          />
        </div>

        <input
          type="text"
          placeholder="Código postal"
          required
          style={{ padding: '0.75rem', borderRadius: '0.75rem', border: '1px solid #ccc' }}
        />

        <button
          type="submit"
          style={{
            width: '100%',
            marginTop: '1rem',
            backgroundColor: 'var(--color-primario)',
            color: 'white',
            padding: '0.75rem',
            border: 'none',
            borderRadius: '1rem',
            fontSize: '1rem',
            cursor: 'pointer',
          }}
        >
          Pagar ahora
        </button>
      </form>
    </div>
  );
}
