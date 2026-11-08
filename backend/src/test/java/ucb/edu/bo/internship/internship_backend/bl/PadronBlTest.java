package ucb.edu.bo.internship.internship_backend.bl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.CarrerasDao;
import ucb.edu.bo.internship.internship_backend.dao.ImportacionPadronDao;
import ucb.edu.bo.internship.internship_backend.dao.ImportacionPadronErrorDao;
import ucb.edu.bo.internship.internship_backend.dao.PadronEstudianteDao;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionPadronDto;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionResumenDto;
import ucb.edu.bo.internship.internship_backend.dto.PadronEstudianteDto;
import ucb.edu.bo.internship.internship_backend.entity.Carreras;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadron;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadronError;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Suite de 15 Pruebas Unitarias para PadronBl.
 * Asignado a: Antonio Yujra (Líder de Equipo)
 * Tecnología: JUnit 5 + Mockito (Pruebas unitarias puras sin contexto pesado de Spring)
 */
@ExtendWith(MockitoExtension.class)
class PadronBlTest {

    @Mock
    private PadronEstudianteDao padronEstudianteDao;

    @Mock
    private ImportacionPadronDao importacionPadronDao;

    @Mock
    private ImportacionPadronErrorDao importacionPadronErrorDao;

    @Mock
    private CarrerasDao carrerasDao;

    @InjectMocks
    private PadronBl padronBl;

    // Helper para generar archivos Excel en memoria para los tests
    private MockMultipartFile crearExcelMock(String fileName, String[][] filas) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Padron");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("CORREO");
            header.createCell(1).setCellValue("NOMBRES");
            header.createCell(2).setCellValue("APELLIDOS");
            header.createCell(3).setCellValue("CARRERA");
            header.createCell(4).setCellValue("ESTADO");
            header.createCell(5).setCellValue("CODIGO");
            header.createCell(6).setCellValue("ANIO");

            int r = 1;
            for (String[] fila : filas) {
                Row row = sheet.createRow(r++);
                for (int c = 0; c < fila.length; c++) {
                    row.createCell(c).setCellValue(fila[c]);
                }
            }
            workbook.write(out);
            return new MockMultipartFile("file", fileName,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", out.toByteArray());
        }
    }

    // Helper para generar CSV en memoria
    private MockMultipartFile crearCsvMock(String fileName, String csvContent) {
        return new MockMultipartFile("file", fileName, "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));
    }

    // =========================================================================
    // BLOQUE A: VALIDACIÓN DE ARCHIVOS Y GENERACIÓN DE PLANTILLA
    // =========================================================================

    @Test
    @DisplayName("CP-01: Generar plantilla Excel retorna bytes válidos y no vacíos")
    void generarPlantillaExcel_retornaBytesValidos() {
        // ARRANGE & ACT
        byte[] resultado = padronBl.generarPlantillaExcel();

        // ASSERT
        assertNotNull(resultado, "El array de bytes de la plantilla no debe ser nulo");
        assertTrue(resultado.length > 0, "El tamaño del archivo Excel debe ser mayor a cero");
    }

    @Test
    @DisplayName("CP-02: Rechazar procesamiento si el archivo subido es nulo o vacío")
    void procesarArchivo_archivoVacio_retornaErrorValidacion() {
        // ARRANGE
        MockMultipartFile archivoVacio = new MockMultipartFile("file", "vacio.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);

        // ACT & ASSERT
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                padronBl.procesarArchivo(archivoVacio, "SIMULACION", false, "admin-uuid-1"));

        assertTrue(ex.getMessage().contains("vacío") || ex.getMessage().contains("válido"));
        verifyNoInteractions(padronEstudianteDao, importacionPadronDao);
    }

    @Test
    @DisplayName("CP-03: Rechazar formato de archivo no permitido (ej. PDF o TXT)")
    void procesarArchivo_formatoNoExcel_retornaErrorFormato() {
        // ARRANGE
        MockMultipartFile archivoInvalido = new MockMultipartFile("file", "documento.pdf",
                "application/pdf", "Contenido no binario Excel".getBytes());

        // ACT & ASSERT
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                padronBl.procesarArchivo(archivoInvalido, "SIMULACION", false, "admin-uuid-1"));

        assertTrue(ex.getMessage().contains("Formato no soportado"));
        verifyNoInteractions(padronEstudianteDao, importacionPadronDao);
    }

    // =========================================================================
    // BLOQUE B: MODOS DE PROCESAMIENTO (SIMULACIÓN vs APLICADO)
    // =========================================================================

    @Test
    @DisplayName("CP-04: Modo SIMULACION procesa métricas pero NUNCA guarda en BD (sin efectos secundarios)")
    void procesarArchivo_modoSimulacion_noGuardaEnBaseDeDatos() throws IOException {
        // ARRANGE
        String[][] filas = {
                {"carlos.perez@ucb.edu.bo", "Carlos", "Perez", "Ingeniería de Sistemas", "ACTIVO", "98765", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_simulacion.xlsx", filas);

        Carreras carrera = new Carreras(1, "Ingeniería de Sistemas", "Carrera UCB");
        when(carrerasDao.findByNombreIgnoreCase("Ingeniería de Sistemas")).thenReturn(Optional.of(carrera));
        when(padronEstudianteDao.findByCorreoIgnoreCase("carlos.perez@ucb.edu.bo")).thenReturn(Optional.empty());

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "SIMULACION", false, "admin-uuid-1");

        // ASSERT
        assertNotNull(resumen);
        assertEquals("SIMULACION", resumen.getModo());
        assertEquals(1, resumen.getTotalNuevos());
        assertEquals(0, resumen.getTotalErrores());
        assertNull(resumen.getIdImportacion(), "En simulación no debe generarse ID de importación persistida");

        // VERIFY: ¡Vital para el docente! Demuestra que nunca se llamó a save()
        verify(padronEstudianteDao, never()).save(any());
        verify(importacionPadronDao, never()).save(any());
    }

    @Test
    @DisplayName("CP-05: Modo APLICADO persiste la auditoría y los estudiantes válidos")
    void procesarArchivo_modoAplicado_guardaRegistrosValidos() throws IOException {
        // ARRANGE
        String[][] filas = {
                {"carlos.perez@ucb.edu.bo", "Carlos", "Perez", "Ingeniería de Sistemas", "ACTIVO", "98765", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_aplicado.xlsx", filas);

        Carreras carrera = new Carreras(1, "Ingeniería de Sistemas", "Carrera UCB");
        when(carrerasDao.findByNombreIgnoreCase("Ingeniería de Sistemas")).thenReturn(Optional.of(carrera));
        when(padronEstudianteDao.findByCorreoIgnoreCase("carlos.perez@ucb.edu.bo")).thenReturn(Optional.empty());

        ImportacionPadron impPersistida = new ImportacionPadron();
        impPersistida.setIdimportacion(101);
        when(importacionPadronDao.save(any(ImportacionPadron.class))).thenReturn(impPersistida);

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "APLICADO", false, "admin-uuid-1");

        // ASSERT
        assertNotNull(resumen);
        assertEquals("APLICADO", resumen.getModo());
        assertEquals(1, resumen.getTotalNuevos());
        assertEquals(101, resumen.getIdImportacion());

        // VERIFY: Comprobar que se invocó a save()
        verify(padronEstudianteDao, times(1)).save(any(PadronEstudiante.class));
        verify(importacionPadronDao, atLeastOnce()).save(any(ImportacionPadron.class));
    }

    @Test
    @DisplayName("CP-06: Sincronización completa desactiva estudiantes activos que no figuran en el archivo")
    void procesarArchivo_sincronizacionCompleta_desactivaAusentes() throws IOException {
        // ARRANGE
        String[][] filas = {
                {"presente@ucb.edu.bo", "Presente", "Alumno", "Ingeniería de Sistemas", "ACTIVO", "11111", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_sync.xlsx", filas);

        Carreras carrera = new Carreras(1, "Ingeniería de Sistemas", "Carrera UCB");
        when(carrerasDao.findByNombreIgnoreCase("Ingeniería de Sistemas")).thenReturn(Optional.of(carrera));
        when(padronEstudianteDao.findByCorreoIgnoreCase("presente@ucb.edu.bo")).thenReturn(Optional.empty());

        ImportacionPadron impPersistida = new ImportacionPadron();
        impPersistida.setIdimportacion(102);
        when(importacionPadronDao.save(any(ImportacionPadron.class))).thenReturn(impPersistida);

        // Estudiante que ya estaba en BD pero NO vino en el nuevo archivo
        PadronEstudiante estudianteAusente = new PadronEstudiante();
        estudianteAusente.setCorreo("ausente@ucb.edu.bo");
        estudianteAusente.setEstado("ACTIVO");
        when(padronEstudianteDao.findAll()).thenReturn(List.of(estudianteAusente));

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "APLICADO", true, "admin-uuid-1");

        // ASSERT
        assertEquals(1, resumen.getTotalDesactivados());
        assertEquals("INACTIVO", estudianteAusente.getEstado(), "El estudiante ausente debe pasar a INACTIVO");
        verify(padronEstudianteDao, atLeast(2)).save(any(PadronEstudiante.class));
    }

    // =========================================================================
    // BLOQUE C: REGLAS DE NEGOCIO Y VALIDACIÓN DE FILAS (DOMINIO, ERRORES, CSV)
    // =========================================================================

    @Test
    @DisplayName("CP-07: Rechazar fila con correo que no pertenezca al dominio institucional @ucb.edu.bo")
    void procesarArchivo_correoNoInstitucional_registraError() throws IOException {
        // ARRANGE: Correo externo (@gmail.com)
        String[][] filas = {
                {"estudiante.trucho@gmail.com", "Pedro", "Gomez", "Ingeniería de Sistemas", "ACTIVO", "22222", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_externo.xlsx", filas);

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "SIMULACION", false, "admin-uuid-1");

        // ASSERT
        assertEquals(1, resumen.getTotalErrores());
        assertTrue(resumen.getErrores().get(0).getMotivo().contains("@ucb.edu.bo"));
        verify(padronEstudianteDao, never()).save(any());
    }

    @Test
    @DisplayName("CP-08: Rechazar fila con carrera que no existe en el catálogo de CarrerasDao")
    void procesarArchivo_carreraInexistente_registraError() throws IOException {
        // ARRANGE: Carrera inexistente
        String[][] filas = {
                {"maria.lopez@ucb.edu.bo", "Maria", "Lopez", "Carrera Inexistente", "ACTIVO", "33333", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_carrera_erronea.xlsx", filas);
        when(carrerasDao.findByNombreIgnoreCase("Carrera Inexistente")).thenReturn(Optional.empty());

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "SIMULACION", false, "admin-uuid-1");

        // ASSERT
        assertEquals(1, resumen.getTotalErrores());
        assertTrue(resumen.getErrores().get(0).getMotivo().contains("Carrera no reconocida"));
    }

    @Test
    @DisplayName("CP-09: Detectar y registrar error si el archivo contiene correos duplicados")
    void procesarArchivo_correoDuplicadoEnMismoArchivo_registraError() throws IOException {
        // ARRANGE: Dos filas con el mismo correo institucional
        String[][] filas = {
                {"mismo.correo@ucb.edu.bo", "Ana", "Rios", "Ingeniería de Sistemas", "ACTIVO", "44441", "2023"},
                {"mismo.correo@ucb.edu.bo", "Ana María", "Rios", "Ingeniería de Sistemas", "ACTIVO", "44442", "2023"}
        };
        MockMultipartFile excel = crearExcelMock("padron_duplicado.xlsx", filas);
        Carreras carrera = new Carreras(1, "Ingeniería de Sistemas", "Carrera UCB");
        when(carrerasDao.findByNombreIgnoreCase("Ingeniería de Sistemas")).thenReturn(Optional.of(carrera));

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(excel, "SIMULACION", false, "admin-uuid-1");

        // ASSERT
        assertEquals(1, resumen.getTotalErrores(), "Debe marcar la segunda fila como duplicada");
        assertTrue(resumen.getErrores().get(0).getMotivo().contains("Correo duplicado"));
    }

    @Test
    @DisplayName("CP-10: Procesar correctamente archivo con formato CSV")
    void procesarArchivo_formatoCsvValido_procesaExitosamente() {
        // ARRANGE: CSV con cabecera y 1 fila
        String csv = "CORREO,NOMBRES,APELLIDOS,CARRERA,ESTADO,CODIGO,ANIO\n" +
                "estudiante.csv@ucb.edu.bo,Lucia,Mendez,Ingeniería de Sistemas,ACTIVO,55555,2024\n";
        MockMultipartFile csvFile = crearCsvMock("padron.csv", csv);

        Carreras carrera = new Carreras(1, "Ingeniería de Sistemas", "Carrera UCB");
        when(carrerasDao.findByNombreIgnoreCase("Ingeniería de Sistemas")).thenReturn(Optional.of(carrera));
        when(padronEstudianteDao.findByCorreoIgnoreCase("estudiante.csv@ucb.edu.bo")).thenReturn(Optional.empty());

        // ACT
        ImportacionResumenDto resumen = padronBl.procesarArchivo(csvFile, "SIMULACION", false, "admin-uuid-1");

        // ASSERT
        assertEquals(0, resumen.getTotalErrores());
        assertEquals(1, resumen.getTotalNuevos());
    }

    // =========================================================================
    // BLOQUE D: CONSULTAS PAGINADAS, FILTROS Y ACTUALIZACIÓN
    // =========================================================================

    @Test
    @DisplayName("CP-11: Listar padrón sin filtros retorna la página de estudiantes mapeada a DTO")
    void listarPadron_sinFiltros_retornaPaginaCompleta() {
        // ARRANGE
        PadronEstudiante entity = new PadronEstudiante();
        entity.setIdpadron(1);
        entity.setCorreo("juan@ucb.edu.bo");
        entity.setNombres("Juan");
        entity.setApellidos("Perez");

        Page<PadronEstudiante> pageMock = new PageImpl<>(List.of(entity));
        when(padronEstudianteDao.findByFiltros(isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(pageMock);

        // ACT
        Page<PadronEstudianteDto> resultado = padronBl.listarPadron(null, null, null, null, 0, 10);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("juan@ucb.edu.bo", resultado.getContent().get(0).getCorreo());
    }

    @Test
    @DisplayName("CP-12: Listar padrón delegando los filtros de búsqueda a findByFiltros")
    void listarPadron_filtradoPorCarreraYEstado_invocaDaoConParametros() {
        // ARRANGE
        Page<PadronEstudiante> pageVacia = new PageImpl<>(Collections.emptyList());
        when(padronEstudianteDao.findByFiltros(eq(2), eq("ACTIVO"), isNull(), eq("Perez"), any(Pageable.class)))
                .thenReturn(pageVacia);

        // ACT
        Page<PadronEstudianteDto> resultado = padronBl.listarPadron(2, "ACTIVO", null, "Perez", 0, 10);

        // ASSERT
        assertNotNull(resultado);
        verify(padronEstudianteDao).findByFiltros(eq(2), eq("ACTIVO"), isNull(), eq("Perez"), any(Pageable.class));
    }

    @Test
    @DisplayName("CP-13: Actualizar estudiante existente modifica campos y persiste en BD")
    void actualizarEstudiante_idExistente_actualizaDatosExitosamente() {
        // ARRANGE
        PadronEstudiante existente = new PadronEstudiante();
        existente.setIdpadron(5);
        existente.setNombres("Nombre Anterior");

        when(padronEstudianteDao.findById(5)).thenReturn(Optional.of(existente));
        when(padronEstudianteDao.save(any(PadronEstudiante.class))).thenAnswer(i -> i.getArgument(0));

        PadronEstudianteDto dtoModificado = new PadronEstudianteDto();
        dtoModificado.setNombres("Nombre Nuevo");
        dtoModificado.setApellidos("Apellido Nuevo");

        // ACT
        PadronEstudianteDto resultado = padronBl.actualizarEstudiante(5, dtoModificado);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Nombre Nuevo", resultado.getNombres());
        assertEquals("Apellido Nuevo", resultado.getApellidos());
        verify(padronEstudianteDao).save(existente);
    }

    @Test
    @DisplayName("CP-14: Actualizar estudiante con ID inexistente lanza NoSuchElementException")
    void actualizarEstudiante_idInexistente_lanzaExcepcion() {
        // ARRANGE
        when(padronEstudianteDao.findById(999)).thenReturn(Optional.empty());

        PadronEstudianteDto dto = new PadronEstudianteDto();

        // ACT & ASSERT
        assertThrows(NoSuchElementException.class, () -> padronBl.actualizarEstudiante(999, dto));
        verify(padronEstudianteDao, never()).save(any());
    }

    // =========================================================================
    // BLOQUE E: REPORTES DE ERRORES E HISTORIAL DE IMPORTACIONES
    // =========================================================================

    @Test
    @DisplayName("CP-15: Generar reporte de errores Excel devuelve binario con las filas fallidas")
    void generarReporteErroresExcel_conErrores_generaArchivoBytes() {
        // ARRANGE
        ImportacionPadron imp = new ImportacionPadron();
        imp.setIdimportacion(50);
        when(importacionPadronDao.findById(50)).thenReturn(Optional.of(imp));

        ImportacionPadronError error1 = new ImportacionPadronError(imp, 2, "Correo", "Formato no válido");
        ImportacionPadronError error2 = new ImportacionPadronError(imp, 5, "Carrera", "Carrera no existe");
        when(importacionPadronErrorDao.findByImportacionPadronOrderByFilaAsc(imp)).thenReturn(List.of(error1, error2));

        // ACT
        byte[] reporteExcel = padronBl.generarReporteErroresExcel(50);

        // ASSERT
        assertNotNull(reporteExcel, "El reporte generado no debe ser nulo");
        assertTrue(reporteExcel.length > 0, "El reporte debe contener bytes válidos de Excel");
        verify(importacionPadronErrorDao).findByImportacionPadronOrderByFilaAsc(imp);
    }
}
