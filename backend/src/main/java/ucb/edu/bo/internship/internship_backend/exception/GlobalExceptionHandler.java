package ucb.edu.bo.internship.internship_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InstitucionNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleInstitucionNotFoundException(InstitucionNotFoundException e) {
        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setCode("404");
        responseDto.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InstitucionServiceExcepcion.class)
    public ResponseEntity<ResponseDto<?>> handleInstitucionServiceException(InstitucionServiceExcepcion e) {
        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setCode("500");
        responseDto.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UsuarioYaRelacionadoException.class)
    public ResponseEntity<ResponseDto<?>> handleUsuarioYaRelacionadoException(UsuarioYaRelacionadoException e) {
        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setCode("500");
        responseDto.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleException(Exception e) {
        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setCode("500");
        responseDto.setErrorMessage("Error interno del servidor"+e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
