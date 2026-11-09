package ucb.edu.bo.internship.internship_backend.bl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;
import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de InstitucionBl (Modulo 4: Portal Empresa).
 * Autor: Alejandro Pardo - SIS-312 Administracion de la Calidad.
 *
 * Son pruebas UNITARIAS PURAS: no se levanta Spring, ni PostgreSQL, ni Keycloak, ni MinIO.
 * Todas las dependencias de InstitucionBl se reemplazan por "mocks" (objetos falsos de Mockito)
 * y cada prueba sigue el patron AAA: Arrange (preparar) - Act (ejecutar) - Assert (verificar).
 */
@ExtendWith(MockitoExtension.class) // Activa Mockito en JUnit 5: crea los @Mock y arma el @InjectMocks antes de cada prueba
class InstitucionBlTest {

    // ===== MOCKS: las 11 dependencias que recibe el constructor de InstitucionBl =====
    // Hay que declararlas TODAS; si falta una, @InjectMocks le pasa null y la prueba puede fallar con NullPointerException.
    @Mock private InstitucionesDao institucionesDao;
    @Mock private PasantiasDao pasantiasDao;
    @Mock private UsuariosInstitucionesDao usuariosInstitucionesDao;
    @Mock private UsuariosDao usuariosDao;
    @Mock private UsuariosBL usuariosBL;
    @Mock private AplicacionPasantiasDao aplicacionPasantiasDao;
    @Mock private SeleccionAplicanteDao seleccionAplicanteDao;
    @Mock private PersonasDao personasDao;
    @Mock private PasantiasCarrerasDao pasantiasCarrerasDao;
    @Mock private CarrerasDao carrerasDao;
    @Mock private EmailService emailService;

    // ===== CLASE BAJO PRUEBA: objeto REAL al que Mockito le inyecta los mocks por constructor =====
    @InjectMocks
    private InstitucionBl institucionBl;

    // ===== METODOS DE AYUDA (fabrican datos de prueba para no repetir codigo) =====

    /** Crea una entidad Instituciones como la que devolveria la base de datos. */
    private Instituciones crearInstitucion(Integer id, String nombre, boolean activo) {
        return new Instituciones(id, nombre, "Descripcion de " + nombre, "Av. Arce 123",
                "foto.png", "contacto@empresa.com", new ArrayList<>(List.of("Tecnologia")),
                "logo.png", new ArrayList<>(), new LinkedHashMap<>(), activo);
    }

    /** Crea una pasantia con fechas (PasantiasDto.fromEntity falla si las fechas son null). */
    private Pasantias crearPasantia(Integer id, String titulo, Instituciones institucion) {
        Pasantias p = new Pasantias(id, List.of("Backend"), titulo, "Descripcion", List.of("Java"),
                List.of("Desarrollar APIs"), List.of("Certificado"), new Date(), new Date(), true, false);
        p.setInstitucionesIdinstituciones(institucion);
        return p;
    }

    // =====================================================================================
    // 1
    @Test
    @DisplayName("UT-M4-01: agregarInstitucion guarda la empresa como inactiva, con id nulo, y devuelve el DTO")
    void agregarInstitucion_datosValidos_guardaYRetornaDto() {
        // ARRANGE: el cliente manda un id (999) y activo=true; la regla de negocio debe ignorar ambos
        InstitucionesDto entrada = new InstitucionesDto(999, "Banco Union", "Banca", "El Prado",
                "foto.png", "rrhh@bu.com.bo", List.of("Banca"), "logo.png", List.of(), new LinkedHashMap<>(), true);
        Instituciones guardada = crearInstitucion(10, "Banco Union", false); // lo que "responde" la BD
        when(institucionesDao.save(any(Instituciones.class))).thenReturn(guardada);

        // ACT
        InstitucionesDto resultado = institucionBl.agregarInstitucion(entrada);

        // ASSERT: ArgumentCaptor "atrapa" el objeto que el BL le paso al DAO para inspeccionarlo
        ArgumentCaptor<Instituciones> captor = ArgumentCaptor.forClass(Instituciones.class);
        verify(institucionesDao, times(1)).save(captor.capture());
        assertNull(captor.getValue().getIdinstituciones(), "El id lo genera la BD, no el cliente");
        assertFalse(captor.getValue().getActivo(), "Toda empresa nueva debe esperar aprobacion de USEI");
        assertEquals(10, resultado.getIdInstituciones());
        assertEquals("Banco Union", resultado.getNombre());
    }

    // =====================================================================================
    // 2
    @Test
    @DisplayName("UT-M4-02: agregarInstitucion con DTO null lanza InstitucionServiceExcepcion y no toca la BD")
    void agregarInstitucion_dtoNulo_lanzaExcepcionValidacion() {
        // ARRANGE: no hace falta stub; el DTO es null.
        // Ojo: @NotNull (org.jetbrains) es solo una anotacion para el IDE, NO valida en tiempo de ejecucion.
        // Por eso ocurre un NullPointerException que el BL envuelve en InstitucionServiceExcepcion.

        // ACT + ASSERT
        InstitucionServiceExcepcion ex = assertThrows(InstitucionServiceExcepcion.class,
                () -> institucionBl.agregarInstitucion(null));
        assertEquals("Error al agregar la institucion", ex.getMessage());
        assertInstanceOf(NullPointerException.class, ex.getCause());
        verifyNoInteractions(institucionesDao); // la BD nunca fue invocada: no se guardo basura
    }

    // =====================================================================================
    // 3
    @Test
    @DisplayName("UT-M4-03: obtenerInstitucionById con id valido devuelve la empresa con sus pasantias")
    void obtenerInstitucionById_idValido_retornaDetalleConPasantias() {
        // ARRANGE
        Instituciones institucion = crearInstitucion(5, "Tigo", true);
        when(institucionesDao.findByIdinstitucionesAndActivo(5, true)).thenReturn(institucion);
        when(pasantiasDao.findPasantiasByInstitucionesIdinstitucionesAndActivoIsTrueAndSinaplicantesIsNotTrue(5))
                .thenReturn(List.of(crearPasantia(1, "Dev Java", institucion), crearPasantia(2, "QA Tester", institucion)));

        // ACT
        InstitucionConPasantiasDto resultado = institucionBl.obtenerInstitucionById(5);

        // ASSERT
        assertEquals("Tigo", resultado.getNombre());
        assertEquals(2, resultado.getPasantias().size());
        assertEquals("Dev Java", resultado.getPasantias().get(0).getTitulo());
        verify(institucionesDao).findByIdinstitucionesAndActivo(5, true); // solo busca empresas ACTIVAS
    }

    // =====================================================================================
    // 4
    @Test
    @DisplayName("UT-M4-04: obtenerInstitucionById con id inexistente lanza InstitucionNotFoundException")
    void obtenerInstitucionById_idInexistente_retornaNullOExcepcion() {
        // ARRANGE: la BD no encuentra nada (devuelve null)
        when(institucionesDao.findByIdinstitucionesAndActivo(999, true)).thenReturn(null);

        // ACT + ASSERT
        InstitucionNotFoundException ex = assertThrows(InstitucionNotFoundException.class,
                () -> institucionBl.obtenerInstitucionById(999));
        assertEquals("Institucion no encontrada", ex.getMessage());
        verifyNoInteractions(pasantiasDao); // si no hay empresa, ni siquiera busca pasantias
    }

    // =====================================================================================
    // 5
    @Test
    @DisplayName("UT-M4-05: obtenerInstituciones con busqueda por nombre usa la consulta correcta y arma el Pageable")
    void obtenerInstituciones_paginadoConFiltro_retornaPagina() {
        // ARRANGE
        Page<InstitucionesDto> paginaFalsa = new PageImpl<>(List.of(new InstitucionesDto(crearInstitucion(1, "Banco BNB", true))));
        when(institucionesDao.findAllWithCountPasantiasAndNombreContaining(eq("Banco"), any(Pageable.class)))
                .thenReturn(paginaFalsa);

        // ACT: pagina 0, 10 por pagina, busca "Banco", ordena por nombre, sin filtro activo ni sector
        Page<InstitucionesDto> resultado = institucionBl.obtenerInstituciones(0, 10, "Banco", "nombre", "", null);

        // ASSERT
        assertEquals(1, resultado.getTotalElements());
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(institucionesDao).findAllWithCountPasantiasAndNombreContaining(eq("Banco"), captor.capture());
        Pageable pageable = captor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        Sort.Order orden = pageable.getSort().getOrderFor("nombre");
        assertNotNull(orden);
        assertTrue(orden.isAscending(), "sort=nombre debe ordenar A-Z");
        verify(institucionesDao, never()).findAllWithCountPasantias(any()); // no uso la consulta sin filtro
    }

    // =====================================================================================
    // 6
    @Test
    @DisplayName("UT-M4-06: obtenerInstitucionesNombre devuelve la lista liviana (id, nombre, logo) solo de activas")
    void obtenerInstitucionesNombre_retornaListaNombresSimple() {
        // ARRANGE
        when(institucionesDao.getAllIdAndNameByActivo(true)).thenReturn(List.of(
                new InstitucionNombreDto(1, "Entel", "entel.png"),
                new InstitucionNombreDto(2, "Tigo", "tigo.png")));

        // ACT
        List<InstitucionNombreDto> resultado = institucionBl.obtenerInstitucionesNombre();

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals("Entel", resultado.get(0).getNombre());
        verify(institucionesDao).getAllIdAndNameByActivo(true);
    }

    // =====================================================================================
    // 7
    @Test
    @DisplayName("UT-M4-07: obtenerInstitucionesDestacadas pide solo activas y respeta el orden por cantidad de ofertas")
    void obtenerInstitucionesDestacadas_retornaEmpresasConMasOfertas() {
        // ARRANGE: el ORDER BY COUNT(...) vive en la consulta SQL del DAO; aqui simulamos su resultado ya ordenado
        Page<InstitucionesConCOUNTPasantiasDto> pagina = new PageImpl<>(List.of(
                new InstitucionesConCOUNTPasantiasDto(3, "Banco Union", 12L, "bu.png"),
                new InstitucionesConCOUNTPasantiasDto(7, "Tigo", 5L, "tigo.png")));
        when(institucionesDao.getAllNameAndCountPasantiasByActivo(eq(true), any(Pageable.class))).thenReturn(pagina);

        // ACT
        Page<InstitucionesConCOUNTPasantiasDto> resultado = institucionBl.obtenerInstitucionesDestacadas(0, 4);

        // ASSERT
        assertEquals("Banco Union", resultado.getContent().get(0).getNombre(), "La de mas ofertas va primero");
        verify(institucionesDao).getAllNameAndCountPasantiasByActivo(true, PageRequest.of(0, 4));
    }

    // =====================================================================================
    // 8
    @Test
    @DisplayName("UT-M4-08: obtenerCuatroInstitucionesRelacionadas arma un DTO por cada empresa sugerida (maximo 4)")
    void obtenerCuatroInstitucionesRelacionadas_retornaMaximoCuatro() {
        // ARRANGE: el limite de 4 lo pone la consulta "findTop4..." del DAO; el BL no debe perder ni agregar empresas
        List<Instituciones> top4 = List.of(crearInstitucion(2, "A", true), crearInstitucion(3, "B", true),
                crearInstitucion(4, "C", true), crearInstitucion(5, "D", true));
        when(institucionesDao.findTop4InstitucionesBySectoresAndPasantias(1)).thenReturn(top4);
        when(pasantiasDao.findPasantiasByInstitucionesIdinstituciones(any(Instituciones.class))).thenReturn(List.of());

        // ACT
        List<InstitucionConPasantiasDto> resultado = institucionBl.obtenerCuatroInstitucionesRelacionadas(1);

        // ASSERT
        assertEquals(4, resultado.size());
        assertTrue(resultado.size() <= 4);
        verify(pasantiasDao, times(4)).findPasantiasByInstitucionesIdinstituciones(any(Instituciones.class));
    }

    // =====================================================================================
    // 9
    @Test
    @DisplayName("UT-M4-09: actualizarInstitucion con usuario vinculado actualiza los datos y guarda")
    void actualizarInstitucion_uuidAutorizado_actualizaDatos() {
        // ARRANGE
        Instituciones existente = crearInstitucion(7, "Nombre Viejo", true);
        InstitucionesDto cambios = new InstitucionesDto(7, "Nombre Nuevo", "Nueva desc", "Calle 21 Calacoto",
                "f.png", "nuevo@empresa.com", List.of("Tecnologia"), "l.png", List.of(), new LinkedHashMap<>(), true);
        when(usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones("uuid-empresa", 7)).thenReturn(true);
        when(institucionesDao.findByIdinstitucionesAndActivo(7, true)).thenReturn(existente);
        when(institucionesDao.save(any(Instituciones.class))).thenAnswer(inv -> inv.getArgument(0)); // devuelve lo mismo que recibe

        // ACT
        InstitucionesDto resultado = institucionBl.actualizarInstitucion("uuid-empresa", cambios, 7);

        // ASSERT
        assertEquals("Nombre Nuevo", resultado.getNombre());
        assertEquals("nuevo@empresa.com", resultado.getCorreo());
        assertEquals("Calle 21 Calacoto", resultado.getDireccion());
        verify(institucionesDao).save(existente);
    }

    // =====================================================================================
    // 10
    @Test
    @DisplayName("UT-M4-10: actualizarInstitucion con usuario NO vinculado lanza UsuarioYaRelacionadoException")
    void actualizarInstitucion_uuidNoVinculado_deniegaAcceso() {
        // ARRANGE: el usuario de otra empresa intenta editar la empresa 7
        when(usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones("uuid-intruso", 7)).thenReturn(false);
        InstitucionesDto cambios = new InstitucionesDto();
        cambios.setNombre("Hackeado");

        // ACT + ASSERT
        UsuarioYaRelacionadoException ex = assertThrows(UsuarioYaRelacionadoException.class,
                () -> institucionBl.actualizarInstitucion("uuid-intruso", cambios, 7));
        assertEquals("El usuario no esta relacionado con la institucion", ex.getMessage());
        verifyNoInteractions(institucionesDao); // ni busca ni guarda: el control de acceso corta antes
    }

    // =====================================================================================
    // 11
    @Test
    @DisplayName("UT-M4-11: suscribirseEmpresa crea la vinculacion usuario-empresa pendiente de aprobacion")
    void suscribirseEmpresa_usuarioValido_creaVinculacion() {
        // ARRANGE
        Instituciones institucion = crearInstitucion(3, "Entel", true);
        Usuarios usuario = new Usuarios(20);
        when(usuariosInstitucionesDao.existsByUsuariosUuid("uuid-nuevo")).thenReturn(false); // aun no esta en ninguna empresa
        when(usuariosBL.userIs("uuid-nuevo", "EMPRESA")).thenReturn(true);                   // tiene rol EMPRESA
        when(institucionesDao.existsByIdinstitucionesAndActivoIsTrue(3)).thenReturn(true);
        when(institucionesDao.findByIdinstitucionesAndActivo(3, true)).thenReturn(institucion);
        when(usuariosDao.findByKcUuid("uuid-nuevo")).thenReturn(usuario);

        // ACT
        InstitucionesDto resultado = institucionBl.suscribirseEmpresa(3, "uuid-nuevo", new SuscribirseInstitucionDto("Jefe de RRHH"));

        // ASSERT
        ArgumentCaptor<Usuariosinstituciones> captor = ArgumentCaptor.forClass(Usuariosinstituciones.class);
        verify(usuariosInstitucionesDao).save(captor.capture());
        Usuariosinstituciones vinculo = captor.getValue();
        assertEquals("Jefe de RRHH", vinculo.getCargo());
        assertFalse(vinculo.getActivo(), "La vinculacion queda pendiente hasta que USEI la apruebe");
        assertSame(institucion, vinculo.getInstitucionesIdinstituciones());
        assertSame(usuario, vinculo.getUsuariosIdusuarios());
        assertEquals(3, resultado.getIdInstituciones());
    }

    // =====================================================================================
    // 12  (REEMPLAZA a "agregarInstitucionConUsuario_creaAmbasEntidades": ese metodo esta COMENTADO
    //      en InstitucionBl.java, no existe. Se prueba otra operacion que crea dos entidades a la vez.)
    @Test
    @DisplayName("UT-M4-12: agregarPasantia crea la pasantia (inactiva) y su relacion con las carreras que existen")
    void agregarPasantia_usuarioVinculado_creaPasantiaYCarreras() {
        // ARRANGE
        Instituciones institucion = crearInstitucion(3, "Entel", true);
        Usuarios usuario = new Usuarios(20);
        // PasantiasDto usa java.sql.Date (no java.util.Date), por eso se escribe el nombre completo de la clase
        java.sql.Date cierre = new java.sql.Date(System.currentTimeMillis() + 15L * 24 * 60 * 60 * 1000); // dentro de 15 dias
        PasantiasDto nueva = new PasantiasDto(null, List.of("Backend"), "Dev Java Jr", "Desc",
                List.of("Java"), List.of("APIs REST"), List.of("Certificado"), cierre, null, false, List.of(1, 99));
        when(usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones("uuid-empresa", 3)).thenReturn(true);
        when(institucionesDao.findByIdinstitucionesAndActivo(3, true)).thenReturn(institucion);
        when(usuariosDao.findByKcUuid("uuid-empresa")).thenReturn(usuario);
        when(pasantiasDao.save(any(Pasantias.class))).thenAnswer(inv -> {
            Pasantias p = inv.getArgument(0);
            p.setIdpasantias(50); // simula el id autogenerado por PostgreSQL
            return p;
        });
        when(carrerasDao.findById(1)).thenReturn(Optional.of(new Carreras(1)));   // carrera que existe
        when(carrerasDao.findById(99)).thenReturn(Optional.empty());              // carrera que NO existe

        // ACT
        PasantiasDto resultado = institucionBl.agregarPasantia("uuid-empresa", 3, nueva);

        // ASSERT
        assertEquals(50, resultado.getIdPasantias());
        ArgumentCaptor<Pasantias> captorPasantia = ArgumentCaptor.forClass(Pasantias.class);
        verify(pasantiasDao).save(captorPasantia.capture());
        assertFalse(captorPasantia.getValue().getActivo(), "Toda pasantia nueva espera moderacion de USEI");
        assertSame(institucion, captorPasantia.getValue().getInstitucionesIdinstituciones());

        ArgumentCaptor<Pasantiascarreras> captorRelacion = ArgumentCaptor.forClass(Pasantiascarreras.class);
        verify(pasantiasCarrerasDao, times(1)).save(captorRelacion.capture()); // solo 1: la carrera 99 se ignora
        assertEquals(1, captorRelacion.getValue().getCarrerasIdcarreras().getIdcarreras());
    }

    // =====================================================================================
    // 13  (BUG-B1: en Java, "==" entre Integer compara REFERENCIAS. Java solo reutiliza objetos Integer
    //      entre -128 y 127; con 1000 se crean dos objetos distintos y "==" daria false.
    //      InstitucionBl usa Objects.equals en eliminarPasantia, asi que debe funcionar.)
    @Test
    @DisplayName("UT-M4-13: eliminarPasantia compara ids > 127 con Objects.equals y no con == (Bug B1)")
    void eliminarPasantia_idMayorA127_comparaConObjectsEquals_BugB1() {
        // ARRANGE: dos objetos Integer DISTINTOS con el mismo valor 1000
        Integer idDeLaUrl = Integer.valueOf(1000);
        Integer idEnBaseDeDatos = Integer.valueOf(1000);
        assertNotSame(idDeLaUrl, idEnBaseDeDatos, "Precondicion: son objetos distintos, '==' daria false");

        Pasantias pasantia = crearPasantia(77, "Dev", new Instituciones(idEnBaseDeDatos));
        when(usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones("uuid-empresa", idDeLaUrl)).thenReturn(true);
        when(pasantiasDao.findById(77)).thenReturn(Optional.of(pasantia));

        // ACT
        Boolean resultado = institucionBl.eliminarPasantia("uuid-empresa", idDeLaUrl, 77);

        // ASSERT: si usara "==", lanzaria "Pasantia no encontrada" en vez de eliminar
        assertTrue(resultado);
        assertFalse(pasantia.getActivo(), "La eliminacion es logica: activo=false");
        verify(pasantiasDao).save(pasantia);
    }

    // =====================================================================================
    // 14  (DOCUMENTA UN DEFECTO REAL: la busqueda NO hace trim. Se reporta en MantisBT.
    //      Cuando el equipo de desarrollo lo corrija, esta prueba fallara y habra que cambiar
    //      la asercion al valor esperado "Banco".)
    @Test
    @DisplayName("UT-M4-14: [DEFECTO] obtenerInstituciones pasa la busqueda al DAO SIN quitar espacios (no hace trim)")
    void obtenerInstituciones_cadenaBusquedaConEspacios_noHaceTrim_DEFECTO() {
        // ARRANGE
        when(institucionesDao.findAllWithCountPasantiasAndNombreContaining(anyString(), any(Pageable.class)))
                .thenReturn(Page.empty());

        // ACT
        institucionBl.obtenerInstituciones(0, 10, "  Banco  ", "nombre", "", null);

        // ASSERT
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(institucionesDao).findAllWithCountPasantiasAndNombreContaining(captor.capture(), any(Pageable.class));
        assertEquals("  Banco  ", captor.getValue(),
                "Comportamiento ACTUAL (defectuoso). Esperado segun la regla de negocio: \"Banco\"");
    }

    // =====================================================================================
    // 15
    @Test
    @DisplayName("UT-M4-15: actualizarInstitucion sobre id inexistente lanza InstitucionNotFoundException y no guarda")
    void actualizarInstitucion_idInexistente_lanzaExcepcionNegocio() {
        // ARRANGE: el usuario si esta vinculado, pero la empresa 404 no existe (o esta inactiva)
        when(usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones("uuid-empresa", 404)).thenReturn(true);
        when(institucionesDao.findByIdinstitucionesAndActivo(404, true)).thenReturn(null);

        // ACT + ASSERT
        assertThrows(InstitucionNotFoundException.class,
                () -> institucionBl.actualizarInstitucion("uuid-empresa", new InstitucionesDto(), 404));
        verify(institucionesDao, never()).save(any());
    }
}
