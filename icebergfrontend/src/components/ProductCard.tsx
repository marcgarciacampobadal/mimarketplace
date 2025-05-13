'use client';
import React, { useState } from "react";
import { useCarrito } from "@/context/CarritoContext";
import { useUser } from "@/context/UserContext";
import ResenasProducto from "@/components/resenas/ResenasProducto";

interface ProductCardProps {
  id: number;
  nombre: string;
  precio: number;
  image: string;
}

const ProductCard: React.FC<ProductCardProps> = ({ id, nombre: name, precio: price, image }) => {
  const { agregarProducto } = useCarrito();
  const { uid } = useUser();
  const [añadido, setAñadido] = useState(false);
  const [mostrarResenas, setMostrarResenas] = useState(false);

  const handleAgregar = async () => {
    if (!agregarProducto) {
      alert("Debes iniciar sesión para agregar productos al carrito.");
      return;
    }

    try {
      await agregarProducto(id);
      setAñadido(true);
      setTimeout(() => setAñadido(false), 1500);
    } catch (err) {
      console.error("Error al agregar producto:", err);
      alert("Hubo un error al añadir el producto al carrito.");
    }
  };

  return (
    <div className="w-full max-w-xs bg-white border border-gray-200 rounded-lg overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 mx-auto flex flex-col h-full">
      <div className="aspect-w-1 aspect-h-1 bg-gray-100">
        <img src={image} alt={name} className="object-cover w-full h-full" />
      </div>

      <div className="flex flex-col justify-between flex-grow p-4">
        <div>
          <h2 className="font-bold text-lg mb-1">{name}</h2>
          <p className="text-gray-600 font-semibold mb-2">€{price.toFixed(2)}</p>
        </div>

        <div className="mt-4">
          <button
            onClick={handleAgregar}
            aria-label="Agregar al carrito"
            className={`w-full ${añadido ? 'bg-green-500' : 'bg-blue-500 hover:bg-blue-600'
              } text-white px-4 py-2 rounded-md transition-colors mb-2`}
          >
            {añadido ? "Añadido" : "Añadir al carrito"}
          </button>

          <button
            onClick={() => setMostrarResenas(!mostrarResenas)}
            className="text-sm text-blue-600 underline w-full text-left"
          >
            {mostrarResenas ? "Ocultar reseñas" : "Ver reseñas"}
          </button>

          {mostrarResenas && (
            <div className="mt-3">
              <ResenasProducto productoId={id} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
