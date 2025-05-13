import type { NextApiRequest, NextApiResponse } from 'next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') return res.status(405).end();

  try {
    const token = req.headers.authorization; //Obtengo el token del header

    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/pagos/stripe/crear-intento`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { Authorization: token }), // Se reenvia el token si existe
      },
      body: JSON.stringify(req.body),
    });

    const data = await response.json();
    res.status(response.status).json(data);
  } catch (error) {
    console.error('Error en crear-intento.ts:', error);
    res.status(500).json({ error: 'Error al procesar el pago' });
  }
}
