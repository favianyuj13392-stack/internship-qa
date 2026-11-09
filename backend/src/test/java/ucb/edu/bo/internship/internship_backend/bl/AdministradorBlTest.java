package ucb.edu.bo.internship.internship_backend.bl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ucb.edu.bo.internship.internship_backend.dao.AplicacionPasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.CurriculumsDao;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.PersonasDao;
import ucb.edu.bo.internship.internship_backend.dao.RolesDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosInstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dto.AplicacionPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.SolicitudIndormacionDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuarioConCorreoYNombreCompletoYFotoDto;
import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;
import ucb.edu.bo.internship.internship_backend.entity.Curriculums;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Personas;
import ucb.edu.bo.internship.internship_backend.entity.Roles;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;
import ucb.edu.bo.internship.internship_backend.service.EmailService;
import ucb.edu.bo.internship.internship_backend.service.impl.KeycloakServiceImpl;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdministradorBlTest {

    @Mock
    private InstitucionesDao institucionesDao;
    @Mock
    private UsuariosInstitucionesDao usuariosInstitucionesDao;
    @Mock
    private PasantiasDao pasantiasDao;
    @Mock
    private UsuariosDao usuariosDao;
    @Mock
    private PersonasDao personasDao;
    @Mock
    private AplicacionPasantiasDao aplicacionPasantiasDao;
    @Mock
    private EmailService emailService;
    @Mock
    private RolesDao rolesDao;
    @Mock
    private CurriculumsDao curriculumsDao;
    @Mock
    private KeycloakServiceImpl keycloakService;
    @Mock
    private NotificacionPasantiaService notificacionPasantiaService;

    @InjectMocks
    private AdministradorBl administradorBl;

    @Test
    @DisplayName("M5-01: activar una institución guarda su nuevo estado")
    void cambiarEstadoInstitucion_activar_cambiaEstadoATrue() {
        // ARRANGE: institución pendiente y repositorio que devuelve la entidad guardada.
        Instituciones institucion = institucion(10, false);
        when(institucionesDao.findById(10)).thenReturn(Optional.of(institucion));
        when(institucionesDao.save(institucion)).thenReturn(institucion);

        // ACT: USEI aprueba la institución.
        InstitucionesDto resultado = administradorBl.cambiarEstadoInstitucion(10, true);

        // ASSERT: el estado se actualiza y se persiste en el DAO.
        assertTrue(institucion.getActivo());
        assertEquals(10, resultado.getIdInstituciones());
        assertTrue(resultado.getActivo());
        verify(institucionesDao).save(institucion);
    }

    @Test
    @DisplayName("M5-02: desactivar una institución guarda su estado suspendido")
    void cambiarEstadoInstitucion_desactivar_cambiaEstadoAFalse() {
        // ARRANGE: institución actualmente activa.
        Instituciones institucion = institucion(11, true);
        when(institucionesDao.findById(11)).thenReturn(Optional.of(institucion));
        when(institucionesDao.save(institucion)).thenReturn(institucion);

        // ACT: USEI suspende la institución.
        InstitucionesDto resultado = administradorBl.cambiarEstadoInstitucion(11, false);

        // ASSERT: tanto la entidad persistida como el DTO reflejan la suspensión.
        assertFalse(institucion.getActivo());
        assertFalse(resultado.getActivo());
        verify(institucionesDao).save(institucion);
    }

    @Test
    @DisplayName("M5-03: cambiar estado de una institución inexistente informa que no existe")
    void cambiarEstadoInstitucion_idInexistente_lanzaExcepcion() {
        // ARRANGE: el DAO no encuentra el identificador.
        when(institucionesDao.findById(404)).thenReturn(Optional.empty());

        // ACT + ASSERT: se conserva la excepción de dominio y no se intenta guardar.
        assertThrows(InstitucionNotFoundException.class,
                () -> administradorBl.cambiarEstadoInstitucion(404, true));
        verify(institucionesDao, never()).save(any(Instituciones.class));
    }

    @Test
    @DisplayName("M5-04: aceptar una pasantía la activa, la guarda y notifica a estudiantes")
    void aceptarPasantia_idValido_cambiaEstadoYPersiste() {
        // ARRANGE: convocatoria pendiente sin contacto de empresa; así aislamos la notificación
        // de estudiantes que ejecuta este flujo.
        Pasantias pasantia = pasantia(20, false);
        when(pasantiasDao.findById(20)).thenReturn(Optional.of(pasantia));
        when(pasantiasDao.save(pasantia)).thenReturn(pasantia);

        // ACT: el administrador acepta la convocatoria.
        PasantiasDto resultado = administradorBl.aceptarPasantia(20);

        // ASSERT: el objeto real queda activo, se guarda y se dispara la notificación.
        assertTrue(pasantia.getActivo());
        assertEquals(20, resultado.getIdPasantias());
        verify(pasantiasDao).save(pasantia);
        verify(notificacionPasantiaService).notificarEstudiantesPorCarrera(20);
    }

    @Test
    @DisplayName("M5-05: aceptar una pasantía inexistente informa que no existe")
    void aceptarPasantia_idInexistente_retornaNullOLanzaExcepcion() {
        // ARRANGE: no existe una convocatoria con ese ID.
        when(pasantiasDao.findById(404)).thenReturn(Optional.empty());

        // ACT + ASSERT: el método lanza la excepción de dominio y no produce efectos secundarios.
        assertThrows(InstitucionNotFoundException.class,
                () -> administradorBl.aceptarPasantia(404));
        verify(pasantiasDao, never()).save(any(Pasantias.class));
        verify(notificacionPasantiaService, never()).notificarEstudiantesPorCarrera(404);
    }

    @Test
    @DisplayName("M5-06: rechazar una pasantía pendiente comunica el rechazo y elimina la solicitud")
    void rechazarPasantia_idValido_enviaCorreoYEliminaSolicitud() {
        // ARRANGE: solicitud pendiente con datos suficientes para construir el correo.
        Pasantias pasantia = pasantia(21, false);
        Usuarios representante = usuario(31, "representante@empresa.bo", persona(41, "Ana"));
        pasantia.setUsuariosIdusuarios(representante);
        when(pasantiasDao.findById(21)).thenReturn(Optional.of(pasantia));

        // ACT: el administrador rechaza la solicitud.
        PasantiasDto resultado = administradorBl.rechazarPasantia(21);

        // ASSERT: el flujo actual envía correo y elimina la convocatoria pendiente.
        // La guía describe un cambio a estado rechazado, pero la entidad no tiene ese estado
        // y el código actual elimina el registro; se deja explícita esa diferencia.
        assertNull(resultado.getIdPasantias());
        verify(emailService).enviarCorreo(any());
        verify(pasantiasDao).delete(pasantia);
        verify(pasantiasDao, never()).save(any(Pasantias.class));
    }

    @Test
    @DisplayName("M5-07: aprobar una relación activa usuario e institución y persiste el vínculo")
    void relacionarUsuarioInstitucion_datosValidos_creaTupla() {
        // ARRANGE: solicitud de vinculación pendiente con usuario e institución relacionados.
        Usuariosinstituciones relacion = relacion(70, false,
                usuario(32, "representante@empresa.bo", persona(42, "Luis")),
                institucion(12, false));
        when(usuariosInstitucionesDao.findById(70)).thenReturn(Optional.of(relacion));
        when(usuariosInstitucionesDao.save(relacion)).thenReturn(relacion);

        // ACT: USEI aprueba la vinculación.
        UsuarioConCorreoYNombreCompletoYFotoDto resultado =
                administradorBl.relacionarUsuarioInstitucion(12, 70, 32, true);

        // ASSERT: se activan y persisten los tres estados del vínculo.
        assertTrue(relacion.getActivo());
        assertTrue(relacion.getUsuariosIdusuarios().getActivo());
        assertTrue(relacion.getInstitucionesIdinstituciones().getActivo());
        assertEquals("representante@empresa.bo", resultado.getCorreo());
        verify(usuariosInstitucionesDao).save(relacion);
        verify(emailService).enviarCorreo(any());
    }

    @Test
    @DisplayName("M5-08: obtener usuarios de una institución transforma sus vínculos en DTOs")
    void obtenerUsuariosInstitucion_retornaRepresentantes() {
        // ARRANGE: institución con una relación válida y perfil asociado.
        Instituciones institucion = institucion(13, true);
        Usuariosinstituciones relacion = relacion(71, true,
                usuario(33, "contacto@empresa.bo", persona(43, "María")), institucion);
        institucion.setUsuariosinstitucionesList(List.of(relacion));
        when(institucionesDao.findById(13)).thenReturn(Optional.of(institucion));

        // ACT: se consulta la lista de representantes.
        List<UsuarioConCorreoYNombreCompletoYFotoDto> resultado =
                administradorBl.obtenerUsuariosInstitucion(13);

        // ASSERT: el DTO contiene la identidad y el correo del representante.
        assertEquals(1, resultado.size());
        assertEquals("contacto@empresa.bo", resultado.get(0).getCorreo());
        assertEquals("María", resultado.get(0).getNombre());
        assertEquals(13, resultado.get(0).getIdInstituciones());
    }

    @Test
    @DisplayName("M5-09: obtener suscripciones activas de una institución")
    void obtenerSuscripcionAInstitucion_retornaUsuariosSuscritos() {
        // ARRANGE: el DAO devuelve un vínculo activo con datos de usuario e institución.
        Instituciones institucion = institucion(14, true);
        Usuariosinstituciones relacion = relacion(72, true,
                usuario(34, "suscriptor@empresa.bo", persona(44, "Carlos")), institucion);
        when(usuariosInstitucionesDao.findByInstitucionesIdinstitucionesIdinstitucionesAndActivoTrue(14))
                .thenReturn(List.of(relacion));

        // ACT: se consultan las suscripciones activas.
        List<UsuarioConCorreoYNombreCompletoYFotoDto> resultado =
                administradorBl.obtenerSuscripcionAInstitucion(14);

        // ASSERT: se obtiene el usuario correspondiente al vínculo.
        assertEquals(1, resultado.size());
        assertEquals("suscriptor@empresa.bo", resultado.get(0).getCorreo());
        assertEquals(14, resultado.get(0).getIdInstituciones());
    }

    @Test
    @DisplayName("M5-10: una solicitud válida devuelve los datos completos de usuario e institución")
    void obtenerTodaLaInformacionDeSolicitud_datosCompletos_retornaDto() {
        // ARRANGE: vínculo pendiente cuyos IDs coinciden con el usuario y la institución
        // solicitados; este es el caso válido descrito por la guía.
        Usuarios usuario = usuarioCompleto(35, "solicitante@empresa.bo", persona(45, "Eva"));
        Instituciones institucion = institucion(15, true);
        Usuariosinstituciones relacion = relacion(73, false, usuario, institucion);
        when(usuariosInstitucionesDao.findById(73)).thenReturn(Optional.of(relacion));
        when(usuariosDao.findById(35)).thenReturn(Optional.of(usuario));
        when(institucionesDao.findById(15)).thenReturn(Optional.of(institucion));

        // ACT: se recupera la información de la solicitud.
        SolicitudIndormacionDto resultado =
                administradorBl.obtenerTodaLaInformacionDeSolicitud(15, 73, 35);

        // ASSERT: la respuesta agrega los cuatro componentes del DTO.
        assertNotNull(resultado);
        assertNotNull(resultado.getUsuario());
        assertNotNull(resultado.getPersona());
        assertNotNull(resultado.getInstitucion());
        assertNotNull(resultado.getUsuarioInstitucion());
    }

    @Test
    @DisplayName("M5-11: consultar una postulación devuelve los IDs y el CV del candidato")
    void obtenerSolicitudDeAplicacion_idValido_retornaDetalleCandidato() {
        // ARRANGE: aplicación enlazada a usuario, pasantía y currículum.
        Usuarios usuario = usuario(36, "estudiante@ucb.edu.bo", persona(46, "Diego"));
        Pasantias pasantia = pasantia(22, true);
        Curriculums curriculum = new Curriculums(56, new Date(1_700_000_000_000L),
                "CV Backend", "cv/diego.pdf");
        Aplicacionespasantias aplicacion = new Aplicacionespasantias(
                81, new Date(1_700_000_000_000L), true);
        aplicacion.setUsuariosIdusuarios(usuario);
        aplicacion.setPasantiasIdpasantias(pasantia);
        aplicacion.setCurriculumsIdcurriculums(curriculum);
        when(aplicacionPasantiasDao.findById(81)).thenReturn(Optional.of(aplicacion));

        // ACT: se consulta el detalle de la aplicación.
        AplicacionPasantiasDto resultado = administradorBl.obtenerSolicitudDeAplicacion(81);

        // ASSERT: el DTO conserva las referencias útiles para moderación.
        assertEquals(81, resultado.getIdAplicacionPasantias());
        assertEquals(36, resultado.getIdUsuarios());
        assertEquals(22, resultado.getIdPasantias());
        assertEquals("cv/diego.pdf", resultado.getUrlCurriculum());
    }

    @Test
    @DisplayName("M5-12: persistir la aceptación antes de enviar los correos de notificación")
    void aceptarPasantia_garantizaPersistenciaAntesDeEnvioCorreo_BugB6() {
        // ARRANGE: convocatoria pendiente con representante y correo válidos.
        Pasantias pasantia = pasantia(23, false);
        pasantia.setUsuariosIdusuarios(
                usuario(37, "contacto@empresa.bo", persona(47, "Rosa")));
        when(pasantiasDao.findById(23)).thenReturn(Optional.of(pasantia));
        when(pasantiasDao.save(pasantia)).thenReturn(pasantia);

        // ACT: se acepta la convocatoria.
        administradorBl.aceptarPasantia(23);

        // ASSERT: se verifica el orden de persistencia, correo al representante y notificación.
        InOrder orden = inOrder(pasantiasDao, emailService, notificacionPasantiaService);
        orden.verify(pasantiasDao).save(pasantia);
        orden.verify(emailService).enviarCorreo(any());
        orden.verify(notificacionPasantiaService).notificarEstudiantesPorCarrera(23);
    }

    @Test
    @DisplayName("M5-13: cambiar estado persiste exactamente la institución modificada")
    void cambiarEstadoInstitucion_guardaAuditoriaEnDao() {
        // ARRANGE: institución existente cuyo estado cambiará a inactivo.
        Instituciones institucion = institucion(16, true);
        when(institucionesDao.findById(16)).thenReturn(Optional.of(institucion));
        when(institucionesDao.save(institucion)).thenReturn(institucion);

        // ACT: se solicita la desactivación.
        administradorBl.cambiarEstadoInstitucion(16, false);

        // ASSERT: el DAO recibe la misma entidad, ya modificada.
        assertFalse(institucion.getActivo());
        verify(institucionesDao).save(institucion);
    }

    @Test
    @DisplayName("M5-14: una relación ya activa debe informar el conflicto en vez de duplicarse")
    void relacionarUsuarioInstitucion_usuarioYaRelacionado_manejaConflicto() {
        // ARRANGE: ya existe una relación activa para el usuario y la institución.
        Usuariosinstituciones relacion = relacion(74, true,
                usuario(38, "representante@empresa.bo", persona(48, "Pablo")),
                institucion(17, true));
        when(usuariosInstitucionesDao.findById(74)).thenReturn(Optional.of(relacion));
        when(usuariosInstitucionesDao.save(relacion)).thenReturn(relacion);

        // ACT + ASSERT: la regla asignada espera que una segunda aprobación rechace el conflicto.
        assertThrows(UsuarioYaRelacionadoException.class,
                () -> administradorBl.relacionarUsuarioInstitucion(17, 74, 38, true));
        verify(usuariosInstitucionesDao, never()).save(any(Usuariosinstituciones.class));
    }

    @Test
    @DisplayName("M5-15: consultar una postulación inexistente informa que no existe")
    void obtenerSolicitudDeAplicacion_idInvalido_lanzaExcepcion() {
        // ARRANGE: no existe una postulación con el ID consultado.
        when(aplicacionPasantiasDao.findById(404)).thenReturn(Optional.empty());

        // ACT + ASSERT: se propaga el error con el mensaje de dominio esperado.
        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> administradorBl.obtenerSolicitudDeAplicacion(404));
        assertEquals("Solicitud de aplicacion no encontrada", excepcion.getMessage());
    }

    private static Instituciones institucion(Integer id, Boolean activo) {
        Instituciones institucion = new Instituciones();
        institucion.setIdinstituciones(id);
        institucion.setNombre("Empresa " + id);
        institucion.setDescripcion("Descripción de prueba");
        institucion.setDireccion("La Paz");
        institucion.setFotoinstitucion("foto.jpg");
        institucion.setCorreo("empresa" + id + "@empresa.bo");
        institucion.setSectores(List.of("Tecnología"));
        institucion.setLogoempresa("logo.png");
        institucion.setFotos(List.of("foto-1.jpg"));
        institucion.setActivo(activo);
        return institucion;
    }

    private static Pasantias pasantia(Integer id, boolean activo) {
        Pasantias pasantia = new Pasantias();
        pasantia.setIdpasantias(id);
        pasantia.setTitulo("Pasantía de prueba " + id);
        pasantia.setDescripcion("Descripción de la pasantía");
        pasantia.setAreas(List.of("Backend"));
        pasantia.setRequisitos(List.of("Java"));
        pasantia.setFunciones(List.of("Desarrollar pruebas"));
        pasantia.setBeneficios(List.of("Aprendizaje"));
        pasantia.setFechacierre(java.sql.Date.valueOf("2026-12-31"));
        pasantia.setFechaingreso(java.sql.Date.valueOf("2026-11-10"));
        pasantia.setActivo(activo);
        pasantia.setSinaplicantes(false);
        return pasantia;
    }

    private static Personas persona(Integer id, String nombre) {
        Personas persona = new Personas();
        persona.setIdpersonas(id);
        persona.setNombres(nombre);
        persona.setApellidopaterno("Prueba");
        persona.setApellidomaterno("QA");
        persona.setFotoperfil("perfil.png");
        return persona;
    }

    private static Usuarios usuario(Integer id, String correo, Personas persona) {
        Usuarios usuario = new Usuarios();
        usuario.setIdusuarios(id);
        usuario.setCorreo(correo);
        usuario.setPersonasIdpersonas(persona);
        usuario.setActivo(false);
        return usuario;
    }

    private static Usuarios usuarioCompleto(Integer id, String correo, Personas persona) {
        Usuarios usuario = usuario(id, correo, persona);
        usuario.setKcUuid("uuid-" + id);
        usuario.setFecharegistro(new Date(1_700_000_000_000L));
        usuario.setHoraregistro(new Time(12, 0, 0));
        usuario.setRolesIdroles(new Roles(1));
        return usuario;
    }

    private static Usuariosinstituciones relacion(
            Integer id,
            Boolean activo,
            Usuarios usuario,
            Instituciones institucion) {
        Usuariosinstituciones relacion = new Usuariosinstituciones(id, "Representante", activo);
        relacion.setUsuariosIdusuarios(usuario);
        relacion.setInstitucionesIdinstituciones(institucion);
        return relacion;
    }
}
