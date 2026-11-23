# Entregable 6 — Pautas para pruebas de aceptación ATDD

**Estado:** base común en main; cinco pruebas de Sergio implementadas localmente en atdd/sergio-administracion-usei. Falta configurar el entorno QA y ejecutar.
**Aplicación:** Internship USEI
**Ejemplo pedido en la consigna:** <https://github.com/gpalabral/ATDD>

## 1. Qué pide el entregable

El equipo debe:

1. Revisar el ejemplo ATDD de la consigna.
2. Elegir los procesos o flujos que se automatizarán.
3. Seleccionar cinco casos que **ya estén en el Entregable 2** para representar los flujos del equipo. No son cinco requisitos nuevos ni casos que haya que volver a crear en TestLink.
4. Implementar hasta cinco pruebas de aceptación por integrante, tomadas de casos del módulo que tiene asignado cada persona. Si hay cinco integrantes, serán hasta 25 pruebas automatizadas.
5. Estructurar cada prueba en preparación, lógica y verificación/assert.
6. Subir el código de las pruebas al repositorio del equipo.

Los cinco casos representativos del punto 3 y las pruebas automatizadas por integrante se deben relacionar con los casos existentes del Entregable 2. No agreguen casos duplicados a TestLink.

## 2. Qué significa probar “desde la interfaz”

Selenium abre Chrome y ejecuta acciones como las de una persona: entrar a una pantalla, iniciar sesión mediante la interfaz, hacer clic, escribir, seleccionar o cargar un archivo y revisar los mensajes o resultados visibles.

El flujo atraviesa la aplicación completa: **Chrome → Vue → Spring Boot y servicios locales**. El sistema puede comunicarse normalmente con el backend durante la prueba, pero el guion no debe invocar endpoints directamente, usar `fetch`/Axios para hacer la acción, consultar la base de datos ni validar leyendo tablas. Los asserts deben comprobar resultados observables en la interfaz.

## 3. Tecnologías y qué hace cada una

| Tecnología | Función en estas pruebas |
|---|---|
| Java 17 | Lenguaje de las clases de prueba; coincide con la versión del backend. |
| Maven | Descarga las dependencias y compila/ejecuta el proyecto de pruebas. |
| Selenium WebDriver | Maneja Chrome: navega, localiza elementos, hace clic, escribe y lee la interfaz. |
| TestNG | Encuentra y ejecuta los métodos `@Test`, administra preparación/limpieza y ofrece asserts. |
| WebDriverManager | Resuelve y prepara ChromeDriver para el Chrome instalado; evita guardar una ruta local personal en el código. |
| Chrome | Navegador inicial con el que se recorren los flujos. |
| Vue 3/Vite | Interfaz que usa la persona y que Selenium controla. |
| Spring Boot 3.3 | Backend real con el que la interfaz se comunica durante el flujo. |

### Relación con el ejemplo

El [POM del ejemplo](https://github.com/gpalabral/ATDD/blob/main/pom.xml) usa Maven, Selenium Java, TestNG y WebDriverManager. Su [caso `BuscadorUCBTest.java`](https://github.com/gpalabral/ATDD/blob/main/src/test/java/BuscadorUCBTest.java) separa la preparación, la lógica y el assert. Seguimos esas tecnologías y esa organización. **No usaremos Cucumber ni JUnit.**

El ejemplo fija Selenium `3.141.59`, TestNG `6.14.3`, WebDriverManager `5.5.2` y Java fuente/objetivo `1.8`. La base de este repositorio mantiene los mismos productos, con versiones actuales compatibles con Java 17 y Chrome: Selenium `4.53.0`, TestNG `7.12.0` y WebDriverManager `6.4.0`. Para el ciclo Maven se fijaron Compiler Plugin `3.16.0` y Surefire Plugin `3.6.1`; Surefire detecta y ejecuta las pruebas TestNG.

## 4. Archivos comunes que ya están preparados

La base se encuentra en `acceptance-tests/`, separada del backend.

```text
acceptance-tests/
  pom.xml
  README.md
  src/test/java/bo/edu/ucb/internship/acceptance/
    BaseTest.java
    sergio/administracionusei/
      package-info.java
    <integrante>/<modulo>/
      <CasoDePrueba>Test.java
```

### `acceptance-tests/pom.xml`

Es el POM propio de la suite de aceptación. Declara Java 17, Selenium, TestNG, WebDriverManager y los plugins Maven. Separa estas pruebas de las pruebas unitarias que ya existen o se agreguen al backend.

El backend conserva su propio `backend/pom.xml`, responsable de construir Spring Boot. Para ejecutar una prueba Selenium, Vue y el backend deben estar levantados; por eso Maven de aceptación no intenta iniciar el servidor ni está mezclado con el ciclo normal del backend.

### `BaseTest.java`

Es la clase compartida que abre Chrome antes de cada método de prueba mediante `@BeforeMethod` y lo cierra al finalizar mediante `@AfterMethod`. Cada clase de casos debe **extender `BaseTest`** y reutilizar su `driver`; no debe crear otro ChromeDriver por su cuenta.

`BaseTest` también lee la URL de la interfaz desde la propiedad `app.baseUrl`. La prueba puede abrir la aplicación con `driver.get(appBaseUrl())`. Si no se envía la propiedad, se usa `http://localhost:5173`.

### `package-info.java`

Este archivo solo declara el paquete `sergio.administracionusei`. Mantiene esa carpeta vacía dentro de Git para que esté lista para tus clases. No es una prueba, no ejecuta acciones y no cambia la aplicación. Cuando la carpeta ya contenga un test Java, se puede quitar si se desea.

### `README.md`

Explica requisitos previos, el comando para ejecutar, la estructura de carpetas y las convenciones comunes. Este documento amplía esas instrucciones con el alcance del entregable y la responsabilidad de cada integrante.

## 5. Qué crea cada integrante

Cada persona debe:

1. Actualizar su copia de `main` y crear una rama propia desde allí.
2. Crear un paquete Java con su nombre y módulo bajo:

   ```text
   acceptance-tests/src/test/java/bo/edu/ucb/internship/acceptance/<integrante>/<modulo>/
   ```

3. Crear hasta cinco clases de prueba para casos existentes de Entregable 2 de su módulo. El nombre de clase debe terminar en `Test`, por ejemplo `Iqa1_42ImportarPadronTest.java`.
4. En cada clase, extender `BaseTest` y agregar métodos TestNG marcados con `@Test`.
5. Documentar el ID de TestLink y dividir el método, con comentarios claros, en **Preparación**, **Lógica** y **Verificación / Assert**.
6. Interactuar y validar solo mediante la interfaz. Usar localizadores estables y esperas explícitas de Selenium; evitar pausas fijas y XPath absoluto cuando haya una alternativa estable.
7. Ejecutar sus casos contra el entorno local de QA, revisar el reporte y guardar evidencia si hay fallos.
8. Subir su rama y solicitar la integración de sus cambios según el flujo acordado por el equipo.

Ejemplo de organización del cuerpo; cada integrante debe reemplazar las notas por acciones y asserts reales de su caso:

```java
@Test
public void nombreDescriptivoDelFlujo() {
    // ID de TestLink: IQA-1-XX

    // Preparación: abrir la aplicación y establecer el estado inicial por la interfaz.
    driver.get(appBaseUrl());

    // Lógica: ejecutar los pasos del caso usando Selenium y controles visibles.

    // Verificación / Assert: comprobar el resultado esperado visible en pantalla.
}
```

No copien credenciales ni rutas locales de ChromeDriver del ejemplo. Los cambios deben limitarse a la suite de pruebas; no modifiquen la lógica de Vue o Spring Boot para hacer pasar un caso.

## 6. Ejecutar la suite

Primero deben estar disponibles Chrome, Vue, Spring Boot, la base y los servicios locales requeridos por el flujo. Desde la raíz del repositorio, ejecutar:

```sh
mvn -f acceptance-tests/pom.xml test -Dapp.baseUrl=http://localhost:5173
```

- `-f acceptance-tests/pom.xml` selecciona el proyecto Maven de aceptación.
- `test` compila y ejecuta las clases TestNG encontradas.
- `-Dapp.baseUrl=...` define la URL que leerá `BaseTest`.

Los resultados de Maven/Surefire quedan en `acceptance-tests/target/surefire-reports/`. Este comando **no inicia la aplicación**. Si todavía no se agregaron clases con métodos `@Test`, no habrá flujos de aceptación que ejecutar.

## 7. Casos de Sergio Mendoza — Administración USEI

Se implementaron cinco pruebas de aceptación basadas en casos existentes del Entregable 2. Los datos identificables se configuran fuera del código y corresponden únicamente al entorno local de QA.

| Caso base | Flujo automatizado desde la interfaz | Verificación principal |
|---|---|---|
| IQA-1-36 — aprobar y publicar una pasantía | Abrir una pasantía QA pendiente y aprobarla | La interfaz confirma la aprobación y muestra la pasantía en el listado administrativo publicado. |
| IQA-1-41 — exportar a Excel estudiantes sin primer acceso | Solicitar Exportar campaña desde el Padrón | El navegador descarga un archivo XLSX válido. |
| IQA-1-42 — importar un registro nuevo al Padrón | Cargar un CSV único, simularlo y confirmar la importación | La interfaz confirma la importación y permite encontrar una sola vez el nuevo registro. |
| IQA-1-43 — validar errores e impedir aplicar un Padrón inválido | Simular un archivo con una fila válida y otra sin apellidos | La interfaz muestra el error y no ofrece confirmar; la fila válida tampoco aparece en el Padrón. |
| IQA-1-44 — consultar pasantías sin aplicantes | Abrir el listado de Administración USEI | La convocatoria QA configurada aparece enlazada a su detalle. |

Trazabilidad de IQA-1-41: el caso y el botón Exportar campaña existen en el Entregable 2 y en la interfaz. La revisión de requisitos no encontró un requisito funcional explícito para la exportación; conviene que el equipo confirme o documente esa relación para fortalecer la trazabilidad académica.

### Preparación, acción y resultado

| ID | Preparación (Given) | Acción en la interfaz (When) | Resultado comprobable (Then / Assert) |
|---|---|---|---|
| IQA-1-36 | Cuenta QA ADMIN y una pasantía de prueba pendiente | Abrir Solicitudes de pasantías, seleccionar la convocatoria preparada y pulsar Aprobar | Aparece la confirmación de aprobación y la convocatoria queda visible en el listado de pasantías publicadas. |
| IQA-1-41 | Cuenta QA ADMIN, un estudiante QA sin primer acceso y otro que ya inició sesión | Abrir Estudiantes y pulsar Exportar campaña | Se descarga estudiantes_sin_acceso_usei.xlsx; incluye el primero y excluye el segundo. |
| IQA-1-42 | Cuenta QA ADMIN y una carrera existente configurada para QA | Cargar el archivo CSV generado por la prueba, simular la validación y confirmar la importación | La simulación indica un registro nuevo y cero errores; tras confirmar, la interfaz encuentra exactamente una fila con el correo QA único generado. |
| IQA-1-43 | Cuenta QA ADMIN, una carrera existente y un CSV con una fila válida más otra sin apellidos | Cargar el archivo y pulsar Simular Validación | La interfaz identifica el error de Apellidos, no muestra Confirmar y, al cancelar, no encuentra la fila válida simulada. |
| IQA-1-44 | Cuenta QA ADMIN y una convocatoria QA sin aplicantes | Abrir Pasantías sin aplicantes | La convocatoria configurada aparece en ese listado y tiene un enlace a su detalle administrativo. |

IQA-1-36 cambia el estado de la convocatoria QA. IQA-1-42 agrega una fila de padrón con correo único generado para esa ejecución; el correo se imprime en la salida para facilitar su limpieza. Preparar o restaurar esos datos entre ejecuciones. No usar convocatorias ni datos personales reales.

## 8. Reparto y trazabilidad del equipo

Completar una fila por integrante. Los cinco casos representativos del equipo se eligen del Entregable 2; cada persona automatiza hasta cinco casos de su propio módulo.

| Integrante | Módulo | IDs de Entregable 2 elegidos | Cantidad automatizada |
|---|---|---|---:|
| Sergio Mendoza | Administración USEI | IQA-1-36, IQA-1-41, IQA-1-42, IQA-1-43, IQA-1-44 | 5 |
| Añadir integrante | Añadir módulo | Añadir IDs existentes | Hasta 5 |

## 9. Ramas, cambios y criterios de entrega

- La base común ya está en `main`. Cada integrante debe partir de ese `main`, crear su rama y trabajar dentro de su paquete de nombre/módulo.
- No cambiar el `pom.xml` común por separado. Si hace falta una dependencia, coordinarlo para que todos usen el mismo entorno.
- No incluir credenciales, tokens, archivos `.env`, rutas personales ni datos reales en el repositorio.
- Si una prueba falla, conservar su expectativa y registrar resultado/evidencia; no cambiar el assert ni la lógica del producto para forzar un éxito.
- Un caso está listo cuando referencia un caso de Entregable 2 de su módulo, tiene preparación/lógica/assert claros, usa solo la interfaz, es repetible con datos QA y aparece en el reporte de ejecución.
- Para entregar, publicar el código de pruebas en el repositorio y adjuntar la evidencia solicitada por la materia.
