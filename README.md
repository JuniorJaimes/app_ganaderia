# Ganadería Project - Sistema de Gestión de Fincas Lecheras

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

Sistema robusto para la gestión integral de fincas ganaderas (específicamente lecheras), diseñado bajo principios de **Arquitectura Hexagonal** y enfocado en la integridad del dominio y la trazabilidad de eventos.

## 🔷 Filosofía del Proyecto

Este sistema se rige por la **Identidad del Constructor** definida en `soul.md`:
- **Estructura sobre estética**: La coherencia interna y la integridad del sistema son prioritarias.
- **Independencia del Dominio**: El núcleo de la lógica de negocio es puro y no depende de frameworks externos.
- **Trazabilidad Inmutable**: Los eventos críticos del ciclo de vida del animal son registrados de forma inmutable.
- **Eficiencia Sistémica**: Diseñado para funcionar eficientemente en el mundo real, no solo en simulaciones.

## 🚀 Funcionalidades Principales

### 🐄 Gestión de Hato
- Registro y seguimiento detallado de vacas y terneros.
- Control del ciclo de vida: registros de nacimiento, compras e ingresos al hato.
- Identificación única de animales.

### 🥛 Producción y Ordeño
- Registro diario de producción de leche por vaca.
- Gestión de turnos de ordeño.
- Histórico de volumen de leche para análisis de rendimiento.

### 🧬 Reproducción y Ciclo de Vida
- Detección y registro de celos.
- Registro de inseminaciones.
- Confirmación de preñez y seguimiento del estado reproductivo.
- Registro de partos y gestión de periodos de secado.

### 🏥 Salud y Tratamientos
- Registro de estados de salud (Saludable, Enfermo, En Tratamiento).
- Gestión de tratamientos médicos con periodos de retiro para asegurar la inocuidad alimentaria.
- Historial clínico detallado por animal.

### 🔐 Seguridad y Auditoría
- Autenticación y autorización mediante **JWT (JSON Web Tokens)**.
- Gestión de usuarios y roles.
- Almacenamiento de eventos (Event Store) para auditoría e integridad sistémica.

## 🏗️ Arquitectura y Tecnologías

El proyecto utiliza una **Arquitectura Hexagonal (Puertos y Adaptadores)** para asegurar que el dominio de negocio sea el centro del sistema:

- **`domain`**: Modelos de negocio, agregados, objetos de valor y especificaciones de repositorios.
- **`application`**: Casos de uso y puertos de entrada/salida.
- **`infrastructure`**: Adaptadores para persistencia (JPA/MySQL), interfaz web (REST API) y configuración de seguridad.

### Stack Tecnológico:
- **Java 17**
- **Spring Boot 3.5.0** (Web, Data JPA, Security)
- **MySQL** como motor de base de datos
- **Lombok** para reducción de código boilerplate
- **JWT (jjwt)** para seguridad escalable
- **Dotenv** para gestión de variables de entorno

## 🛠️ Configuración e Instalación

### Requisitos
- **JDK 17** o superior.
- **Maven 3.6+**.
- **MySQL 8.0**.

### Pasos
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/app_ganaderia.git
   cd app_ganaderia
   ```

2. **Configurar variables de entorno**:
   Crea un archivo `.env` en la raíz del proyecto basado en `.env.example` (o configurar las propiedades en `application.properties`):
   ```env
   DB_URL=jdbc:mysql://localhost:3306/ganaderia_db
   DB_USERNAME=tu_usuario
   DB_PASSWORD=tu_contrasena
   JWT_SECRET=tu_secreto_super_seguro
   ```

3. **Construir el proyecto**:
   ```bash
   ./mvnw clean install
   ```

4. **Ejecutar la aplicación**:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📄 Licencia

Este proyecto está bajo la Licencia [MIT](LICENSE).

---
*Desarrollado con enfoque en la excelencia arquitectónica y la coherencia estructural.*
