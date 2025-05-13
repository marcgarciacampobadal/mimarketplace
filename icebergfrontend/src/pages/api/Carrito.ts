// src/pages/api/Carrito.ts
import type { NextApiRequest, NextApiResponse } from 'next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const { usuarioId, ...query } = req.query;

  const url = `http://localhost:8080/api/carrito/${usuarioId}?${new URLSearchParams(query as Record<string, string>)}`;

  const response = await fetch(url, {
    method: req.method,
    headers: { 'Content-Type': 'application/json' },
    body: req.body ? JSON.stringify(req.body) : undefined,
  });

  const data = await response.json();
  res.status(response.status).json(data);
}
