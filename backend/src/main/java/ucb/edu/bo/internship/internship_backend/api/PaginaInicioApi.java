package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucb.edu.bo.internship.internship_backend.bl.PaginaInicioBl;
import ucb.edu.bo.internship.internship_backend.dto.RecuentoPaginaInicioDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;

@RequestMapping("/api/v1/inicio")
@RestController
public class PaginaInicioApi {
    private final PaginaInicioBl paginaInicioBl;
    public PaginaInicioApi(PaginaInicioBl paginaInicioBl) {
        this.paginaInicioBl = paginaInicioBl;
    }
    @GetMapping("/recuento")
    public ResponseEntity<ResponseDto<RecuentoPaginaInicioDto>> getRecuento(){
        ResponseDto<RecuentoPaginaInicioDto> responseDto = new ResponseDto<>();
        try {
            RecuentoPaginaInicioDto recuentoPaginaInicioDto = paginaInicioBl.obtenerRecuentoInstituciones();
            responseDto.setResponse(recuentoPaginaInicioDto);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (InstitucionServiceExcepcion e){
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al obtener el recuento");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
