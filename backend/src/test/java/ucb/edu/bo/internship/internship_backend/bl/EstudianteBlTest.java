package ucb.edu.bo.internship.internship_backend.bl;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteBlTest {

    @Mock private UsuariosDao usuariosDao;
    @Mock private PersonasDao personasDao;
    @Mock private RolesDao rolesDao;
    @Mock private CurriculumsDao curriculumsDao;
    @Mock private MinioBl minioBl;
    @Mock private AplicacionPasantiasDao aplicacionPasantiasDao;
    @Mock private PasantiasDao pasantiasDao;
    @Mock private MinioClient minioClient;

    @InjectMocks
    private EstudianteBl estudianteBl;

    private Usuarios crearUsuarioEstudiante(Integer id, String uuid) {
        Roles rol = new Roles(1);
        rol.setRol("ESTUDIANTE");

        Personas persona = new Personas(id);
        persona.setNombres("Mauricio");
        persona.setApellidopaterno("Rivera");
        persona.setApellidomaterno("Mayan");
        persona.setCi("9876543");
        persona.setTelefono(70012345);
        persona.setFechadenacimiento(new java.sql.Date(System.currentTimeMillis()));
        // Columnas JSON: UsuariosConPersonaYCarreraDto las parsea con ObjectMapper y no tolera null
        persona.setExperiencia("[]");
        persona.setHabilidades("[\"Java\",\"Spring Boot\"]");
        persona.setHabilidadesseleccionadas("[\"Java\"]");
        persona.setRedessociales("{}");

        Carreras carrera = new Carreras(1);
        carrera.setNombre("Ingenieria de Sistemas");

        Usuarios usuario = new Usuarios(id);
        usuario.setKcUuid(uuid);
        usuario.setCorreo("mauricio.rivera@ucb.edu.bo");
        usuario.setFecharegistro(new Date());
        usuario.setHoraregistro(new Date());
        usuario.setRolesIdroles(rol);
        usuario.setPersonasIdpersonas(persona);
        usuario.setCarrerasIdcarreras(carrera);
        return usuario;
    }

    private Curriculums crearCurriculum(Integer id, String titulo, Usuarios propietario) {
        Curriculums cv = new Curriculums(id);
        cv.setTitulo(titulo);
        cv.setFechacargado(new Date());
        cv.setPdfcurriculum("http://localhost:8085/api/v1/estudiante/public/files/download/" + titulo);
        cv.setUsuariosIdusuarios(propietario);
        return cv;
    }

    private Pasantias crearPasantia(Integer id, String titulo) {
        Instituciones inst = new Instituciones(1, "Empresa Aliada", "Desc", "Av. Arce 123",
                "foto.png", "rrhh@empresa.com", new ArrayList<>(List.of("Tecnologia")),
                "logo.png", new ArrayList<>(), new LinkedHashMap<>(), true);
        Pasantias p = new Pasantias(id, List.of("Backend"), titulo, "Descripcion", List.of("Java"),
                List.of("Desarrollar APIs"), List.of("Certificado"), new Date(), new Date(), true, false);
        p.setInstitucionesIdinstituciones(inst);
        return p;
    }

    @Test
    @DisplayName("UT-M3-01: obtenerEstudianteByUuid con UUID existente y rol ESTUDIANTE retorna el DTO compuesto")
    void obtenerEstudianteByUuid_uuidExistente_retornaDto() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);

        UsuariosDto resultado = estudianteBl.obtenerEstudianteByUuid("uuid-mauricio");

        assertNotNull(resultado);
        assertInstanceOf(UsuariosConPersonaYCarreraDto.class, resultado);
        verify(usuariosDao, times(1)).findByKcUuid("uuid-mauricio");
    }

    @Test
    @DisplayName("UT-M3-02: obtenerEstudianteByUuid con UUID inexistente lanza RuntimeException")
    void obtenerEstudianteByUuid_uuidInexistente_lanzaExcepcionOError() {
        when(usuariosDao.findByKcUuid("uuid-inexistente")).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> estudianteBl.obtenerEstudianteByUuid("uuid-inexistente"));
        verify(usuariosDao, times(1)).findByKcUuid("uuid-inexistente");
    }

    @Test
    @DisplayName("UT-M3-03: actualizarEstudianteByUuid con datos validos modifica la entidad Personas y guarda en BD")
    void actualizarEstudianteByUuid_datosValidos_actualizaPersona() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        PersonasDto cambios = new PersonasDto();
        cambios.setNombre("Mauricio Orlando");
        cambios.setApellidoPaterno("Rivera");
        cambios.setApellidoMaterno("Mayan");
        cambios.setCi("9876543");
        cambios.setTelefono(77799988);
        cambios.setDescripcion("Estudiante de ultimo anio de Ingenieria de Sistemas");
        cambios.setFechaDeNacimiento(new java.sql.Date(System.currentTimeMillis()));

        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(personasDao.save(any(Personas.class))).thenAnswer(inv -> inv.getArgument(0));

        PersonasDto resultado = estudianteBl.actualizarEstudianteByUuid("uuid-mauricio", cambios);

        assertNotNull(resultado);
        assertEquals("Mauricio Orlando", resultado.getNombre());
        assertEquals(Integer.valueOf(77799988), resultado.getTelefono());
        verify(personasDao, times(1)).save(estudiante.getPersonasIdpersonas());
    }

    @Test
    @DisplayName("UT-M3-04: agregarCurriculum con PDF valido lo sube al bucket internship-cv en MinIO y guarda en BD")
    void agregarCurriculum_pdfValido_subeAMinioYGuardaDao() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        MultipartFile archivoPdf = mock(MultipartFile.class);
        when(archivoPdf.getOriginalFilename()).thenReturn("cv_mauricio_2026.pdf");

        NewFileDto archivoSubido = mock(NewFileDto.class);
        when(archivoSubido.getFileName()).thenReturn("170000_cv_mauricio_2026.pdf");

        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(minioBl.uploadFile(archivoPdf, "internship-cv")).thenReturn(archivoSubido);
        when(curriculumsDao.save(any(Curriculums.class))).thenAnswer(inv -> {
            Curriculums c = inv.getArgument(0);
            c.setIdcurriculums(1);
            return c;
        });

        CurriculumsDto resultado = estudianteBl.agregarCurriculum("uuid-mauricio", archivoPdf, "http://localhost:8085");

        assertNotNull(resultado);
        ArgumentCaptor<Curriculums> captor = ArgumentCaptor.forClass(Curriculums.class);
        verify(minioBl, times(1)).uploadFile(archivoPdf, "internship-cv");
        verify(curriculumsDao, times(1)).save(captor.capture());

        Curriculums guardado = captor.getValue();
        assertEquals("cv_mauricio_2026.pdf", guardado.getTitulo());
        assertSame(estudiante, guardado.getUsuariosIdusuarios());
    }

    @Test
    @DisplayName("UT-M3-05: agregarCurriculum cuando el archivo es nulo o rechazado por MinIO lanza RuntimeException")
    void agregarCurriculum_archivoNuloOVacio_retornaError() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        MultipartFile archivoVacio = mock(MultipartFile.class);
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(minioBl.uploadFile(archivoVacio, "internship-cv")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> estudianteBl.agregarCurriculum("uuid-mauricio", archivoVacio, "http://localhost:8085"));
        assertEquals("Error al subir el curriculum", ex.getMessage());
        verify(curriculumsDao, never()).save(any());
    }

    @Test
    @DisplayName("UT-M3-06: obtenerCurriculum existente devuelve la URL de descarga del PDF")
    void obtenerCurriculum_existente_retornaUrlOIdentificador() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        Curriculums cv = crearCurriculum(5, "cv_backend.pdf", estudiante);
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findByTituloAndUsuariosIdusuarios("cv_backend.pdf", estudiante)).thenReturn(cv);

        String urlDescarga = estudianteBl.obtenerCurriculum("uuid-mauricio", "cv_backend.pdf");

        assertEquals("http://localhost:8085/api/v1/estudiante/public/files/download/cv_backend.pdf", urlDescarga);
        verify(curriculumsDao, times(1)).findByTituloAndUsuariosIdusuarios("cv_backend.pdf", estudiante);
    }

    @Test
    @DisplayName("UT-M3-07: obtenerTodosLosCurriculums retorna la lista de CVs pertenecientes al estudiante")
    void obtenerTodosLosCurriculums_retornaListaPorUsuario() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        List<Curriculums> cvs = List.of(
                crearCurriculum(1, "cv_fullstack.pdf", estudiante),
                crearCurriculum(2, "cv_qa_engineer.pdf", estudiante)
        );
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findByUsuariosIdusuarios(estudiante)).thenReturn(cvs);

        List<CurriculumsDto> resultado = estudianteBl.obtenerTodosLosCurriculums("uuid-mauricio");

        assertEquals(2, resultado.size());
        verify(curriculumsDao, times(1)).findByUsuariosIdusuarios(estudiante);
    }

    @Test
    @DisplayName("UT-M3-08: eliminarCurriculum sin postulaciones activas lo borra en el DAO y retorna su DTO")
    void eliminarCurriculum_sinPostulacion_eliminaExitosamente() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        Curriculums cv = crearCurriculum(5, "cv_antiguo.pdf", estudiante);
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findById(5)).thenReturn(Optional.of(cv));
        when(aplicacionPasantiasDao.findByCurriculumsIdcurriculums(cv)).thenReturn(List.of());

        CurriculumsDto eliminado = estudianteBl.eliminarCurriculum("uuid-mauricio", 5);

        assertNotNull(eliminado);
        assertEquals("cv_antiguo.pdf", eliminado.getTitulo());
        verify(curriculumsDao, times(1)).delete(cv);
    }

    @Test
    @DisplayName("UT-M3-09: eliminarCurriculum con ID inexistente lanza RuntimeException y no ejecuta delete")
    void eliminarCurriculum_idInexistente_lanzaExcepcion() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> estudianteBl.eliminarCurriculum("uuid-mauricio", 999));
        assertEquals("Curriculum no encontrado", ex.getMessage());
        verify(curriculumsDao, never()).delete(any());
    }

    @Test
    @DisplayName("UT-M3-10: aplicarPasantia con datos validos crea la postulacion en estado pendiente (activo=false)")
    void aplicarPasantia_datosValidos_creaAplicacionPendiente() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        Curriculums cv = crearCurriculum(5, "cv_sistemas.pdf", estudiante);
        Pasantias pasantia = crearPasantia(3, "Pasantia QA Automation");

        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findById(5)).thenReturn(Optional.of(cv));
        when(pasantiasDao.findById(3)).thenReturn(Optional.of(pasantia));
        when(aplicacionPasantiasDao.findByUsuariosIdusuariosAndPasantiasIdpasantias(estudiante, pasantia))
                .thenReturn(List.of());
        when(aplicacionPasantiasDao.save(any(Aplicacionespasantias.class))).thenAnswer(inv -> {
            Aplicacionespasantias app = inv.getArgument(0);
            app.setIdaplicacionpasantias(100);
            return app;
        });

        AplicacionPasantiasDto resultado = estudianteBl.aplicarPasantia("uuid-mauricio", 3, 5);

        assertNotNull(resultado);
        ArgumentCaptor<Aplicacionespasantias> captor = ArgumentCaptor.forClass(Aplicacionespasantias.class);
        verify(aplicacionPasantiasDao, times(1)).save(captor.capture());

        Aplicacionespasantias guardada = captor.getValue();
        assertFalse(guardada.getActivo());
        assertSame(estudiante, guardada.getUsuariosIdusuarios());
        assertSame(pasantia, guardada.getPasantiasIdpasantias());
        assertSame(cv, guardada.getCurriculumsIdcurriculums());
    }

    @Test
    @DisplayName("UT-M3-11: aplicarPasantia con ID de pasantia inexistente lanza RuntimeException")
    void aplicarPasantia_pasantiaInexistente_retornaError() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        Curriculums cv = crearCurriculum(5, "cv_sistemas.pdf", estudiante);
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findById(5)).thenReturn(Optional.of(cv));
        when(pasantiasDao.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> estudianteBl.aplicarPasantia("uuid-mauricio", 999, 5));
        assertEquals("Pasantia no encontrada", ex.getMessage());
        verify(aplicacionPasantiasDao, never()).save(any());
    }

    @Test
    @DisplayName("UT-M3-12: aplicarPasantia con CV inexistente rechaza la postulacion")
    void aplicarPasantia_cvInexistente_rechazaPostulacion() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(curriculumsDao.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> estudianteBl.aplicarPasantia("uuid-mauricio", 3, 999));
        assertEquals("Curriculum no encontrado", ex.getMessage());
        verifyNoInteractions(pasantiasDao);
        verify(aplicacionPasantiasDao, never()).save(any());
    }

    @Test
    @DisplayName("UT-M3-13: postularPasantia (variante booleana) guarda la aplicacion y retorna true")
    void postularPasantia_metodoBooleano_retornaTrueExito() {
        Usuarios estudiante = crearUsuarioEstudiante(10, "uuid-mauricio");
        Curriculums cv = crearCurriculum(5, "cv_sistemas.pdf", estudiante);
        Pasantias pasantia = crearPasantia(3, "Pasantia Backend");

        when(usuariosDao.findByKcUuid("uuid-mauricio")).thenReturn(estudiante);
        when(pasantiasDao.findById(3)).thenReturn(Optional.of(pasantia));
        when(curriculumsDao.findById(5)).thenReturn(Optional.of(cv));
        when(aplicacionPasantiasDao.findByUsuariosIdusuariosAndPasantiasIdpasantias(estudiante, pasantia))
                .thenReturn(List.of());

        Boolean exito = estudianteBl.postularPasantia("uuid-mauricio", 5, 3);

        assertTrue(exito);
        verify(aplicacionPasantiasDao, times(1)).save(any(Aplicacionespasantias.class));
    }

    @Test
    @DisplayName("UT-M3-14: downloadFile con idFile valido obtiene el stream de MinIO y retorna ResponseEntity PDF")
    void downloadFile_idValido_retornaStreamBytes() throws Exception {
        byte[] bytesPdfEsperados = "%PDF-1.4 contenido binario de prueba".getBytes();
        StatObjectResponse statMock = mock(StatObjectResponse.class);
        when(statMock.object()).thenReturn("cv_mauricio.pdf");

        GetObjectResponse streamMinio = new GetObjectResponse(
                null, "internship-cv", null, "cv_mauricio.pdf", new ByteArrayInputStream(bytesPdfEsperados));

        when(minioClient.statObject(any(StatObjectArgs.class))).thenReturn(statMock);
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(streamMinio);

        ResponseEntity<byte[]> respuesta = estudianteBl.downloadFile("cv_mauricio.pdf");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, respuesta.getHeaders().getContentType());
        assertArrayEquals(bytesPdfEsperados, respuesta.getBody());
        verify(minioClient, times(1)).statObject(any(StatObjectArgs.class));
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
    }

    @Test
    @DisplayName("UT-M3-15: downloadFile captura excepcion cuando MinIO falla y responde HTTP 500 sin crashear")
    void downloadFile_minioFalla_capturaExcepcion() throws Exception {
        when(minioClient.statObject(any(StatObjectArgs.class)))
                .thenThrow(new RuntimeException("MinIO Connection Refused"));

        ResponseEntity<byte[]> respuesta = estudianteBl.downloadFile("cv_caido.pdf");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
        verify(minioClient, never()).getObject(any(GetObjectArgs.class));
    }
}