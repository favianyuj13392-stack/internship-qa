package ucb.edu.bo.internship.internship_backend.bl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminAnalyticsBl {

    private final PadronEstudianteDao padronEstudianteDao;
    private final EventoAccesoDao eventoAccesoDao;
    private final VistaPasantiaDao vistaPasantiaDao;
    private final PasantiasDao pasantiasDao;
    private final AplicacionPasantiasDao aplicacionPasantiasDao;
    private final CarrerasDao carrerasDao;
    private final EnvioCorreoPasantiaDao envioCorreoPasantiaDao;
    private final NotificacionPasantiaService notificacionPasantiaService;

    public AdminAnalyticsBl(PadronEstudianteDao padronEstudianteDao,
                            EventoAccesoDao eventoAccesoDao,
                            VistaPasantiaDao vistaPasantiaDao,
                            PasantiasDao pasantiasDao,
                            AplicacionPasantiasDao aplicacionPasantiasDao,
                            CarrerasDao carrerasDao,
                            EnvioCorreoPasantiaDao envioCorreoPasantiaDao,
                            NotificacionPasantiaService notificacionPasantiaService) {
        this.padronEstudianteDao = padronEstudianteDao;
        this.eventoAccesoDao = eventoAccesoDao;
        this.vistaPasantiaDao = vistaPasantiaDao;
        this.pasantiasDao = pasantiasDao;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.carrerasDao = carrerasDao;
        this.envioCorreoPasantiaDao = envioCorreoPasantiaDao;
        this.notificacionPasantiaService = notificacionPasantiaService;
    }

    @Transactional(readOnly = true)
    public DashboardKpiDto obtenerDashboardKpi() {
        Long totalEstudiantesPadron = padronEstudianteDao.count();
        Long estudiantesConAcceso = eventoAccesoDao.countEstudiantesDistintosConAcceso();
        Long totalVistasRegistradas = vistaPasantiaDao.count();

        DashboardKpiDto dto = new DashboardKpiDto(totalEstudiantesPadron, estudiantesConAcceso, totalVistasRegistradas);

        List<Carreras> carreras = carrerasDao.findAllByOrderByNombreAsc();
        List<DashboardKpiDto.CarreraKpiDto> carrerasKpi = new ArrayList<>();
        for (Carreras c : carreras) {
            Long totalCarrera = padronEstudianteDao.countPorCarrera(c.getIdcarreras());
            if (totalCarrera != null && totalCarrera > 0) {
                Long accedieronCarrera = eventoAccesoDao.countEstudiantesDistintosConAccesoPorCarrera(c.getIdcarreras());
                carrerasKpi.add(new DashboardKpiDto.CarreraKpiDto(c.getNombre(), totalCarrera, accedieronCarrera));
            }
        }
        dto.setCarreras(carrerasKpi);
        return dto;
    }

    @Transactional(readOnly = true)
    public PasantiaAlcanceDto obtenerAlcancePasantia(Integer idPasantia) {
        Optional<Pasantias> pasantiaOpt = pasantiasDao.findById(idPasantia);
        if (pasantiaOpt.isEmpty()) {
            throw new RuntimeException("Pasantía no encontrada con ID: " + idPasantia);
        }
        Pasantias pasantia = pasantiaOpt.get();

        Long estudiantesUnicos = vistaPasantiaDao.countEstudiantesUnicosPorPasantia(idPasantia);
        Long totalVistas = vistaPasantiaDao.sumTotalVistasPorPasantia(idPasantia);
        Long totalPostulaciones = aplicacionPasantiasDao.countPorPasantia(idPasantia);
        Long vistasCorreo = vistaPasantiaDao.countVistasPorOrigenCorreo(idPasantia);
        Long vistasWeb = vistaPasantiaDao.countVistasPorOrigenWeb(idPasantia);
        Long correosEnviados = envioCorreoPasantiaDao.countCorreosEnviados(idPasantia);
        Long correosFallidos = envioCorreoPasantiaDao.countCorreosFallidos(idPasantia);

        return new PasantiaAlcanceDto(
                idPasantia,
                pasantia.getTitulo(),
                estudiantesUnicos,
                totalVistas,
                totalPostulaciones,
                vistasCorreo,
                vistasWeb,
                correosEnviados,
                correosFallidos
        );
    }

    public void notificarEstudiantesPasantia(Integer idPasantia) {
        notificacionPasantiaService.notificarEstudiantesPorCarrera(idPasantia);
    }

    @Transactional(readOnly = true)
    public EstudianteFichaActividadDto obtenerFichaActividadEstudiante(Integer idPadron) {
        Optional<PadronEstudiante> padronOpt = padronEstudianteDao.findById(idPadron);
        if (padronOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado en el padrón con ID: " + idPadron);
        }
        PadronEstudiante estudiante = padronOpt.get();

        PadronEstudianteDto padronDto = PadronEstudianteDto.fromEntity(estudiante);
        Long totalAccesos = eventoAccesoDao.countByPadronEstudiante(estudiante);

        EstudianteFichaActividadDto ficha = new EstudianteFichaActividadDto(
                padronDto,
                totalAccesos != null ? totalAccesos.intValue() : 0,
                estudiante.getPrimerAcceso(),
                estudiante.getUltimoAcceso()
        );

        List<EventoAcceso> eventos = eventoAccesoDao.findByPadronEstudianteOrderByFechaHoraDesc(estudiante);
        ficha.setHistorialAccesos(eventos.stream().map(EventoAccesoDto::fromEntity).collect(Collectors.toList()));

        List<VistaPasantia> vistas = vistaPasantiaDao.findByPadronEstudianteOrderByUltimaVistaDesc(estudiante);
        ficha.setPasantiasVistas(vistas.stream().map(VistaPasantiaDto::fromEntity).collect(Collectors.toList()));

        if (estudiante.getUsuariosIdusuarios() != null) {
            List<Aplicacionespasantias> apps = aplicacionPasantiasDao.findByUsuariosIdusuarios(estudiante.getUsuariosIdusuarios());
            ficha.setPostulaciones(apps.stream().map(AplicacionPasantiasInformacionPasantiasDto::fromEntity).collect(Collectors.toList()));
        }

        return ficha;
    }

    @Transactional(readOnly = true)
    public byte[] exportarEstudiantesSinAccesoExcel() {
        List<PadronEstudiante> sinAcceso = padronEstudianteDao.findByPrimerAccesoIsNullOrderByApellidosAsc();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Estudiantes Sin Acceso");

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            String[] headers = {"ID", "Código Estudiante", "Apellidos", "Nombres", "Correo Institucional", "Carrera", "Año Ingreso", "Estado"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (PadronEstudiante est : sinAcceso) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(est.getIdpadron() != null ? est.getIdpadron() : 0);
                row.createCell(1).setCellValue(est.getCodigoEstudiante() != null ? est.getCodigoEstudiante() : "");
                row.createCell(2).setCellValue(est.getApellidos() != null ? est.getApellidos() : "");
                row.createCell(3).setCellValue(est.getNombres() != null ? est.getNombres() : "");
                row.createCell(4).setCellValue(est.getCorreo() != null ? est.getCorreo() : "");
                row.createCell(5).setCellValue(est.getCarrerasIdcarreras() != null ? est.getCarrerasIdcarreras().getNombre() : "");
                row.createCell(6).setCellValue(est.getAnioIngreso() != null ? String.valueOf(est.getAnioIngreso()) : "");
                row.createCell(7).setCellValue(est.getEstado() != null ? est.getEstado() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar reporte Excel de estudiantes sin acceso", e);
        }
    }
}
