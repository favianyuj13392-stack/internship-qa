# 🎓 Internship UCB - Monorepo de Calidad y Pruebas de Software (QA)
### Sistema de Gestión Integral de Pasantías y Prácticas Preprofesionales — USEI (Universidad Católica Boliviana "San Pablo" - Sede La Paz)

**Equipo de QA (5 Integrantes):**
- **Antonio Yujra** (Líder de Equipo)
- **Jamie Huaycho**
- **Mauricio Rivera**
- **Alejandro Pardo**
- **Sergio Mendoza**

---

## 📌 ENTREGABLE #5: Suite de 75 Pruebas Unitarias Automatizadas (TDD)

En cumplimiento estricto con los requerimientos del Entregable #5 de la materia de Gestión de Calidad de Software, se documentan los 4 puntos fundamentales solicitados en la rúbrica académica:

---

### 1. Lenguajes de Programación Utilizados en el Software

El sistema "Internship USEI" implementa una arquitectura desacoplada cliente-servidor orientada a microservicios/APIs, utilizando los siguientes lenguajes de programación:

- **Java 17 (Amazon Corretto / OpenJDK):** Lenguaje principal del backend, utilizado para implementar la lógica de negocio orientada a objetos, inyección de dependencias, filtros de seguridad perimetral y servicios RESTful con el framework Spring Boot 3.3.
- **JavaScript (ECMAScript 6+ / Node.js 18+):** Lenguaje base de la capa de presentación, desarrollado sobre el framework progresivo Vue 3 con Composition API (`<script setup>`) y empaquetado ultra-rápido mediante Vite 5.
- **SQL (PostgreSQL 14 / Dialecto Hibernate):** Lenguaje de definición y manipulación de datos relacionales, incluyendo soporte para transacciones ACID, tipos estructurados `JSONB` y secuencias numéricas.
- **HTML5 & SCSS / Tailwind CSS:** Lenguajes de marcado semántico y estilos visuales para la construcción de interfaces adaptativas y accesibles de cara a estudiantes, empresas y administradores.

---

### 2. Identificación de Clases y Métodos para Pruebas Unitarias (TDD)

Se seleccionó la **Capa de Lógica de Negocio (`BL` - Business Logic)** del backend, ubicada en el paquete:  
`ucb.edu.bo.internship.internship_backend.bl`

Se asignó una clase y sus métodos principales a cada uno de los 5 integrantes (15 pruebas unitarias por miembro = 75 pruebas en total):

| Miembro del Equipo | Clase de Negocio Asignada | Archivo de Prueba Unitario (`src/test/...`) | Métodos Clave Evaluados |
|---|---|---|---|
| **Antonio Yujra (Líder)** | `PadronBl.java` | `bl/PadronBlTest.java` | `generarPlantillaExcel()`, `procesarArchivo()`, `listarPadron()`, `actualizarEstudiante()`, `generarReporteErroresExcel()` |
| **Jamie Huaycho** | `PasantiaBl.java` | `bl/PasantiaBlTest.java` | `obtenerTodasPasantias()`, `obtenerPasantiasPorCarrera()`, `obtenerPasantiasPorAreas()`, `obtenerPasantiasPorTerminoDeBusqueda()`, `obtenerPasantiasPorFiltros()` |
| **Mauricio Rivera** | `EstudianteBl.java` | `bl/EstudianteBlTest.java` | `obtenerEstudianteByUuid()`, `actualizarEstudianteByUuid()`, `agregarCurriculum()`, `eliminarCurriculum()`, `aplicarPasantia()`, `downloadFile()` |
| **Alejandro Pardo** | `InstitucionBl.java` | `bl/InstitucionBlTest.java` | `agregarInstitucion()`, `obtenerInstitucionById()`, `obtenerInstituciones()`, `actualizarInstitucion()`, `suscribirseEmpresa()`, `obtenerInstitucionesDestacadas()` |
| **Sergio Mendoza** | `AdministradorBl.java` | `bl/AdministradorBlTest.java` | `cambiarEstadoInstitucion()`, `aceptarPasantia()`, `rechazarPasantia()`, `relacionarUsuarioInstitucion()`, `obtenerTodaLaInformacionDeSolicitud()` |

---

### 3. Objetivo y Función que Realizan las Clases y Métodos

#### A. `PadronBl.java` (Responsable: Antonio Yujra)
- **Objetivo:** Actuar como el servicio guardián del padrón de estudiantes matriculados en la UCB, gobernando quién puede acceder a las convocatorias privadas.
- **Funciones:**
  - `generarPlantillaExcel()`: Construye en memoria el archivo Excel `.xlsx` oficial con formato, cabeceras y filas de muestra para la carga masiva.
  - `procesarArchivo(file, modo, sincronizacionCompleta, adminUuid)`: Parsea y valida el Excel/CSV. En modo `SIMULACION` (dry-run) valida reglas sin persistir datos; en modo `APLICADO` abre transacción y persiste la auditoría en `importacion_padron` y los registros en `padron_estudiante`.
  - `validarYAgregarFila()`: Valida que el correo termine estrictamente en `@ucb.edu.bo` (vía regex), que la carrera exista en `CarrerasDao` y que el estado sea válido (`ACTIVO`, `INACTIVO`, `EGRESADO`).
  - `listarPadron()`: Retorna páginas de estudiantes con filtros multicriterio (carrera, estado, año de ingreso, búsqueda).
  - `actualizarEstudiante()`: Modifica datos de un estudiante existente validando integridad referencial.

#### B. `PasantiaBl.java` (Responsable: Jamie Huaycho)
- **Objetivo:** Gestionar el ciclo de vida, consulta y catálogo de ofertas de pasantía publicadas por las empresas aliadas.
- **Funciones:**
  - `obtenerTodasPasantias()`: Lista convocatorias públicas vigentes con paginación y ordenamiento temporal.
  - `obtenerPasantiasPorCarrera()`: Filtra las pasantías vinculadas a una titulación específica.
  - `obtenerPasantiasPorAreas()`: Permite filtrar según el área de especialidad técnica requerida.
  - `obtenerPasantiasPorTerminoDeBusqueda()`: Ejecuta búsqueda textual sensible al caso sobre títulos y requisitos.
  - `obtenerPasantiasPorFiltros()`: Orquestador multicriterio que combina carrera, sector, texto y estado.

#### C. `EstudianteBl.java` (Responsable: Mauricio Rivera)
- **Objetivo:** Administrar el perfil del postulante, su repositorio de currículums en MinIO y las aplicaciones a vacantes.
- **Funciones:**
  - `obtenerEstudianteByUuid()` y `actualizarEstudianteByUuid()`: Consulta y edición de datos personales mediante el identificador criptográfico de Keycloak.
  - `agregarCurriculum()`: Ingesta de archivos PDF, delegando el almacenamiento del objeto binario en MinIO y registrando los metadatos en la base de datos.
  - `eliminarCurriculum()`: Remueve hojas de vida no vinculadas a postulaciones activas.
  - `aplicarPasantia()`: Registra formalmente la postulación a una convocatoria con control estricto de duplicidad (RF-E07).
  - `downloadFile()`: Despacha el flujo de bytes de un archivo de MinIO hacia el navegador del usuario.

#### D. `InstitucionBl.java` (Responsable: Alejandro Pardo)
- **Objetivo:** Administrar los perfiles de empresas aliadas, vinculación de representantes corporativos y catálogo público.
- **Funciones:**
  - `agregarInstitucion()`: Registra una nueva entidad legal solicitante con validaciones de campos obligatorios.
  - `obtenerInstitucionById()`: Recupera la ficha detallada de la empresa y sus pasantías abiertas, asegurando la comparación de identificadores con `Objects.equals()` (corrección del Bug B1).
  - `obtenerInstituciones()`: Provee consulta paginada y filtrada del directorio empresarial.
  - `actualizarInstitucion()`: Permite al representante de la empresa modificar datos institucionales y logotipos.
  - `obtenerInstitucionesDestacadas()`: Calcula y ordena las organizaciones con mayor oferta de pasantías.

#### E. `AdministradorBl.java` (Responsable: Sergio Mendoza)
- **Objetivo:** Moderación institucional USEI, aprobación de convocatorias, gobierno de empresas y auditoría.
- **Funciones:**
  - `cambiarEstadoInstitucion()`: Habilita (`ACTIVA`) o suspende (`INACTIVA`) empresas para operar en la plataforma.
  - `aceptarPasantia()`: Aprueba y publica convocatorias, garantizando la persistencia en base de datos antes de disparar correos masivos a estudiantes de la carrera (corrección del Bug B6).
  - `rechazarPasantia()`: Declina ofertas que no cumplen políticas curriculares de la UCB.
  - `relacionarUsuarioInstitucion()`: Asigna permisos de administración corporativa a usuarios de Keycloak.
  - `obtenerTodaLaInformacionDeSolicitud()`: Agrega datos de la empresa, representante y pasantías para auditoría.

---

### 4. Definición y Descripción del Framework de Pruebas Unitarias

Para la suite de pruebas unitarias se seleccionó el estándar de la industria en el ecosistema Java enterprise:

#### **JUnit 5 (Jupiter API) + Mockito Framework**
- **JUnit 5 (`org.junit.jupiter.api.*`):** Motor de pruebas moderno que provee anotaciones declarativas (`@Test`, `@DisplayName`, `@BeforeEach`, `@Disabled`), soporte nativo para aserciones (`assertEquals`, `assertNotNull`, `assertTrue`, `assertThrows`) y ciclo de vida parametrizado.
- **Mockito (`org.mockito.*` con `@ExtendWith(MockitoExtension.class)`):** Framework de simulación de objetos que permite aislar completamente la clase bajo prueba (`@InjectMocks`), reemplazando las capas de base de datos (`*Dao`), servicios externos (`MinioBl`) y APIs externas por dobles de prueba (`@Mock`).
- **Por qué NO se utilizó `@SpringBootTest` (Justificación Arquitectónica):**  
  `@SpringBootTest` levanta todo el contenedor de Spring y requiere conexiones reales a PostgreSQL, MinIO y Keycloak (pruebas de integración lentas). Al utilizar **pruebas unitarias puras con Mockito**, las 75 pruebas se ejecutan en **menos de 3 segundos** en cualquier entorno sin dependencias de infraestructura levantadas.

---

### 5. Estructura Canónica de las Pruebas (Patrón AAA: Arrange - Act - Assert)

Cada una de las 75 pruebas unitarias implementa rigurosamente las 3 fases obligatorias:

1. **Preparación (Arrange):** Se definen los datos de entrada ficticios y se programa el comportamiento esperado de los mocks mediante `when(...).thenReturn(...)` o `when(...).thenThrow(...)`.
2. **Lógica de la Prueba (Act):** Se invoca de forma aislada el método de negocio de la clase `BL` bajo prueba.
3. **Verificación (Assert):** Se comprueba el resultado retornado con aserciones (`assertEquals`, `assertThrows`) y se valida con `verify(mock, times/never)` que no existan efectos secundarios no deseados en la capa de persistencia.

```java
@Test
@DisplayName("CP-04: Modo SIMULACION procesa métricas pero NUNCA guarda en BD")
void procesarArchivo_modoSimulacion_noGuardaEnBaseDeDatos() throws IOException {
    // 1. PREPARACIÓN (ARRANGE)
    MockMultipartFile excel = crearExcelMock("padron.xlsx", filasValidas);
    when(carrerasDao.findByNombreIgnoreCase(anyString())).thenReturn(Optional.of(carrera));
    when(padronEstudianteDao.findByCorreoIgnoreCase(anyString())).thenReturn(Optional.empty());

    // 2. LÓGICA DE LA PRUEBA (ACT)
    ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "SIMULACION", false, "admin-1");

    // 3. VERIFICACIÓN (ASSERT)
    assertEquals("SIMULACION", resumen.getModo());
    assertEquals(1, resumen.getTotalNuevos());
    verify(padronEstudianteDao, never()).save(any()); // Verificación de no persistencia
}
```

---

## 🚀 Cómo Ejecutar la Suite de Pruebas Unitarias

Desde la carpeta `/backend` en cualquier sistema operativo:

```bash
# Correr las 75 pruebas completas del equipo:
./mvnw test

# En Windows PowerShell:
.\mvnw.cmd test

# Correr solo la clase de un integrante específico:
.\mvnw.cmd test -Dtest=PadronBlTest
.\mvnw.cmd test -Dtest=PasantiaBlTest
.\mvnw.cmd test -Dtest=EstudianteBlTest
.\mvnw.cmd test -Dtest=InstitucionBlTest
.\mvnw.cmd test -Dtest=AdministradorBlTest
```

---

## 📁 Estructura del Monorepo

```text
internship-qa/
├── backend/                                       # Backend Java 17 / Spring Boot 3
│   ├── src/main/java/ucb/edu/bo/internship/...    # Código fuente del sistema
│   └── src/test/java/ucb/edu/bo/internship/
│       └── internship_backend/bl/                 # Suite de 75 Pruebas Unitarias
│           ├── PadronBlTest.java                  # 15 tests - Antonio Yujra (Módulo 1)
│           ├── PasantiaBlTest.java                # 15 tests - Jamie Huaycho (Módulo 2)
│           ├── EstudianteBlTest.java              # 15 tests - Mauricio Rivera (Módulo 3)
│           ├── InstitucionBlTest.java             # 15 tests - Alejandro Pardo (Módulo 4)
│           └── AdministradorBlTest.java           # 15 tests - Sergio Mendoza (Módulo 5)
├── frontend/                                      # Frontend Vue 3 / Vite / Tailwind
├── docker-compose.qa.yml                          # Stack local de QA (MantisBT + MariaDB + TestLink)
├── TEST_PLAN_75_CASOS_TESTLINK.md                 # Matriz oficial de 75 casos de sistema
├── DEFECT_MANAGEMENT_50_BUGS_MANTISBT.md          # Matriz de 50 defectos con evidencias
├── GUIA_OFICIAL_75_UNIT_TESTS_QA.md               # Guía técnica de defensa y TDD
└── README.md                                      # Especificación del Entregable #5
```
