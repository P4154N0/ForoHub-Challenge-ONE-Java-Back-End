# 📖 Foro Hub - API REST

---

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.9-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0202?style=for-the-badge&logo=flyway&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Insomnia](https://img.shields.io/badge/Insomnia-4000BF?style=for-the-badge&logo=Insomnia&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Trello](https://img.shields.io/badge/Trello-%23026AA7.svg?style=for-the-badge&logo=Trello&logoColor=white)

---

**Foro Hub** es una API REST diseñada para centralizar y gestionar las dudas de una comunidad de aprendizaje. El sistema permite que los estudiantes publiquen tópicos relacionados con cursos específicos, facilitando el intercambio de conocimientos y la organización de preguntas técnicas.

> **🚧 Roadmap:** Próximamente se estará desarrollando el módulo de **Mensajes y Respuestas**, permitiendo que los usuarios interactúen directamente en los tópicos y marquen las soluciones más útiles.

---

## 🛠️ Estándares y Protocolo HTTP Aplicados

---

La API sigue el modelo de madurez de Richardson, asegurando respuestas estandarizadas y el uso semántico de los verbos HTTP para una integración limpia con cualquier Frontend.

| Operación | Método | URI | Estatus Exitoso | Estatus Error | Descripción |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Registrar** | `POST` | `/topicos` | `201 Created` | `400 Bad Request` | Publicar un nuevo tópico de duda. |
| **Listar** | `GET` | `/topicos` | `200 OK` | `N/A` | Ver todos los tópicos (paginados). |
| **Detallar** | `GET` | `/topicos/{id}` | `200 OK` | `404 Not Found` | Ver el cuerpo completo de una duda. |
| **Actualizar** | `PUT` | `/topicos` | `200 OK` | `404 Not Found` | Corregir o editar un tópico propio. |
| **Eliminar** | `DELETE` | `/topicos/{id}` | `204 No Content` | `404 Not Found` | Eliminación definitiva del tópico. |

---

## 🚀 Funcionalidades Actuales

---

## 🚀 Funcionalidades y Mejoras Implementadas

### 1. 🔍 Gestión de Tópicos y Contenido
* **Validación de Duplicados:** Lógica de negocio integrada para impedir la creación de tópicos con títulos y mensajes idénticos, garantizando la integridad de la información.
* **Filtros Avanzados:** Consultas optimizadas para búsqueda por nombre de curso, fecha de creación y estado del tópico.
* **Paginación Eficiente:** Implementación de `Pageable` para una navegación de datos fluida, evitando sobrecargas en el cliente y optimizando el consumo de recursos.

### 2. 🛡️ Seguridad y Control de Acceso (JWT)
* **Arquitectura Stateless:** Configuración de `SecurityFilterChain` para manejar sesiones sin estado, delegando la autenticación a los JSON Web Tokens (JWT).
* **Security Filter Personalizado:** Interceptor de peticiones (`OncePerRequestFilter`) que valida el `Header Authorization` y gestiona el contexto de seguridad de Spring de forma transparente.
* **Protección de Rutas:** Acceso restringido mediante `requestMatchers`, manteniendo el endpoint de `/login` como acceso público y blindando el resto de la API.
* **Bcrypt Hashing:** Seguridad proactiva mediante el cifrado unidireccional de contraseñas de usuario antes de su persistencia en la base de datos.

### 3. ⚠️ Gestión Global de Errores (Error Handling)
* **Estandarización de Respuestas:** Uso de `@RestControllerAdvice` para capturar excepciones específicas como `EntityNotFoundException` (404) y `MethodArgumentNotValidException` (400).
* **Feedback Técnico Detallado:** Las validaciones de Bean Validation devuelven un JSON estructurado con el campo afectado y el mensaje de error, facilitando el trabajo del equipo Frontend.

### 4. 🏗️ Arquitectura y Calidad de Código
* **Java Records:** Uso de Records para la implementación de DTOs, asegurando un transporte de datos inmutable y un código más legible.
* **Flyway Migrations:** Control de versiones del esquema de base de datos MySQL, permitiendo un despliegue y mantenimiento de tablas consistente y profesional.
* **Clean Code & SOLID:** Aplicación de anotaciones de Spring y principios de diseño para mantener una lógica de negocio desacoplada, escalable y mantenible.

---

## ⚙️ Configuración e Instalación

---

### 1. Requisitos
* JDK 17
* Maven 3.x
* MySQL 8.x

### 2. Instalación y Uso
```bash
# Clonar el repositorio
git clone [https://github.com/P4154N0/ForoHub-Challenge-ONE-Java-Back-End.git](https://github.com/P4154N0/ForoHub-Challenge-ONE-Java-Back-End.git)

# Entrar al directorio del proyecto
cd ForoHub-Challenge-ONE-Java-Back-End

# Ejecutar la aplicación
mvn spring-boot:run
```

## 🤠 Sobre el Autor
Este proyecto fue desarrollado por un P4154N0 🇦🇷, cebando varios mates 🧉 y buscando siempre la excelencia en el código backend.



Use Control + Shift + m to toggle the tab key moving focus. Alternatively, use esc then tab to move to the next interactive element on the page.
Ningún archivo seleccionado
Attach files by dragging & dropping, selecting or pasting them.
