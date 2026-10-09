# Internship UCB - QA Monorepo

Repositorio de control de calidad y pruebas integradas para el sistema de pasantías USEI (Universidad Católica Boliviana "San Pablo").

Contiene la suite completa de Frontend y Backend desacoplada del entorno de producción.

---

## 📁 Estructura del Proyecto

```text
internship-qa/
├── backend/    # Servicio Backend (Spring Boot 3 / Java 17)
├── frontend/   # Aplicación Frontend (Vue 3 / Vite / TailwindCSS)
└── README.md
```

---

## 🚀 Guía de Inicio Rápido para QA

### 1. Backend (`/backend`)

**Requisitos:**
- Java 17 (JDK)
- Maven 3.8+
- PostgreSQL
- MinIO Object Storage
- Keycloak

**Ejecución:**
```bash
cd backend
mvn clean spring-boot:run
```

---

### 2. Frontend (`/frontend`)

**Requisitos:**
- Node.js 18+
- npm

**Instalación y ejecución:**
```bash
cd frontend
npm install
npm run dev
```

La aplicación web estará disponible por defecto en: `http://localhost:5173`.
