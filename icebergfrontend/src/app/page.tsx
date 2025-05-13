'use client';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useUser } from '../context/UserContext';

export default function RedirectToLoginOrHome() {
  const { token, loading } = useUser();
  const router = useRouter();

  useEffect(() => {
    if (!loading) {
      if (token) {
        router.replace('/home');
      } else {
        router.replace('/login');
      }
    }
  }, [token, loading, router]);

  return <div className="text-center mt-20 text-gray-600">Cargando...</div>;
}
