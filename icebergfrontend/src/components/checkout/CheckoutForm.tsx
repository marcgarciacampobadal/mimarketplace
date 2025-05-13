'use client';
import React, { useState } from 'react';
import { CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import { useRouter } from 'next/navigation';

interface Props {
  ordenId: number;
  montoTotal: number;
}

export default function CheckoutForm({ ordenId, montoTotal }: Props) {
  const stripe = useStripe();
  const elements = useElements();
  const router = useRouter();

  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const getClientSecret = async () => {
    const token = localStorage.getItem('token');

    const res = await fetch('/api/pagos/crear-intento', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { Authorization: `Bearer ${token}` }),
      },
      body: JSON.stringify({ ordenId, monto: montoTotal, moneda: 'MXN' }),
    });

    if (!res.ok) {
      const msg = await res.text();
      throw new Error(`Fallo al crear intento de pago: ${msg}`);
    }

    const data = await res.json();
    if (!data.clientSecret) {
      throw new Error('No se recibió clientSecret del backend');
    }

    return data.clientSecret;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    if (!stripe || !elements) return;

    try {
      const clientSecret = await getClientSecret();
      const result = await stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: elements.getElement(CardElement)!,
        },
      });

      if (result.error) {
        setError(result.error.message || 'Error al procesar el pago');
      } else if (result.paymentIntent?.status === 'succeeded') {
        alert(`¡Pago exitoso! ID: ${result.paymentIntent.id}`);
        router.push('/');
      }
    } catch (err: any) {
      console.error('Error de pago:', err);
      setError(err.message || 'Error inesperado al procesar el pago.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="space-y-4 p-6 bg-white rounded-lg shadow-md max-w-md mx-auto"
    >
      <CardElement className="p-3 border border-gray-300 rounded-md" />
      {error && (
        <div className="text-red-600 text-sm font-medium">{error}</div>
      )}
      <button
        type="submit"
        disabled={!stripe || loading}
        className={`w-full py-2 px-4 text-white font-semibold rounded-md ${
          loading ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-600 hover:bg-blue-700'
        }`}
      >
        {loading ? 'Procesando...' : `Pagar ${montoTotal}€`}
      </button>
    </form>
  );
}
