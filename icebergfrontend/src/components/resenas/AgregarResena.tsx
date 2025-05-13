'use client';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useUser } from '@/context/UserContext';

interface AgregarResenaProps {
  productoId: number;
  onResenaAñadida?: () => void; // Para recargar reseñas
}

const AgregarResena: React.FC<AgregarResenaProps> = ({ productoId, onResenaAñadida }) => {
  const { uid, token } = useUser();
  const [usuarioId, setUsuarioId] = useState<number | null>(null);
  const [comentario, setComentario] = useState('');
  const [calificacion, setCalificacion] = useState<number>(5);

  useEffect(() => {
    const fetchUsuarioId = async () => {
      if (!uid || !token) return;

      try {
        const res = await axios.get(`http://localhost:8080/api/usuarios/porUid/${uid}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const data = res.data as { id: number };
        setUsuarioId(data.id);
      } catch (err) {
        console.error('Error al obtener ID de usuario', err);
      }
    };

    fetchUsuarioId();
  }, [uid, token]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!usuarioId) {
      alert('No se pudo identificar al usuario.');
      return;
    }

    try {
      await axios.post(
        'http://localhost:8080/api/resenas',
        {
          comentario,
          calificacion,
          productoId,
          usuarioId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert('¡Gracias por dejar tu reseña! 😊');
      setComentario('');
      onResenaAñadida?.(); // Actualiza la lista de reseñas
    } catch (error: any) {
      if (error.response?.status === 409) {
        alert('Ya has dejado una reseña para este producto. ¡Gracias por compartir tu opinión!');
      } else {
        alert('Oops, ocurrió un error. Intenta nuevamente más tarde.');
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h4 className="font-semibold text-lg mb-2">Deja tu opinión</h4>
      <select
        value={calificacion}
        onChange={(e) => setCalificacion(parseInt(e.target.value))}
        className="mb-2 border px-2 py-1 rounded"
      >
        {[1, 2, 3, 4, 5].map((num) => (
          <option key={num} value={num}>
            {num} ★
          </option>
        ))}
      </select>
      <textarea
        value={comentario}
        onChange={(e) => setComentario(e.target.value)}
        placeholder="Escribe tu reseña..."
        required
        className="w-full border rounded p-2 mt-2"
      />
      <button
        type="submit"
        className="mt-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        Enviar reseña
      </button>
    </form>
  );
};

export default AgregarResena;
