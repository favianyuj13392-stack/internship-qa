package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.AdministradorBl;
import ucb.edu.bo.internship.internship_backend.bl.EstudianteBl;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.bl.PasantiaBl;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.List;
import java.util.function.Supplier;

@RequestMapping("/api/v1/admin/{uuid}")
@RestController
public class AdministradorApi {
    private final AdministradorBl administradorBl;
    private final InstitucionBl institucionBl;
    private final PasantiaBl pasantiaBl;
    private final EstudianteBl estudianteBl;
    public AdministradorApi(AdministradorBl administradorBl, InstitucionBl institucionBl, PasantiaBl pasantiaBl, EstudianteBl estudianteBl) {
        this.administradorBl = administradorBl;
        this.pasantiaBl = pasantiaBl;
        this.institucionBl = institucionBl;
        this.estudianteBl = estudianteBl;
    }
    //Obtener todas las instituciones
    @GetMapping("/instituciones")
    public ResponseEntity<ResponseDto<Page<InstitucionesDto>>> getInstituciones(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "nombre", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String active,
            @RequestParam(required = false) String sector
            ) {
        return handleRequest(() -> institucionBl.obtenerInstituciones(page, size, search, sort,active, sector));
    }
    // Obtener una institución por id
    @GetMapping("/instituciones/{id}")
    public ResponseEntity<ResponseDto<InstitucionConPasantiasDto>> getInstitucionById(@PathVariable String uuid,@PathVariable Integer id) {
        return handleRequest(() -> institucionBl.obtenerInstitucionById(id));
    }
    @PutMapping("/instituciones/{idInstituciones}/estado")
    public ResponseEntity<ResponseDto<InstitucionesDto>> cambiarEstadoInstitucion(@PathVariable Integer idInstituciones, @RequestBody ChangeEstadoDto estado, @PathVariable String uuid){
        return handleRequest(() -> administradorBl.cambiarEstadoInstitucion(idInstituciones, estado.getEstado()));
    }
    //Obtener todas las solicitudes de suscripción a instituciones
    @GetMapping("/instituciones/usuarios/solicitudes")
    public ResponseEntity<ResponseDto<List<UsuarioConCorreoYNombreCompletoYFotoDto>>> getUsuarios(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "", required = false) String search
    ) {
        return handleRequest(() -> administradorBl.obtenerSuscripcionAInstituciones(search));
    }
    //Obtener todas las solicitudes de suscripción a una institución
    @GetMapping("/instituciones/{idInstitucion}/usuarios/solicitudes")
    public ResponseEntity<ResponseDto<List<UsuarioConCorreoYNombreCompletoYFotoDto>>> getSolicitudesUsuariosByIdInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstitucion
    ) {
        return handleRequest(() -> administradorBl.obtenerSuscripcionAInstitucion(idInstitucion));
    }
    //Obtener toda la informacion de una solicitud
    @GetMapping("/institucion/{idInstitucion}/solicitud/{idSolicitud}/usuario/{idUsuario}")
    public ResponseEntity<ResponseDto<SolicitudIndormacionDto>> getUsuario(
            @PathVariable String uuid,
            @PathVariable Integer idInstitucion,
            @PathVariable Integer idSolicitud,
            @PathVariable Integer idUsuario
    ) {
        return handleRequest(() -> administradorBl.obtenerTodaLaInformacionDeSolicitud(idInstitucion, idSolicitud, idUsuario));
    }
    @GetMapping("/instituciones/{idInstituciones}/usuarios")
    public ResponseEntity<ResponseDto<List<UsuarioConCorreoYNombreCompletoYFotoDto>>> getUsuariosInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones
    ) {
        return handleRequest(() -> administradorBl.obtenerUsuariosInstitucion(idInstituciones));
    }
    @PutMapping("/instituciones/{idInstituciones}/solicitud/{idsolicitud}/usuarios/{idUsuarios}/aceptar")
    public ResponseEntity<ResponseDto<UsuarioConCorreoYNombreCompletoYFotoDto>> relacionarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones,
            @PathVariable Integer idsolicitud,
            @PathVariable Integer idUsuarios
    ) {
        return handleRequest(() -> administradorBl.relacionarUsuarioInstitucion(idInstituciones, idsolicitud, idUsuarios, true));
    }
    @DeleteMapping("/instituciones/{idInstituciones}/solicitud/{idsolicitud}/usuarios/{idUsuarios}/aceptar")
    public ResponseEntity<ResponseDto<UsuarioConCorreoYNombreCompletoYFotoDto>> desrelacionarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones,
            @PathVariable Integer idsolicitud,
            @PathVariable Integer idUsuarios
    ) {
        return handleRequest(() -> administradorBl.relacionarUsuarioInstitucion(idInstituciones,idsolicitud, idUsuarios, false));
    }



    @GetMapping("/pasantia")
    public ResponseEntity<ResponseDto<Page<PasantiaConNombreYLogoEmpresaDto>>> getPasantias(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idpasantias", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String active
    ) {
        return handleRequest(() -> pasantiaBl.obtenerPasantias(page, size, search, sort,active));
    }
    //Obtener todas las pasantías marcadas como sin aplicantes
    @GetMapping("/pasantia/sinaplicantes")
    public ResponseEntity<ResponseDto<Page<PasantiaConNombreYLogoEmpresaDto>>> getPasantiasSinAplicantes(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idpasantias", required = false) String sort
    ) {
        return handleRequest(() -> pasantiaBl.obtenerPasantiasSinAplicantes(page, size, search, sort));
    }
    @GetMapping("/pasantia/{idPasantia}")
    public ResponseEntity<ResponseDto<PasantiaConEmpresaYPostulantes>> getPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantia
    ) {
        return handleRequest(() -> pasantiaBl.obtenerPasantia(idPasantia));
    }
    //Obtener el perfil de un estudiante
    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<ResponseDto<UsuarioCompletoDto>> getEstudiante(
            @PathVariable String uuid,
            @PathVariable Integer idEstudiante
    ) {
        return handleRequest(() -> estudianteBl.obtenerEstudianteCompleto(idEstudiante));
    }
    //Obtener una solicitud de aplicación a pasantía
    @GetMapping("/solicitud/{idSolicitud}")
    public ResponseEntity<ResponseDto<AplicacionPasantiasDto>> getSolicitud(
            @PathVariable String uuid,
            @PathVariable Integer idSolicitud
    ) {
        return handleRequest(() -> administradorBl.obtenerSolicitudDeAplicacion( idSolicitud));
    }


    @PutMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> aceptarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.aceptarPasantia(idPasantias));
    }
    @DeleteMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> rechazarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.rechazarPasantia(idPasantias));
    }
    @GetMapping("/dashboard/KPI")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithoutParams(@PathVariable String uuid) {
        return handleRequest(administradorBl::getAllKPISWithoutParams);
    }
    @GetMapping("/dashboard/KPI/carrera")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithCareerParams(
            @PathVariable String uuid,
            @RequestParam Integer idCarrera
    ) {
        return handleRequest(() -> administradorBl.getAllKPISWithCareerParams(idCarrera));
    }
    @GetMapping("/dashboard/KPI/fecha")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithDateParams(
            @PathVariable String uuid,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin
    ) {
        return handleRequest(() -> administradorBl.getAllKPISWithDateParams(fechaInicio, fechaFin));
    }
    @GetMapping("/dashboard/KPI/empresa")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithEmpresaParams(
            @PathVariable String uuid,
            @RequestParam Integer idEmpresa
    ) {
        return handleRequest(() -> administradorBl.getAllKPISWithEmpresaParams(idEmpresa));
    }
    @GetMapping("/dashboard/KPI/sector")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithSectorParams(
            @PathVariable String uuid,
            @RequestParam String sector
    ) {
        return handleRequest(() -> administradorBl.getAllKPISWithSectorParams(sector));
    }
    @GetMapping("/dashboard/KPI/area")
    public ResponseEntity<ResponseDto<List<KPIDto>>> getKPIWithAreaParams(
            @PathVariable String uuid,
            @RequestParam String area
    ) {
        return handleRequest(() -> administradorBl.getAllKPISWithAreaParams(area));
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
        }catch (UsuarioYaRelacionadoException | InstitucionServiceExcepcion e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            responseDto.setResponse(null);
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
