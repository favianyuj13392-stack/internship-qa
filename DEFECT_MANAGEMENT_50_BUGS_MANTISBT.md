# Matriz y Guía de 50 Reportes de Defectos (MantisBT)
**Sistema:** Plataforma Web y Sistema de Gestión Integral de Pasantías USEI (UCB La Paz)  
**Entorno:** Frontend (Vue 3 / Vite) + Backend (Spring Boot 3) + Keycloak + MinIO + PostgreSQL  

---

## 📹 Protocolo de Grabación de Evidencias en Video Corto (10 a 30 Segundos)

Para que cada reporte de defecto en MantisBT sea irrefutable y facilite la depuración del equipo de desarrollo, cada integrante debe adjuntar una cápsula de video de corta duración siguiendo este estándar:

### 1. Herramientas Recomendadas
- **Xbox Game Bar (Nativo en Windows 10/11 - Sin instalación previa):**
  - Presionar `Win + Alt + R` para iniciar o detener la grabación instantáneamente.
  - Los clips se guardan automáticamente en: `C:\Users\<usuario>\Videos\Captures`.
- **Loom (Extensión de navegador o App Desktop):**
  - Ideal para generar links directos o descargar clips `.mp4` comprimidos de alta calidad.
- **OBS Studio:**
  - Configurar salida a resolución 1080p o 720p a 30fps en formato `.mp4` con bitrate optimizado (máximo 2-5 MB por archivo para no saturar MantisBT).

### 2. Estándar de Contenido del Video
1. **Segundo 0 a 5:** Mostrar el punto de partida (URL, rol del usuario autenticado, estado inicial).
2. **Segundo 5 a 20:** Ejecutar los pasos exactos de reproducción en pantalla, manteniendo visible la consola de desarrollador (`F12` -> Pestañas *Console* o *Network*) en bugs de severidad Major, Crash o Block.
3. **Segundo 20 a 30:** Evidenciar la falla (el error en pantalla, la congelación, el fallo de red HTTP 500, o el elemento roto).
4. **Nomenclatura del archivo de video:** `BUG-[ID]-[PalabraClave].mp4` (Ejemplo: `BUG-M1-002-Token-Loop.mp4`).

---

## 👥 Resumen de Asignación de 50 Bugs (10 Bugs por Integrante)

| Integrante | Módulo / Área Asignada | Rango de Bugs | Cobertura de Severidades y Prioridades |
|---|---|---|---|
| **Antonio Yujra (Líder)** | Módulo 1: Autenticación, Seguridad y Padrón | `BUG-M1-001` a `BUG-M1-010` | Block, Crash, Major, Minor, Trivial / Immediate a Low |
| **Jamie Huaycho** | Módulo 2: Portal Público y Catálogo | `BUG-M2-001` a `BUG-M2-010` | Block, Crash, Major, Minor, Trivial / Immediate a Low |
| **Mauricio Rivera** | Módulo 3: Portal Estudiante y MinIO CVs | `BUG-M3-001` a `BUG-M3-010` | Block, Crash, Major, Minor, Trivial / Immediate a Low |
| **Alejandro Pardo** | Módulo 4: Portal Empresa y Convocatorias | `BUG-M4-001` a `BUG-M4-010` | Block, Crash, Major, Minor, Trivial / Immediate a Low |
| **Sergio Mendoza** | Módulo 5: Administración USEI y Métricas | `BUG-M5-001` a `BUG-M5-010` | Block, Crash, Major, Minor, Trivial / Immediate a Low |

---

## 🪲 ANTONIO YUJRA (Líder): Módulo 1 (Autenticación, Seguridad y Padrón) - 10 Bugs

### BUG-M1-001: Cierre forzado de sesión (Force Logout) al acceder a la vista de Perfil de Estudiante
- **Categoría:** Autenticación / Sesión
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Sesión iniciada con rol de estudiante en `http://localhost:5173/`.
- **Pasos para reproducir:**
  1. Iniciar sesión y hacer clic en el avatar de usuario en la barra superior.
  2. Seleccionar la opción "Perfil" (`/perfil/estudiante`).
- **Resultado Actual:** La aplicación invoca inmediatamente `this.$keycloak.logout()` expulsando al usuario hacia el login de Keycloak tras fallar la petición de datos.
- **Resultado Esperado:** Manejar el fallo de carga de datos mostrando un mensaje de error o vista vacía sin revocar ni destruir la sesión SSO del usuario.
- **Evidencia en Video:** `BUG-M1-001-Forced-Logout-On-Profile..mp4` (Acceso a perfil y expulsión inmediata de sesión).

### BUG-M1-002: Placeholder erróneo "Apellido Paterno:" e ID duplicado en el campo "Apellido Materno"
- **Categoría:** Frontend / UI
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** En `/finish/register-estudiante` con sesión iniciada.
- **Pasos para reproducir:**
  1. Iniciar sesión y navegar al formulario de registro de estudiante (`/finish/register-estudiante`).
  2. Inspeccionar o vaciar el campo de texto "Apellido Materno".
  3. Observar el texto del placeholder en la caja de texto y la estructura del elemento en el DOM.
- **Resultado Actual:** El campo de Apellido Materno muestra erróneamente el placeholder `"Apellido Paterno:"`, utiliza el identificador duplicado `id="lastname"` (idéntico al del Apellido Paterno) y `name="name"`, y la cabecera de sección está en inglés (`Personal Detail :`).
- **Resultado Esperado:** El placeholder debe indicar `"Apellido Materno:"`, el elemento debe tener un identificador único (ej. `id="mother-lastname"`), y las etiquetas deben mantener consistencia en español.
- **Evidencia en Video:** `BUG-M1-002-Placeholder-Apellido-Materno.mp4` (Inspección del campo en DevTools evidenciando el placeholder erróneo e ID duplicado).

### BUG-M1-003: Función "Bloquear" en menú de usuario congela la interfaz sin opción de desbloqueo
- **Categoría:** UI / Usabilidad
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Sesión iniciada en cualquier vista del sistema.
- **Pasos para reproducir:**
  1. Desplegar el menú de usuario desde el avatar superior derecho.
  2. Hacer clic en la opción "Bloquear".
- **Resultado Actual:** Se despliega un overlay oscuro permanente que bloquea toda interacción con la pantalla, sin solicitar contraseña de desbloqueo, sin modal para reanudar sesión y obligando a recargar la página.
- **Resultado Esperado:** La función debe presentar un diálogo formal solicitando contraseña o PIN para desbloquear la sesión, o ser removida si no cuenta con implementación funcional.
- **Evidencia en Video:** `BUG-M1-003-Broken-Lock-Screen.mp4` (Clic en bloquear y pantalla completamente congelada con fondo negro).

### BUG-M1-004: Error 404 en consola de DevTools por recursos de imágenes de avatar no encontrados
- **Categoría:** Frontend / Consola
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Abrir DevTools (`F12`) en la pestaña *Console* y *Network*.
- **Pasos para reproducir:**
  1. Navegar por la página principal `http://localhost:5173/` o entrar al directorio de empresas.
  2. Inspeccionar la pestaña Network filtrando por imágenes / estado 404.
- **Resultado Actual:** Aparecen solicitudes en rojo con código HTTP 404 buscando imágenes o avatares por defecto inexistentes.
- **Resultado Esperado:** Disponer de una imagen SVG de avatar por defecto local (`fallback.png`) sin generar errores 404 en consola.
- **Evidencia en Video:** `BUG-M1-004-Avatar-404-Console-Error.mp4` (Pestaña Network y Console mostrando errores 404 en rojo).

### BUG-M1-005: Botón "Cancelar" en Registro de Estudiante ejecuta Logout destructivo sin confirmación
- **Categoría:** Autenticación / Sesión / Padrón
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** En formulario `/finish/register-estudiante` con sesión iniciada.
- **Pasos para reproducir:**
  1. Navegar a `http://localhost:5173/finish/register-estudiante`.
  2. Hacer scroll al pie del formulario y presionar el botón rojo "Cancelar".
- **Resultado Actual:** El botón tiene hardcodeado `@click="this.$keycloak.logout()"` en [CompletarRegistroEstudianteView.vue#L331](file:///d:/DEV/internship-qa/frontend/src/views/CompletarRegistro/CompletarRegistroEstudianteView.vue#L331). Destruye la sesión del usuario inmediatamente y lo expulsa a Keycloak perdiendo todo el avance sin confirmación.
- **Resultado Esperado:** Debe solicitar confirmación mediante un modal de advertencia y redirigir limpiamente a la página de inicio (`/`) sin revocar la sesión institucional del usuario.
- **Evidencia en Video:** `BUG-M1-005-Destructive-Cancel-Logout.mp4` (Clic en Cancelar y expulsión destructiva de la sesión).

### BUG-M1-006: Falta de atributo max en selector de Fecha de Nacimiento permite elegir fechas futuras
- **Categoría:** Validación / Frontend
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** En `/finish/register-estudiante` con sesión iniciada.
- **Pasos para reproducir:**
  1. Abrir el formulario de registro y desplegar el selector de "Fecha de Nacimiento".
  2. Navegar a un año y fecha futuros (ej. 2030) y seleccionarla.
  3. Presionar "Siguiente".
- **Resultado Actual:** El widget de calendario carece del atributo `max`, permitiendo seleccionar fechas futuras. Al enviar, la línea 1418 emite un toast informal (`toast.error("No puedes nacer en el futuro >:c payaso")`).
- **Resultado Esperado:** El `<input type="date">` debe implementar `:max="fechaMaxima"` para inhabilitar preventivamente fechas futuras a nivel de control de interfaz.
- **Evidencia en Video:** `BUG-M1-006-Fecha-Nacimiento-Futura-Max.mp4` (Selección de fecha futura en el datepicker por ausencia de restricción max).

### BUG-M1-007: Ausencia de indicador de carga (Spinner) al presionar "Iniciar Sesión" permite clics repetidos
- **Categoría:** UI / Usabilidad
- **Severidad:** **Minor** | **Prioridad:** **Low**
- **Precondiciones:** En la barra superior con el botón "Iniciar Sesión".
- **Pasos para reproducir:**
  1. Hacer dos clics veloces consecutivos sobre el botón "Iniciar Sesión".
- **Resultado Actual:** El botón no muestra animación de carga ni se deshabilita, disparando múltiples llamadas redundantes hacia Keycloak.
- **Resultado Esperado:** Deshabilitar el botón y mostrar un spinner de carga inmediato mientras se resuelve la redirección.
- **Evidencia en Video:** `BUG-M1-007-Double-Click-Login-Spam.mp4` (Clics repetidos sobre el botón sin bloqueo visual).

### BUG-M1-008: Pantalla en blanco total (White Screen) por falta de ruta catch-all 404 en rutas no mapeadas
- **Categoría:** Frontend / Vue Router
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Navegador en cualquier estado en `http://localhost:5173/`.
- **Pasos para reproducir:**
  1. Ingresar una ruta inexistente o no registrada en el router, por ejemplo: `http://localhost:5173/convocatorias`.
  2. Presionar Enter.
- **Resultado Actual:** Vue Router no tiene configurada una ruta comodín `/:pathMatch(.*)*`, resultando en una pantalla completamente en blanco sin barra de navegación ni mensaje informativo para el usuario.
- **Resultado Esperado:** Renderizar una vista amigable de error 404 (Not Found) con enlace de retorno a la página de inicio.
- **Evidencia en Video:** `BUG-M1-008-White-Screen-Direct-Route.mp4.mp4` (Ingreso a /convocatorias y pantalla blanca total).

### BUG-M1-009: Enlaces del pie de página (Términos del Servicio y Privacidad) apuntan a enlaces vacíos (#)
- **Categoría:** UI / Enlaces Rotos
- **Severidad:** **Trivial** | **Prioridad:** **Low**
- **Precondiciones:** En cualquier página del sistema, hacer scroll hasta el pie de página (footer).
- **Pasos para reproducir:**
  1. Hacer clic en los enlaces "Términos del Servicio" o "Política de Privacidad".
- **Resultado Actual:** El enlace tiene `href="#"`, recarga el scroll hacia arriba y no muestra ninguna política institucional.
- **Resultado Esperado:** Abrir un modal informativo o redirigir a la página de normativas oficiales de USEI UCB.
- **Evidencia en Video:** `BUG-M1-009-Dead-Footer-Links.mp4` (Clics en enlaces del pie de página sin efecto).

### BUG-M1-010: Bloqueo de registro al no validar dominio institucional @ucb.edu.bo en formularios de estudiante
- **Categoría:** Seguridad / Regla de Negocio
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** En formulario de registro o contacto estudiantil.
- **Pasos para reproducir:**
  1. Intentar registrar un estudiante ingresando un correo externo como `estudiante.externo@gmail.com`.
  2. Presionar el botón de verificación o envío.
- **Resultado Actual:** El formulario no restringe el dominio en tiempo real en el input permitiendo que la solicitud viaje al backend.
- **Resultado Esperado:** Validación reactiva inmediata con borde rojo indicando: "Debe ingresar obligatoriamente su correo institucional @ucb.edu.bo".
- **Evidencia en Video:** `BUG-M1-010-Invalid-Domain-Check.mp4` (Ingreso de correo gmail y falta de advertencia inmediata).

---

## 🪲 JAMIE HUAYCHO: Módulo 2 (Portal Público y Catálogo) - 10 Bugs

### BUG-M2-001: Desbordamiento horizontal y colapso de diseño en catálogo público bajo pantallas móviles (< 375px)
- **Categoría:** Frontend / UI Responsiva
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Dispositivo móvil iPhone SE o resolución 360x640px.
- **Pasos para reproducir:**
  1. Navegar a `/pasantias`.
  2. Desplazarse horizontalmente en la pantalla.
- **Resultado Actual:** La cuadrícula de filtros tiene ancho fijo provocando barra de desplazamiento horizontal y contenido recortado a la derecha.
- **Resultado Esperado:** Diseño fluido vertical adaptado a una sola columna en pantallas móviles sin scroll horizontal.
- **Evidencia en Video:** `BUG-M2-001-Mobile-Overflow-360px.mp4` (Grabación de scroll horizontal fallido en DevTools).

### BUG-M2-002: El contador público de pasantías disponibles muestra valor negativo cuando se despublican convocatorias
- **Categoría:** Lógica de Negocio / Backend
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Backend con cálculo de recuento basado en contador incremental sin recalcular sobre tabla.
- **Pasos para reproducir:**
  1. Ingresar a la página de inicio `http://localhost:5173/`.
  2. Consultar la tarjeta de estadísticas "Pasantías Abiertas".
- **Resultado Actual:** Muestra "-2 Pasantías Disponibles".
- **Resultado Esperado:** La consulta SQL debe ejecutar `COUNT(*)` sobre registros con `estado = 'PUBLICADA'` y fecha límite vigente, asegurando siempre valores `>= 0`.
- **Evidencia en Video:** `BUG-M2-002-Negative-Counter-Home.mp4` (Zoom en la tarjeta mostrando número negativo).

### BUG-M2-003: Bloqueo de renderizado (Crash de UI) al filtrar por carrera si el backend retorna `carreras: null`
- **Categoría:** Frontend / Vue 3
- **Severidad:** **Crash** | **Prioridad:** **Urgent**
- **Precondiciones:** Pasantía creada en base de datos sin carreras asociadas.
- **Pasos para reproducir:**
  1. Ingresar al catálogo de pasantías.
  2. Seleccionar cualquier carrera en el dropdown de filtros.
- **Resultado Actual:** Pantalla blanca con error en consola: `Uncaught TypeError: Cannot read properties of null (reading 'some')`.
- **Resultado Esperado:** Validación con optional chaining `pasantia.carreras?.some(...)` o array vacío por defecto, evitando que la aplicación se congele.
- **Evidencia en Video:** `BUG-M2-003-White-Screen-Null-Filter.mp4` (Selección de filtro y aparición de pantalla blanca).

### BUG-M2-004: Falla de carga de logotipos institucionales (imágenes rotas 404) por URL absoluta hardcodeada en MinIO
- **Categoría:** Infraestructura / MinIO
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Despliegue en red local con IP distinta a `localhost`.
- **Pasos para reproducir:**
  1. Abrir `/empresas` desde una máquina secundaria en la red.
- **Resultado Actual:** Los logotipos de las empresas muestran el icono de imagen rota; intentan consultar `http://localhost:9000/...` en vez de la ruta del proxy.
- **Resultado Esperado:** El backend debe generar URLs relativas `/api/v1/files/download/...` gestionadas por el proxy Nginx.
- **Evidencia en Video:** `BUG-M2-004-Broken-Images-MinIO-404.mp4` (Grilla de empresas con logotipos rotos).

### BUG-M2-005: El buscador de pasantías distingue mayúsculas y minúsculas (sensible a Case)
- **Categoría:** Backend / Búsqueda
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Pasantía con título "Desarrollador Fullstack".
- **Pasos para reproducir:**
  1. Buscar "desarrollador" (en minúsculas).
  2. Buscar "Desarrollador" (con mayúscula).
- **Resultado Actual:** La búsqueda en minúsculas retorna 0 resultados; solo coincide al ingresar mayúscula exacta.
- **Resultado Esperado:** Búsqueda insensible a mayúsculas utilizando `ILIKE` o `LOWER(titulo)` en la consulta SQL.
- **Evidencia en Video:** `BUG-M2-005-Case-Sensitive-Search.mp4` (Comparación de búsqueda con mayúscula y minúscula).

### BUG-M2-006: Botón "Postularme" en detalle de pasantía no reacciona al hacer clic si el usuario no tiene rol
- **Categoría:** Frontend / Usabilidad
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Usuario con sesión iniciada pero sin rol asignado (ej. usuario pendiente de completar registro).
- **Pasos para reproducir:**
  1. Abrir `/pasantias/5/detalle`.
  2. Hacer clic repetidamente en el botón "Postularme".
- **Resultado Actual:** No ocurre nada visualmente ni se muestra mensaje de advertencia; en consola aparece error no capturado.
- **Resultado Esperado:** Mostrar SweetAlert2 informativo: "Debe completar su perfil de estudiante antes de poder postular".
- **Evidencia en Video:** `BUG-M2-006-Dead-Click-Apply.mp4` (Clics infructuosos sobre el botón).

### BUG-M2-007: Logotipo de la Universidad en el Navbar pierde relación de aspecto en tablets
- **Categoría:** UI / CSS
- **Severidad:** **Trivial** | **Prioridad:** **Low**
- **Precondiciones:** Resolución de pantalla en 768x1024px (iPad).
- **Pasos para reproducir:**
  1. Abrir la barra de navegación superior.
- **Resultado Actual:** El logo aparece estirado verticalmente debido a una regla de `height: 100%` sin `object-contain`.
- **Resultado Esperado:** Mantener aspecto original con dimensiones proporcionales (`h-10 w-auto object-contain`).
- **Evidencia en Video:** `BUG-M2-007-Logo-Distortion-Tablet.mp4` (Inspección del elemento en DevTools mostrando deformación).

### BUG-M2-008: Convocatorias expiradas continúan listadas en el catálogo público
- **Categoría:** Lógica de Negocio / Backend
- **Severidad:** **Major** | **Prioridad:** **Urgent**
- **Precondiciones:** Pasantía con fecha límite `2026-09-01` (fecha en el pasado).
- **Pasos para reproducir:**
  1. Ingresar a `/pasantias`.
  2. Revisar las convocatorias visibles.
- **Resultado Actual:** La pasantía caducada sigue listada con normalidad en la primera página.
- **Resultado Esperado:** El backend debe filtrar automáticamente convocatorias cuya `fecha_limite < CURRENT_DATE`.
- **Evidencia en Video:** `BUG-M2-008-Expired-Internship-Visible.mp4` (Tarjeta con fecha pasada en catálogo).

### BUG-M2-009: Error 404 no controlado al acceder a detalle de empresa con ID alfanumérico inválido
- **Categoría:** Frontend / Manejo de Errores
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** URL directa en navegador.
- **Pasos para reproducir:**
  1. Escribir: `http://localhost:5173/empresas/abc-invalido/detalle`.
- **Resultado Actual:** La página se queda en estado de carga infinita con loader girando sin mostrar error.
- **Resultado Esperado:** Captura de error 400/404 y renderizado de la vista amigable de "Empresa no encontrada".
- **Evidencia en Video:** `BUG-M2-009-Infinite-Loader-404.mp4` (Carga permanente del spinner sin respuesta).

### BUG-M2-010: La página de contacto (/contactanos) envía correos con remitente no sanitizado provocando inyección de cabeceras
- **Categoría:** Seguridad / Backend
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** Formulario de contacto abierto.
- **Pasos para reproducir:**
  1. En el campo de correo ingresar: `test@ucb.edu.bo%0ABcc:spam@external.com`.
  2. Enviar mensaje de contacto.
- **Resultado Actual:** El backend no valida saltos de línea permitiendo inyección de destinatarios ocultos (Email Header Injection).
- **Resultado Esperado:** Rechazo estricto mediante regex de validación RFC 5322 en backend impidiendo saltos de línea `\r\n`.
- **Evidencia en Video:** `BUG-M2-010-Header-Injection-Mail.mp4` (Petición enviada con payload y recepción de copias ocultas).

---

## 🪲 MAURICIO RIVERA: Módulo 3 (Portal Estudiante y MinIO CVs) - 10 Bugs

### BUG-M3-001: Subida de archivo ejecutable malicioso renombrado a .pdf elude la validación en MinIO
- **Categoría:** Seguridad / Carga de Archivos
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** Archivo binario ejecutable (`script.sh` o `payload.exe`) renombrado artificialmente a `curriculum.pdf`.
- **Pasos para reproducir:**
  1. Ir a `/perfil/estudiante/editar`.
  2. Subir el archivo modificado en la sección de CVs.
- **Resultado Actual:** El backend solo valida la extensión del nombre del archivo y lo persiste en MinIO con éxito.
- **Resultado Esperado:** El backend debe validar los Magic Bytes (firmas de cabecera `%PDF-1.`) mediante Apache Tika y rechazar cualquier archivo no genuino.
- **Evidencia en Video:** `BUG-M3-001-Magic-Bytes-Bypass.mp4` (Subida de script malicioso renombrado y confirmación en MinIO).

### BUG-M3-002: Corrupción del nombre de archivo PDF en la descarga si contiene espacios y caracteres latinos
- **Categoría:** Backend / Despacho de Archivos
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** CV cargado con nombre `Curriculum Vitae - Juan Pérez & Asociados.pdf`.
- **Pasos para reproducir:**
  1. Presionar "Descargar CV".
- **Resultado Actual:** El navegador descarga el archivo como `Curriculum%20Vitae%20-%20Juan%20P_rez.pdf` o trunca el nombre en el primer espacio.
- **Resultado Esperado:** Uso correcto del encabezado `Content-Disposition: attachment; filename*=UTF-8''...` según RFC 6266.
- **Evidencia en Video:** `BUG-M3-002-Filename-Encoding-Download.mp4` (Descarga con nombre truncado en el explorador).

### BUG-M3-003: Postulación simultánea por doble clic veloz genera dos registros de postulación en BD (Race Condition)
- **Categoría:** Concurrencia / Base de Datos
- **Severidad:** **Major** | **Prioridad:** **Urgent**
- **Precondiciones:** Estudiante en la ventana de confirmación de postulación.
- **Pasos para reproducir:**
  1. Realizar un doble clic muy rápido sobre el botón "Confirmar Postulación".
- **Resultado Actual:** Se envían dos peticiones HTTP concurrentes y ambas se insertan en `aplicacionespasantias`, duplicando al estudiante.
- **Resultado Esperado:** Bloqueo del botón en cliente durante el envío y restricción de unicidad `UNIQUE(id_estudiante, id_pasantia)` en PostgreSQL.
- **Evidencia en Video:** `BUG-M3-003-Double-Submit-RaceCondition.mp4` (Doble clic y dos filas reflejadas en la base de datos).

### BUG-M3-004: La eliminación de un CV en MinIO deja registros huérfanos en la base de datos si ocurre un timeout de red
- **Categoría:** Integridad Transaccional / Backend
- **Severidad:** **Crash** | **Prioridad:** **High**
- **Precondiciones:** Falla temporal de conectividad con el contenedor MinIO.
- **Pasos para reproducir:**
  1. El estudiante solicita eliminar un CV.
  2. La llamada al SDK de MinIO lanza `ConnectTimeoutException`.
- **Resultado Actual:** La operación no es atómica: el registro en PostgreSQL se borró pero el archivo binario queda huérfano en MinIO para siempre consumiendo almacenamiento.
- **Resultado Esperado:** Uso de patrón transaccional compensatorio: no eliminar el registro de BD hasta confirmar eliminación física en MinIO, o registrar tarea de limpieza.
- **Evidencia en Video:** `BUG-M3-004-Orphan-File-MinIO.mp4` (Error de timeout y archivo persistente en MinIO Console).

### BUG-M3-005: Formulario de edición de perfil no valida formato de número telefónico boliviano
- **Categoría:** Validación / Frontend
- **Severidad:** **Minor** | **Prioridad:** **Low**
- **Precondiciones:** En `/perfil/estudiante/editar`.
- **Pasos para reproducir:**
  1. Ingresar en teléfono: `12345` o caracteres alfabéticos `mi-telefono`.
  2. Presionar "Guardar".
- **Resultado Actual:** El sistema guarda el valor sin advertencias.
- **Resultado Esperado:** Expresión regular que valide números bolivianos de 8 dígitos (celulares que inician con 6 o 7, o fijos de 7-8 dígitos).
- **Evidencia en Video:** `BUG-M3-005-Invalid-Phone-Validation.mp4` (Ingreso de letras en campo teléfono y guardado exitoso).

### BUG-M3-006: Previsualización de PDF en dispositivos móviles abre ventana en blanco en Safari iOS
- **Categoría:** Compatibilidad / Frontend
- **Severidad:** **Major** | **Prioridad:** **Normal**
- **Precondiciones:** Dispositivo iPhone con navegador Safari.
- **Pasos para reproducir:**
  1. Presionar "Ver CV".
- **Resultado Actual:** Safari bloquea el `window.open()` o muestra un marco en blanco debido a restricciones de sandbox en `iframe`.
- **Resultado Esperado:** Descarga directa con fallback a visor nativo del sistema operativo en navegadores móviles.
- **Evidencia en Video:** `BUG-M3-006-Safari-Blank-PDF.mp4` (Grabación de pantalla en iPhone mostrando pestaña vacía).

### BUG-M3-007: El modal de confirmación de postulación no se cierra tras presionar Escape (ESC)
- **Categoría:** Accesibilidad / UI
- **Severidad:** **Trivial** | **Prioridad:** **Low**
- **Precondiciones:** Modal de confirmación abierto.
- **Pasos para reproducir:**
  1. Presionar la tecla física `Escape`.
- **Resultado Actual:** El modal permanece abierto obligando a hacer clic en la "X" o cancelar.
- **Resultado Esperado:** Event listener `keydown.esc` que cierre el diálogo cumpliendo pautas de accesibilidad WCAG.
- **Evidencia en Video:** `BUG-M3-007-Modal-Esc-Key.mp4` (Presión de tecla Escape sin respuesta).

### BUG-M3-008: Error HTTP 500 al listar postulaciones si la empresa propietaria de la pasantía fue dada de baja
- **Categoría:** Backend / JPA Joins
- **Severidad:** **Crash** | **Prioridad:** **Urgent**
- **Precondiciones:** Estudiante con postulación a una pasantía cuya empresa fue suspendida o eliminada por USEI.
- **Pasos para reproducir:**
  1. Navegar a `/estudiante/solicitudes`.
- **Resultado Actual:** Pantalla de error con código 500 (`EntityNotFoundException: Unable to find Institucion with id...`). El estudiante no puede ver ninguna de sus otras postulaciones.
- **Resultado Esperado:** Consulta resiliente con `LEFT JOIN` que maneje instituciones inactivas mostrando un placeholder: "Institución no disponible".
- **Evidencia en Video:** `BUG-M3-008-Join-Crash-InactiveCompany.mp4` (Error 500 al entrar a mis postulaciones).

### BUG-M3-009: La fecha de postulación muestra la hora con zona horaria UTC en lugar de la hora local de Bolivia (GMT-4)
- **Categoría:** Frontend / Formato de Fechas
- **Severidad:** **Minor** | **Prioridad:** **Low**
- **Precondiciones:** Postulación realizada a las 21:00 hora de La Paz.
- **Pasos para reproducir:**
  1. Consultar la fecha de postulación en la lista de solicitudes.
- **Resultado Actual:** Muestra la fecha del día siguiente a las `01:00 UTC` desorientando al estudiante respecto al plazo límite.
- **Resultado Esperado:** Conversión de timestamps UTC a `America/La_Paz` mediante date-fns o Luxon.
- **Evidencia en Video:** `BUG-M3-009-Timezone-Discrepancy.mp4` (Comparación de hora del sistema vs hora en la tabla).

### BUG-M3-010: Fuga de memoria en el componente de carga de archivos por no revocar ObjectURL
- **Categoría:** Frontend / Rendimiento
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** En la pantalla de carga de imagen de perfil.
- **Pasos para reproducir:**
  1. Seleccionar sucesivamente 15 imágenes grandes para previsualización.
  2. Monitorear pestaña Memory de DevTools.
- **Resultado Actual:** El uso de memoria heap del navegador se incrementa continuamente porque no se llama a `URL.revokeObjectURL(previewUrl)`.
- **Resultado Esperado:** Invocación de `revokeObjectURL` antes de crear una nueva URL y en el hook `onBeforeUnmount`.
- **Evidencia en Video:** `BUG-M3-010-Memory-Leak-ObjectURL.mp4` (Gráfico de asignación de memoria en DevTools).

---

## 🪲 ALEJANDRO PARDO: Módulo 4 (Portal Empresa y Convocatorias) - 10 Bugs

### BUG-M4-001: La fecha límite de pasantía permite seleccionar fechas en el pasado en el selector del calendario
- **Categoría:** Validación / Frontend
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** En `/empresa/administrador/pasantias/crear`.
- **Pasos para reproducir:**
  1. Abrir el campo "Fecha Límite de Postulación".
  2. Seleccionar una fecha de hace un mes.
  3. Presionar "Publicar".
- **Resultado Actual:** El frontend no restringe el atributo `min` y el backend acepta la fecha creando una pasantía expirada desde su origen.
- **Resultado Esperado:** El datepicker debe bloquear fechas anteriores al día actual (`min="today"`) y el backend validar `@Future` en el DTO.
- **Evidencia en Video:** `BUG-M4-001-Past-Date-Picker.mp4` (Selección de fecha pasada y guardado exitoso).

### BUG-M4-002: El cupo de vacantes permite ingresar números negativos (-5 vacantes)
- **Categoría:** Validación / Backend
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Formulario de creación de pasantía.
- **Pasos para reproducir:**
  1. En el campo "Cupos Disponibles" ingresar el valor `-5`.
  2. Guardar la pasantía.
- **Resultado Actual:** Se almacena la pasantía con cupo `-5` corrompiendo la lógica de cálculo de vacantes en el catálogo.
- **Resultado Esperado:** Restricción `@Min(value = 1, message = "Debe ofrecer al menos una vacante")` en el backend.
- **Evidencia en Video:** `BUG-M4-002-Negative-Vacancies.mp4` (Guardado de número negativo y visualización en catálogo).

### BUG-M4-003: Al cambiar de estado a un postulante a "Aceptado", el botón sigue activo permitiendo múltiples clics
- **Categoría:** UI / Estado de Botones
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** En la lista de postulantes de una pasantía.
- **Pasos para reproducir:**
  1. Hacer clic en "Aceptar Candidato".
- **Resultado Actual:** Mientras la API responde, el botón permanece clickeable sin indicador de spinner o deshabilitación.
- **Resultado Esperado:** Deshabilitar botón inmediatamente con clase `:disabled` y mostrar estado de carga mientras se resuelve la petición.
- **Evidencia en Video:** `BUG-M4-003-Button-Not-Disabled-Pending.mp4` (Clic repetido sin bloqueo visual).

### BUG-M4-004: Inyección de código HTML en el campo "Requisitos de la Convocatoria" altera el layout del portal
- **Categoría:** Seguridad / XSS
- **Severidad:** **Major** | **Prioridad:** **Urgent**
- **Precondiciones:** Empresa creando pasantía.
- **Pasos para reproducir:**
  1. En la descripción de requisitos pegar: `<div style="position:fixed;top:0;left:0;width:100vw;height:100vh;background:red;z-index:9999;">HACK</div>`.
  2. Publicar la oferta y abrirla en el portal público.
- **Resultado Actual:** El frontend renderiza mediante `v-html` sin sanitizar, cubriendo toda la pantalla de rojo.
- **Resultado Esperado:** Sanitización obligatoria con DOMPurify antes de renderizar HTML enriquecido o uso de texto plano.
- **Evidencia en Video:** `BUG-M4-004-XSS-HTML-Injection.mp4` (Inyección de div que rompe la pantalla del portal).

### BUG-M4-005: Fallo al descargar CV de postulante si el estudiante actualizó su CV después de postular
- **Categoría:** Backend / MinIO References
- **Severidad:** **Crash** | **Prioridad:** **Urgent**
- **Precondiciones:** Estudiante postuló con CV Versión 1. Luego el estudiante eliminó o sobreescribió la versión 1 en su perfil.
- **Pasos para reproducir:**
  1. La empresa intenta descargar el CV del postulante.
- **Resultado Actual:** Error HTTP 404 de MinIO (`NoSuchKey: The specified key does not exist`) y crash 500 en el backend.
- **Resultado Esperado:** Los CVs asociados a postulaciones deben quedar inmutables o versionados con snapshot permanente en MinIO.
- **Evidencia en Video:** `BUG-M4-005-NoSuchKey-MinIO-Crash.mp4` (Intento de descarga y error NoSuchKey).

### BUG-M4-006: Texto en botón de acción dice "Cancelar" en vez de "Rechazar Candidato"
- **Categoría:** UI / Copywriting
- **Severidad:** **Trivial** | **Prioridad:** **Low**
- **Precondiciones:** Modal de confirmación para declinar a un estudiante.
- **Pasos para reproducir:**
  1. Seleccionar la acción de descartar candidato.
- **Resultado Actual:** Los dos botones del diálogo dicen "Cancelar" y "Cerrar", confundiendo al reclutador sobre cuál ejecuta la acción.
- **Resultado Esperado:** Botón de acción: "Rechazar Candidato" (color rojo) y botón de descarte: "Cancelar" (color gris).
- **Evidencia en Video:** `BUG-M4-006-Confusing-Buttons-Modal.mp4` (Visualización de botones con texto ambiguo).

### BUG-M4-007: La empresa puede ver postulantes de convocatorias que pertenecen a otra sucursal o institución
- **Categoría:** Seguridad / Control de Acceso
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** Dos usuarios de empresas distintas.
- **Pasos para reproducir:**
  1. Usuario de Empresa B navega directamente a `/empresa/administrador/pasantias/informacion/1` (ID de pasantía de Empresa A).
- **Resultado Actual:** La interfaz renderiza los nombres y hojas de vida de los postulantes de la Empresa A.
- **Resultado Esperado:** El backend debe validar que `pasantia.getInstitucion().getId() == usuario.getInstitucion().getId()` y denegar con HTTP 403.
- **Evidencia en Video:** `BUG-M4-007-Cross-Company-Data-Leak.mp4` (Cambio de ID en URL y visualización de datos de otra empresa).

### BUG-M4-008: Paginador de candidatos reinicia a la página 1 tras cambiar el estado de un postulante en la página 3
- **Categoría:** Frontend / Usabilidad
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Pasantía con 40 postulantes divididos en 4 páginas.
- **Pasos para reproducir:**
  1. Ir a la página 3 de la tabla de candidatos.
  2. Aceptar o rechazar a un estudiante.
- **Resultado Actual:** La tabla recarga la página 1 obligando al usuario a volver a navegar a la página 3 manualmente.
- **Resultado Esperado:** Preservar el parámetro de paginación activo (`currentPage = 3`) tras la mutación de estado.
- **Evidencia en Video:** `BUG-M4-008-Pagination-Reset-Bug.mp4` (Aprobación en página 3 y salto abrupto a página 1).

### BUG-M4-009: Error de truncado en base de datos al ingresar descripción de funciones mayor a 255 caracteres
- **Categoría:** Backend / Base de Datos
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Formulario de edición de pasantía.
- **Pasos para reproducir:**
  1. Escribir una descripción detallada de funciones de 300 caracteres.
  2. Presionar "Guardar".
- **Resultado Actual:** Backend arroja `DataException: value too long for type character varying(255)`.
- **Resultado Esperado:** La columna en PostgreSQL debe ser de tipo `TEXT` o definir `@Column(columnDefinition = "TEXT")` en la entidad JPA.
- **Evidencia en Video:** `BUG-M4-009-Varchar-Truncation-Error.mp4` (Texto largo y respuesta con error 500).

### BUG-M4-010: La carga de logotipo de empresa no previsualiza la imagen antes de presionar guardar
- **Categoría:** Frontend / UX
- **Severidad:** **Minor** | **Prioridad:** **Low**
- **Precondiciones:** En `/empresa/administrador/informacion`.
- **Pasos para reproducir:**
  1. Seleccionar un archivo de imagen en el input file.
- **Resultado Actual:** El contenedor del logotipo permanece vacío o con la imagen previa hasta presionar "Guardar Cambios".
- **Resultado Esperado:** Crear vista previa inmediata mediante `URL.createObjectURL(file)` para verificar encuadre antes de enviar.
- **Evidencia en Video:** `BUG-M4-010-Missing-Image-Preview.mp4` (Selección de archivo sin cambio visual previo).

---

## 🪲 SERGIO MENDOZA: Módulo 5 (Administración USEI y Métricas) - 10 Bugs

### BUG-M5-001: Bloqueo de la API al solicitar difusión de correo masivo por ejecución síncrona en hilo web
- **Categoría:** Backend / Rendimiento y Arquitectura
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** Carrera con 450 estudiantes registrados en el padrón.
- **Pasos para reproducir:**
  1. En el panel de administrador, presionar "Enviar correo masivo a Ingeniería de Sistemas".
- **Resultado Actual:** La petición HTTP se queda colgada durante 2 minutos hasta arrojar `504 Gateway Timeout`. Durante este tiempo la API no responde a otros usuarios.
- **Resultado Esperado:** El método de `EmailService` debe ejecutarse en segundo plano con `@Async` y retornar inmediatamente HTTP 202 Accepted.
- **Evidencia en Video:** `BUG-M5-001-Sync-Email-Thread-Lock.mp4` (Espera de 2 minutos y posterior Gateway Timeout en Nginx).

### BUG-M5-002: El gráfico de inserción laboral en el Dashboard duplica el conteo si un estudiante postuló a dos carreras
- **Categoría:** Backend / SQL Analytics
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Estudiante de doble titulación con postulación aceptada.
- **Pasos para reproducir:**
  1. Consultar el gráfico "Estudiantes Insertados por Carrera" en `/administrador/dashboard`.
- **Resultado Actual:** La consulta SQL ejecuta un `JOIN` sin `DISTINCT(id_estudiante)` contando a la persona dos veces en el total acumulado.
- **Resultado Esperado:** Contar identificadores únicos de personas insertadas evitando inflación artificial de estadísticas institucionales.
- **Evidencia en Video:** `BUG-M5-002-Double-Count-Analytics.mp4` (Discrepancia entre la suma del gráfico y el total real de estudiantes).

### BUG-M5-003: Caída del Dashboard (Crash) al seleccionar un rango de fechas con fecha fin anterior a fecha inicio
- **Categoría:** Frontend / Validación
- **Severidad:** **Crash** | **Prioridad:** **Urgent**
- **Precondiciones:** Selector de fechas en panel de analítica.
- **Pasos para reproducir:**
  1. Elegir Fecha Inicio: `2026-12-31`.
  2. Elegir Fecha Fin: `2026-01-01`.
  3. Presionar "Filtrar".
- **Resultado Actual:** La librería de gráficos (ApexCharts/Chart.js) arroja `Uncaught RangeError: Invalid time value` dejando todo el Dashboard en blanco.
- **Resultado Esperado:** Validación en cliente que impida seleccionar una fecha de finalización previa y muestre toast: "Rango de fechas no válido".
- **Evidencia en Video:** `BUG-M5-003-Chart-Date-Crash.mp4` (Selección de fecha invertida y pantalla en blanco).

### BUG-M5-004: La exportación a CSV genera caracteres ilegibles en Excel debido a la ausencia de Byte Order Mark (BOM)
- **Categoría:** Backend / Exportación de Archivos
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Reporte con nombres con tildes (ej. "José María Velásquez").
- **Pasos para reproducir:**
  1. Presionar "Exportar Reporte a CSV".
  2. Abrir el archivo directamente con Microsoft Excel en Windows.
- **Resultado Actual:** Todos los caracteres con tildes se muestran distorsionados (mojibake).
- **Resultado Esperado:** Incluir el prefijo UTF-8 BOM (`\uFEFF`) al inicio del archivo generado para que Excel reconozca automáticamente UTF-8.
- **Evidencia en Video:** `BUG-M5-004-CSV-BOM-Excel-Glitch.mp4` (Apertura del archivo CSV en Excel mostrando texto corrupto).

### BUG-M5-005: Desalineación visual de las tarjetas de KPIs en monitores ultra-wide (2560x1440px)
- **Categoría:** UI / CSS
- **Severidad:** **Trivial** | **Prioridad:** **Low**
- **Precondiciones:** Resolución de pantalla en 2K o 4K.
- **Pasos para reproducir:**
  1. Maximizar la ventana en monitor ultra-wide.
- **Resultado Actual:** Las tarjetas de métricas no tienen contenedor de ancho máximo (`max-w-7xl`), expandiéndose hasta los bordes extremos de la pantalla.
- **Resultado Esperado:** Envolver el dashboard en una clase container centrada (`max-w-7xl mx-auto px-4`).
- **Evidencia en Video:** `BUG-M5-005-UltraWide-Layout-Stretch.mp4` (Pantalla estirada en monitor de alta resolución).

### BUG-M5-006: Rechazar una empresa sin ingresar motivo deja el estado en blanco en la base de datos
- **Categoría:** Backend / Integridad de Datos
- **Severidad:** **Minor** | **Prioridad:** **Normal**
- **Precondiciones:** Empresa en estado `PENDIENTE_APROBACION`.
- **Pasos para reproducir:**
  1. Presionar "Rechazar Empresa".
  2. Borrar el texto del motivo y enviar.
- **Resultado Actual:** El backend actualiza el estado a `RECHAZADA` pero la columna `motivo_rechazo` queda como `null`, impidiendo saber el porqué en auditorías.
- **Resultado Esperado:** Validación que exija al menos 15 caracteres descriptivos para documentar formalmente el motivo del rechazo.
- **Evidencia en Video:** `BUG-M5-006-Null-Rejection-Reason.mp4` (Envío de modal vacío y campo null en PostgreSQL).

### BUG-M5-007: Fallo en la reactivación de una pasantía desierta debido a validación de fecha antigua
- **Categoría:** Lógica de Negocio / Frontend
- **Severidad:** **Major** | **Prioridad:** **High**
- **Precondiciones:** Pasantía en `/administrador/pasantia/sinaplicantes`.
- **Pasos para reproducir:**
  1. Administrador intenta extender el plazo de una pasantía desierta para dar 15 días adicionales.
- **Resultado Actual:** La pantalla de edición mantiene la fecha original y al guardar arroja error de validación impidiendo salvar la nueva fecha límite.
- **Resultado Esperado:** Permitir modificar y guardar la nueva fecha de vencimiento actualizando su estado nuevamente a `PUBLICADA`.
- **Evidencia en Video:** `BUG-M5-007-Republish-Deserted-Failure.mp4` (Error al intentar extender la fecha de pasantía desierta).

### BUG-M5-008: El filtro de analítica por sector empresarial no limpia los gráficos al desmarcar todos los sectores
- **Categoría:** Frontend / Reactividad
- **Severidad:** **Minor** | **Prioridad:** **Low**
- **Precondiciones:** Sectores "Banca" y "Tecnología" seleccionados.
- **Pasos para reproducir:**
  1. Desmarcar todos los checkboxes de sectores.
- **Resultado Actual:** Los gráficos siguen mostrando los datos del último sector desmarcado en vez de restablecerse a la vista global.
- **Resultado Esperado:** Al vaciar la selección, restablecer automáticamente el filtro mostrando el acumulado consolidado de todos los sectores.
- **Evidencia en Video:** `BUG-M5-008-Filter-Uncheck-Stale-State.mp4` (Desmarcado de checkboxes sin actualización del gráfico).

### BUG-M5-009: Peticiones repetidas al endpoint de métricas cada 2 segundos provocan saturación del pool HikariCP
- **Categoría:** Frontend / Polling Ineficiente
- **Severidad:** **Crash** | **Prioridad:** **Urgent**
- **Precondiciones:** Pestaña de Dashboard abierta en segundo plano.
- **Pasos para reproducir:**
  1. Dejar la pestaña abierta durante 20 minutos sin interactuar.
  2. Revisar los logs del backend Spring Boot.
- **Resultado Actual:** Un `setInterval` no cancelado continúa llamando a `/analytics` en segundo plano saturando el pool de conexiones de base de datos (`HikariPool-1 - Connection is not available`).
- **Resultado Esperado:** Detener polling cuando la pestaña esté inactiva (`document.hidden`) y limpiar el temporizador en `onUnmounted()`.
- **Evidencia en Video:** `BUG-M5-009-Hikari-Pool-Exhaustion.mp4` (Inspección de logs de Spring Boot mostrando pool saturado).

### BUG-M5-010: El botón de revocar acceso de empresa no invalida los tokens activos de la organización en Keycloak
- **Categoría:** Seguridad / IAM
- **Severidad:** **Block** | **Prioridad:** **Immediate**
- **Precondiciones:** Administrador USEI suspende a una empresa por irregularidades.
- **Pasos para reproducir:**
  1. USEI marca a la empresa como "SUSPENDIDA".
  2. El usuario de la empresa (con sesión iniciada previamente) intenta publicar una nueva pasantía antes de que expire su JWT de 30 min.
- **Resultado Actual:** La solicitud es aceptada porque el backend solo valida la firma del token sin consultar la revocación o el estado actual de la empresa en BD.
- **Resultado Esperado:** El backend debe consultar el estado de la institución en base de datos o comunicarse con el Admin Client de Keycloak para revocar sesiones activas inmediatamente.
- **Evidencia en Video:** `BUG-M5-010-Revocation-Bypass-JWT.mp4` (Empresa suspendida que continúa operando con token existente).
