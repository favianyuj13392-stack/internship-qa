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
import ucb.edu.bo.internship.internship_backend.dao.AplicacionPasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.CarrerasDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasCarrerasDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.SeleccionAplicanteDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasConInstitucionYCarrerasDto;
import ucb.edu.bo.internship.internship_backend.entity.Carreras;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Pasantiascarreras;
import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

// Prueba unitaria pura: sin Spring ni base de datos, los DAO son mocks.
@ExtendWith(MockitoExtension.class)
class PasantiaBlTest {

    // Mocks de TODAS las dependencias del constructor de PasantiaBl.
    // Algunas no se usan en estos tests, pero @InjectMocks las necesita para construir el objeto.
    @Mock
    private PasantiasDao pasantiasDao;
    @Mock
    private PasantiasCarrerasDao pasantiasCarrerasDao;
    @Mock
    private UsuariosDao usuariosDao;
    @Mock
    private AplicacionPasantiasDao aplicacionPasantiasDao;
    @Mock
    private SeleccionAplicanteDao seleccionAplicanteDao;
    @Mock
    private CarrerasDao carrerasDao;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasantiaBl pasantiaBl;

    private final Pageable pageable = PageRequest.of(0, 10);

    // Construye una entidad Pasantias completa: el mapeo a DTO falla con NPE si faltan
    // la institucion o las fechas, por eso las dejamos siempre cargadas.
    private Pasantias crearPasantia(Integer id, String titulo) {
        Pasantias pasantia = new Pasantias(id);
        pasantia.setTitulo(titulo);
        pasantia.setFechacierre(new Date());
        pasantia.setFechaingreso(new Date());

        Instituciones institucion = new Instituciones(1);
        institucion.setNombre("Empresa Demo");
        pasantia.setInstitucionesIdinstituciones(institucion);

        Pasantiascarreras relacion = new Pasantiascarreras();
        relacion.setCarrerasIdcarreras(new Carreras(5, "Ingenieria de Sistemas", "desc"));
        pasantia.setPasantiascarrerasList(List.of(relacion));
        return pasantia;
    }

    private Page<Pasantias> paginaConUna(String titulo) {
        return new PageImpl<>(List.of(crearPasantia(1, titulo)), pageable, 1);
    }

    // ---------------------------------------------------------------- Listar todas

    @Test
    @DisplayName("CP-01: obtenerTodasPasantias devuelve la pagina de convocatorias activas")
    void obtenerTodasPasantias_retornaPaginaPasantias() {
        // ARRANGE: el DAO falso devuelve una pagina con 1 pasantia
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), eq(pageable)))
                .thenReturn(paginaConUna("Desarrollador Java"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado = pasantiaBl.obtenerTodasPasantias(pageable);

        // ASSERT: 1 elemento mapeado a DTO y el DAO fue consultado con el mismo pageable
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Desarrollador Java", resultado.getContent().get(0).getTitulo());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), eq(pageable));
    }

    @Test
    @DisplayName("CP-14: obtenerTodasPasantias respeta el ordenamiento del Pageable recibido")
    void obtenerTodasPasantias_verificaOrdenamientoPorFecha() {
        // ARRANGE: Pageable ordenado por fechacierre descendente
        Pageable ordenado = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "fechacierre"));
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), any(Pageable.class)))
                .thenReturn(paginaConUna("Pasantia A"));

        // ACT
        pasantiaBl.obtenerTodasPasantias(ordenado);

        // ASSERT: capturamos el Pageable que realmente llego al DAO y revisamos el orden
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), captor.capture());
        Sort.Order orden = captor.getValue().getSort().getOrderFor("fechacierre");
        assertTrue(orden != null && orden.isDescending());
    }

    @Test
    @DisplayName("CP-15: si el DAO falla, obtenerTodasPasantias propaga la excepcion (no la captura)")
    void obtenerTodasPasantias_daoRetornaError_propagaOEncapsulaExcepcion() {
        // ARRANGE: el DAO simula una caida de persistencia
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), any(Pageable.class)))
                .thenThrow(new IllegalStateException("BD no disponible"));

        // ACT + ASSERT: el codigo actual no tiene try/catch, la excepcion sale tal cual
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> pasantiaBl.obtenerTodasPasantias(pageable));
        assertEquals("BD no disponible", ex.getMessage());
    }

    // ---------------------------------------------------------------- Filtros simples y combinados

    @Test
    @DisplayName("CP-02: obtenerPasantiasPorCarrera consulta por el id de carrera pedido")
    void obtenerPasantiasPorCarrera_carreraValida_filtraCorrectamente() {
        // ARRANGE
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndCarreras(any(Date.class), eq(5), eq(pageable)))
                .thenReturn(paginaConUna("Pasantia Sistemas"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado = pasantiaBl.obtenerPasantiasPorCarrera(5, pageable);

        // ASSERT: la pasantia devuelta trae la carrera 5 en su DTO
        assertEquals(1, resultado.getTotalElements());
        assertEquals(5, resultado.getContent().get(0).getCarreras().get(0).getIdCarreras());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndCarreras(any(Date.class), eq(5), eq(pageable));
    }

    @Test
    @DisplayName("CP-03: obtenerPasantiasPorAreas filtra por la lista de areas")
    void obtenerPasantiasPorAreas_listaAreas_retornaCoincidencias() {
        // ARRANGE
        List<String> areas = List.of("Software", "Datos");
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndAreas(any(Date.class), eq(areas), eq(pageable)))
                .thenReturn(paginaConUna("Analista de Datos"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado = pasantiaBl.obtenerPasantiasPorAreas(areas, pageable);

        // ASSERT
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndAreas(any(Date.class), eq(areas), eq(pageable));
    }

    @Test
    @DisplayName("CP-04: obtenerPasantiasPorAreasYCarrera combina area y carrera en una sola consulta")
    void obtenerPasantiasPorAreasYCarrera_combinaFiltros() {
        // ARRANGE
        List<String> areas = List.of("Software");
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndAreasAndCarreras(
                any(Date.class), eq(areas), eq(5), eq(pageable))).thenReturn(paginaConUna("Dev Backend"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorAreasYCarrera(areas, 5, pageable);

        // ASSERT
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndAreasAndCarreras(
                any(Date.class), eq(areas), eq(5), eq(pageable));
    }

    @Test
    @DisplayName("CP-10: carrera sin pasantias registradas devuelve pagina vacia")
    void obtenerPasantiasPorCarrera_carreraInexistente_retornaVacio() {
        // ARRANGE: la carrera 999 no tiene pasantias
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndCarreras(any(Date.class), eq(999), eq(pageable)))
                .thenReturn(Page.empty(pageable));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado = pasantiaBl.obtenerPasantiasPorCarrera(999, pageable);

        // ASSERT
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());
    }

    @Test
    @DisplayName("CP-11: con areas nulas el router ignora el filtro de area y usa solo la carrera")
    void obtenerPasantiasPorAreas_areasNulas_ignoraFiltroArea() {
        // ARRANGE: solo carrera, areas = null
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndCarreras(any(Date.class), eq(5), eq(pageable)))
                .thenReturn(paginaConUna("Pasantia Sistemas"));

        // ACT: pasamos por el router general (obtenerPasantiasPorFiltros)
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorFiltros(null, null, 5, pageable);

        // ASSERT: se uso la consulta por carrera y NUNCA la de areas
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndCarreras(any(Date.class), eq(5), eq(pageable));
        verify(pasantiasDao, never()).findAllByActivoIsTrueAndFechacierreAfterAndAreas(any(), any(), any());
        verify(pasantiasDao, never()).findAllByActivoIsTrueAndFechacierreAfterAndAreasAndCarreras(any(), any(), any(), any());
    }

    // ---------------------------------------------------------------- Busqueda por texto

    @Test
    @DisplayName("CP-05: busqueda por texto usa la consulta Containing + IgnoreCase")
    void obtenerPasantiasPorTerminoDeBusqueda_textoValido_ejecutaLike() {
        // ARRANGE
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                any(Date.class), eq("Java"), eq(pageable))).thenReturn(paginaConUna("Desarrollador Java"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorTerminoDeBusqueda("Java", pageable);

        // ASSERT
        assertEquals("Desarrollador Java", resultado.getContent().get(0).getTitulo());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                any(Date.class), eq("Java"), eq(pageable));
    }

    @Test
    @DisplayName("CP-06: texto vacio NO se descarta ni se recorta: llega tal cual al DAO (comportamiento actual)")
    void obtenerPasantiasPorTerminoDeBusqueda_textoVacio_comportamientoNeutro() {
        // ARRANGE: el codigo solo trata null como "sin filtro"; "" cuenta como texto
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                any(Date.class), eq(""), eq(pageable))).thenReturn(paginaConUna("Cualquiera"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorFiltros("", null, null, pageable);

        // ASSERT: no explota, y se uso la busqueda por texto (no la de "todas")
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                any(Date.class), eq(""), eq(pageable));
        verify(pasantiasDao, never()).findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), any(Pageable.class));
    }

    @Test
    @DisplayName("CP-12: texto + areas se resuelve en una sola consulta combinada")
    void obtenerPasantiasPorTerminoDeBusquedaAreas_combinaBusquedaYArea() {
        // ARRANGE
        List<String> areas = List.of("Software");
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreas(
                any(Date.class), eq("Java"), eq(areas), eq(pageable))).thenReturn(paginaConUna("Dev Java"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorTerminoDeBusquedaAreas("Java", areas, pageable);

        // ASSERT
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreas(
                any(Date.class), eq("Java"), eq(areas), eq(pageable));
    }

    @Test
    @DisplayName("CP-13: texto + areas + carrera (filtro total) en una sola consulta")
    void obtenerPasantiasPorTerminoDeBusquedaAreasYCarrera_filtroTotal() {
        // ARRANGE
        List<String> areas = List.of("Software");
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
                any(Date.class), eq("Java"), eq(areas), eq(5), eq(pageable))).thenReturn(paginaConUna("Dev Java"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorTerminoDeBusquedaAreasYCarrera("Java", areas, 5, pageable);

        // ASSERT
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
                any(Date.class), eq("Java"), eq(areas), eq(5), eq(pageable));
    }

    // ---------------------------------------------------------------- Buscador general (router)

    @Test
    @DisplayName("CP-07: con los 3 criterios el router elige SOLO la consulta total")
    void obtenerPasantiasPorFiltros_todosLosCriterios_armaQueryPaginada() {
        // ARRANGE
        List<String> areas = List.of("Software");
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
                any(Date.class), eq("Java"), eq(areas), eq(5), eq(pageable))).thenReturn(paginaConUna("Dev Java"));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorFiltros("Java", areas, 5, pageable);

        // ASSERT: una unica llamada al DAO, ninguna otra consulta
        assertEquals(1, resultado.getTotalElements());
        verify(pasantiasDao).findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
                any(Date.class), eq("Java"), eq(areas), eq(5), eq(pageable));
        verifyNoMoreInteractions(pasantiasDao);
    }

    @Test
    @DisplayName("CP-08: sin resultados devuelve pagina vacia sin lanzar excepcion")
    void obtenerPasantiasPorFiltros_sinResultados_retornaPaginaVacia() {
        // ARRANGE
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                any(Date.class), eq("XYZ999"), eq(pageable))).thenReturn(Page.empty(pageable));

        // ACT
        Page<PasantiasConInstitucionYCarrerasDto> resultado =
                pasantiaBl.obtenerPasantiasPorFiltros("XYZ999", null, null, pageable);

        // ASSERT
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getContent().size());
    }

    @Test
    @DisplayName("CP-09: la entidad Pasantias se mapea correctamente a PasantiasConInstitucionYCarrerasDto")
    void obtenerPasantiasPorFiltros_verificaMapeoADto() {
        // ARRANGE: entidad con id, titulo, institucion y carrera conocidos
        when(pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(any(Date.class), eq(pageable)))
                .thenReturn(paginaConUna("Desarrollador Java"));

        // ACT
        PasantiasConInstitucionYCarrerasDto dto =
                pasantiaBl.obtenerPasantiasPorFiltros(null, null, null, pageable).getContent().get(0);

        // ASSERT: cada bloque del DTO sale de la entidad
        assertEquals(1, dto.getIdPasantias());
        assertEquals("Desarrollador Java", dto.getTitulo());
        assertEquals("Empresa Demo", dto.getInstitucion().getNombre());
        assertEquals(1, dto.getCarreras().size());
        assertEquals("Ingenieria de Sistemas", dto.getCarreras().get(0).getNombre());
    }
}
