'use client';
import React from 'react';
import { use } from 'react';
import { notFound, useSearchParams } from 'next/navigation';
import CheckoutStripe from '@/components/checkout/CheckoutStripe';

interface Props {
  params: Promise<{ id: string }>;
}

export default function CheckoutPage({ params }: Props) {
  const { id } = use(params);
  const ordenId = Number(id);

  if (isNaN(ordenId)) return notFound();

  const searchParams = useSearchParams();
  const rawTotal = searchParams?.get('total') ?? '0';
  const montoTotal = Number(rawTotal);

  return (
    <div className="max-w-xl mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-6">Finalizar Compra</h1>
      <CheckoutStripe ordenId={ordenId} montoTotal={montoTotal} />
    </div>
  );
}

