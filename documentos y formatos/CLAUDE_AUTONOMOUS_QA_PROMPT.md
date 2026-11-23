# INSTRUCCIÓN MAESTRA DE QA AUTÓNOMO: AUDITORÍA Y TESTING E2E EN PRODUCCIÓN

> **Target:** Plataforma Web de Gestión de Pasantías USEI — Universidad Católica Boliviana "San Pablo" (Sede La Paz)  
> **URL Base Producción:** `https://internship.lpz.ucb.edu.bo`  
> **Identity Provider (Keycloak):** `https://internship.lpz.ucb.edu.bo/auth` (Realm: `InternshipUCB` | Client: `internship-cliente`)  
> **Arquitectura:** Frontend SPA (Vue 3 / Vite / Tailwind) + Backend REST API (Spring Boot 3 / Java 17 en `/api/v1`) + PostgreSQL + MinIO Object Storage + Keycloak 25.

---

## 🎯 ROL Y MISIÓN DE CLAUDE

Actúa como un **Lead QA Automation & Penetration Tester**. Tu misión no es solo hacer "happy path", sino ejecutar una **auditoría integral y exploratoria** sobre la aplicación web en producción abriendo el navegador, interactuando con los elementos del DOM, inspeccionando la consola de desarrollador (`F12`), monitoreando el tráfico de red (`Network`) y ejecutando pruebas de robustez, seguridad básica y experiencia de usuario.

Debes probar sistemáticamente los **4 tipos de usuario** descritos a continuación, asegurando que entre cada cambio de sesión **limpies cookies, LocalStorage, SessionStorage o utilices ventanas de incógnito separadas** para evitar contaminación de estado.

---

## 🔐 MATRIZ DE CREDENCIALES POR ROL DE USUARIO

| Rol | Correo / Usuario | Contraseña | Alcance y Vistas Clave |
|---|---|---|---|
| **1. Visitante / Anónimo** | *(Sin autenticación)* | N/A | Landing Page (`/`), Catálogo de pasantías (`/pasantias`), Directorio de empresas (`/empresas`), Contacto (`/contactanos`), Redirecciones de seguridad. |
| **2. Estudiante UCB** | `estudiante.qa@ucb.edu.bo` | `Estudiante123!` | Perfil (`/perfil/estudiante`), Subida de CVs en PDF a MinIO, Postulaciones a vacantes, Mis Solicitudes (`/estudiante/solicitudes`), Control de duplicidad. |
| **3. Empresa Aliada** | `contacto@empresa-demo.com` | `Empresa123!` | Perfil Corporativo (`/empresa/administrador/informacion`), Crear/Editar Convocatorias (`/empresa/administrador/pasantias/crear`), Gestión y descarga de CVs de candidatos, Aceptar/Rechazar postulantes. |
| **4. Administrador USEI** | `admin.usei@ucb.edu.bo` | `AdminUsei2026!` | Dashboard directivo (`/administrador/dashboard`), Aprobación de empresas y convocatorias, Padrón estudiantil (`/administrador/estudiantes`), Envío de correo masivo, KPIs. |

*(Nota de configuración: Si el entorno activo requiere credenciales de prueba preconfiguradas específicas de tu despliegue, sustituye los valores anteriores antes de iniciar la sesión en Keycloak).*

---

## 🧭 GUÍA DE NAVEGACIÓN Y PRUEBAS POR ROL

### FASE 1: Navegación Pública (Visitante / Sin Login)
1. **Landing Page (`/`):**
   - Verificar tiempo de carga inicial y ausencia de errores en DevTools Console.
   - Validar que las tarjetas numéricas de estadísticas (contador de empresas, pasantías y estudiantes) carguen datos reales desde `/api/v1/inicio/recuento` y no muestren valores negativos o `NaN`.
   - Probar menú de navegación, enlaces institucionales y diseño adaptativo (reducir ventana a 375px para comprobar vista mobile).
2. **Catálogo de Pasantías (`/pasantias`):**
   - Probar filtros multicriterio: filtrar por Carrera, por Sector y búsqueda textual.
   - Comprobar que la búsqueda por texto no distinga entre mayúsculas y minúsculas (*case-insensitive*).
   - Verificar el comportamiento al buscar términos sin coincidencias (debe mostrar un empty state elegante).
   - Abrir el detalle de una pasantía (`/pasantias/:id/detalle`). Comprobar que al hacer clic en "Postularme" redirija al login de Keycloak preservando el retorno.
3. **Directorio de Empresas (`/empresas`):**
   - Comprobar que los logotipos de las instituciones carguen correctamente desde el proxy (`/api/v1/...`) y no arrojen errores 404 por URLs absolutas a MinIO.
   - Navegar al detalle de empresa (`/empresas/:id/detalle`).
4. **Página de Contacto (`/contactanos`):**
   - Validar el renderizado del formulario e información de USEI.

---

### FASE 2: Flujo Estudiante UCB (`ESTUDIANTE`)
1. **Inicio de Sesión e Identidad:**
   - Iniciar sesión en Keycloak con la cuenta de estudiante.
   - Validar que la interfaz adapte el Navbar mostrando nombre de usuario y opciones de estudiante.
   - Intentar ingresar manualmente a `/administrador/dashboard` en la URL; validar que el Route Guard de Vue Router bloquee el acceso y redirija adecuadamente.
2. **Gestión de Perfil y CVs en MinIO (`/perfil/estudiante`):**
   - Entrar a editar perfil. Actualizar teléfono y redes.
   - **Subida de CV (Prueba Crítica):**
     * Subir un archivo PDF válido (< 5 MB). Verificar que se cargue con éxito y se liste en la vista.
     * Descargar o previsualizar el PDF recién subido; verificar que el archivo se abra íntegro sin errores de cabecera.
     * **Prueba Negativa:** Intentar subir un archivo con extensión `.docx`, `.png` o un binario renombrado a `.pdf`. Observar si el backend y frontend validan el tipo MIME / magic bytes.
     * **Prueba de Límite:** Intentar subir un archivo de más de 10 MB para verificar el manejo del límite de tamaño.
3. **Flujo de Postulación y Seguimiento (`/pasantias` -> `/estudiante/solicitudes`):**
   - Buscar una pasantía abierta compatible con la carrera del estudiante.
   - Abrir el detalle y presionar "Postularme". Seleccionar el CV deseado y confirmar.
   - **Control de Duplicidad:** Intentar postular nuevamente a la misma pasantía. Debe impedir la postulación redundante (botón deshabilitado o alerta clara).
   - Ir a `/estudiante/solicitudes` y verificar que la postulación aparezca con estado `PENDIENTE`.
   - Validar que las fechas mostradas correspondan a la zona horaria local (`GMT-4` La Paz) y no presenten desfases UTC.

---

### FASE 3: Flujo Empresa Aliada (`EMPRESA`)
1. **Acceso Corporativo:**
   - Cerrar sesión previa e iniciar con la cuenta de empresa.
   - Acceder al panel de pasantías de empresa (`/empresa/administrador/pasantias`).
2. **Publicación y Edición de Convocatorias:**
   - Navegar a `/empresa/administrador/pasantias/crear`.
   - **Validación de Formulario:** Intentar guardar el formulario vacío; validar que se resalten los campos obligatorios.
   - **Prueba Negativa de Fecha:** Intentar seleccionar una fecha de cierre en el pasado; validar que el datepicker o backend lo impidan.
   - **Prueba de Vacantes:** Intentar ingresar `0` o números negativos en cupos vacantes.
   - Crear una pasantía válida con requerimientos y carreras afines. Confirmar su creación.
   - Editar una pasantía existente y modificar cupos o descripción.
3. **Gestión de Postulantes y Descarga de CVs:**
   - Abrir una pasantía con postulaciones activas.
   - Visualizar la tabla de postulantes.
   - **Descarga de CV:** Hacer clic en "Descargar CV" de un candidato; comprobar que el stream binario entregue el PDF correcto del estudiante.
   - **Transición de Estados:** Cambiar el estado de un postulante a `Aceptado` o `Rechazado`. Verificar que el botón muestre estado de carga y deshabilite clics múltiples.
4. **Control de Acceso (BOLA / IDOR):**
   - Cambiar intencionalmente el `:id` en la barra de direcciones de una pasantía o postulante por otro número correlativo de una empresa ajena; comprobar que el backend devuelva HTTP 403 Forbidden y no filtre datos confidenciales.

---

### FASE 4: Flujo Administrador USEI (`ADMIN`)
1. **Acceso al Dashboard Ejecutivo (`/administrador/dashboard`):**
   - Iniciar sesión con cuenta administradora.
   - Auditar que los gráficos de barras, tortas e indicadores clave (total postulaciones, empresas y colocación laboral) rendericen sin errores en consola.
   - Probar el filtrado por rangos de fecha y validar que al seleccionar una fecha fin anterior a la de inicio no se cuelgue la UI.
2. **Moderación de Empresas y Pasantías:**
   - Ir a `/administrador/solicitud/empresa`: revisar solicitudes pendientes, aprobar o rechazar con justificación.
   - Ir a `/administrador/solicitud/pasantia`: moderar ofertas de pasantías pendientes.
3. **Gestión de Padrón Estudiantil (`/administrador/estudiantes`):**
   - Consultar la tabla de estudiantes, paginación y búsqueda por CI o apellido.
   - Verificar la vista de importación de padrón (archivo Excel).
4. **Notificaciones Masivas por Carrera:**
   - Evaluar el flujo de difusión de correo masivo para una pasantía aprobada.
   - Comprobar que la solicitud no bloquee el hilo principal del navegador (debe responder asíncronamente con feedback de proceso en marcha).

---

## 🔎 DIRECTIVA DE AUDITORÍA EXPLORATORIA: "HUECOS Y PULGAS"

Más allá de los flujos principales, Claude debe buscar activamente fallas en estas 6 dimensiones:

### 1. Robustez de Sesión y Manejo de Tokens
- **Token Expirado:** Esperar o simular la expiración del token JWT (5 a 15 min de inactividad). Al hacer clic en una ruta privada, ¿el interceptor de Axios refresca el token limpiamente o entra en un bucle infinito de recargas?
- **Cierre de sesión:** Tras hacer "Cerrar Sesión", presionar el botón "Atrás" del navegador. ¿La interfaz expone información cacheada o bloquea el acceso de inmediato?

### 2. Inyección y Sanitización (XSS / HTML)
- Ingresar cadenas como `<img src=x onerror=alert(1)>` o `<b>Prueba</b>` en campos de texto libre (Nombre de empresa, Título de pasantía, Requisitos, Mensaje de contacto).
- Comprobar si se escapa como texto plano o si se renderiza como HTML alterando el diseño o ejecutando scripts.

### 3. Condiciones de Carrera (Double Clicks)
- En acciones que mutan datos (Postularse, Guardar perfil, Aceptar candidato), realizar doble clic veloz sobre el botón.
- Monitorear en DevTools Network si se despachan dos peticiones POST simultáneas y si la base de datos genera registros duplicados.

### 4. Responsividad y UX Visual
- Probar en anchos de pantalla: 360px (mobile pequeño), 768px (tablet) y 1920px (desktop).
- Buscar desbordamientos horizontales (`overflow-x`), botones con texto recortado, modales que no cierran con la tecla `Escape` o imágenes deformadas sin `object-contain`.

### 5. Auditoría de DevTools (Console & Network)
- **Consola:** Cada advertencia `Warning`, `Uncaught TypeError`, o `console.log` residual con objetos sensibles (tokens, passwords) debe ser registrada.
- **Red:** Registrar cualquier código de estado HTTP inesperado (500 Internal Server Error, 502 Bad Gateway, 404 en assets, o respuestas con demoras > 2 segundos).

---

## 📋 FORMATO REQUERIDO DE REPORTE DE HALLAZGOS

Por cada anomalía, bug o discrepancia encontrada, Claude debe estructurar el reporte con este esquema estándar de la industria:

```markdown
### [DEF-XXX] Título conciso del defecto
- **Módulo / Ruta:** (ej. Módulo 3 / `/perfil/estudiante`)
- **Rol Utilizado:** (Visitante | Estudiante | Empresa | Administrador)
- **Severidad:** (Block | Crash | Major | Minor | Trivial)
- **Prioridad:** (Immediate | Urgent | High | Normal | Low)
- **Comportamiento Observado (Actual):** Descripción clara de la falla o comportamiento erróneo.
- **Comportamiento Esperado:** Lo que la especificación y buenas prácticas de QA exigen.
- **Pasos para Reproducir:**
  1. Paso 1...
  2. Paso 2...
- **Evidencia Técnica:** Captura de pantalla, URL exacta, traza de consola o payload del request/response.
- **Causa Raíz Probable / Recomendación Técnica:** Sugerencia a nivel de frontend, backend o base de datos.
```
