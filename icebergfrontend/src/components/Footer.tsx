import React from "react";

const Footer = () => {
  return (
    <footer style={{
      backgroundColor: 'var(--color-nieve)',
      color: '#6b7280',
      textAlign: 'center',
      padding: '1.5rem',
      fontSize: '0.875rem',
      marginTop: '3rem'
    }}>
      <p>&copy; {new Date().getFullYear()} Iceberg Marketplace. Todos los derechos reservados.</p>
    </footer>
  );
};

export default Footer;
