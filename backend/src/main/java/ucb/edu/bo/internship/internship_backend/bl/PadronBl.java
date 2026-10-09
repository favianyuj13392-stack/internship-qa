package ucb.edu.bo.internship.internship_backend.bl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.CarrerasDao;
import ucb.edu.bo.internship.internship_backend.dao.ImportacionPadronDao;
import ucb.edu.bo.internship.internship_backend.dao.ImportacionPadronErrorDao;
import ucb.edu.bo.internship.internship_backend.dao.PadronEstudianteDao;
import ucb.edu.bo.internship.internship_backend.dto.FilaErrorDto;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionPadronDto;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionResumenDto;
import ucb.edu.bo.internship.internship_backend.dto.PadronEstudianteDto;
import ucb.edu.bo.internship.internship_backend.entity.Carreras;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadron;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadronError;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PadronBl {

    private static final Logger logger = LoggerFactory.getLogger(PadronBl.class);
    private static final Pattern UCB_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\\.)?ucb\\.edu\\.bo$");

    private final PadronEstudianteDao padronEstudianteDao;
    private final ImportacionPadronDao importacionPadronDao;
    private final ImportacionPadronErrorDao importacionPadronErrorDao;
    private final CarrerasDao carrerasDao;

    public PadronBl(PadronEstudianteDao padronEstudianteDao,
                    ImportacionPadronDao importacionPadronDao,
                    ImportacionPadronErrorDao importacionPadronErrorDao,
                    CarrerasDao carrerasDao) {
        this.padronEstudianteDao = padronEstudianteDao;
        this.importacionPadronDao = importacionPadronDao;
        this.importacionPadronErrorDao = importacionPadronErrorDao;
        this.carrerasDao = carrerasDao;
    }

    public byte[] generarPlantillaExcel() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Padron_Estudiantes");

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "CORREO INSTITUCIONAL", "NOMBRES", "APELLIDOS", "CARRERA",
                    "ESTADO", "CODIGO ESTUDIANTE", "ANIO INGRESO"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Fila de ejemplo
            Row sampleRow = sheet.createRow(1);
            sampleRow.createCell(0).setCellValue("estudiante.ejemplo@ucb.edu.bo");
            sampleRow.createCell(1).setCellValue("Juan Carlos");
            sampleRow.createCell(2).setCellValue("Pérez Gómez");
            sampleRow.createCell(3).setCellValue("Ingeniería de Sistemas");
            sampleRow.createCell(4).setCellValue("ACTIVO");
            sampleRow.createCell(5).setCellValue("849302");
            sampleRow.createCell(6).setCellValue(2022);

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar plantilla Excel del padrón", e);
            throw new RuntimeException("Error al generar plantilla Excel", e);
        }
    }

    @Transactional
    public ImportacionResumenDto procesarArchivo(MultipartFile file, String modo, Boolean sincronizacionCompleta, String adminUuid) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo subido está vacío o no es válido");
        }

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "padron.xlsx";
        boolean isCsv = fileName.toLowerCase().endsWith(".csv");
        boolean isXlsx = fileName.toLowerCase().endsWith(".xlsx") || fileName.toLowerCase().endsWith(".xls");

        if (!isCsv && !isXlsx) {
            throw new IllegalArgumentException("Formato no soportado. Debe ser un archivo .xlsx o .csv");
        }

        boolean modoAplicar = "APLICADO".equalsIgnoreCase(modo);
        String sha256 = calcularHashSha256(file);

        List<FilaPadronParsed> filasValidas = new ArrayList<>();
        List<FilaErrorDto> errores = new ArrayList<>();
        Set<String> correosProcesados = new HashSet<>();

        if (isCsv) {
            parsearCsv(file, filasValidas, errores, correosProcesados);
        } else {
            parsearExcel(file, filasValidas, errores, correosProcesados);
        }

        ImportacionResumenDto resumen = new ImportacionResumenDto();
        resumen.setModo(modoAplicar ? "APLICADO" : "SIMULACION");
        resumen.setErrores(errores);
        resumen.setTotalErrores(errores.size());

        int nuevos = 0;
        int actualizados = 0;
        int sinCambios = 0;

        for (FilaPadronParsed fila : filasValidas) {
            Optional<PadronEstudiante> existenteOpt = padronEstudianteDao.findByCorreoIgnoreCase(fila.correo);
            if (existenteOpt.isEmpty()) {
                nuevos++;
            } else {
                PadronEstudiante ext = existenteOpt.get();
                boolean cambio = !Objects.equals(ext.getNombres(), fila.nombres) ||
                        !Objects.equals(ext.getApellidos(), fila.apellidos) ||
                        (ext.getCarrerasIdcarreras() != null && !Objects.equals(ext.getCarrerasIdcarreras().getIdcarreras(), fila.carrera.getIdcarreras())) ||
                        !Objects.equals(ext.getEstado(), fila.estado) ||
                        !Objects.equals(ext.getCodigoEstudiante(), fila.codigoEstudiante) ||
                        !Objects.equals(ext.getAnioIngreso(), fila.anioIngreso);
                if (cambio) {
                    actualizados++;
                } else {
                    sinCambios++;
                }
            }
        }

        resumen.setTotalNuevos(nuevos);
        resumen.setTotalActualizados(actualizados);
        resumen.setTotalSinCambios(sinCambios);

        if (modoAplicar) {
            ImportacionPadron importacion = new ImportacionPadron();
            importacion.setAdminKcUuid(adminUuid);
            importacion.setNombreArchivo(fileName);
            importacion.setHashArchivo(sha256);
            importacion.setFecha(new Date());
            importacion.setModo("APLICADO");
            importacion.setTotalNuevos(nuevos);
            importacion.setTotalActualizados(actualizados);
            importacion.setTotalSinCambios(sinCambios);
            importacion.setTotalErrores(errores.size());

            ImportacionPadron guardada = importacionPadronDao.save(importacion);
            resumen.setIdImportacion(guardada.getIdimportacion());

            for (FilaErrorDto err : errores) {
                ImportacionPadronError errEntity = new ImportacionPadronError(guardada, err.getFila(), err.getCampo(), err.getMotivo());
                importacionPadronErrorDao.save(errEntity);
            }

            for (FilaPadronParsed fila : filasValidas) {
                PadronEstudiante entity = padronEstudianteDao.findByCorreoIgnoreCase(fila.correo)
                        .orElse(new PadronEstudiante());

                entity.setCorreo(fila.correo);
                entity.setNombres(fila.nombres);
                entity.setApellidos(fila.apellidos);
                entity.setCarrerasIdcarreras(fila.carrera);
                entity.setEstado(fila.estado);
                entity.setCodigoEstudiante(fila.codigoEstudiante);
                entity.setAnioIngreso(fila.anioIngreso);
                entity.setImportacionPadron(guardada);

                padronEstudianteDao.save(entity);
            }

            int desactivados = 0;
            if (Boolean.TRUE.equals(sincronizacionCompleta)) {
                List<PadronEstudiante> todosActivos = padronEstudianteDao.findAll();
                for (PadronEstudiante estudiante : todosActivos) {
                    if ("ACTIVO".equalsIgnoreCase(estudiante.getEstado()) && !correosProcesados.contains(estudiante.getCorreo().toLowerCase())) {
                        estudiante.setEstado("INACTIVO");
                        padronEstudianteDao.save(estudiante);
                        desactivados++;
                    }
                }
            }
            guardada.setTotalDesactivados(desactivados);
            importacionPadronDao.save(guardada);
            resumen.setTotalDesactivados(desactivados);
        }

        return resumen;
    }

    private void parsearExcel(MultipartFile file, List<FilaPadronParsed> validas, List<FilaErrorDto> errores, Set<String> correosProcesados) {
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                errores.add(new FilaErrorDto(1, "Archivo", "La hoja Excel está vacía"));
                return;
            }

            rowIterator.next(); // Saltar cabecera
            int numFila = 1;

            while (rowIterator.hasNext()) {
                numFila++;
                Row row = rowIterator.next();
                if (isRowEmpty(row)) continue;

                String correo = getCellValueAsString(row.getCell(0));
                String nombres = getCellValueAsString(row.getCell(1));
                String apellidos = getCellValueAsString(row.getCell(2));
                String carreraStr = getCellValueAsString(row.getCell(3));
                String estadoStr = getCellValueAsString(row.getCell(4));
                String codigoEstudiante = getCellValueAsString(row.getCell(5));
                Integer anioIngreso = getCellValueAsInteger(row.getCell(6));

                validarYAgregarFila(numFila, correo, nombres, apellidos, carreraStr, estadoStr, codigoEstudiante, anioIngreso, validas, errores, correosProcesados);
            }
        } catch (Exception e) {
            logger.error("Error al procesar archivo Excel", e);
            errores.add(new FilaErrorDto(1, "Estructura", "Error al leer el archivo Excel: " + e.getMessage()));
        }
    }

    private void parsearCsv(MultipartFile file, List<FilaPadronParsed> validas, List<FilaErrorDto> errores, Set<String> correosProcesados) {
        try (InputStream is = file.getInputStream();
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            int numFila = 1;
            for (CSVRecord record : csvParser) {
                numFila++;
                String correo = record.size() > 0 ? record.get(0) : "";
                String nombres = record.size() > 1 ? record.get(1) : "";
                String apellidos = record.size() > 2 ? record.get(2) : "";
                String carreraStr = record.size() > 3 ? record.get(3) : "";
                String estadoStr = record.size() > 4 ? record.get(4) : "ACTIVO";
                String codigoEstudiante = record.size() > 5 ? record.get(5) : null;
                Integer anioIngreso = null;
                if (record.size() > 6) {
                    try {
                        anioIngreso = Integer.parseInt(record.get(6).trim());
                    } catch (NumberFormatException ignored) {}
                }

                validarYAgregarFila(numFila, correo, nombres, apellidos, carreraStr, estadoStr, codigoEstudiante, anioIngreso, validas, errores, correosProcesados);
            }
        } catch (Exception e) {
            logger.error("Error al procesar archivo CSV", e);
            errores.add(new FilaErrorDto(1, "Estructura", "Error al leer el archivo CSV: " + e.getMessage()));
        }
    }

    private void validarYAgregarFila(int fila, String correo, String nombres, String apellidos,
                                     String carreraStr, String estadoStr, String codigoEstudiante,
                                     Integer anioIngreso, List<FilaPadronParsed> validas,
                                     List<FilaErrorDto> errores, Set<String> correosProcesados) {
        if (correo == null || correo.isBlank()) {
            errores.add(new FilaErrorDto(fila, "Correo", "El correo institucional es obligatorio"));
            return;
        }

        String correoNorm = correo.trim().toLowerCase();
        if (!UCB_EMAIL_PATTERN.matcher(correoNorm).matches()) {
            errores.add(new FilaErrorDto(fila, "Correo", "El correo debe pertenecer al dominio institucional (@ucb.edu.bo): " + correo));
            return;
        }

        if (correosProcesados.contains(correoNorm)) {
            errores.add(new FilaErrorDto(fila, "Correo", "Correo duplicado dentro del archivo: " + correoNorm));
            return;
        }
        correosProcesados.add(correoNorm);

        if (nombres == null || nombres.isBlank()) {
            errores.add(new FilaErrorDto(fila, "Nombres", "El nombre es obligatorio"));
            return;
        }
        if (apellidos == null || apellidos.isBlank()) {
            errores.add(new FilaErrorDto(fila, "Apellidos", "El apellido es obligatorio"));
            return;
        }

        if (carreraStr == null || carreraStr.isBlank()) {
            errores.add(new FilaErrorDto(fila, "Carrera", "La carrera es obligatoria"));
            return;
        }

        Optional<Carreras> carreraOpt = carrerasDao.findByNombreIgnoreCase(carreraStr.trim());
        if (carreraOpt.isEmpty()) {
            errores.add(new FilaErrorDto(fila, "Carrera", "Carrera no reconocida: '" + carreraStr + "'"));
            return;
        }

        String estado = (estadoStr != null && !estadoStr.isBlank()) ? estadoStr.trim().toUpperCase() : "ACTIVO";
        if (!Set.of("ACTIVO", "INACTIVO", "EGRESADO").contains(estado)) {
            errores.add(new FilaErrorDto(fila, "Estado", "Estado no válido: '" + estadoStr + "'. Permitidos: ACTIVO, INACTIVO, EGRESADO"));
            return;
        }

        FilaPadronParsed p = new FilaPadronParsed();
        p.fila = fila;
        p.correo = correoNorm;
        p.nombres = nombres.trim();
        p.apellidos = apellidos.trim();
        p.carrera = carreraOpt.get();
        p.estado = estado;
        p.codigoEstudiante = codigoEstudiante != null ? codigoEstudiante.trim() : null;
        p.anioIngreso = anioIngreso;

        validas.add(p);
    }

    public byte[] generarReporteErroresExcel(Integer importacionId) {
        ImportacionPadron imp = importacionPadronDao.findById(importacionId)
                .orElseThrow(() -> new NoSuchElementException("Importación no encontrada: " + importacionId));

        List<ImportacionPadronError> errores = importacionPadronErrorDao.findByImportacionPadronOrderByFilaAsc(imp);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Errores_Importacion_" + importacionId);

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FILA");
            headerRow.createCell(1).setCellValue("CAMPO");
            headerRow.createCell(2).setCellValue("MOTIVO DEL ERROR");

            for (int i = 0; i < 3; i++) {
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (ImportacionPadronError err : errores) {
                Row r = sheet.createRow(rowIdx++);
                r.createCell(0).setCellValue(err.getFila() != null ? err.getFila() : 0);
                r.createCell(1).setCellValue(err.getCampo() != null ? err.getCampo() : "");
                r.createCell(2).setCellValue(err.getMotivo() != null ? err.getMotivo() : "");
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar reporte Excel de errores", e);
            throw new RuntimeException("Error al exportar errores", e);
        }
    }

    public Page<PadronEstudianteDto> listarPadron(Integer carreraId, String estado, Integer anioIngreso, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellidos").ascending());
        return padronEstudianteDao.findByFiltros(carreraId, estado, anioIngreso, search, pageable)
                .map(PadronEstudianteDto::fromEntity);
    }

    @Transactional
    public PadronEstudianteDto actualizarEstudiante(Integer id, PadronEstudianteDto dto) {
        PadronEstudiante estudiante = padronEstudianteDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado en el padrón con ID: " + id));

        if (dto.getNombres() != null) estudiante.setNombres(dto.getNombres().trim());
        if (dto.getApellidos() != null) estudiante.setApellidos(dto.getApellidos().trim());
        if (dto.getEstado() != null) estudiante.setEstado(dto.getEstado().trim().toUpperCase());
        if (dto.getCodigoEstudiante() != null) estudiante.setCodigoEstudiante(dto.getCodigoEstudiante().trim());
        if (dto.getAnioIngreso() != null) estudiante.setAnioIngreso(dto.getAnioIngreso());
        if (dto.getIdCarrera() != null) {
            Carreras carrera = carrerasDao.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada"));
            estudiante.setCarrerasIdcarreras(carrera);
        }

        return PadronEstudianteDto.fromEntity(padronEstudianteDao.save(estudiante));
    }

    public Page<ImportacionPadronDto> listarImportaciones(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return importacionPadronDao.findAllByOrderByFechaDesc(pageable)
                .map(ImportacionPadronDto::fromEntity);
    }

    private String calcularHashSha256(MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(file.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "UNKNOWN_HASH";
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK && !getCellValueAsString(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                }
                double d = cell.getNumericCellValue();
                if (d == (long) d) yield String.format("%d", (long) d);
                yield String.valueOf(d);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return Integer.parseInt(cell.getStringCellValue().trim());
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static class FilaPadronParsed {
        int fila;
        String correo;
        String nombres;
        String apellidos;
        Carreras carrera;
        String estado;
        String codigoEstudiante;
        Integer anioIngreso;
    }
}
