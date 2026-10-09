package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@RequestMapping("/api/v1/institucion")
@RestController
public class InstitucionApi {
    private final InstitucionBl institucionBl;
    public InstitucionApi(InstitucionBl institucionBl) {
        this.institucionBl = institucionBl;
    }
    // Obtener todas las instituciones
    @GetMapping
    public ResponseEntity<ResponseDto<Page<InstitucionesDto>>> getInstituciones(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "nombre", required = false) String sort,
            @RequestParam(required = false) String sector
            ) {
        return handleRequest(() -> institucionBl.obtenerInstituciones(page, size, search, sort,"", sector));
    }

    // Obtener todos los id y nombre de las instituciones activas
    @GetMapping("/nombre")
    public ResponseEntity<ResponseDto<List<InstitucionNombreDto>>> getInstitucionesNombre() {
        return handleRequest(institucionBl::obtenerInstitucionesNombre);
    }

    // Obtener una institución por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<InstitucionConPasantiasDto>> getInstitucionById(@PathVariable Integer id) {
        return handleRequest(() -> institucionBl.obtenerInstitucionById(id));
    }

    // Obtener cuatro instituciones relacionadas
    @GetMapping("/{id}/relacionadas")
    public ResponseEntity<ResponseDto<List<InstitucionConPasantiasDto>>> getCuatroInstitucionesRelacionadas(@PathVariable Integer id) {
        return handleRequest(() -> institucionBl.obtenerCuatroInstitucionesRelacionadas(id));
    }

    // Obtener instituciones destacadas
    @GetMapping("/destacadas")
    public ResponseEntity<ResponseDto<Page<InstitucionesConCOUNTPasantiasDto>>> getInstitucionesDestacadas(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return handleRequest(() -> institucionBl.obtenerInstitucionesDestacadas(page, size));
    }

    //Obtener usuario por uuid
    @GetMapping("/usuario/{uuid}")
    public ResponseDto<UsuariosDto> getUsuarioInstitucionByUuid(
            @PathVariable String uuid
    ) {
        ResponseDto<UsuariosDto> response = new ResponseDto<>();
        try {
            response.setResponse(institucionBl.obtenerUsuarioInstitucionByUuid(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PutMapping("/usuario/{uuid}")
    public ResponseDto<Boolean> actualizarUsuario(
            @PathVariable String uuid,
            @RequestBody UsuarioConPersonaEInstitucionDto usuario
    ) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            response.setResponse(institucionBl.actualizarUsuarioInstitucion(uuid, usuario));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/sectores")
    public ResponseEntity<ResponseDto<Set<String>>> obtenerSectores(){
        return handleRequest(institucionBl::obtenerSectores);
    }

    private <T> ResponseEntity<ResponseDto<T>> handleRequest(Supplier<T> supplier) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        try {
            T result = supplier.get();
            responseDto.setResponse(result);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InstitucionNotFoundException e) {
            responseDto.setCode("404");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (UsuarioYaRelacionadoException | InstitucionServiceExcepcion e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseDto.setResponse(null);
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
