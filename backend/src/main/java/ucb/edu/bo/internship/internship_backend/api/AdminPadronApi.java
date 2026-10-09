package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.bl.PadronBl;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionPadronDto;
import ucb.edu.bo.internship.internship_backend.dto.ImportacionResumenDto;
import ucb.edu.bo.internship.internship_backend.dto.PadronEstudianteDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

import java.util.NoSuchElementException;

@RequestMapping("/api/v1/admin/{uuid}/estudiantes")
@RestController
public class AdminPadronApi {

    private final PadronBl padronBl;

    public AdminPadronApi(PadronBl padronBl) {
        this.padronBl = padronBl;
    }

    @GetMapping("/plantilla")
    public ResponseEntity<byte[]> descargarPlantilla(@PathVariable String uuid) {
        byte[] excelBytes = padronBl.generarPlantillaExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plantilla_padron_estudiantes.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }

    @PostMapping(value = "/importaciones", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<ImportacionResumenDto>> importarPadron(
            @PathVariable String uuid,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "SIMULACION") String modo,
            @RequestParam(defaultValue = "false") Boolean sincronizacionCompleta
    ) {
        ResponseDto<ImportacionResumenDto> response = new ResponseDto<>();
        try {
            ImportacionResumenDto resumen = padronBl.procesarArchivo(file, modo, sincronizacionCompleta, uuid);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(resumen);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setCode("400");
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage("Error al procesar el archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/importaciones")
    public ResponseEntity<ResponseDto<Page<ImportacionPadronDto>>> listarImportaciones(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ResponseDto<Page<ImportacionPadronDto>> response = new ResponseDto<>();
        try {
            Page<ImportacionPadronDto> result = padronBl.listarImportaciones(page, size);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/importaciones/{id}/errores")
    public ResponseEntity<byte[]> descargarReporteErrores(
            @PathVariable String uuid,
            @PathVariable Integer id
    ) {
        byte[] excelBytes = padronBl.generarReporteErroresExcel(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=errores_importacion_" + id + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }

    @GetMapping("/padron")
    public ResponseEntity<ResponseDto<Page<PadronEstudianteDto>>> listarPadron(
            @PathVariable String uuid,
            @RequestParam(required = false) Integer carreraId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer anioIngreso,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        ResponseDto<Page<PadronEstudianteDto>> response = new ResponseDto<>();
        try {
            Page<PadronEstudianteDto> result = padronBl.listarPadron(carreraId, estado, anioIngreso, search, page, size);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/padron/{id}")
    public ResponseEntity<ResponseDto<PadronEstudianteDto>> actualizarEstudiante(
            @PathVariable String uuid,
            @PathVariable Integer id,
            @RequestBody PadronEstudianteDto dto
    ) {
        ResponseDto<PadronEstudianteDto> response = new ResponseDto<>();
        try {
            PadronEstudianteDto result = padronBl.actualizarEstudiante(id, dto);
            response.setCode("200");
            response.setErrorMessage("");
            response.setResponse(result);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response.setCode("404");
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
