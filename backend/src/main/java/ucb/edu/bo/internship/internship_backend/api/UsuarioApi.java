package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;

import ucb.edu.bo.internship.internship_backend.bl.PasantiaBl;
import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.List;
import java.util.function.Supplier;


@RequestMapping("/api/v1/usuario")
@RestController
public class UsuarioApi {
    private final InstitucionBl institucionBl;
    private final UsuariosBL usuariosBL;
    private final PasantiaBl pasantiaBl;
    public UsuarioApi(InstitucionBl institucionBl, UsuariosBL usuariosBL,PasantiaBl pasantiaBl) {
        this.institucionBl = institucionBl;
        this.usuariosBL = usuariosBL;
        this.pasantiaBl = pasantiaBl;
    }

    @GetMapping("/{uuid}/existencia")
    public ResponseDto<Boolean> usuarioExiste(@PathVariable String uuid){
        ResponseDto<Boolean> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.obtenerUsuarioExistencia(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


    @GetMapping("/{uuid}/empresa/aprobado")
    public ResponseDto<Boolean> usuarioAprobado(@PathVariable String uuid){
        ResponseDto<Boolean> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.obtenerUsuarioAprobado(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{uuid}/institucion")
    public ResponseDto<InstitucionesDto> obtenerInstitucionPorUsuario(@PathVariable String uuid){
        ResponseDto<InstitucionesDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.obtenerInstitucionPorUsuario(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


    //Agregar una institucion
    @PostMapping("/{uuid}/institucion")
    public ResponseEntity<ResponseDto<InstitucionesDto>> agregarInstitucion(@RequestBody InstitucionesDto institucionesDto, @PathVariable String uuid) {
        return handleRequest(() -> institucionBl.agregarInstitucion(institucionesDto));
    }
    //Suscribirse a una institucion
    @PostMapping("/{uuid}/institucion/{id}")
    public ResponseEntity<ResponseDto<InstitucionesDto>> suscribirseEmpresa(@PathVariable Integer id, @PathVariable String uuid, @RequestBody SuscribirseInstitucionDto suscribirseInstitucionDto) {
        return handleRequest(() -> institucionBl.suscribirseEmpresa(id, uuid, suscribirseInstitucionDto));
    }
    //Actualizar una institucion
    @PutMapping("/{uuid}/institucion/{id}")
    public ResponseEntity<ResponseDto<InstitucionesDto>> actualizarInstitucion(@RequestBody InstitucionesDto institucionesDto, @PathVariable Integer id, @PathVariable String uuid) {
        return handleRequest(() -> institucionBl.actualizarInstitucion(uuid, institucionesDto, id));
    }

    @GetMapping("/{uuid}/institucion/{institucionId}/pasantias")
    public ResponseEntity<ResponseDto<Page<PasantiasDto>>> obtenerPasantiasPorInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idpasantias", required = false) String sort
    ){
        return handleRequest(() -> institucionBl.obtenerPasantiasPorInstitucion(uuid, institucionId, page, size, search, sort));
    }

    @DeleteMapping("/{uuid}/institucion/{institucionId}/pasantias/{pasantiaId}")
    public ResponseEntity<ResponseDto<Boolean>> eliminarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @PathVariable Integer pasantiaId
    ){
        return handleRequest(() -> institucionBl.eliminarPasantia(uuid, institucionId, pasantiaId));
    }

    @PostMapping("/{uuid}/institucion/{institucionId}/pasantia")
    public ResponseEntity<ResponseDto<PasantiasDto>> agregarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @RequestBody PasantiasDto pasantiasDto
    ){
        return handleRequest(() -> institucionBl.agregarPasantia(uuid, institucionId, pasantiasDto));
    }

    @PutMapping("/{uuid}/institucion/{institucionId}/pasantia/{pasantiaId}")
    public ResponseEntity<ResponseDto<PasantiasDto>> actualizarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @PathVariable Integer pasantiaId,
            @RequestBody PasantiasDto pasantiasDto
    ){
        return handleRequest(() -> institucionBl.actualizarPasantia(uuid, institucionId, pasantiaId, pasantiasDto));
    }

    @GetMapping("/{uuid}/institucion/{institucionId}/pasantia/{pasantiaId}/aplicantes")
    public ResponseEntity<ResponseDto<Page<UsuariosConPersonaYCarreraDto>>> obtenerAplicantes(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @PathVariable Integer pasantiaId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idaplicantes", required = false) String sort
    ){
        return handleRequest(() -> institucionBl.obtenerAplicantes(uuid, institucionId, pasantiaId, page, size, search, sort));
    }

    @PostMapping("/{uuid}/institucion/{institucionId}/pasantia/{pasantiaId}/aplicantes/{aplicanteId}")
    public ResponseEntity<ResponseDto<Boolean>> aceptarAplicante(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @PathVariable Integer pasantiaId,
            @PathVariable Integer aplicanteId
    ){
        return handleRequest(() -> institucionBl.aceptarAplicante(uuid, institucionId, pasantiaId, aplicanteId));
    }

    @PostMapping("/{uuid}/institucion/{institucionId}/pasantia/{pasantiaId}/noaplicante")
    public ResponseEntity<ResponseDto<Boolean>> pasantiaSinAplicante(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @PathVariable Integer pasantiaId
    ){
        return handleRequest(() -> institucionBl.pasantiaSinAplicante(uuid, institucionId, pasantiaId));
    }

    //Metodo para manejar las respuestas
    private <T> ResponseEntity<ResponseDto<T>> handleRequest(Supplier<T> supplier) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        try {
            T result = supplier.get();
            responseDto.setResponse(result);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (UsuarioYaRelacionadoException | InstitucionServiceExcepcion e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InstitucionNotFoundException e) {
            responseDto.setCode("404");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error interno del servidor"+e.getMessage());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/persona")
    public ResponseDto<PersonasDto> agregarPersona(
            @RequestBody PersonasDto personasDto
            ){
        ResponseDto<PersonasDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarPersona(personasDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

//    @PostMapping()
//    public ResponseDto<UsuariosDto> agregarUsuario(
//            @RequestBody UsuariosDto usuariosDto
//    ){
//        ResponseDto<UsuariosDto> response = new ResponseDto<>();
//        try{
//            response.setResponse(usuariosBL.agregarUsuario(usuariosDto));
//            response.setCode("200");
//            response.setErrorMessage("");
//        }catch (Exception e){
//            response.setCode("500");
//            response.setErrorMessage(e.getMessage());
//        }
//        return response;
//    }

    @PostMapping("/{uuid}/institucion/{institucionId}")
    public ResponseDto<UsuariosInstitucionesDto> agregarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @RequestParam String cargo
    ){
        ResponseDto<UsuariosInstitucionesDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuarioInstitucion(uuid, institucionId, cargo));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping()
    public ResponseDto<UsuarioRegistroCompletoDto> agregarUsuarioCompleto(
            @RequestBody UsuarioRegistroCompletoDto usuarioRegistroCompletoDto
    ){
        ResponseDto<UsuarioRegistroCompletoDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuarioCompleto(usuarioRegistroCompletoDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    @PostMapping("/institucion")
    public ResponseDto<UsuarioRegistroCompletoDto> agregarUsuarioCompletoInstitucion(
            @RequestBody UsuarioRegistroCompletoDto usuarioRegistroCompletoDto
    ){
        ResponseDto<UsuarioRegistroCompletoDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuarioCompletoInstitucion(usuarioRegistroCompletoDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Obtener detalles como: Aplicantes, Institucion y pasantía
    @GetMapping("{uuid}/pasantia/{idPasantia}/detalle")
    public ResponseDto<PasantiaConPostulantesDto> obtenerPasantiaDetalle(
            @PathVariable String uuid,
            @PathVariable Integer idPasantia
    ){
        ResponseDto<PasantiaConPostulantesDto> response = new ResponseDto<>();
        try{
            response.setResponse(pasantiaBl.obtenerPasantiaDetalle(uuid, idPasantia));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Aceptar solicitud de pasantía
    @PutMapping("{uuid}/pasantia/{idPasantia}/aplicacion/{idAplicacionPasantia}/aceptar")
    public ResponseDto<SeleccionAplicanteDto> aceptarPasantia(@PathVariable String uuid, @PathVariable Integer idPasantia, @PathVariable Integer idAplicacionPasantia, @RequestBody AceptarSolicitudRequestDto aceptarSolicitudRequestDto ){
        ResponseDto<SeleccionAplicanteDto> response = new ResponseDto<>();
        try{
            response.setResponse(pasantiaBl.aceptarAplicacionPasantia(uuid, idPasantia, idAplicacionPasantia,aceptarSolicitudRequestDto.getComentarios()));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Rechazar solicitud de pasantía
    @DeleteMapping("{uuid}/pasantia/{idPasantia}/aplicacion/{idAplicacionPasantia}/rechazar")
    public ResponseDto<AplicacionPasantiasDto> rechazarPasantia(@PathVariable String uuid, @PathVariable Integer idPasantia, @PathVariable Integer idAplicacionPasantia){
        ResponseDto<AplicacionPasantiasDto> response = new ResponseDto<>();
        try{
            response.setResponse(pasantiaBl.rechazarAplicacionPasantia(uuid, idPasantia, idAplicacionPasantia));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    //Marcar pasantía como finalizada y sin aplicantes seleccionados
    @PutMapping("{uuid}/pasantia/{idPasantia}/finalizar/sin-seleccion")
    public ResponseDto<SeleccionAplicanteDto> finalizarPasantiaSinSeleccion(@PathVariable String uuid, @PathVariable Integer idPasantia){
        ResponseDto<SeleccionAplicanteDto> response = new ResponseDto<>();
        try{
            response.setResponse(pasantiaBl.finalizarPasantiaSinSeleccion(uuid, idPasantia));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
