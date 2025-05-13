import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyBMmunLH_zR38mjcwjLxbePiXd0D3m4OF0",
  authDomain: "iceberg-marketplace-1dbe9.firebaseapp.com",
  projectId: "iceberg-marketplace-1dbe9",
  storageBucket: "iceberg-marketplace-1dbe9.firebasestorage.app",
  messagingSenderId: "936719564014",
  appId: "1:936719564014:web:f99dd341f2fcffc70307ff"
};

// Se inicia Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
