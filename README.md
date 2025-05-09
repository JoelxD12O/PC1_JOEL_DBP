[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/WXuJsimR)
# 📝 **Práctica Calificada 1 – Desarrollo Basado en Plataformas**

## ⚙️ **Contexto del desafío**

El Registro Nacional de Identificación y Estado Civil (RENIEC) requiere desarrollar un sistema backend para la gestión
de **registro, consulta y validación de relaciones familiares entre personas**. El sistema debe manejar información
clave de cada ciudadano, incluyendo relaciones de parentesco, y debe garantizar restricciones legales para matrimonios
por consanguinidad.

## 📌 **Requisitos del modelo `Persona`**

Tu modelo de datos deberá cumplir con lo siguiente:

- **DNI**: Campo obligatorio e **inmutable**. Será tu clave primaria.
- **Nombres y Apellidos**: Editables mediante endpoint dedicado.
- **Fecha de nacimiento y Sexo**: Datos **inmutables** después del registro inicial.
- **Estado Civil**: Modificable (SOLTERO, CASADO, DIVORCIADO, VIUDO, etc.).
- **Correo electrónico**: Debe validarse con formato de email estándar.
- Cada persona puede tener asociados un **padre** y una **madre**, que también son personas registradas en el sistema (
  estructura recursiva).
- Registrar padres no es obligatorio en la creación inicial, pero se podrán asociar después mediante un endpoint.

## 🚫 **Restricciones Legales para Matrimonio**

El sistema deberá validar automáticamente impedimentos legales antes de registrar un matrimonio. Está **prohibido**
casarse en los siguientes casos:

- **Menor de edad**
- **Línea recta (ascendientes-descendientes)**: Prohibido siempre (padre-hija, madre-hijo, abuelo-nieta, etc.).
- **Línea colateral**:
    - Segundo grado (hermanos): Prohibido.
    - Tercer grado (tíos-sobrinos): Prohibido.
- **Persona ya casada**: Si cualquiera de los contrayentes tiene estado civil "CASADO".

## 📡 **Endpoints Obligatorios (14 en total)**

A continuación, encontrarás los endpoints que deben ser implementados exactamente como se especifican. Recuerda que cada
endpoint tiene restricciones según roles asignados a los usuarios del sistema.

| #  | Método | Endpoint                           | Descripción                                                                  | Roles Permitidos              |
|----|--------|------------------------------------|------------------------------------------------------------------------------|-------------------------------|
| 1  | POST   | /personas                          | Crear una nueva persona en el sistema.                                       | ADMIN, REGISTRADOR            |
| 2  | GET    | /personas/{dni}                    | Consultar todos los datos de una persona por su DNI.                         | ADMIN, REGISTRADOR, CONSULTOR |
| 3  | PUT    | /personas/{dni}/nombres            | Actualizar solamente nombres y apellidos de una persona.                     | ADMIN, REGISTRADOR            |
| 4  | PUT    | /personas/{dni}/padres             | Registrar o actualizar datos de los padres de una persona.                   | ADMIN, REGISTRADOR            |
| 5  | DELETE | /personas/{dni}                    | Eliminar una persona del sistema (si no tiene descendientes).                | ADMIN                         |
| 6  | GET    | /personas                          | Listar todas las personas, con filtros opcionales por nombre o estado civil. | ADMIN, REGISTRADOR, CONSULTOR |
| 7  | POST   | /matrimonios                       | Registrar matrimonio entre dos personas previa validación.                   | ADMIN, REGISTRADOR            |
| 8  | GET    | /matrimonios/validar/{dni1}/{dni2} | Verificar impedimentos matrimoniales entre dos personas.                     | ADMIN, REGISTRADOR, CONSULTOR |
| 9  | GET    | /personas/{dni}/familia            | Consultar árbol familiar de una persona hasta sus abuelos.                   | ADMIN, REGISTRADOR, CONSULTOR |
| 10 | PUT    | /personas/{dni}/estado-civil       | Actualizar el estado civil de una persona.                                   | ADMIN, REGISTRADOR            |
| 11 | POST   | /auth/register                     | Registrar un nuevo usuario en el sistema.                                    | ADMIN                         |
| 12 | POST   | /auth/login                        | Iniciar sesión y obtener un token JWT.                                       | PUBLIC                        |
| 13 | GET    | /auth/me                           | Obtener la información del usuario autenticado actual.                       | ADMIN, REGISTRADOR, CONSULTOR |
| 14 | PUT    | /auth/change-password              | Cambiar la contraseña del usuario actual.                                    | ADMIN, REGISTRADOR, CONSULTOR |

## 🔐 **Roles y permisos del sistema**

El sistema del RENIEC tendrá dos tipos de acceso claramente diferenciados:

- **Uso interno (empleados RENIEC)**:
    - `ADMIN`: Puede realizar cualquier acción sobre el sistema (crear, actualizar, eliminar, consultar).
    - `REGISTRADOR`: Puede crear personas, actualizar información (nombres, estado civil y padres) y registrar
      matrimonios. No puede eliminar personas.

- **Uso externo (entidades externas como SBS, PNP, bancos)**:
    - `CONSULTOR`: Únicamente puede realizar consultas (`GET`). No tiene permitido crear, actualizar o eliminar
      información.

> **Importante**: Debes asegurar la protección estricta de los endpoints según estos roles asignados.

## ⚠️ **Requisitos Técnicos Adicionales**

- Usa relaciones `@ManyToOne` y recursividad correctamente en JPA.
- Implementa consultas avanzadas exclusivamente con métodos JPA (`@Query` o métodos derivados de JpaRepository).
- Debes implementar DTO claramente definidos y excepciones personalizadas con manejo global (`@ControllerAdvice`).
- Seguridad obligatoria con roles (`ADMIN`, `REGISTRADOR`, `CONSULTOR`). Define adecuadamente la protección de endpoints
  según rol.
- 🛠️ **Variables de entorno**:
  Todos los valores sensibles y configuraciones como credenciales de base de datos, puertos y claves secretas (por
  ejemplo, para JWT) deben manejarse exclusivamente mediante **variables de entorno**.
  No se permite hardcodear ningún dato sensible en el código fuente.
- 🐘 **Base de datos PostgreSQL usando Docker**:
  La base de datos del sistema deberá ser **PostgreSQL** y debe correr dentro de un contenedor Docker.

## 🔎 **Criterios de Evaluación**

- Claridad, consistencia y complejidad del modelo de datos (relaciones y entidades).
- Cumplimiento correcto de reglas y validaciones solicitadas.
- Implementación efectiva y limpia de capas Repository y Service.
- Eficacia del manejo de excepciones globales y validaciones.
- Implementación adecuada de seguridad basada en roles.
- Código ordenado, legible y uso correcto de las mejores prácticas.

⏳ **Tiempo límite**: 2 horas.

🤖 **Uso de IA**: Permitido como asistente, pero se evaluará tu comprensión y aplicación práctica.

¡Éxitos! 🚀
