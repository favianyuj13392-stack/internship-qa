package ucb.edu.bo.internship.internship_backend.api;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.bl.EstudianteBl;
import ucb.edu.bo.internship.internship_backend.dto.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiante")
public class EstudianteApi {

    private final EstudianteBl estudianteBl;
    public EstudianteApi(EstudianteBl estudianteBl) {
        this.estudianteBl = estudianteBl;
    }

    @GetMapping("/{uuid}")
    public ResponseDto<UsuariosDto> getEstudianteByUuid(
            @PathVariable String uuid
    ) {
        ResponseDto<UsuariosDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerEstudianteByUuid(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PutMapping("/{uuid}")
    public ResponseDto<PersonasDto> updateEstudianteByUuid(
            @PathVariable String uuid,
            @RequestBody PersonasDto personasDto
            ) {
        ResponseDto<PersonasDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.actualizarEstudianteByUuid(uuid, personasDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/{uuid}/curriculum")
    public ResponseDto<CurriculumsDto> addCurriculum(
            @PathVariable String uuid,
            @RequestParam("file") MultipartFile curriculum,
            HttpServletRequest request
    ) {

        // Obtener la URL completa del request
        String fullUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            fullUrl += "?" + queryString;
        }
        //recortar la url a solo la base quitando el endpoint
        fullUrl = fullUrl.substring(0, fullUrl.indexOf("/api/v1/estudiante/"));
        ResponseDto<CurriculumsDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.agregarCurriculum(uuid, curriculum,fullUrl));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    @GetMapping("/public/files/download/{idFile}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String idFile) {
        try {
            return estudianteBl.downloadFile(idFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{uuid}/curriculum/{archivo}")
    public ResponseDto<String> getCurriculum(
            @PathVariable String uuid,
            @PathVariable String archivo
    ) {
        ResponseDto<String> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerCurriculum(uuid, archivo));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/{uuid}/curriculum/{curriculumId}/pasantia/{pasantiaId}")
    public ResponseDto<Boolean> postularPasantia(
            @PathVariable String uuid,
            @PathVariable Integer curriculumId,
            @PathVariable Integer pasantiaId
    ) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.postularPasantia(uuid, curriculumId, pasantiaId));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Obtener todos los curriculum de un estudiante
    @GetMapping("{uuid}/curruculum")
    public ResponseDto<List<CurriculumsDto>> getCurriculum(@PathVariable String uuid) {
        ResponseDto<List<CurriculumsDto>> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerTodosLosCurriculums(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("{uuid}/aplicaciones/pasantias")
    public ResponseDto<List<AplicacionPasantiasInformacionPasantiasDto>> getAplicacionesPasantias(@PathVariable String uuid) {
        ResponseDto<List<AplicacionPasantiasInformacionPasantiasDto>> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerAplicacionesPasantias(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


    //Eliminar un curriculum
    @DeleteMapping("{uuid}/curriculum/{curriculumId}")
    public ResponseDto<CurriculumsDto> deleteCurriculum(@PathVariable String uuid, @PathVariable Integer curriculumId) {
        ResponseDto<CurriculumsDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.eliminarCurriculum(uuid, curriculumId));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Aplicar a una pasantía
    @PostMapping("{uuid}/pasantia/{pasantiaId}/curriculum/{curriculumId}")
    public ResponseDto<AplicacionPasantiasDto> aplicarPasantia(@PathVariable String uuid, @PathVariable Integer pasantiaId, @PathVariable Integer curriculumId) {
        ResponseDto<AplicacionPasantiasDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.aplicarPasantia(uuid, pasantiaId, curriculumId));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}
