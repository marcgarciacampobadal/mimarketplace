import '../styles/styles.css';
import { UserProvider } from '@/context/UserContext';
import { CarritoProvider } from '@/context/CarritoContext';
import Header from '@/components/Header';
import Footer from '@/components/Footer';

export const metadata = {
  title: 'Iceberg Marketplace',
  description: 'El marketplace más moderno!',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="es">
      <head>
        {/* Fuente Poppins cargada manualmente */}
        <link
          href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap"
          rel="stylesheet"
        />
      </head>
      <body className="bg-nieve text-gray-900" style={{ fontFamily: "'Poppins', sans-serif" }}>
        <UserProvider>
          <CarritoProvider>
            <Header />
            <main className="container" style={{ minHeight: '100vh', paddingBottom: '3rem' }}>
              {children}
            </main>
            <Footer />
          </CarritoProvider>
        </UserProvider>
      </body>
    </html>
  );
}
