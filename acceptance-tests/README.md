# Pruebas de aceptación ATDD

Este proyecto contiene únicamente automatización de navegador para el sistema Internship. Usa Maven, Java 17, Selenium WebDriver, TestNG y WebDriverManager, siguiendo las tecnologías del [repositorio ATDD de la consigna](https://github.com/gpalabral/ATDD).

## Antes de ejecutar

1. Iniciar el entorno local de QA y todos los servicios necesarios para el flujo que se probará.
2. Dejar disponibles el frontend Vue y el backend Spring Boot.
3. No usar este proyecto contra producción.

## Ejecutar

Desde la raíz del repositorio:

```sh
mvn -f acceptance-tests/pom.xml test -Dapp.baseUrl=http://localhost:5173
```

TestNG/Maven guarda los resultados de la ejecución en `acceptance-tests/target/surefire-reports/`.

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
