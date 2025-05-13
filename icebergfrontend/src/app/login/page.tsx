'use client';

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useUser } from '../../context/UserContext';
import { login } from '../../firebase/FirebaseAuth';
import { getAuth } from 'firebase/auth';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const { token, loading, setUserData } = useUser();
  const router = useRouter();

  useEffect(() => {
    if (!loading && token) {
      console.log('Ya tienes token. Redirigiendo...');
      router.replace('/home');
    }
  }, [token, loading, router]);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      console.log('Iniciando sesión con Firebase...');
      const firebaseToken = await login(email, password);
      console.log("TOKEN A ENVIAR AL BACKEND:");
      console.log(firebaseToken);
      const auth = getAuth();
      const user = auth.currentUser;

      if (!user) {
        throw new Error('Usuario no autenticado');
      }

      console.log('Firebase token:', firebaseToken);
      console.log('UID:', user.uid);

      await fetch('http://localhost:8080/api/usuarios/registrar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          uid: user.uid,
          email: user.email,
          nombre: user.displayName || 'Sin nombre',
          correo: user.email,
          contrasena: 'firebase',
        }),
      });

      const res = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${firebaseToken}`,
        },
      });

      const text = await res.text();
      let responseData;
      try {
        responseData = text ? JSON.parse(text) : {};
      } catch (err) {
        console.error('⚠️ Error al parsear JSON:', err);
        throw new Error('La respuesta del backend no es JSON válido');
      }

      if (!res.ok) {
        throw new Error(responseData?.message || `Error del servidor (código ${res.status})`);
      }

      console.log('Guardando token en localStorage...');
      localStorage.setItem('token', firebaseToken); // GUARDA el token aquí

      console.log('Guardando usuario en contexto...');
      setUserData(firebaseToken, responseData.email, responseData.uid);

      console.log('Redirigiendo al home...');
      router.replace('/home');
    } catch (err: any) {
      console.error('Error al iniciar sesión:', err);
      setError(err.message || 'Error desconocido');
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-20 text-gray-600">
        Cargando...
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto mt-20 p-6 border rounded-lg shadow-md bg-white">
      <h1 className="text-2xl font-bold mb-4 text-center">Iniciar sesión</h1>
      <form onSubmit={handleLogin} className="space-y-4">
        <div>
          <label className="block font-medium">Correo electrónico</label>
          <input
            type="email"
            className="w-full border px-4 py-2 rounded-md"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label className="block font-medium">Contraseña</label>
          <input
            type="password"
            className="w-full border px-4 py-2 rounded-md"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition"
        >
          Entrar
        </button>
        {error && <p className="text-red-500 text-sm text-center">{error}</p>}
      </form>
    </div>
  );
}
