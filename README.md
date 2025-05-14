
# 🧊 Iceberg – Aplicación Web Completa (Marketplace Full Stack)

**Iceberg** es una aplicación web full stack que actúa como un **marketplace moderno**, combinando un potente backend desarrollado en **Java Spring Boot** con un frontend responsivo hecho en **Next.js y TypeScript**. Su propósito es ofrecer una experiencia fluida a los usuarios autenticados, permitiéndoles explorar productos, gestionar carritos de compra, realizar pagos, dejar reseñas y más.

---

## 🚀 Tecnologías utilizadas

### 🧩 Frontend
- **Next.js**
- **React**
- **TypeScript**
- **CSS Modules**
- **Firebase Auth** (autenticación de usuarios)

### 🛠️ Backend
- **Java 17**
- **Spring Boot 2.7.18**
- **Spring Security + JWT**
- **Firebase Admin SDK**
- **Stripe API** (procesamiento de pagos)
- **MySQL / H2**
- **JPA / Hibernate**
- **REST API**
- **Maven**
- **Lombok**

---

## 📁 Estructura del Proyecto

```
/icebergfrontend         # Frontend con Next.js
/icebergbackend          # Backend con Spring Boot
```

---

## 🧪 Funcionalidades destacadas

### 🔐 Seguridad
- Autenticación y autorización con **JWT** y **Firebase**
- Validación de entradas y control de sesiones
- Configuración de CORS

### 🛒 Marketplace
- Gestión de usuarios
- Carrito de compras dinámico
- Gestión de productos y categorías
- Procesamiento de pagos con **Stripe**
- Sistema de reseñas
- Control y seguimiento de órdenes

### 🌐 Frontend
- Interfaz moderna y responsiva
- Navegación fluida para usuarios autenticados
- Integración con backend vía API REST

---

## 📦 Instalación y ejecución local

### 🔧 Requisitos previos
- Java 17+
- Maven 3+
- Node.js y npm
- Firebase Admin SDK (`serviceAccountKey.json`)
- Stripe Secret Key

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/iceberg.git
cd iceberg
```

### 2. Configurar Backend

Agrega `application.properties` con lo siguiente:

```properties
firebase.sdk.path=src/main/resources/serviceAccountKey.json
stripe.api.key=sk_test_xxx
jwt.secret=mi_clave_secreta_super_segura
jwt.expiration=3600000
```

Luego ejecuta:

```bash
cd icebergbackend
./mvnw spring-boot:run
```

### 3. Ejecutar Frontend

```bash
cd icebergfrontend
npm install
npm run dev
```

Accede a la aplicación en `http://localhost:3000`

---

## 🧪 Testing

Puedes usar Postman o cualquier cliente HTTP para probar los endpoints. Recuerda incluir el token JWT:

```http
Authorization: Bearer tu_token
```

---

## 📌 Estado del proyecto

Actualmente en desarrollo. Próximas tareas:
- Implementación de roles y permisos avanzados
- Mejoras en la experiencia del usuario
- Pruebas automatizadas
- Validaciones más robustas en formularios

---

## 👤 Autor

Marc Garcia Campobadal  
[GitHub](https://github.com/marcgarciacampobadal/) | [Web Personal](https://marcgarciacampobadal.github.io/miweb/)

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Puedes modificarlo y distribuirlo libremente, siempre que menciones al autor original.
