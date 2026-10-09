package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.AdminAnalyticsBl;
import ucb.edu.bo.internship.internship_backend.dto.DashboardKpiDto;
import ucb.edu.bo.internship.internship_backend.dto.EstudianteFichaActividadDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiaAlcanceDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

@RestController
@RequestMapping("/api/v1/admin/{uuid}")
public class AdminAnalyticsApi {

    private final AdminAnalyticsBl adminAnalyticsBl;

    public AdminAnalyticsApi(AdminAnalyticsBl adminAnalyticsBl) {
        this.adminAnalyticsBl = adminAnalyticsBl;
    }

    @GetMapping({"/dashboard/estudiantes/kpi", "/analytics/dashboard"})
    public ResponseEntity<ResponseDto<DashboardKpiDto>> obtenerDashboardKpis(@PathVariable String uuid) {
        ResponseDto<DashboardKpiDto> response = new ResponseDto<>();
        try {
            DashboardKpiDto kpis = adminAnalyticsBl.obtenerDashboardKpi();
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(kpis);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage("Error al obtener KPIs del dashboard: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping({"/pasantia/{idPasantia}/alcance", "/analytics/pasantia/{idPasantia}/alcance"})
    public ResponseEntity<ResponseDto<PasantiaAlcanceDto>> obtenerAlcancePasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantia
    ) {
        ResponseDto<PasantiaAlcanceDto> response = new ResponseDto<>();
        try {
            PasantiaAlcanceDto alcance = adminAnalyticsBl.obtenerAlcancePasantia(idPasantia);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(alcance);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("404");
            response.setErrorMessage("Error al obtener alcance de pasantía: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping({"/estudiantes/{idPadron}/actividad", "/analytics/estudiantes/{idPadron}/actividad"})
    public ResponseEntity<ResponseDto<EstudianteFichaActividadDto>> obtenerFichaActividad(
            @PathVariable String uuid,
            @PathVariable Integer idPadron
    ) {
        ResponseDto<EstudianteFichaActividadDto> response = new ResponseDto<>();
        try {
            EstudianteFichaActividadDto ficha = adminAnalyticsBl.obtenerFichaActividadEstudiante(idPadron);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(ficha);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("404");
            response.setErrorMessage("Error al obtener ficha de actividad del estudiante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping({"/estudiantes/sin-acceso/excel", "/analytics/estudiantes/sin-acceso/excel"})
    public ResponseEntity<byte[]> exportarSinAccesoExcel(@PathVariable String uuid) {
        byte[] excelBytes = adminAnalyticsBl.exportarEstudiantesSinAccesoExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estudiantes_sin_acceso_usei.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }

    @PostMapping({"/pasantia/{idPasantia}/notificar", "/analytics/pasantia/{idPasantia}/notificar"})
    public ResponseEntity<ResponseDto<String>> notificarEstudiantesPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantia
    ) {
        ResponseDto<String> response = new ResponseDto<>();
        try {
            adminAnalyticsBl.notificarEstudiantesPasantia(idPasantia);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse("Proceso de notificación iniciado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage("Error al iniciar notificación de pasantía: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
