# Iceberg Marketplace

Iceberg es un marketplace desarrollado con Java y Spring Boot que permite a los usuarios explorar productos, agregar artículos a un carrito, realizar pagos (con Stripe), dejar reseñas, y más. El sistema también cuenta con autenticación basada en JWT y soporte para usuarios autenticados mediante Firebase.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 2.7.18**
- **Maven**
- **Spring Security + JWT**
- **Firebase Admin SDK**
- **Stripe API (Pagos)**
- **H2 / MySQL** (dependiendo de configuración)
- **REST API**
- **Lombok**
- **JPA/Hibernate**

---

## 📁 Estructura del Proyecto

```
icebergbackend/
├── src/
│   └── main/
│       └── java/com/tienda/
│           ├── controller/        # Controladores REST para entidades
│           ├── model/             # Entidades JPA
│           ├── dto/               # Clases de transferencia de datos
│           ├── config/            # Configuración de seguridad, Firebase, CORS
│           └── IcebergMarketplaceApplication.java
├── .mvn/                          # Wrapper de Maven
├── pom.xml                        # Dependencias y configuración del proyecto
└── HELP.md
```

---

## 🚀 Cómo ejecutar localmente

### Prerrequisitos

- Java 17+
- Maven 3+
- Firebase (archivo de configuración `serviceAccountKey.json`)
- Stripe (clave secreta del API)

### Pasos

1. **Clona el repositorio**

   ```bash
   git clone https://github.com/tu-usuario/iceberg-marketplace.git
   cd iceberg-marketplace/icebergbackend
   ```

2. **Agrega las variables necesarias**

   Crea un archivo `application.properties` o `application.yml` con:

   ```properties
   # Firebase
   firebase.sdk.path=src/main/resources/serviceAccountKey.json

   # Stripe
   stripe.api.key=sk_test_xxx

   # JWT
   jwt.secret=mi_clave_secreta_super_segura
   jwt.expiration=3600000
   ```

3. **Ejecuta el proyecto**

   ```bash
   ./mvnw spring-boot:run
   ```

4. Accede a la API REST en: `http://localhost:8080`

---

## 🔐 Funcionalidades destacadas

- **Autenticación y Autorización** con JWT y Firebase
- **Gestión de Usuarios**
- **Carrito de compras** dinámico
- **Gestión de productos y categorías**
- **Procesamiento de pagos** con Stripe
- **Reseñas de productos**
- **Control y gestión de órdenes**
- **Soporte para CORS**

---

## 🧪 Testing

Puedes usar Postman o cualquier cliente HTTP para probar los endpoints. Incluye el token JWT en el header:

```http
Authorization: Bearer tu_token
```

---

## 🤝 Cómo contribuir

1. Haz un fork del repositorio
2. Crea una nueva rama con tu funcionalidad: `git checkout -b nueva-funcionalidad`
3. Haz tus cambios y realiza commits: `git commit -m 'Agrega nueva funcionalidad'`
4. Sube tu rama: `git push origin nueva-funcionalidad`
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Puedes modificarlo y distribuirlo libremente, siempre y cuando menciones al autor original.

---

## 📬 Contacto

¿Tienes dudas o sugerencias? Escríbeme a: [tu-email@example.com]
