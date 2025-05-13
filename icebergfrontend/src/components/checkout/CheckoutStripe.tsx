'use client';
import React from 'react';
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import CheckoutForm from './CheckoutForm';

const stripePublicKey = process.env.NEXT_PUBLIC_STRIPE_PUBLIC_KEY;

if (!stripePublicKey) {
  throw new Error('Stripe public key no está definida en las variables de entorno');
}

const stripePromise = loadStripe(stripePublicKey);

export default function CheckoutStripe({ ordenId, montoTotal }: { ordenId: number; montoTotal: number }) {
  return (
    <Elements stripe={stripePromise}>
      <CheckoutForm ordenId={ordenId} montoTotal={montoTotal} />
    </Elements>
  );
}
