'use client';
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface UserContextType {
  token: string | null;
  email: string | null;
  uid: string | null;
  loading: boolean; 
  setUserData: (token: string, email: string, uid: string) => void;
  logout: () => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(null);
  const [email, setEmail] = useState<string | null>(null);
  const [uid, setUid] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true); // 🆕

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedEmail = localStorage.getItem('email');
    const storedUid = localStorage.getItem('uid');

    if (storedToken && storedEmail && storedUid) {
      setToken(storedToken);
      setEmail(storedEmail);
      setUid(storedUid);
    }

    setLoading(false);
  }, []);

  const setUserData = (newToken: string, newEmail: string, newUid: string) => {
    localStorage.setItem('token', newToken);
    localStorage.setItem('email', newEmail);
    localStorage.setItem('uid', newUid);
    setToken(newToken);
    setEmail(newEmail);
    setUid(newUid);
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('uid');
    setToken(null);
    setEmail(null);
    setUid(null);
  };

  return (
    <UserContext.Provider value={{ token, email, uid, loading, setUserData, logout }}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = (): UserContextType => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
};
