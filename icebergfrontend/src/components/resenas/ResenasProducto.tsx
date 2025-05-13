'use client';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AgregarResena from './AgregarResena';
import { useUser } from '@/context/UserContext'; // NECESARIO PARA TOKEN!!!

interface Usuario {
  id: number;
  nombre: string;
}

interface Resena {
  id: number;
  comentario: string;
  calificacion: number;
  usuario: Usuario;
}

interface Props {
  productoId: number;
}

const ResenasProducto: React.FC<Props> = ({ productoId }) => {
  const { token } = useUser(); //obtener token
  const [resenas, setResenas] = useState<Resena[]>([]);

  const getResenas = async () => {
    try {
      const response = await axios.get<Resena[]>(
        `http://localhost:8080/api/resenas/producto/${productoId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`, // lo enviamos
          },
        }
      );
      setResenas(response.data);
    } catch (error) {
      console.error('Error cargando reseñas:', error);
    }
  };

  useEffect(() => {
    if (token) {
      getResenas();
    }
  }, [productoId, token]);

  return (
    <div className="resenas-container mt-6">
      <h3 className="text-xl font-semibold mb-3">Opiniones del producto</h3>

      {/* Lista de reseñas */}
      {resenas.length === 0 ? (
        <p>No hay reseñas aún</p>
      ) : (
        resenas.map((resena) => (
          <div key={resena.id} className="resena-card border p-3 rounded mb-2 bg-gray-50">
            <p className="font-medium">
              {resena.usuario.nombre} - {resena.calificacion}/5 ★
            </p>
            <p>{resena.comentario}</p>
          </div>
        ))
      )}

      {/* Formulario para dejar nueva reseña */}
      <AgregarResena productoId={productoId} onResenaAñadida={getResenas} />
    </div>
  );
};

export default ResenasProducto;
