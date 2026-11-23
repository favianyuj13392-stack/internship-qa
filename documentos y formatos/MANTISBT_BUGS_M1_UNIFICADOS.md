# Fichas Unificadas de Defectos para MantisBT — Módulo 1 (Antonio Yujra)

Este documento contiene las **6 fichas de defectos ya grabados en video** con un formato 100% estandarizado, mapeado exactamente a los campos y opciones desplegables del MantisBT del equipo.

> **Carpeta de evidencias de video:** `C:\Users\favia\Downloads\videos bugs final`

---

## Estándar de Campos de MantisBT

| Campo en MantisBT | Valor Unificado para Módulo 1 |
|---|---|
| **Categoría** | `[Todos los proyectos] M1 - Autenticación y Padrón` |
| **Reproducibilidad** | `siempre` |
| **Gravedad** | Según impacto: `bloqueo`, `fallo`, `mayor`, `menor`, `texto`, `trivial` |
| **Prioridad** | Según urgencia: `inmediata`, `urgente`, `alta`, `normal`, `baja` |
| **Resumen** | `[BUG-M1-00X] <Descripción concisa del defecto>` |
| **Descripción** | Explicación del problema, contexto de uso e impacto funcional o institucional |
| **Pasos para reproducir** | Pasos numerados (1, 2, 3...) seguidos obligatoriamente de **Resultado Actual** y **Resultado Esperado** |
| **Información adicional** | URL afectada, archivo de código fuente, líneas y causa raíz técnica |
| **Subir archivo** | Nombre exacto del archivo `.mp4` en `C:\Users\favia\Downloads\videos bugs final` |

---

## 🪲 FICHA 1: BUG-M1-001

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `mayor`
* **Prioridad:** `alta`
* **Resumen:** `[BUG-M1-001] Cierre forzado de sesión (Force Logout) al acceder a la vista de Perfil de Estudiante`

* **Descripción:**  
Al intentar ingresar a la vista de Perfil de Estudiante desde el menú de usuario autenticado, el sistema ejecuta una llamada destructiva que invalida la sesión activa del usuario y lo expulsa hacia el login institucional de Keycloak debido al manejo inapropiado de una excepción en la carga de datos.

* **Pasos para reproducir:**  
1. Iniciar sesión en el sistema (`http://localhost:5173/`) con cuenta de estudiante.
2. Hacer clic en el avatar de usuario en la esquina superior derecha de la barra de navegación.
3. En el menú desplegable, hacer clic en la opción "Perfil".

**Resultado Actual:**  
La aplicación invoca inmediatamente el método de logout expulsando al usuario del sistema y redirigiéndolo a la pantalla de login de Keycloak.

**Resultado Esperado:**  
La vista de perfil debe cargar los datos del estudiante o, ante un fallo de conexión con el backend, mostrar un mensaje de error controlado o estado vacío sin revocar la sesión institucional del usuario.

* **Información adicional:**  
- **URL afectada:** `http://localhost:5173/perfil/estudiante`  
- **Archivo:** `frontend/src/views/Estudiante/PerfilEstudiante.vue`, línea 369  
- **Causa raíz técnica:** El bloque `catch` ante el fallo de la petición HTTP ejecuta directamente `this.$keycloak.logout()`.

* **Subir archivo:**  
`BUG-M1-001-Forced-Logout-On-Profile..mp4`

---

## 🪲 FICHA 2: BUG-M1-002

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `menor`
* **Prioridad:** `normal`
* **Resumen:** `[BUG-M1-002] Placeholder erróneo "Apellido Paterno:" e ID duplicado en campo Apellido Materno`

* **Descripción:**  
En el formulario de completado de registro del padrón estudiantil, el campo de texto correspondiente a "Apellido Materno" tiene configurado erróneamente el texto de ayuda (placeholder) como "Apellido Paterno:". Adicionalmente, reutiliza el identificador HTML `id="lastname"`, generando colisión de IDs en el DOM y confusión visual cuando el campo se encuentra vacío. La cabecera de la sección también se encuentra descontextualizada en inglés (`Personal Detail :`).

* **Pasos para reproducir:**  
1. Iniciar sesión y navegar al formulario `http://localhost:5173/finish/register-estudiante`.
2. Inspeccionar con DevTools (`F12`) o vaciar el valor existente en el campo "Apellido Materno".
3. Observar el texto de fondo (placeholder) que se muestra en la caja y los atributos HTML en el DOM.

**Resultado Actual:**  
El campo muestra como texto guía `"Apellido Paterno:"`, utiliza el atributo `id="lastname"` duplicado con respecto al campo anterior y `name="name"`, y la cabecera de la sección figura en inglés (`Personal Detail :`).

**Resultado Esperado:**  
El texto guía debe indicar claramente `"Apellido Materno:"`, el elemento debe poseer un identificador único en el DOM (ej. `id="mother-lastname"`), y las etiquetas deben mantener consistencia en español.

* **Información adicional:**  
- **URL afectada:** `http://localhost:5173/finish/register-estudiante`  
- **Archivo:** `frontend/src/views/CompletarRegistro/CompletarRegistroEstudianteView.vue`, líneas 206-216  
- **Causa raíz técnica:** Atributos copiados y pegados del campo Apellido Paterno sin actualizar (`placeholder="Apellido Paterno:"` e `id="lastname"`).

* **Subir archivo:**  
`BUG-M1-002-Placeholder-Apellido-Materno.mp4`

---

## 🪲 FICHA 3: BUG-M1-003

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `mayor`
* **Prioridad:** `alta`
* **Resumen:** `[BUG-M1-003] Función "Bloquear" en menú de usuario congela la interfaz sin opción de desbloqueo`

* **Descripción:**  
Al presionar la opción "Bloquear" dentro del menú de usuario autenticado, el sistema despliega un overlay oscuro permanente que bloquea por completo la interacción con la interfaz gráfica. No existe campo para ingresar contraseña o PIN de desbloqueo ni botón para restaurar la sesión, obligando al usuario a forzar la recarga del navegador.

* **Pasos para reproducir:**  
1. Iniciar sesión con cualquier usuario y ubicarse en cualquier vista del sistema.
2. Desplegar el menú de usuario desde el avatar en la barra superior.
3. Hacer clic en la opción "Bloquear".

**Resultado Actual:**  
La pantalla se oscurece con una capa negra que inhabilita todo clic o interacción, sin presentar ningún formulario o mecanismo para desbloquear la sesión.

**Resultado Esperado:**  
La acción de bloqueo debe mostrar un diálogo modal solicitando las credenciales/PIN para reanudar el uso, o bien retirarse del menú si no cuenta con implementación funcional.

* **Información adicional:**  
- **Archivo:** `frontend/src/components/General/navbarGeneral.vue`, línea 279  
- **Causa raíz técnica:** El método `bloquearPantalla()` activa una clase de overlay CSS sin contar con lógica ni componentes de autenticación para el desbloqueo.

* **Subir archivo:**  
`BUG-M1-003-Broken-Lock-Screen.mp4`

---

## 🪲 FICHA 4: BUG-M1-005

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `mayor`
* **Prioridad:** `alta`
* **Resumen:** `[BUG-M1-005] Botón "Cancelar" en Registro de Estudiante ejecuta Logout destructivo sin confirmación`

* **Descripción:**  
En la vista de incorporación al padrón (`/finish/register-estudiante`), el botón secundario "Cancelar" invoca directamente la invalidación de la sesión SSO en Keycloak. Al presionarlo con la intención de cancelar la edición o regresar al inicio, el usuario pierde todos los datos diligenciados y su sesión es destruida sin previo aviso ni diálogo de confirmación.

* **Pasos para reproducir:**  
1. Iniciar sesión y navegar a `http://localhost:5173/finish/register-estudiante`.
2. Desplazarse al final del formulario hasta los botones de acción.
3. Hacer clic en el botón rojo "Cancelar".

**Resultado Actual:**  
El sistema ejecuta de forma inmediata `this.$keycloak.logout()`, cerrando la sesión del usuario y redirigiéndolo abruptamente hacia la página de inicio de sesión de Keycloak.

**Resultado Esperado:**  
El botón debe mostrar un cuadro de confirmación (SweetAlert o Modal) advirtiendo sobre la pérdida de datos y, en caso de aceptar, redirigir limpiamente a la página de inicio (`/`) preservando la sesión autenticada.

* **Información adicional:**  
- **URL afectada:** `http://localhost:5173/finish/register-estudiante`  
- **Archivo:** `frontend/src/views/CompletarRegistro/CompletarRegistroEstudianteView.vue`, línea 331  
- **Causa raíz técnica:** Hardcode directo en el template `@click="this.$keycloak.logout()"` en lugar de redireccionar mediante el router.

* **Subir archivo:**  
`BUG-M1-005-Destructive-Cancel-Logout.mp4`

---

## 🪲 FICHA 5: BUG-M1-006

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `menor`
* **Prioridad:** `normal`
* **Resumen:** `[BUG-M1-006] Falta de atributo max en selector de Fecha de Nacimiento permite elegir fechas futuras`

* **Descripción:**  
En el formulario de registro del estudiante (`/finish/register-estudiante`), el selector de Fecha de Nacimiento no implementa la restricción HTML5 `max` (ni límite dinámico en Vue). Esto permite al usuario interactuar con el widget del calendario y seleccionar fechas futuras temporalmente imposibles (ej. año 2030), violando el principio de diseño de interfaces defensivo.

* **Pasos para reproducir:**  
1. Iniciar sesión y navegar a `http://localhost:5173/finish/register-estudiante`.
2. Hacer clic en el selector de fecha del campo "Fecha de Nacimiento".
3. Navegar en el calendario hacia un año y mes futuro (ej. 15/05/2030) y seleccionarlo.

**Resultado Actual:**  
El calendario permite seleccionar y establecer libremente fechas futuras en el campo sin ninguna restricción visual ni límite a nivel de control HTML.

**Resultado Esperado:**  
El control `<input type="date">` debe incluir el atributo `:max="fechaMaxima"` (fijado a la fecha actual o considerando la edad mínima de 17 años) para bloquear e inhabilitar días y años futuros directamente en el widget.

* **Información adicional:**  
- **URL afectada:** `http://localhost:5173/finish/register-estudiante`  
- **Archivo:** `frontend/src/views/CompletarRegistro/CompletarRegistroEstudianteView.vue`, líneas 240-246  
- **Causa raíz técnica:** Ausencia del atributo `max` en el elemento `<input type="date">`. Evidencia secundaria: al enviar el form, la línea 1418 muestra un toast informal (`toast.error("No puedes nacer en el futuro >:c payaso")`).

* **Subir archivo:**  
`BUG-M1-006-Fecha-Nacimiento-Futura-Max.mp4`

---

## 🪲 FICHA 6: BUG-M1-008

* **Categoría:** `[Todos los proyectos] M1 - Autenticación y Padrón`
* **Reproducibilidad:** `siempre`
* **Gravedad:** `mayor`
* **Prioridad:** `alta`
* **Resumen:** `[BUG-M1-008] Pantalla en blanco total (White Screen) por falta de ruta catch-all 404 en rutas no mapeadas`

* **Descripción:**  
Al ingresar manualmente por la barra de direcciones del navegador a una ruta inexistente o no registrada en el enrutador de Vue (por ejemplo `/convocatorias` o `/rutainvalida`), la aplicación no renderiza ningún contenido, dejando una pantalla en blanco total sin barra de navegación, sin pie de página y sin mensaje de error 404.

* **Pasos para reproducir:**  
1. Abrir el navegador en `http://localhost:5173/`.
2. En la barra de direcciones escribir una ruta no mapeada: `http://localhost:5173/convocatorias`.
3. Presionar la tecla Enter.

**Resultado Actual:**  
La aplicación muestra una pantalla completamente blanca (White Screen of Death), sin barra de navegación ni indicación visual de que la página no existe.

**Resultado Esperado:**  
Vue Router debe capturar las rutas inexistentes mediante una ruta comodín (`path: "/:pathMatch(.*)*"`) y renderizar una vista amigable 404 Not Found con botón para volver a Inicio.

* **Información adicional:**  
- **URL afectada:** `http://localhost:5173/convocatorias`  
- **Archivo:** `frontend/src/router/index.js`  
- **Causa raíz técnica:** El arreglo de rutas carece de una regla comodín catch-all al final de la definición de rutas.

* **Subir archivo:**  
`BUG-M1-008-White-Screen-Direct-Route.mp4.mp4`
