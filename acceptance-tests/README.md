# Pruebas de aceptación ATDD

Este proyecto contiene únicamente automatización de navegador para el sistema Internship. Usa Maven, Java 17, Selenium WebDriver, TestNG y WebDriverManager, siguiendo las tecnologías del [repositorio ATDD de la consigna](https://github.com/gpalabral/ATDD).

## Configuración local

1. Desde la raíz del repositorio, ejecutar `cp acceptance-tests/.env.example acceptance-tests/.env` una sola vez.
2. Completar en `acceptance-tests/.env` las credenciales de una cuenta QA autorizada con rol ADMIN y los datos QA que requiera cada caso.
3. Iniciar el entorno local de QA y dejar disponibles el frontend Vue, el backend Spring Boot y los servicios de autenticación necesarios.
4. Ejecutar el caso con el botón del IDE o con Maven. No usar este proyecto contra producción.

El archivo `acceptance-tests/.env` se carga automáticamente al iniciar una prueba desde el botón del IDE o desde Maven, aunque el directorio de ejecución sea la raíz del repositorio o el módulo. El repositorio ignora ese archivo para mantener los valores locales fuera de Git. Comparte únicamente `acceptance-tests/.env.example`; nunca subas el `.env` con credenciales.

## Ejecutar

Desde la raíz del repositorio:

```sh
mvn -f acceptance-tests/pom.xml test -Dapp.baseUrl=http://localhost:5173
```

TestNG/Maven guarda los resultados de la ejecución en `acceptance-tests/target/surefire-reports/`.

## Datos locales de QA

Completa los valores de `acceptance-tests/.env`; las variables del sistema operativo tienen prioridad sobre los valores del archivo. Para que cada caso funcione, usa cuentas y registros que existan en tu entorno QA local.

| Variable | Uso |
|---|---|
| APP_BASE_URL | URL del frontend local. Por defecto: `http://localhost:5173`. |
| QA_ADMIN_USERNAME y QA_ADMIN_PASSWORD | Iniciar sesión en Keycloak con una cuenta QA que tenga el rol ADMIN. |
| QA_PADRON_CARRERA | Nombre exacto de una carrera existente para generar las filas CSV de importación. Tiene un valor QA predeterminado. |
| QA_PASANTIA_PENDIENTE_TITULO | Título exacto de la convocatoria QA pendiente que se aprobará en IQA-1-36. Tiene un valor QA predeterminado. |
| QA_PASANTIA_SIN_APLICANTES_TITULO | Título exacto de una convocatoria QA sin aplicantes para IQA-1-44. Tiene un valor QA predeterminado. |

IQA-1-42 crea un registro del Padrón con correo QA único por ejecución y lo imprime en la salida. La importación queda guardada en el entorno QA; limpiar o restablecer esos datos antes de repetir la prueba. IQA-1-36 también cambia el estado de la convocatoria preparada; restaurarla antes de repetir. Las pruebas no limpian datos mediante endpoints ni consultas a la base de datos.

Las descargas se guardan en una carpeta única bajo target/downloads para que la prueba IQA-1-41 pueda inspeccionar el libro generado.

## Organización de casos

Cada integrante agrega sus clases bajo su nombre y módulo dentro de:

```text
src/test/java/bo/edu/ucb/internship/acceptance/<integrante>/<modulo>/
```

Ejemplo para Sergio:

```text
src/test/java/bo/edu/ucb/internship/acceptance/sergio/administracionusei/
```

Cada método de prueba debe corresponder a un ID existente del Entregable 2 y dejar explícitos sus apartados **Preparación**, **Lógica** y **Verificación / Assert**. Los pasos y asserts interactúan con la interfaz; no llamar directamente a la API ni consultar la base de datos.

## Convenciones compartidas

- Extender `BaseTest` para abrir y cerrar Chrome por prueba.
- Usar `By.id` o selectores CSS estables cuando existan; recurrir a XPath solo cuando sea necesario.
- Esperar los elementos con esperas explícitas de Selenium, sin pausas fijas.
- Leer la URL del frontend desde `-Dapp.baseUrl`; no fijarla en cada caso.
- No guardar credenciales en el repositorio. Usar únicamente cuentas locales de QA y manejar secretos mediante variables locales fuera de Git.
