# 📘 Guía de Estudio — Mauricio Rivera (Módulo 3: `EstudianteBlTest.java`)

> Objetivo: que puedas defender oralmente, línea por línea, cada una de tus 15 pruebas unitarias ante el docente. No memorices el código: entiende **qué prueba, cómo lo prueba y por qué se eligió ese mock o esa aserción.**

---

## 1. Conceptos base que te pueden preguntar

### 1.1 `@ExtendWith(MockitoExtension.class)`
Activa la integración de Mockito con JUnit 5. Sin esto, las anotaciones `@Mock` e `@InjectMocks` no se inicializan y los mocks quedarían `null`.

### 1.2 `@Mock`
Crea un **doble de prueba** (test double) de una dependencia (DAO, servicio externo). No ejecuta lógica real: solo responde lo que tú le digas con `when(...).thenReturn(...)`.

En tu clase mockeaste:
```java
@Mock private UsuariosDao usuariosDao;
@Mock private PersonasDao personasDao;
@Mock private RolesDao rolesDao;
@Mock private CurriculumsDao curriculumsDao;
@Mock private MinioBl minioBl;
@Mock private AplicacionPasantiasDao aplicacionPasantiasDao;
@Mock private PasantiasDao pasantiasDao;
@Mock private MinioClient minioClient;
```
**Por qué estas y no otras:** son exactamente las dependencias que `EstudianteBl` recibe por constructor (ver `EstudianteBl.java` líneas 40-50). Si el docente pregunta "¿por qué no mockeaste `rolesDao` en los `when()`?", la respuesta es: está inyectado pero `EstudianteBl` nunca lo invoca directamente en los métodos probados (el rol se lee desde `usuario.getRolesIdroles()`, que ya viene seteado en el objeto `Usuarios` de prueba).

### 1.3 `@InjectMocks`
```java
@InjectMocks
private EstudianteBl estudianteBl;
```
Mockito crea una instancia real de `EstudianteBl` e **inyecta automáticamente** todos los `@Mock` declarados arriba en su constructor. Así pruebas la lógica real de la clase, pero con dependencias falsas controladas por ti.

### 1.4 Patrón AAA (Arrange – Act – Assert)
Todas tus pruebas siguen esta estructura:
1. **Arrange**: preparas los datos y configuras los mocks (`when(...).thenReturn(...)`).
2. **Act**: llamas al método real de `EstudianteBl`.
3. **Assert**: verificas el resultado (`assertEquals`, `assertThrows`, etc.) y las interacciones (`verify(...)`).

### 1.5 Métodos helper (fixtures)
Creaste 3 métodos privados para no repetir código en cada test:
- `crearUsuarioEstudiante(id, uuid)`: construye un `Usuarios` completo con `Roles`, `Personas` y `Carreras`.
- `crearCurriculum(id, titulo, propietario)`: construye un `Curriculums`.
- `crearPasantia(id, titulo)`: construye una `Pasantias` con su `Instituciones`.

**Pregunta típica del docente:** *"¿Por qué en `crearUsuarioEstudiante` seteas `experiencia`, `habilidades`, etc. como strings JSON (`"[]"`, `"{}"`)?"*
**Respuesta:** porque `UsuariosConPersonaYCarreraDto` (el DTO que arma `obtenerEstudianteByUuid`) usa `ObjectMapper` para parsear esos campos como JSON. Si quedan en `null`, el `ObjectMapper` lanza excepción al intentar parsear `null` como árbol JSON, y el test fallaría por una razón ajena a lo que realmente quieres probar.

---

## 2. Tus 15 pruebas, una por una

### UT-M3-01 — `obtenerEstudianteByUuid_uuidExistente_retornaDto`
- **Qué prueba:** que al buscar por UUID un usuario que sí existe y tiene rol `ESTUDIANTE`, el método devuelve un DTO compuesto.
- **Cómo:** `when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante)` simula que el DAO "encuentra" al usuario.
- **Assert clave:** `assertInstanceOf(UsuariosConPersonaYCarreraDto.class, resultado)` — no solo que no sea null, sino que sea exactamente el tipo de DTO compuesto que arma `UsuariosDto + PersonasDto + CarrerasDto`.
- **Verify:** `verify(usuariosDao, times(1)).findByKcUuid(...)` confirma que el DAO se llamó exactamente una vez (ni cero, ni dos veces por error de lógica).

### UT-M3-02 — `obtenerEstudianteByUuid_uuidInexistente_lanzaExcepcionOError`
- **Qué prueba:** comportamiento cuando el DAO no encuentra nada (`null`).
- **Detalle importante:** en el código real (`EstudianteBl.java:53-55`), si `usuario` es `null`, la línea `usuario.getRolesIdroles()` lanza un `NullPointerException`, que **es una subclase de `RuntimeException`**. Por eso `assertThrows(RuntimeException.class, ...)` pasa, aunque el mensaje real sea un NPE y no una excepción de negocio explícita.
- **Posible pregunta de examen:** *"¿Es esto un bug?"* — Sí, es una inconsistencia: otros métodos como `obtenerCurriculum` sí validan `if (usuario == null) throw new RuntimeException("Usuario no encontrado")` explícitamente, pero `obtenerEstudianteByUuid` no. Según la regla del equipo ("no corregir, solo reportar"), esto debería ir como hallazgo en MantisBT, no arreglarse en `src/main`.

### UT-M3-03 — `actualizarEstudianteByUuid_datosValidos_actualizaPersona`
- **Qué prueba:** que al actualizar datos, los campos del objeto `Personas` interno se modifican y se guardan.
- **Mock encadenado:** `when(personasDao.save(any(Personas.class))).thenAnswer(inv -> inv.getArgument(0))` — en vez de devolver un objeto fijo, devuelve el mismo argumento que recibió (simulando que `save()` en JPA retorna la misma entidad persistida).
- **Verify con objeto específico:** `verify(personasDao, times(1)).save(estudiante.getPersonasIdpersonas())` — no verificas "cualquier Personas", sino que sea exactamente la instancia que colgaba del usuario mockeado (demuestra que se modificó el objeto correcto, no uno nuevo).

### UT-M3-04 — `agregarCurriculum_pdfValido_subeAMinioYGuardaDao`
- **Qué prueba:** el flujo feliz de subir un CV: sube a MinIO, genera URL, guarda en BD.
- **`ArgumentCaptor<Curriculums>`:** en vez de solo verificar que `save()` se llamó, **capturas el objeto real** que se le pasó para poder inspeccionar sus campos (`guardado.getTitulo()`, `guardado.getUsuariosIdusuarios()`). Esto es más fuerte que un simple `verify()`, porque confirma el **contenido**, no solo la llamada.
- **`assertSame` vs `assertEquals`:** usás `assertSame(estudiante, guardado.getUsuariosIdusuarios())` porque quieres verificar que es **la misma referencia de objeto** (identidad), no solo un objeto "igual" por valor.

### UT-M3-05 — `agregarCurriculum_archivoNuloOVacio_retornaError`
- **Qué prueba:** cuando MinIO rechaza el archivo (`uploadFile` devuelve `null`), el método debe lanzar `RuntimeException` con mensaje `"Error al subir el curriculum"` y **nunca** llegar a guardar en el DAO.
- **Detalle de implementación:** en `EstudianteBl.java:92`, el `throw new RuntimeException("Error al subir el curriculum")` interno es capturado por el `catch (Exception e)` de la línea 102, que **vuelve a envolver** la excepción en otra `RuntimeException("Error al subir el curriculum", e)`. El mensaje final sigue siendo el mismo texto por casualidad de diseño, no por accidente — pero es un patrón que vale la pena poder explicar si te preguntan "¿por qué hay un try/catch que relanza lo mismo que ya se lanzó?".
- **`verify(curriculumsDao, never()).save(any())`:** confirma que el flujo se detuvo antes de tocar la base de datos.

### UT-M3-06 — `obtenerCurriculum_existente_retornaUrlOIdentificador`
- **Qué prueba:** que dado un UUID y un título de CV, se devuelve la URL de descarga guardada.
- **Mock con dos argumentos:** `curriculumsDao.findByTituloAndUsuariosIdusuarios("cv_backend.pdf", estudiante)` — simula un método de Spring Data JPA derivado del nombre (`findBy<Campo1>And<Campo2>`).

### UT-M3-07 — `obtenerTodosLosCurriculums_retornaListaPorUsuario`
- **Qué prueba:** listado de CVs de un usuario, con mapeo correcto entidad→DTO.
- **Dato clave:** el método real usa `.stream().map(CurriculumsDto::fromEntity).toList()` — por eso tu assert es sobre el **tamaño de la lista** (`assertEquals(2, resultado.size())`), confirmando que el mapeo no pierde ni duplica elementos.

### UT-M3-08 — `eliminarCurriculum_sinPostulacion_eliminaExitosamente`
- **Qué prueba:** que un CV sin aplicaciones asociadas se puede borrar.
- **Mock de "lista vacía" como condición de negocio:** `when(aplicacionPasantiasDao.findByCurriculumsIdcurriculums(cv)).thenReturn(List.of())` — la regla de negocio en `EstudianteBl.java:154-155` es "si la lista de aplicaciones NO está vacía, prohibir borrado". Devolver lista vacía es la forma de simular el camino feliz.

### UT-M3-09 — `eliminarCurriculum_idInexistente_lanzaExcepcion`
- **Qué prueba:** `Optional.empty()` del DAO dispara `orElseThrow(() -> new RuntimeException("Curriculum no encontrado"))` (línea 151 del BL).
- **Por qué usar `Optional`:** `findById` en Spring Data JPA siempre retorna `Optional<T>`, nunca `null` directamente — por eso el mock es `thenReturn(Optional.empty())` y no `thenReturn(null)`.

### UT-M3-10 — `aplicarPasantia_datosValidos_creaAplicacionPendiente`
- **Qué prueba:** el camino feliz completo de postular: usuario válido + CV propio + pasantía existente + sin postulación previa → crea `Aplicacionespasantias` con `activo = false` (pendiente de revisión).
- **3 `assertSame` en cadena:** verificas que el objeto capturado (`guardada`) tenga exactamente las referencias de `estudiante`, `pasantia` y `cv` que preparaste — no copias, no nulls.
- **`assertFalse(guardada.getActivo())`:** clave de negocio — toda nueva postulación nace **inactiva** (pendiente), nunca aprobada automáticamente.

### UT-M3-11 — `aplicarPasantia_pasantiaInexistente_retornaError`
- **Qué prueba:** si la pasantía no existe, se corta el flujo antes de guardar nada.
- **Orden de validación en el código real:** fíjate que en `aplicarPasantia` (línea 165-187) **primero** se valida el curriculum, **luego** la pasantía. Por eso en este test sí mockeas `curriculumsDao.findById(5)` con éxito, para que el flujo llegue a fallar justo en la validación de pasantía.

### UT-M3-12 — `aplicarPasantia_cvInexistente_rechazaPostulacion`
- **Qué prueba:** si el CV no existe, el flujo se corta **antes** de siquiera consultar la pasantía.
- **`verifyNoInteractions(pasantiasDao)`:** esta es la aserción más importante del test — no solo dice "no se guardó nada", sino "`pasantiasDao` ni se tocó". Esto confirma el **orden exacto** de las validaciones en el código (CV se valida antes que pasantía).

### UT-M3-13 — `postularPasantia_metodoBooleano_retornaTrueExito`
- **Qué prueba:** la variante antigua/alternativa del método (`postularPasantia`, marcado con `//TODO: Seleccionar curriculum` en el código — es un método con una deuda técnica pendiente), que retorna `Boolean` en vez de un DTO.
- **Diferencia con `aplicarPasantia`:** este método **no** valida que el CV pertenezca al usuario (no tiene el `if(!curriculum.getUsuariosIdusuarios().equals(usuario))`). Es un posible hallazgo de seguridad/negocio a reportar: un estudiante podría postular con el ID de CV de otro usuario.

### UT-M3-14 — `downloadFile_idValido_retornaStreamBytes`
- **Qué prueba:** la descarga de un archivo desde MinIO devuelve un `ResponseEntity<byte[]>` con status 200, `Content-Type: application/pdf` y los bytes correctos.
- **Mock de un objeto final/complejo:** `GetObjectResponse` no se puede mockear fácilmente con `mock()` porque su constructor requiere varios parámetros — por eso lo **instancias directamente** con `new GetObjectResponse(null, "internship-cv", null, "cv_mauricio.pdf", new ByteArrayInputStream(bytesPdfEsperados))`, pasando un `InputStream` real de prueba en memoria.
- **`assertArrayEquals`:** para arrays de bytes, `assertEquals` compara referencias — por eso se usa específicamente `assertArrayEquals`.

### UT-M3-15 — `downloadFile_minioFalla_capturaExcepcion`
- **Qué prueba:** si MinIO lanza una excepción (por ejemplo, conexión caída), el método **no debe propagar la excepción ni crashear** la petición HTTP: debe responder `500 INTERNAL_SERVER_ERROR` con body `null`.
- **`thenThrow(new RuntimeException(...))`:** así simulas una falla real de infraestructura sin necesitar que MinIO esté realmente caído.
- **`verify(minioClient, never()).getObject(...)`:** confirma que, al fallar el primer paso (`statObject`), el código no sigue intentando el segundo paso (`getObject`) — el `try/catch` corta el flujo correctamente.

---

## 3. Preguntas que probablemente te haga el docente (y cómo responder)

| Pregunta | Respuesta corta |
|---|---|
| ¿Por qué no usaste `@SpringBootTest`? | Porque es una prueba **unitaria pura**: debe correr en milisegundos, sin levantar contexto de Spring ni conexión a BD real. `@ExtendWith(MockitoExtension.class)` + `@Mock`/`@InjectMocks` aíslan la clase bajo prueba. |
| ¿Qué diferencia hay entre `verify()` y `assertEquals()`? | `assertEquals` compara el **resultado devuelto** por el método. `verify()` comprueba **interacciones** con los mocks (si se llamó, cuántas veces, con qué argumentos). |
| ¿Qué es un `ArgumentCaptor` y cuándo lo usaste? | Permite capturar el objeto exacto que se pasó a un mock para inspeccionar sus campos después. Lo usaste en UT-04 y UT-10 para verificar el contenido de las entidades guardadas, no solo que `save()` se llamó. |
| ¿Por qué algunos tests esperan `RuntimeException` genérica y no una excepción personalizada? | Porque `EstudianteBl` no tiene excepciones de dominio propias (como `CurriculumNoEncontradoException`) — todo se maneja con `RuntimeException` de mensaje variable. Es una decisión de diseño del sistema, no de tus pruebas. |
| ¿Qué pasa si el docente pide agregar un 16vo caso? | Regla del equipo: solo puedes trabajar en `backend/src/test/.../EstudianteBlTest.java`. Nunca tocar `src/main`. Si encuentras un bug al escribir el nuevo caso, se reporta en MantisBT, no se corrige en el código fuente. |
| ¿Por qué `crearUsuarioEstudiante` setea JSON como string? | Porque `UsuariosConPersonaYCarreraDto` parsea esos campos con Jackson `ObjectMapper`, y un valor `null` rompe el parseo con una excepción no relacionada al test. |

---

## 4. Bugs/hallazgos potenciales que descubriste (para reportar en MantisBT, no corregir)

1. **`obtenerEstudianteByUuid`** no valida `usuario == null` antes de llamar `usuario.getRolesIdroles()` → lanza `NullPointerException` sin mensaje de negocio claro (inconsistente con `obtenerCurriculum`, que sí valida explícitamente).
2. **`postularPasantia`** (variante booleana) no valida que el curriculum pertenezca al usuario autenticado, a diferencia de `aplicarPasantia` que sí lo hace — posible falla de autorización.
3. El mensaje de error en `agregarCurriculum` se duplica (se lanza y luego se vuelve a envolver con el mismo texto) — no es un bug funcional, pero sí una señal de manejo de excepciones mejorable.

---

## 5. Comandos útiles para la defensa

```bash
# Ejecutar solo tus pruebas
./mvnw.cmd -Dtest=EstudianteBlTest test

# Ver el reporte detallado de Surefire
cat target/surefire-reports/ucb.edu.bo.internship.internship_backend.bl.EstudianteBlTest.txt
```

Estado actual confirmado: **15/15 pruebas en verde, BUILD SUCCESS, sin tocar `src/main`.**
