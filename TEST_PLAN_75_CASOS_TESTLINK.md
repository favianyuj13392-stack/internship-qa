# Plan de Pruebas y Matriz de 75 Casos de Prueba (TestLink)
**Sistema:** Plataforma Web y Sistema de Gestión Integral de Pasantías USEI (UCB La Paz)  
**Entorno bajo prueba:** Frontend (Vue 3 / Vite en `:5173`) + Backend (Spring Boot 3 en `:8085`) + Keycloak 25 (`:8081`) + MinIO (`:9000`) + PostgreSQL 14 (`:5432`)

---

## 👥 Estructura de Asignación por Integrante (15 Casos por Miembro)

| Integrante | Módulo / Test Suite | Rango de IDs | Enfoque de Pruebas |
|---|---|---|---|
| **Antonio Yujra (Líder)** | Módulo 1: Autenticación, Seguridad y Padrón Estudiantil | `TC-M1-001` a `TC-M1-015` | Keycloak SSO, JWT Bearer, RBAC, Padrón Estudiantil Excel e importación |
| **Jamie Huaycho** | Módulo 2: Portal Público y Catálogo de Pasantías | `TC-M2-001` a `TC-M2-015` | Landing page, contadores KPI públicos, filtros multicriterio, directorio de empresas |
| **Mauricio Rivera** | Módulo 3: Portal Estudiante | `TC-M3-001` a `TC-M3-015` | Perfil de estudiante, subida/descarga de CVs en MinIO (PDF), postulación y control de duplicidad |
| **Alejandro Pardo** | Módulo 4: Portal Empresa | `TC-M4-001` a `TC-M4-015` | Registro institucional, publicación de ofertas, gestión de candidatos y descarga de CVs |
| **Sergio Mendoza** | Módulo 5: Administración USEI | `TC-M5-001` a `TC-M5-015` | Aprobación de empresas, moderación de pasantías, correos masivos por carrera, Dashboard de analítica |

---

## 📁 MÓDULO 1: Autenticación, Seguridad y Padrón Estudiantil (Asignado a: Antonio Yujra - Líder)

### TC-M1-001: Inicio de sesión exitoso con rol ESTUDIANTE vía Keycloak SSO
- **Precondiciones:** Servidor Keycloak activo con usuario estudiante registrado (`juan.perez@ucb.edu.bo`) con rol `ESTUDIANTE` en el realm institucional.
- **Pasos:**
  1. Navegar a `http://localhost:5173/`.
  2. Hacer clic en "Iniciar Sesión".
  3. En la pantalla de Keycloak, ingresar usuario `juan.perez@ucb.edu.bo` y contraseña válida.
  4. Presionar "Log In".
- **Resultado Esperado:** Redirección exitosa al portal con token JWT en LocalStorage/Cookies y barra de navegación adaptada al perfil estudiante.
- **Resultado de Ejecución:** Passed

### TC-M1-002: Rechazo de autenticación con credenciales inválidas en Keycloak
- **Precondiciones:** Usuario en pantalla de login de Keycloak.
- **Pasos:**
  1. Ingresar correo institucional válido `maria.gomez@ucb.edu.bo`.
  2. Ingresar contraseña incorrecta `wrongpass123`.
  3. Presionar "Log In".
- **Resultado Esperado:** Mensaje de error de Keycloak ("Invalid username or password"), sin emisión de token y manteniendo la sesión cerrada.
- **Resultado de Ejecución:** Passed

### TC-M1-003: Acceso no autorizado de Estudiante a ruta administrativa (/administrador/dashboard)
- **Precondiciones:** Sesión iniciada con rol `ESTUDIANTE`.
- **Pasos:**
  1. Escribir manualmente en la barra de direcciones del navegador: `http://localhost:5173/administrador/dashboard`.
  2. Presionar Enter.
- **Resultado Esperado:** El Route Guard de Vue Router intercepta la solicitud, deniega el acceso y redirige a la página de inicio o vista de error 403.
- **Resultado de Ejecución:** Passed

### TC-M1-004: Invocación de endpoint protegido de Backend sin cabecera Authorization (HTTP 401)
- **Precondiciones:** Backend ejecutándose en `http://localhost:8085`.
- **Pasos:**
  1. Enviar una petición HTTP GET a `/api/v1/estudiante/perfil` sin cabecera `Authorization`.
- **Resultado Esperado:** El servidor responde con código de estado HTTP 401 Unauthorized y estructura JSON estandarizada de error.
- **Resultado de Ejecución:** Passed

### TC-M1-005: Invocación de endpoint de empresa con token de estudiante (HTTP 403 Forbidden)
- **Precondiciones:** Token JWT válido emitido para un usuario con rol `ESTUDIANTE`.
- **Pasos:**
  1. Enviar petición HTTP POST a `/api/v1/pasantia` con el Bearer token de estudiante.
- **Resultado Esperado:** Spring Security valida el token, identifica la ausencia del rol `EMPRESA` y responde con HTTP 403 Forbidden.
- **Resultado de Ejecución:** Passed

### TC-M1-006: Expiración de Access Token y renovación automática vía Refresh Token
- **Precondiciones:** Sesión activa con token de acceso expirado (simulado o tras lapso de expiración configurado).
- **Pasos:**
  1. Realizar una acción en el Frontend que invoque un endpoint protegido.
  2. Inspeccionar la pestaña Network de DevTools.
- **Resultado Esperado:** El interceptor de Axios detecta el 401, solicita nuevo token al endpoint `/token` de Keycloak con el Refresh Token y reintenta la petición original con éxito.
- **Resultado de Ejecución:** Passed

### TC-M1-007: Cierre de sesión (Single Sign-Out) e invalidación de token
- **Precondiciones:** Sesión iniciada en el sistema.
- **Pasos:**
  1. Hacer clic en el menú de usuario y presionar "Cerrar Sesión".
  2. Intentar regresar mediante el botón "Atrás" del navegador.
- **Resultado Esperado:** Tokens eliminados del almacenamiento del navegador, sesión destruida en Keycloak y bloqueo de acceso a vistas protegidas.
- **Resultado de Ejecución:** Passed

### TC-M1-008: Verificación de estudiante en Padrón Estudiantil activo durante registro
- **Precondiciones:** Estudiante nuevo cuyo CI y código universitario existen en el Padrón importado por USEI.
- **Pasos:**
  1. Ingresar al flujo de completar registro de estudiante (`/completar-registro-estudiante`).
  2. Ingresar CI, código de estudiante y carrera.
  3. Presionar "Verificar y Completar".
- **Resultado Esperado:** El backend valida contra la tabla `padron_estudiante`, confirma coincidencia y habilita la creación del perfil con estado activo.
- **Resultado de Ejecución:** Passed

### TC-M1-009: Bloqueo de registro para estudiante inexistente en Padrón Estudiantil
- **Precondiciones:** Estudiante con credencial Keycloak pero con datos no listados en el padrón universitario.
- **Pasos:**
  1. Ingresar al formulario de completar registro con CI inexistente `99999999`.
  2. Presionar "Verificar y Completar".
- **Resultado Esperado:** Backend rechaza la solicitud (HTTP 400/404) y redirige a la vista `/no-autorizado-padron` informando que debe regularizar su estado con USEI.
- **Resultado de Ejecución:** Passed

### TC-M1-010: Carga masiva de Padrón Estudiantil mediante archivo Excel por Administrador
- **Precondiciones:** Sesión activa con rol `ADMIN`. Archivo `.xlsx` con columnas requeridas (CI, Nombres, Carrera, Semestre, Código).
- **Pasos:**
  1. Navegar a `/administrador/estudiantes/padron`.
  2. Seleccionar el archivo Excel preparado y presionar "Importar Padrón".
- **Resultado Esperado:** Backend procesa el archivo mediante `AdminPadronApi`, registra los registros válidos en base de datos y muestra resumen de importación (filas insertadas y omitidas).
- **Resultado de Ejecución:** Passed

### TC-M1-011: Validación de archivo corrupto o formato no soportado en importación de padrón
- **Precondiciones:** Sesión con rol `ADMIN`.
- **Pasos:**
  1. En `/administrador/estudiantes/padron`, subir un archivo `.txt` o `.pdf` renombrado a `.xlsx`.
  2. Presionar "Importar Padrón".
- **Resultado Esperado:** El backend captura la excepción, no corrompe la tabla y retorna HTTP 400 con mensaje: "Formato de archivo inválido".
- **Resultado de Ejecución:** Passed

### TC-M1-012: Prevención de SQL Injection en campo de búsqueda de padrón
- **Precondiciones:** Acceso al buscador de padrón estudiantil.
- **Pasos:**
  1. En el campo de búsqueda ingresar el payload: `' OR '1'='1' --`.
  2. Ejecutar la búsqueda.
- **Resultado Esperado:** Spring Data JPA utiliza consultas parametrizadas; no se altera la lógica SQL y la consulta retorna 0 resultados o filtra literalmente por el texto.
- **Resultado de Ejecución:** Passed

### TC-M1-013: Detección y registro de eventos de acceso (Auditoría de Login)
- **Precondiciones:** Acceso a la base de datos o endpoint de auditoría.
- **Pasos:**
  1. Realizar login exitoso con usuario estudiante.
  2. Consultar la tabla `evento_acceso` en PostgreSQL.
- **Resultado Esperado:** Se registra un registro con timestamp, ID de usuario, dirección IP y tipo de evento `LOGIN_SUCCESS`.
- **Resultado de Ejecución:** Passed

### TC-M1-014: Validación de Cross-Site Scripting (XSS) en nombres de usuario en cabecera
- **Precondiciones:** Formulario de edición de perfil.
- **Pasos:**
  1. Ingresar en el campo de nombres: `<script>alert('XSS')</script>`.
  2. Guardar cambios y recargar el portal.
- **Resultado Esperado:** Vue 3 escapa el contenido en el DOM; no se ejecuta el script y se renderiza como texto plano.
- **Resultado de Ejecución:** Passed

### TC-M1-015: Control de concurrencia y sesión única por usuario
- **Precondiciones:** Cuenta de usuario estudiante activa.
- **Pasos:**
  1. Iniciar sesión en Navegador A (Chrome).
  2. Iniciar sesión con la misma cuenta en Navegador B (Firefox en incógnito).
  3. Realizar una acción en Navegador A.
- **Resultado Esperado:** Dependiendo de la política institucional de Keycloak, se mantiene o invalida la sesión anterior con notificación adecuada sin inconsistencias de estado.
- **Resultado de Ejecución:** Passed

---

## 📁 MÓDULO 2: Portal Público y Catálogo de Pasantías (Asignado a: Jamie Huaycho)

### TC-M2-001: Carga y renderizado inicial de la Landing Page sin autenticación
- **Precondiciones:** Navegador web en modo incógnito (sin cookies ni tokens).
- **Pasos:**
  1. Navegar a `http://localhost:5173/`.
- **Resultado Esperado:** La página de inicio carga completamente en menos de 1.5s, mostrando hero banner, enlaces institucionales y sección de oportunidades.
- **Resultado de Ejecución:** Passed

### TC-M2-002: Visualización de contadores estadísticos públicos (Recuento Inicio)
- **Precondiciones:** Base de datos con pasantías, empresas y estudiantes cargados.
- **Pasos:**
  1. Visualizar la sección de estadísticas en la Landing Page.
  2. Verificar la llamada a `/api/v1/inicio/recuento`.
- **Resultado Esperado:** Se muestran tarjetas numéricas con total de empresas aliadas, pasantías publicadas y estudiantes registrados con datos sincronizados del backend.
- **Resultado de Ejecución:** Passed

### TC-M2-003: Listado de convocatorias de pasantías activas y públicas
- **Precondiciones:** Existen pasantías en estado `PUBLICADA` en la base de datos.
- **Pasos:**
  1. Navegar a la sección del catálogo `/pasantias`.
- **Resultado Esperado:** Se muestran tarjetas de pasantías con título, logo institucional, carrera requerida y fecha límite de postulación.
- **Resultado de Ejecución:** Passed

### TC-M2-004: Filtrado de pasantías por carrera universitaria
- **Precondiciones:** Catálogo con pasantías de múltiples carreras (Ing. de Sistemas, Adm. de Empresas, Derecho).
- **Pasos:**
  1. En el selector de carreras, elegir "Ingeniería de Sistemas".
- **Resultado Esperado:** El catálogo actualiza la vista reactivamente mostrando únicamente las convocatorias asociadas a Ingeniería de Sistemas.
- **Resultado de Ejecución:** Passed

### TC-M2-005: Filtrado de pasantías por sector económico de la empresa
- **Precondiciones:** Empresas registradas en sectores Tecnología, Banca y Telecomunicaciones.
- **Pasos:**
  1. Seleccionar filtro por sector "Tecnología".
- **Resultado Esperado:** La lista excluye empresas de otros sectores y muestra solo ofertas del sector tecnológico.
- **Resultado de Ejecución:** Passed

### TC-M2-006: Búsqueda de pasantías por palabra clave en título y descripción
- **Precondiciones:** Convocatorias con palabras clave ("Desarrollador Java", "Analista Contable").
- **Pasos:**
  1. Ingresar en el input de búsqueda: "Java".
  2. Presionar Enter o esperar debounce.
- **Resultado Esperado:** Se muestran únicamente las pasantías que contengan el término "Java" en título o funciones requeridas.
- **Resultado de Ejecución:** Passed

### TC-M2-007: Visualización de estado "Sin resultados" ante filtros sin coincidencias
- **Precondiciones:** Catálogo abierto.
- **Pasos:**
  1. Ingresar en el buscador una cadena inexistente: `XYZ999NONEXISTENT`.
- **Resultado Esperado:** Mensaje amigable: "No se encontraron pasantías disponibles para los criterios seleccionados", sin errores de consola.
- **Resultado de Ejecución:** Passed

### TC-M2-008: Consulta de detalle de una convocatoria pública de pasantía
- **Precondiciones:** Pasantía con ID conocido accesible públicamente.
- **Pasos:**
  1. En el catálogo, hacer clic en "Ver Detalles" de una pasantía.
- **Resultado Esperado:** Carga de `/pasantias/:id/detalle` mostrando descripción completa, beneficios, requisitos excluyentes y fecha de incorporación.
- **Resultado de Ejecución:** Passed

### TC-M2-009: Directorio público de empresas aliadas registradas
- **Precondiciones:** Instituciones activas en el sistema.
- **Pasos:**
  1. Navegar a `/empresas`.
- **Resultado Esperado:** Se visualiza la grilla de instituciones con su nombre comercial, logotipo servido desde MinIO y sector productivo.
- **Resultado de Ejecución:** Passed

### TC-M2-010: Consulta del perfil detallado de una empresa aliada
- **Precondiciones:** Institución con ID en el directorio.
- **Pasos:**
  1. Hacer clic en una empresa desde el directorio público.
- **Resultado Esperado:** Se muestra `/empresas/:id/detalle` con reseña institucional, enlaces a redes sociales y listado de sus pasantías activas.
- **Resultado de Ejecución:** Passed

### TC-M2-011: Comportamiento responsivo en resolución móvil (360px - 414px)
- **Precondiciones:** DevTools en modo emulación móvil (iPhone 12 / Galaxy S20).
- **Pasos:**
  1. Navegar por Landing Page, Catálogo y Detalle de Pasantía.
- **Resultado Esperado:** Menú hamburguesa funcional, tarjetas en una sola columna, sin desbordamiento horizontal (`overflow-x`).
- **Resultado de Ejecución:** Passed

### TC-M2-012: Enrutamiento seguro hacia login al presionar "Postularme" sin sesión
- **Precondiciones:** Usuario no autenticado en el detalle de una pasantía.
- **Pasos:**
  1. Hacer clic sobre el botón "Postularme a esta pasantía".
- **Resultado Esperado:** Redirección automática hacia Keycloak para autenticación, guardando la URL de retorno para volver tras login.
- **Resultado de Ejecución:** Passed

### TC-M2-013: Renderizado de página de Contacto institucional (/contactanos)
- **Precondiciones:** Aplicación web levantada.
- **Pasos:**
  1. Navegar a `/contactanos`.
  2. Validar información de contacto USEI (dirección campus, teléfonos, correo institucional).
- **Resultado Esperado:** Vista renderizada correctamente con formulario de contacto y mapa o datos oficiales de la UCB.
- **Resultado de Ejecución:** Passed

### TC-M2-014: Validación de paginación o scroll infinito en catálogo de pasantías
- **Precondiciones:** Más de 12 convocatorias cargadas en el backend.
- **Pasos:**
  1. Navegar al catálogo y avanzar a la página 2.
- **Resultado Esperado:** Se solicita la página 2 con parámetros `page=1&size=10`, actualizando la lista sin recarga de pantalla completa.
- **Resultado de Ejecución:** Passed

### TC-M2-015: Tiempo de respuesta de consulta de catálogo bajo red 3G simulada
- **Precondiciones:** DevTools con throttling de red configurado en "Fast 3G".
- **Pasos:**
  1. Cargar el catálogo de pasantías.
- **Resultado Esperado:** Skeletons de carga visibles mientras finaliza el request; el bundle principal optimizado permite interactividad sin bloqueos.
- **Resultado de Ejecución:** Passed

---

## 📁 MÓDULO 3: Portal Estudiante (Asignado a: Mauricio Rivera)

### TC-M3-001: Visualización del perfil de estudiante autenticado (/perfil/estudiante)
- **Precondiciones:** Sesión iniciada con usuario `ESTUDIANTE`.
- **Pasos:**
  1. Navegar a `/perfil/estudiante`.
- **Resultado Esperado:** Se despliegan nombres, código de estudiante, carrera, datos de contacto y sección de currículums cargados.
- **Resultado de Ejecución:** Passed

### TC-M3-002: Edición y actualización de datos personales de estudiante
- **Precondiciones:** En la vista `/perfil/estudiante/editar`.
- **Pasos:**
  1. Modificar número de teléfono y enlace de LinkedIn.
  2. Presionar "Guardar Cambios".
- **Resultado Esperado:** Toast de confirmación "Perfil actualizado exitosamente", persistencia en BD y reflejo inmediato en la interfaz.
- **Resultado de Ejecución:** Passed

### TC-M3-003: Subida exitosa de currículum vitae en formato PDF a MinIO
- **Precondiciones:** Archivo `cv_juan_perez_2026.pdf` de 1.5 MB válido.
- **Pasos:**
  1. En el perfil, presionar "Subir Nuevo CV".
  2. Seleccionar el archivo PDF y agregar un título ("CV Desarrollo Web").
  3. Presionar "Cargar".
- **Resultado Esperado:** Archivo almacenado en el bucket `curriculums` de MinIO, registro insertado en tabla `curriculums` con hash y URL relativa, mostrado en la lista.
- **Resultado de Ejecución:** Passed

### TC-M3-004: Rechazo de subida de archivo con formato no permitido (ej. .docx o .exe)
- **Precondiciones:** Archivo `curriculum.docx` preparado.
- **Pasos:**
  1. Intentar subir `curriculum.docx` en el selector de CV.
- **Resultado Esperado:** Validación en cliente y backend rechaza la subida con mensaje: "Solo se admiten documentos en formato PDF".
- **Resultado de Ejecución:** Passed

### TC-M3-005: Rechazo de archivo PDF que excede el tamaño máximo permitido (> 5 MB)
- **Precondiciones:** Archivo PDF de 8 MB.
- **Pasos:**
  1. Intentar cargar el PDF de 8 MB.
- **Resultado Esperado:** Error en frontend y backend: "El archivo excede el límite máximo permitido de 5 MB". No se sube nada a MinIO.
- **Resultado de Ejecución:** Passed

### TC-M3-006: Previsualización y descarga segura de CV cargado por el estudiante
- **Precondiciones:** Al menos un CV cargado en el perfil.
- **Pasos:**
  1. Hacer clic en el icono "Descargar / Previsualizar" del CV.
- **Resultado Esperado:** El backend sirve el flujo binario con cabecera `Content-Type: application/pdf`, abriéndose en visor PDF del navegador.
- **Resultado de Ejecución:** Passed

### TC-M3-007: Eliminación de currículum vitae sin postulaciones asociadas
- **Precondiciones:** CV creado recientemente que no ha sido enviado a ninguna pasantía.
- **Pasos:**
  1. Hacer clic en "Eliminar CV" y confirmar en el modal de SweetAlert2.
- **Resultado Esperado:** Registro marcado como inactivo o eliminado en BD, objeto removido de MinIO y lista actualizada.
- **Resultado de Ejecución:** Passed

### TC-M3-008: Restricción de eliminación de CV con postulaciones activas
- **Precondiciones:** CV asociado a una postulación en estado `PENDIENTE`.
- **Pasos:**
  1. Intentar eliminar dicho CV.
- **Resultado Esperado:** Mensaje de advertencia: "No puede eliminar un currículum que forma parte de una postulación activa".
- **Resultado de Ejecución:** Passed

### TC-M3-009: Postulación exitosa a pasantía seleccionando CV específico
- **Precondiciones:** Estudiante con 2 CVs distintos registrados. Pasantía abierta afín a su carrera.
- **Pasos:**
  1. Ir al detalle de la pasantía `/pasantias/:id/detalle`.
  2. Presionar "Postularme".
  3. En el modal, seleccionar el CV "CV Especialidad Backend".
  4. Confirmar postulación.
- **Resultado Esperado:** Registro creado en `aplicacionespasantias` con estado `PENDIENTE`. Modal de éxito desplegado.
- **Resultado de Ejecución:** Passed

### TC-M3-010: Bloqueo de postulación duplicada a la misma convocatoria (RF-E07)
- **Precondiciones:** Estudiante ya postulado previamente a la pasantía ID 12.
- **Pasos:**
  1. Intentar postular nuevamente a la misma pasantía ID 12.
- **Resultado Esperado:** El botón de postulación aparece deshabilitado como "Ya postulado" o la API responde HTTP 409: "Ya cuenta con una postulación activa a esta vacante".
- **Resultado de Ejecución:** Passed

### TC-M3-011: Visualización del listado de postulaciones propias (/estudiante/solicitudes)
- **Precondiciones:** Estudiante con 3 postulaciones en distintos estados.
- **Pasos:**
  1. Navegar a `/estudiante/solicitudes`.
- **Resultado Esperado:** Tabla reactiva con nombre de la empresa, título de pasantía, fecha de aplicación y badge de estado (`Pendiente`, `Aceptada`, `Rechazada`).
- **Resultado de Ejecución:** Passed

### TC-M3-012: Consulta de detalle y estado de una postulación individual
- **Precondiciones:** Postulación existente en la lista.
- **Pasos:**
  1. Hacer clic sobre una postulación en la vista de solicitudes.
- **Resultado Esperado:** Se visualiza el historial de la postulación, datos de contacto de la empresa y retroalimentación en caso de existir.
- **Resultado de Ejecución:** Passed

### TC-M3-013: Bloqueo de postulación a convocatoria cuya fecha límite ya venció
- **Precondiciones:** Pasantía con fecha límite `fecha_limite < hoy`.
- **Pasos:**
  1. Intentar postular a la convocatoria vencida.
- **Resultado Esperado:** El sistema bloquea la acción indicando: "Esta convocatoria ha cerrado su periodo de recepción de postulaciones".
- **Resultado de Ejecución:** Passed

### TC-M3-014: Actualización de foto de perfil con subida a MinIO
- **Precondiciones:** En `/perfil/estudiante/editar`.
- **Pasos:**
  1. Subir una imagen `perfil.png` (800x800px).
  2. Guardar cambios.
- **Resultado Esperado:** Imagen subida a MinIO en bucket de medios, URL actualizada y foto visible en la cabecera del portal.
- **Resultado de Ejecución:** Passed

### TC-M3-015: Recepción de notificación interna ante cambio de estado a "Aceptada"
- **Precondiciones:** Empresa aprueba la postulación del estudiante.
- **Pasos:**
  1. Estudiante ingresa al portal y revisa el icono de campana de notificaciones.
- **Resultado Esperado:** Aparece notificación no leída: "¡Felicidades! Tu postulación para [Pasantía] fue aceptada por la empresa".
- **Resultado de Ejecución:** Passed

---

## 📁 MÓDULO 4: Portal Empresa (Asignado a: Alejandro Pardo)

### TC-M4-001: Registro y solicitud de afiliación de nueva empresa (/finish-register-empresa)
- **Precondiciones:** Usuario autenticado en Keycloak con rol asignado de empresa no vinculada.
- **Pasos:**
  1. Completar formulario con Razón Social, NIT, sector económico, dirección, teléfono y logo corporativo.
  2. Presionar "Enviar Solicitud de Afiliación".
- **Resultado Esperado:** Solicitud registrada con estado `PENDIENTE_APROBACION` y notificación de que USEI revisará los datos.
- **Resultado de Ejecución:** Passed

### TC-M4-002: Bloqueo de publicación de pasantías para empresa no aprobada
- **Precondiciones:** Empresa en estado `PENDIENTE_APROBACION`.
- **Pasos:**
  1. Intentar navegar a `/empresa/administrador/pasantias/crear`.
- **Resultado Esperado:** Acceso restringido con mensaje: "Su cuenta institucional se encuentra en proceso de validación por USEI".
- **Resultado de Ejecución:** Passed

### TC-M4-003: Publicación exitosa de nueva convocatoria de pasantía
- **Precondiciones:** Empresa activa y aprobada.
- **Pasos:**
  1. Navegar a `/empresa/administrador/pasantias/crear`.
  2. Ingresar título, descripción, carreras afines seleccionadas, cupos vacantes, requisitos y fecha límite.
  3. Presionar "Publicar Pasantía".
- **Resultado Esperado:** Convocatoria creada en backend con estado inicial `PENDIENTE_MODERACION` o `PUBLICADA` según política y visible en su dashboard.
- **Resultado de Ejecución:** Passed

### TC-M4-004: Validación de campos obligatorios en formulario de creación de pasantía
- **Precondiciones:** Formulario de creación abierto.
- **Pasos:**
  1. Dejar campos obligatorios vacíos (Título, Carreras requeridas, Fecha límite).
  2. Presionar "Publicar Pasantía".
- **Resultado Esperado:** Vee-Validate / Vuelidate detiene el envío, marcando bordes rojos y mostrando textos de validación ("Campo requerido").
- **Resultado de Ejecución:** Passed

### TC-M4-005: Edición y actualización de convocatoria de pasantía existente
- **Precondiciones:** Pasantía publicada por la empresa.
- **Pasos:**
  1. En `/empresa/administrador/pasantias`, presionar "Editar" sobre una pasantía.
  2. Modificar cupos de 2 a 4 y ampliar fecha límite.
  3. Presionar "Actualizar".
- **Resultado Esperado:** Backend actualiza los registros en base de datos; toast de confirmación y cambios reflejados de inmediato.
- **Resultado de Ejecución:** Passed

### TC-M4-006: Desactivación / Cierre manual de convocatoria de pasantía
- **Precondiciones:** Pasantía activa en el catálogo.
- **Pasos:**
  1. Presionar botón "Cerrar Convocatoria" y confirmar acción.
- **Resultado Esperado:** El estado de la pasantía cambia a `CERRADA`; deja de mostrarse en el catálogo público para estudiantes.
- **Resultado de Ejecución:** Passed

### TC-M4-007: Visualización de lista de estudiantes postulantes a una pasantía
- **Precondiciones:** Pasantía con al menos 3 postulantes registrados.
- **Pasos:**
  1. En `/empresa/administrador/pasantias/informacion/:id`, abrir la pestaña de "Postulantes".
- **Resultado Esperado:** Tabla con nombres de estudiantes, carreras, fecha de postulación y estado actual de cada uno.
- **Resultado de Ejecución:** Passed

### TC-M4-008: Descarga segura del CV en PDF de un postulante
- **Precondiciones:** Estudiante en la lista de postulantes con CV cargado en MinIO.
- **Pasos:**
  1. Presionar el botón "Descargar CV" en la fila del postulante.
- **Resultado Esperado:** El backend valida que la empresa es propietaria de la pasantía, obtiene el stream desde MinIO y entrega el archivo PDF para descarga.
- **Resultado de Ejecución:** Passed

### TC-M4-009: Prevención de acceso cruzado a CVs de pasantías ajenas (IDOR)
- **Precondiciones:** Dos empresas distintas (Empresa A y Empresa B).
- **Pasos:**
  1. Empresa B intenta invocar el endpoint de descarga de CV con el ID de postulación perteneciente a la pasantía de Empresa A.
- **Resultado Esperado:** El backend detecta la incompatibilidad de tenencia y retorna HTTP 403 Forbidden.
- **Resultado de Ejecución:** Passed

### TC-M4-010: Aprobación / Selección de un postulante (Cambio a estado "Aceptado")
- **Precondiciones:** Postulante en estado `PENDIENTE`.
- **Pasos:**
  1. En el menú de acciones del candidato, seleccionar "Aceptar Candidato".
  2. Confirmar en el modal de advertencia.
- **Resultado Esperado:** Estado de la postulación pasa a `ACEPTADA`, se crea registro en `seleccionaplicante` y se notifica al estudiante.
- **Resultado de Ejecución:** Passed

### TC-M4-011: Rechazo de un postulante con retroalimentación
- **Precondiciones:** Postulante en estado `PENDIENTE`.
- **Pasos:**
  1. Seleccionar "Rechazar Candidato".
  2. Escribir motivo breve opcional y confirmar.
- **Resultado Esperado:** Estado actualizado a `RECHAZADA` en base de datos; candidato notificado con el motivo correspondiente.
- **Resultado de Ejecución:** Passed

### TC-M4-012: Actualización de datos institucionales de la empresa
- **Precondiciones:** En `/empresa/administrador/informacion`.
- **Pasos:**
  1. Modificar teléfono de contacto, dirección física y enlace a sitio web.
  2. Guardar cambios.
- **Resultado Esperado:** Datos actualizados en tabla `instituciones`; cambios visibles en el perfil público de la empresa.
- **Resultado de Ejecución:** Passed

### TC-M4-013: Subida y actualización de logotipo institucional en MinIO
- **Precondiciones:** Archivo `logo_nuevo.png` (formato PNG transparente, 500x500).
- **Pasos:**
  1. Subir el nuevo logotipo en la sección de información corporativa.
  2. Guardar.
- **Resultado Esperado:** Imagen cargada en el bucket correspondiente de MinIO, previsualización actualizada inmediatamente en frontend.
- **Resultado de Ejecución:** Passed

### TC-M4-014: Cierre de convocatoria sin selección de candidatos
- **Precondiciones:** Convocatoria con postulantes que no cumplieron el perfil requerido.
- **Pasos:**
  1. Ejecutar la acción "Cerrar sin selección".
- **Resultado Esperado:** Estado actualizado a `FINALIZADA_SIN_SELECCION`; postulantes pendientes notificados del cierre de la vacante.
- **Resultado de Ejecución:** Passed

### TC-M4-015: Filtrado de postulantes por carrera en el panel de empresa
- **Precondiciones:** Convocatoria multidisciplinaria con postulantes de Sistemas e Industrial.
- **Pasos:**
  1. Aplicar filtro de carrera: "Ingeniería Industrial".
- **Resultado Esperado:** La lista muestra únicamente los postulantes pertenecientes a dicha titulación.
- **Resultado de Ejecución:** Passed

---

## 📁 MÓDULO 5: Administración USEI (Asignado a: Sergio Mendoza)

### TC-M5-001: Acceso exclusivo a Dashboard de Administrador USEI (/administrador/dashboard)
- **Precondiciones:** Usuario autenticado con rol `ADMIN`.
- **Pasos:**
  1. Navegar a `/administrador/dashboard`.
- **Resultado Esperado:** Carga exitosa del panel directivo con tarjetas de KPIs, gráficos de inserción y accesos a gestión.
- **Resultado de Ejecución:** Passed

### TC-M5-002: Aprobación de registro de nueva institución aliada
- **Precondiciones:** Empresa en estado `PENDIENTE_APROBACION`.
- **Pasos:**
  1. Navegar a `/administrador/solicitud/empresa`.
  2. Revisar datos de la institución y presionar "Aprobar Institución".
- **Resultado Esperado:** La institución cambia a estado `ACTIVA` en base de datos; la empresa queda habilitada para publicar ofertas.
- **Resultado de Ejecución:** Passed

### TC-M5-003: Rechazo o suspensión de institución aliada
- **Precondiciones:** Empresa en solicitud o activa que incumplió normativas.
- **Pasos:**
  1. En `/administrador/empresa`, seleccionar la institución y presionar "Suspender / Rechazar".
- **Resultado Esperado:** Estado actualizado a `SUSPENDIDA`; sus pasantías activas se retiran automáticamente del catálogo público.
- **Resultado de Ejecución:** Passed

### TC-M5-004: Moderación y autorización de convocatoria de pasantía
- **Precondiciones:** Pasantía en `/administrador/solicitud/pasantia` pendiente de revisión.
- **Pasos:**
  1. Abrir detalle de la pasantía, revisar términos y presionar "Aprobar Convocatoria".
- **Resultado Esperado:** Pasantía aprobada y publicada en el catálogo general accesible para estudiantes.
- **Resultado de Ejecución:** Passed

### TC-M5-005: Rechazo de convocatoria de pasantía con observaciones
- **Precondiciones:** Pasantía con salario o condiciones no acordes a convenios UCB.
- **Pasos:**
  1. Presionar "Rechazar Convocatoria" e ingresar observaciones.
- **Resultado Esperado:** Convocatoria marcada como rechazada; la empresa recibe la retroalimentación para corrección.
- **Resultado de Ejecución:** Passed

### TC-M5-006: Envío masivo de notificaciones por correo a estudiantes de una carrera
- **Precondiciones:** Nueva pasantía para Ingeniería Civil aprobada. Servicio `EmailService` configurado con SMTP.
- **Pasos:**
  1. Desde el panel de administración, seleccionar la opción "Difundir por Correo a Carrera".
  2. Seleccionar carrera "Ingeniería Civil" y confirmar envío.
- **Resultado Esperado:** Backend consulta estudiantes del padrón registrados en dicha carrera y despacha los correos asíncronamente registrando en `envio_correo_pasantia`.
- **Resultado de Ejecución:** Passed

### TC-M5-007: Manejo de fallas SMTP en despacho masivo sin bloqueo de hilo principal
- **Precondiciones:** Servidor SMTP no disponible o timeout de red simulado.
- **Pasos:**
  1. Ejecutar difusión masiva de correos.
- **Resultado Esperado:** `EmailService` gestiona la excepción en bloque `@Async`, registra el error en log sin provocar HTTP 500 en la UI del administrador.
- **Resultado de Ejecución:** Passed

### TC-M5-008: Auditoría y consulta de perfil completo de estudiante
- **Precondiciones:** En `/administrador/estudiantes`.
- **Pasos:**
  1. Buscar estudiante por CI o apellido y hacer clic en su perfil.
- **Resultado Esperado:** Se despliega ficha con historial de aplicaciones, estados, currículums subidos y trazabilidad académica.
- **Resultado de Ejecución:** Passed

### TC-M5-009: Cálculo y consistencia de KPIs de inserción laboral en Dashboard
- **Precondiciones:** 10 estudiantes con estado de postulación `ACEPTADA`.
- **Pasos:**
  1. Consultar endpoint `/api/v1/admin/analytics/kpis`.
- **Resultado Esperado:** El KPI "Total Estudiantes Insertados" refleja exactamente el número de aceptaciones sin duplicar por postulaciones previas rechazadas.
- **Resultado de Ejecución:** Passed

### TC-M5-010: Filtrado de analítica y métricas por rango de fechas personalizado
- **Precondiciones:** Convocatorias y postulaciones registradas en distintos meses del año.
- **Pasos:**
  1. En el Dashboard, seleccionar filtro fecha inicio `2026-01-01` y fecha fin `2026-06-30`.
- **Resultado Esperado:** Los gráficos y tablas de analítica recalculan métricas correspondientes exclusivamente a dicho intervalo semestral.
- **Resultado de Ejecución:** Passed

### TC-M5-011: Analítica de demanda laboral agrupada por carrera universitaria
- **Precondiciones:** Datos de postulaciones distribuidos en varias carreras.
- **Pasos:**
  1. Visualizar gráfico de barras de demanda por titulación.
- **Resultado Esperado:** Visualización precisa del porcentaje y número absoluto de ofertas y postulantes por cada carrera de la universidad.
- **Resultado de Ejecución:** Passed

### TC-M5-012: Supervisión y reporte de convocatorias desiertas (sin aplicantes)
- **Precondiciones:** En `/administrador/pasantia/sinaplicantes`.
- **Pasos:**
  1. Consultar el listado de pasantías con fecha próxima a vencer y 0 candidatos.
- **Resultado Esperado:** Se listan las ofertas desiertas para que el coordinador USEI contacte a directores de carrera.
- **Resultado de Ejecución:** Passed

### TC-M5-013: Exportación de reportes analíticos consolidados
- **Precondiciones:** Reportes generados en pantalla.
- **Pasos:**
  1. Presionar botón "Exportar Reporte (CSV/Excel)".
- **Resultado Esperado:** Descarga de archivo con datos estructurados de inserción laboral y convocatorias para acreditación académica.
- **Resultado de Ejecución:** Passed

### TC-M5-014: Aprobación de vinculación de usuario-empresa en Keycloak
- **Precondiciones:** Usuario representante solicita vincularse a una empresa ya creada.
- **Pasos:**
  1. En `/administrador/empresa-solicitud-usuario`, revisar la solicitud y aprobar.
- **Resultado Esperado:** Se genera la tupla en `usuariosinstituciones`, otorgándole permisos para administrar dicha empresa.
- **Resultado de Ejecución:** Passed

### TC-M5-015: Auditoría de seguridad sobre revocación de rol de administrador
- **Precondiciones:** Dos cuentas de administrador activas en Keycloak.
- **Pasos:**
  1. Remover el rol `ADMIN` a una cuenta.
  2. Intentar ingresar a cualquier endpoint `/api/v1/admin/**`.
- **Resultado Esperado:** Inmediata respuesta HTTP 403 Forbidden; el usuario pierde todo acceso a las vistas de administración.
- **Resultado de Ejecución:** Passed
