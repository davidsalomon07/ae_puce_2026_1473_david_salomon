# Sistema de Gestión y Reservación de Eventos (NRC 1473)

Este proyecto consiste en un microservicio API REST para la gestión de eventos, registro de asistentes y reservaciones de entradas, desarrollado en Kotlin y Spring Boot. El sistema está completamente protegido mediante OAuth2 utilizando **AWS Cognito** como proveedor de identidad (IDP).

---

## 🛠️ Tecnologías y Herramientas
* **Lenguaje**: Kotlin 1.9.x
* **Framework**: Spring Boot 3.2.x (Web, Security, JPA)
* **Seguridad**: OAuth2 Resource Server (AWS Cognito JWT Validation)
* **Base de Datos**: H2 (Base de datos en memoria para desarrollo local)
* **Gestor de Dependencias**: Gradle (Kotlin DSL)
* **Pruebas**: JUnit 5, Mockito & Mockito-Kotlin

---

## 🚀 Arquitectura y Endpoints

El microservicio está estructurado siguiendo las mejores prácticas de separación de capas (Controller -> Service -> Repository / Entity).

### Endpoints Públicos
* **`GET /api/events`**: Permite a cualquier usuario visualizar los eventos creados en el sistema.

### Endpoints Protegidos (Requiere JWT de AWS Cognito)
Cualquier petición a las siguientes rutas debe contener la cabecera `Authorization: Bearer <TOKEN_VALIDO>`:

* **Asistentes**:
  * `POST /api/attendees`: Registrar un nuevo asistente.
* **Eventos**:
  * `POST /api/events`: Registrar un nuevo evento.
* **Reservaciones**:
  * `POST /api/reservations`: Crear una reservación (asociando un asistente a un evento).
  * `GET /api/reservations`: Listar todas las reservaciones registradas.
  * `PUT /api/reservations/{id}/cancel`: Cancelar una reservación activa (liberando el cupo del evento).

---

## 🔒 Configuración de Seguridad (AWS Cognito)
El microservicio funciona como un **OAuth2 Resource Server** conectado al User Pool de pruebas provisto para la materia:
* **Issuer URI**: `https://cognito-idp.us-east-1.amazonaws.com/us-east-1_yzwNALI2A`
* **JWK Set URI**: `https://cognito-idp.us-east-1.amazonaws.com/us-east-1_yzwNALI2A/.well-known/jwks.json`

---

## 🧪 Pruebas Unitarias y Cobertura (100%)
El microservicio cuenta con pruebas unitarias para la capa de lógica de negocio (`services`), logrando un **100% de cobertura en líneas y 100% de cobertura en ramas (branches)** utilizando Mockito para simular el comportamiento de la persistencia.

Para ejecutar los tests locales:
```bash
./gradlew test
```

---

## 📦 Insumos del Repositorio
* **Colección de Postman**: Se incluye el archivo `ae_puce_2026_1473_david_salomon.postman_collection.json` en la raíz del proyecto para importar las llamadas parametrizadas directamente en Postman.
* **Carpeta de Evidencias**: Las capturas de pantalla de validación solicitadas por la rúbrica se encuentran organizadas en el directorio `evidencias/`.

---

## 👤 Autor
* **Estudiante**: David Salomón
* **Materia**: Arquitectura de Entornos de Software
* **NRC**: 1473
* **Institución**: PUCE - Pontificia Universidad Católica del Ecuador
