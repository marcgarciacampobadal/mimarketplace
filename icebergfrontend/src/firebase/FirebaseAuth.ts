import { auth } from "./config";
import {
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  signOut,
  User
} from "firebase/auth";

// Función para obtener el token
export const getCurrentToken = async (): Promise<string | null> => {
  const user = auth.currentUser;
  if (!user) return null;
  return await user.getIdToken(true); // Refresh para obtener token fresco
};

export const login = async (email: string, password: string) => {
  const result = await signInWithEmailAndPassword(auth, email, password);
  return await result.user.getIdToken(true);
};

export const register = async (email: string, password: string) => {
  const result = await createUserWithEmailAndPassword(auth, email, password);
  return await result.user.getIdToken(true); 
};

export const logout = async () => {
  await signOut(auth);
};

// Cambios en la autenticación (función)
export const onAuthStateChanged = (callback: (user: User | null) => void) => {
  return auth.onAuthStateChanged(callback);
};