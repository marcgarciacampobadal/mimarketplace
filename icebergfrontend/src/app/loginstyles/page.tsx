'use client';
import React from 'react';

export default function Login() {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <div className="card" style={{ padding: '2rem', width: '100%', maxWidth: '400px' }}>
        <h2 style={{ textAlign: 'center', color: 'var(--color-primario)' }}>Inicia sesión</h2>
        <form style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <input type="email" placeholder="Email" />
          <input type="password" placeholder="Contraseña" />
          <button>Entrar</button>
        </form>
      </div>
    </div>
  );
}
