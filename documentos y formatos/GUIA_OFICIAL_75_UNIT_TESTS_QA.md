# 🛡️ GUÍA OFICIAL Y DIRECTIVA DE PRUEBAS UNITARIAS (75 TESTS)
### Proyecto: Sistema de Gestión de Pasantías USEI (UCB Sede La Paz)
### Monorepo: `d:\DEV\internship-qa`
### Equipo de QA: Antonio Yujra (Líder), Jamie Huaycho, Mauricio Rivera, Alejandro Pardo, Sergio Mendoza

---

## 🤖 INSTRUCCIÓN DIRECTA PARA EL AGENTE DE IA (Claude / Copilot / Antigravity / Cursor)

> **ATENCIÓN AGENTE DE IA:**  
> Estás asistiendo a un estudiante universitario del equipo de QA en su materia de Calidad y Pruebas de Software.  
> Tu comportamiento está sujeto a las siguientes **REGLAS ESTRICTAS DE SEGURIDAD Y ARQUITECTURA**:
>
> 1. **PROHIBIDO MODIFICAR EL CÓDIGO DE PRODUCCIÓN (`backend/src/main/**` o `frontend/src/**`):**  
>    El código fuente del sistema está en estado **READ-ONLY (Solo Lectura)**. No modifiques ni agregues nada en `src/main`.  
> 2. **POLÍTICA DE BUGS ("No arreglar, solo reportar"):**  
>    Si durante la escritura o ejecución de una prueba descubres un bug, una inconsistencia o una excepción no controlada en el código del sistema, **NO LO CORRIJAS EN EL CÓDIGO FUENTE**. Regístralo como un hallazgo para el reporte de defectos de MantisBT y documenta la prueba reflejando el comportamiento actual o esperado según la regla de negocio.  
> 3. **UBICACIÓN EXCLUSIVA DE TRABAJO:**  
>    Solo puedes crear y editar archivos en:  
>    `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/`  
> 4. **STACK TÉCNICO PERMITIDO:**  
>    - **JUnit 5** (`org.junit.jupiter.api.*`)  
>    - **Mockito** (`org.mockito.*`, `@ExtendWith(MockitoExtension.class)`)  
>    - **PROHIBIDO `@SpringBootTest`**: No levantes el contexto completo de Spring ni te conectes a bases de datos reales. Son pruebas unitarias puras que deben ejecutarse en milisegundos mediante `@Mock` e `@InjectMocks`.  
> 5. **ROL PEDAGÓGICO:**  
>    No le des simplemente el código terminado al estudiante sin explicárselo. El docente le tomará un examen oral individual sobre cada función y cada línea de la prueba. Tu trabajo es enseñarle: **Dónde está, qué hace, cómo lo hace y por qué usó ese mock o aserción.**

---

## 👥 ASIGNACIÓN OFICIAL POR INTEGRANTE (15 PRUEBAS CADA UNO)

| Integrante | Rol en el Equipo | Clase Asignada (`.java`) | Módulo de Negocio |
|---|---|---|---|
| **Antonio Yujra** | Líder de Equipo | `PadronBlTest.java` | Módulo 1: Padrón Estudiantil, Excel e Integración Institucional |
| **Jamie Huaycho** | QA Engineer | `PasantiaBlTest.java` | Módulo 2: Catálogo de Pasantías, Búsquedas y Filtros Multicriterio |
| **Mauricio Rivera** | QA Engineer | `EstudianteBlTest.java` | Módulo 3: Portal Estudiante, Gestión de CVs en MinIO y Postulaciones |
| **Alejandro Pardo** | QA Engineer | `InstitucionBlTest.java` | Módulo 4: Portal Empresa, Instituciones Aliadas y Gestión de Postulantes |
| **Sergio Mendoza** | QA Engineer | `AdministradorBlTest.java` | Módulo 5: Administración USEI, Aprobación de Ofertas y Moderación |

---

## 📋 DETALLE DE CASOS ASIGNADOS A CADA INTEGRANTE

---

### 👤 ANTONIO YUJRA (Líder de Equipo)
- **Archivo de prueba:** `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/PadronBlTest.java`
- **Clase bajo prueba:** `ucb.edu.bo.internship.internship_backend.bl.PadronBl`
- **Dependencias a mockear:** `PadronEstudianteDao`, `ImportacionPadronDao`, `ImportacionPadronErrorDao`, `CarrerasDao`, `UsuariosDao`.

**Prompt de inicio para la IA de Antonio:**
> *"Hola, soy Antonio Yujra (Líder del equipo). Leé atentamente `GUIA_OFICIAL_75_UNIT_TESTS_QA.md`. Guiame para implementar mis 15 pruebas unitarias en `PadronBlTest.java`. Explicame cada caso bajo el patrón AAA y preparame para la defensa oral ante el docente."*

**Sus 15 Pruebas Unitarias:**
1. `generarPlantillaExcel_retornaBytesValidos()`: Verifica que el método genere el array de bytes no nulo del template `.xlsx`.
2. `procesarArchivo_archivoVacio_retornaErrorValidacion()`: Valida que un `MultipartFile` vacío devuelva resumen con error sin insertar registros.
3. `procesarArchivo_formatoNoExcel_retornaErrorFormato()`: Prueba subida de archivo `.txt` o con contentType inválido.
4. `procesarArchivo_modoSimulacion_noGuardaEnBaseDeDatos()`: Verifica que en modo `SIMULACION` se procesen métricas pero nunca se invoque `padronEstudianteDao.save()`.
5. `procesarArchivo_modoAplicado_guardaRegistrosValidos()`: Con filas válidas y correo `@ucb.edu.bo`, verifica invocación exitosa del DAO.
6. `procesarArchivo_correoNoInstitucional_registraError()`: Prueba estudiante con correo `@gmail.com`; valida que se añada a la lista de errores.
7. `procesarArchivo_carreraInexistente_registraError()`: Prueba fila con nombre de carrera no registrado en `CarrerasDao`.
8. `procesarArchivo_sincronizacionCompleta_desactivaAusentes()`: Valida que con flag `sincronizacionCompleta = true` los estudiantes no incluidos pasen a estado `INACTIVO`.
9. `listarPadron_sinFiltros_retornaPaginaCompleta()`: Consulta paginada neutra (`findAll(Pageable)`).
10. `listarPadron_filtradoPorCarrera_invocaMetodoCorrectoDao()`: Valida invocación de búsqueda por `carreraId`.
11. `listarPadron_filtradoPorEstado_retornaEstudiantesActivos()`: Valida filtro por estado `ACTIVO`.
12. `actualizarEstudiante_idExistente_actualizaDatosExitosamente()`: Encuentra el estudiante, actualiza campos permitidos y retorna DTO.
13. `actualizarEstudiante_idInexistente_lanzaExcepcion()`: Simula `findById` vacío y valida que lance excepción o retorne null según contrato.
14. `generarReporteErroresExcel_conErrores_generaArchivoBytes()`: Valida exportación de errores de una importación existente.
15. `listarImportaciones_paginado_retornaHistorial()`: Valida consulta paginada sobre `ImportacionPadronDao`.

---

### 👤 JAMIE HUAYCHO
- **Archivo de prueba:** `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/PasantiaBlTest.java`
- **Clase bajo prueba:** `ucb.edu.bo.internship.internship_backend.bl.PasantiaBl`
- **Dependencias a mockear:** `PasantiasDao`, `PasantiasCarrerasDao`, `UsuariosDao`, `InstitucionesDao`.

**Prompt de inicio para la IA de Jamie:**
> *"Hola, soy Jamie Huaycho. Leé atentamente `GUIA_OFICIAL_75_UNIT_TESTS_QA.md`. Guiame para implementar mis 15 pruebas unitarias en `PasantiaBlTest.java`. Explicame cada caso bajo el patrón AAA y preparame para la defensa oral ante el docente."*

**Sus 15 Pruebas Unitarias:**
1. `obtenerTodasPasantias_retornaPaginaPasantias()`: Valida paginación básica de convocatorias activas.
2. `obtenerPasantiasPorCarrera_carreraValida_filtraCorrectamente()`: Valida que solo retorne pasantías asociadas al ID de carrera consultado.
3. `obtenerPasantiasPorAreas_listaAreas_retornaCoincidencias()`: Valida filtro multicriterio por áreas de conocimiento.
4. `obtenerPasantiasPorAreasYCarrera_combinaFiltros()`: Valida combinación de área y carrera.
5. `obtenerPasantiasPorTerminoDeBusqueda_textoValido_ejecutaLike()`: Valida búsqueda por palabra clave (ej. "Java", "Contable").
6. `obtenerPasantiasPorTerminoDeBusqueda_textoVacio_comportamientoNeutro()`: Valida manejo de cadenas vacías o espacios.
7. `obtenerPasantiasPorFiltros_todosLosCriterios_armaQueryPaginada()`: Prueba integral del buscador general del portal público.
8. `obtenerPasantiasPorFiltros_sinResultados_retornaPaginaVacia()`: Simula retorno de lista vacía sin arrojar excepciones.
9. `obtenerPasantiasPorFiltros_verificaMapeoADto()`: Comprueba que la entidad `Pasantias` se transforme correctamente a `PasantiasConInstitucionYCarrerasDto`.
10. `obtenerPasantiasPorCarrera_carreraInexistente_retornaVacio()`: Simula ID de carrera sin pasantías registradas.
11. `obtenerPasantiasPorAreas_areasNulas_ignoraFiltroArea()`: Valida robustez cuando el parámetro `areas` llega nulo.
12. `obtenerPasantiasPorTerminoDeBusquedaAreas_combinaBusquedaYArea()`: Valida filtro conjunto de texto y categoría técnica.
13. `obtenerPasantiasPorTerminoDeBusquedaAreasYCarrera_filtroTotal()`: Comprueba la consulta con los 3 filtros simultáneos.
14. `obtenerTodasPasantias_verificaOrdenamientoPorFecha()`: Valida que el `Pageable` mantenga el criterio de ordenamiento solicitado.
15. `obtenerTodasPasantias_daoRetornaError_propagaOEncapsulaExcepcion()`: Simula fallo inesperado de persistencia y valida manejo seguro.

---

### 👤 MAURICIO RIVERA
- **Archivo de prueba:** `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/EstudianteBlTest.java`
- **Clase bajo prueba:** `ucb.edu.bo.internship.internship_backend.bl.EstudianteBl`
- **Dependencias a mockear:** `UsuariosDao`, `PersonasDao`, `RolesDao`, `CurriculumsDao`, `AplicacionPasantiasDao`, `MinioBl`.

**Prompt de inicio para la IA de Mauricio:**
> *"Hola, soy Mauricio Rivera. Leé atentamente `GUIA_OFICIAL_75_UNIT_TESTS_QA.md`. Guiame para implementar mis 15 pruebas unitarias en `EstudianteBlTest.java`. Explicame cada caso bajo el patrón AAA y preparame para la defensa oral ante el docente."*

**Sus 15 Pruebas Unitarias:**
1. `obtenerEstudianteByUuid_uuidExistente_retornaDto()`: Valida obtención de perfil por UUID de Keycloak.
2. `obtenerEstudianteByUuid_uuidInexistente_lanzaExcepcionOError()`: Simula UUID no registrado.
3. `actualizarEstudianteByUuid_datosValidos_actualizaPersona()`: Valida actualización de teléfono y enlaces personales.
4. `agregarCurriculum_pdfValido_subeAMinioYGuardaDao()`: Valida que se invoque `MinioBl` para almacenar el archivo y se registre en `CurriculumsDao`.
5. `agregarCurriculum_archivoNuloOVacio_retornaError()`: Valida rechazo inmediato si el archivo no contiene bytes.
6. `obtenerCurriculum_existente_retornaUrlOIdentificador()`: Valida recuperación de ruta de descarga para el estudiante propietario.
7. `obtenerTodosLosCurriculums_retornaListaPorUsuario()`: Valida listado multiversión de hojas de vida del estudiante.
8. `eliminarCurriculum_sinPostulacion_eliminaExitosamente()`: Valida borrado lógico o físico del CV no utilizado.
9. `eliminarCurriculum_idInexistente_lanzaExcepcion()`: Simula intento de eliminación de CV que no pertenece al usuario.
10. `aplicarPasantia_datosValidos_creaAplicacionPendiente()`: Valida registro en `AplicacionPasantiasDao` con estado inicial.
11. `aplicarPasantia_pasantiaInexistente_retornaError()`: Simula postulación a ID de pasantía que no existe en el sistema.
12. `aplicarPasantia_cvInexistente_rechazaPostulacion()`: Simula postulación seleccionando un CV no registrado.
13. `postularPasantia_metodoBooleano_retornaTrueExito()`: Valida la variante booleana del flujo de postulación.
14. `downloadFile_idValido_retornaStreamBytes()`: Valida la descarga del binario con cabeceras `ResponseEntity<byte[]>`.
15. `downloadFile_minioFalla_capturaExcepcion()`: Simula caída del servicio MinIO y verifica que no crashee sin control.

---

### 👤 ALEJANDRO PARDO
- **Archivo de prueba:** `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/InstitucionBlTest.java`
- **Clase bajo prueba:** `ucb.edu.bo.internship.internship_backend.bl.InstitucionBl`
- **Dependencias a mockear:** `InstitucionesDao`, `PasantiasDao`, `UsuariosInstitucionesDao`, `UsuariosDao`.

**Prompt de inicio para la IA de Alejandro:**
> *"Hola, soy Alejandro Pardo. Leé atentamente `GUIA_OFICIAL_75_UNIT_TESTS_QA.md`. Guiame para implementar mis 15 pruebas unitarias en `InstitucionBlTest.java`. Explicame cada caso bajo el patrón AAA y preparame para la defensa oral ante el docente."*

**Sus 15 Pruebas Unitarias:**
1. `agregarInstitucion_datosValidos_guardaYRetornaDto()`: Valida registro inicial de nueva empresa solicitante.
2. `agregarInstitucion_dtoNulo_lanzaExcepcionValidacion()`: Prueba validación de parámetro `@NotNull`.
3. `obtenerInstitucionById_idValido_retornaDetalleConPasantias()`: Valida obtención de ficha empresarial completa.
4. `obtenerInstitucionById_idInexistente_retornaNullOExcepcion()`: Simula consulta con ID inexistente.
5. `obtenerInstituciones_paginadoConFiltro_retornaPagina()`: Valida listado de empresas con búsqueda por nombre.
6. `obtenerInstitucionesNombre_retornaListaNombresSimple()`: Valida endpoint ligero para combos y selects.
7. `obtenerInstitucionesDestacadas_retornaEmpresasConMasOfertas()`: Valida la consulta con `COUNT` de pasantías activas.
8. `obtenerCuatroInstitucionesRelacionadas_retornaMaximoCuatro()`: Valida limitación de tamaño y lógica de empresas sugeridas.
9. `actualizarInstitucion_uuidAutorizado_actualizaDatos()`: Valida edición de datos institucionales por el representante de la empresa.
10. `actualizarInstitucion_uuidNoVinculado_deniegaAcceso()`: Simula intento de edición por un usuario no perteneciente a la empresa.
11. `suscribirseEmpresa_usuarioValido_creaVinculacion()`: Valida suscripción o seguimiento institucional de usuarios.
12. `agregarInstitucionConUsuario_creaAmbasEntidades()`: Valida la transacción compuesta de empresa + usuario representante.
13. `obtenerInstitucionById_verificaComparacionCorrectaId_BugB1()`: Valida que la comparación de IDs use `Objects.equals` y no `==` (crítico para IDs > 127).
14. `obtenerInstituciones_cadenaBusquedaConEspacios_haceTrim()`: Valida sanitización de búsqueda de empresas.
15. `actualizarInstitucion_idInexistente_lanzaExcepcionNegocio()`: Valida actualización sobre entidad inexistente.

---

### 👤 SERGIO MENDOZA
- **Archivo de prueba:** `backend/src/test/java/ucb/edu/bo/internship/internship_backend/bl/AdministradorBlTest.java`
- **Clase bajo prueba:** `ucb.edu.bo.internship.internship_backend.bl.AdministradorBl`
- **Dependencias a mockear:** `InstitucionesDao`, `UsuariosInstitucionesDao`, `PasantiasDao`, `AplicacionPasantiasDao`, `NotificacionesDao`.

**Prompt de inicio para la IA de Sergio:**
> *"Hola, soy Sergio Mendoza. Leé atentamente `GUIA_OFICIAL_75_UNIT_TESTS_QA.md`. Guiame para implementar mis 15 pruebas unitarias en `AdministradorBlTest.java`. Explicame cada caso bajo el patrón AAA y preparame para la defensa oral ante el docente."*

**Sus 15 Pruebas Unitarias:**
1. `cambiarEstadoInstitucion_activar_cambiaEstadoATrue()`: Valida aprobación de empresa aliada por USEI.
2. `cambiarEstadoInstitucion_desactivar_cambiaEstadoAFalse()`: Valida suspensión de empresa y retiro de actividad.
3. `cambiarEstadoInstitucion_idInexistente_lanzaExcepcion()`: Valida manejo de ID de empresa inválido.
4. `aceptarPasantia_idValido_cambiaEstadoYPersiste()`: Valida aprobación de convocatoria y verificación de guardado antes de notificar.
5. `aceptarPasantia_idInexistente_retornaNullOLanzaExcepcion()`: Simula aprobación de oferta inexistente.
6. `rechazarPasantia_idValido_actualizaEstadoRechazado()`: Valida descarte de oferta que no cumple lineamientos USEI.
7. `relacionarUsuarioInstitucion_datosValidos_creaTupla()`: Valida aprobación de vinculación de representante a empresa.
8. `obtenerUsuariosInstitucion_retornaRepresentantes()`: Valida consulta de contactos corporativos de una institución.
9. `obtenerSuscripcionAInstitucion_retornaUsuariosSuscritos()`: Valida consulta de seguidores o usuarios afiliados.
10. `obtenerTodaLaInformacionDeSolicitud_datosCompletos_retornaDto()`: Valida auditoría integral de solicitud de empresa.
11. `obtenerSolicitudDeAplicacion_idValido_retornaDetalleCandidato()`: Valida consulta detallada de postulación para moderación.
12. `aceptarPasantia_garantizaPersistenciaAntesDeEnvioCorreo_BugB6()`: Comprueba que `pasantiasDao.save()` se ejecute primero.
13. `cambiarEstadoInstitucion_guardaAuditoriaEnDao()`: Valida invocación al repositorio correspondiente.
14. `relacionarUsuarioInstitucion_usuarioYaRelacionado_manejaConflicto()`: Valida control de duplicidad en relaciones usuario-empresa.
15. `obtenerSolicitudDeAplicacion_idInvalido_lanzaExcepcion()`: Valida consulta de solicitud inexistente.

---

## 🏛️ PATRÓN CANÓNICO DE PRUEBA UNITARIA (AAA)

Toda prueba escrita por los integrantes o generada por las IAs debe respetar obligatoriamente esta estructura:

```java
package ucb.edu.bo.internship.internship_backend.bl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 1. Aisla la prueba (Sin Spring Boot lento)
class PadronBlTest {

    @Mock
    private PadronEstudianteDao padronEstudianteDao; // 2. Mocks de dependencias

    @InjectMocks
    private PadronBl padronBl; // 3. Objeto real bajo prueba con dependencias inyectadas

    @Test
    @DisplayName("CP-01: Rechazar importación si el correo no pertenece al dominio @ucb.edu.bo")
    void procesarArchivo_correoNoInstitucional_registraError() {
        // ARRANGE (Preparar datos y simular comportamiento de mocks)
        String correoInvalido = "juan.perez@gmail.com";
        // when(padronEstudianteDao.existsByCorreo(anyString())).thenReturn(false);

        // ACT (Ejecutar la función bajo prueba)
        // ImportacionResumenDto resultado = padronBl.procesarLinea(correoInvalido, ...);

        // ASSERT (Verificar resultados y asegurar que NO hubo efectos secundarios no deseados)
        // assertEquals(1, resultado.getTotalErrores());
        // verify(padronEstudianteDao, never()).save(any()); // ¡Vital! No debe guardar basura en BD
    }
}
```

---

## 🎓 GUÍA DE DEFENSA ANTE EL DOCENTE DE QA

Cuando el docente evalúe a cada miembro del equipo, hará preguntas técnicas directas. Esta es la matriz de respuestas que deben dominar:

### Pregunta 1: "¿Por qué usaste `@ExtendWith(MockitoExtension.class)` y no `@SpringBootTest`?"
- **Respuesta Correcta:**  
  *"Porque estamos realizando pruebas unitarias puras, no pruebas de integración. Con Mockito aislamos completamente la clase de negocio (`BL`), simulando los DAOs en memoria. La prueba tarda menos de 50 milisegundos y no depende de que PostgreSQL, Keycloak o MinIO estén levantados."*

### Pregunta 2: "¿Qué hace la sección Arrange - Act - Assert en tu prueba?"
- **Respuesta Correcta:**  
  *"Sigue el estándar AAA de la industria: en **Arrange** preparo las condiciones de entrada y defino qué van a responder los mocks con `when().thenReturn()`; en **Act** ejecuto únicamente el método de negocio que estoy evaluando; y en **Assert** compruebo con aserciones (`assertEquals`, `assertThrows`) que el resultado sea el esperado y verifico con `verify()` que las dependencias hayan sido invocadas correctamente."*

### Pregunta 3: "¿Cómo probás un caso negativo o de excepción?"
- **Respuesta Correcta:**  
  *"Uso `assertThrows(MiExcepcion.class, () -> objetoBl.metodo(...))` y además agrego un `verify(dao, never()).save(...)` para demostrar que, al ocurrir un error de validación, la transacción se abortó y la base de datos no fue corrompida."*

---

## 🚀 COMANDOS PARA EJECUTAR Y VALIDAR

Desde la terminal en `d:\DEV\internship-qa\backend`:

- **Correr únicamente tus 15 pruebas individuales (ejemplo Antonio Yujra):**
  ```powershell
  mvn test -Dtest=PadronBlTest
  ```
- **Correr las 75 pruebas de todo el equipo juntas:**
  ```powershell
  mvn test
  ```
- **Verificar que no haya fallos de compilación:**
  ```powershell
  mvn test-compile
  ```
