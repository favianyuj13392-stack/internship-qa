package ucb.edu.bo.internship.internship_backend.bl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;
import ucb.edu.bo.internship.internship_backend.service.impl.KeycloakServiceImpl;

import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final AplicacionPasantiasDao aplicacionPasantiasDao;
    private final CurriculumsDao curriculumsDao;
    private final KeycloakServiceImpl keycloakService;

    private final EmailService emailService;
    private final RolesDao rolesDao;
    private final NotificacionPasantiaService notificacionPasantiaService;
    private final Logger logger = LoggerFactory.getLogger(AdministradorBl.class);

    public AdministradorBl(InstitucionesDao institucionesDao, UsuariosInstitucionesDao usuariosInstitucionesDao, PasantiasDao pasantiasDao, UsuariosDao usuariosDao, PersonasDao personasDao, AplicacionPasantiasDao aplicacionPasantiasDao, EmailService emailService, RolesDao rolesDao, CurriculumsDao curriculumsDao, KeycloakServiceImpl keycloakService, NotificacionPasantiaService notificacionPasantiaService) {
        this.institucionesDao = institucionesDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.emailService = emailService;
        this.rolesDao = rolesDao;
        this.curriculumsDao = curriculumsDao;
        this.keycloakService = keycloakService;
        this.notificacionPasantiaService = notificacionPasantiaService;
    }

    public InstitucionesDto cambiarEstadoInstitucion(Integer idInstituciones, Boolean estado) {
        try {
            Instituciones instituciones = institucionesDao.findById(idInstituciones).orElse(null);
            if (instituciones != null) {
                instituciones.setActivo(estado);
                instituciones = institucionesDao.save(instituciones);
                return new InstitucionesDto(instituciones);
            }else{
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        }catch (InstitucionNotFoundException e){
            throw new InstitucionNotFoundException("Institucion no encontrada");
        }catch (Exception e){
            throw new RuntimeException("Error al cambiar el estado de la institucion",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerUsuariosInstitucion(Integer idInstituciones) {
        try {
            return institucionesDao.findById(idInstituciones)
                    .orElseThrow(() -> new InstitucionNotFoundException("Institucion no encontrada"))
                    .getUsuariosinstitucionesList()
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (InstitucionNotFoundException e) {
            throw new InstitucionNotFoundException("Institucion no encontrada");
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener los usuarios de la institucion", e);
        }
    }
    @Transactional
    public UsuarioConCorreoYNombreCompletoYFotoDto relacionarUsuarioInstitucion(Integer idInstituciones, Integer idUsuariosInstituciones, Integer idUsuarios, Boolean estado) {
        try {
            Usuariosinstituciones usuariosInstituciones = usuariosInstitucionesDao.findById(idUsuariosInstituciones).orElse(null);
            if(usuariosInstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(!estado){
                //Borrar la relacion y los datos asociados
                Usuarios usuarios = usuariosInstituciones.getUsuariosIdusuarios();
                Personas personas = usuarios.getPersonasIdpersonas();
                Instituciones instituciones = usuariosInstituciones.getInstitucionesIdinstituciones();
                usuariosInstitucionesDao.delete(usuariosInstituciones);
                if(!instituciones.getActivo()){
                    institucionesDao.delete(instituciones);
                }

                EmailRequest emailRequest = new EmailRequest();

                emailRequest.setTo(
                        usuariosInstituciones.getUsuariosIdusuarios().getCorreo()
                );

                emailRequest.setSubject(
                        "Rechazo de su solicitud de cuenta de empresa en nuestro sistema de pasantías"
                );

                emailRequest.setBody(
                        "<p>Estimado/a "+usuarios.getPersonasIdpersonas().getNombres()+",</p>" +
                                "<p>Lamentamos informarle que su solicitud para registrar la empresa <strong>"+instituciones.getNombre()+"</strong> en nuestro sistema de pasantías no ha sido aceptada.</p>" +
                                "<p>Le agradecemos por su interés en colaborar con nosotros. Le invitamos a volver a postularse en el futuro o a ponerse en contacto con nosotros para discutir cualquier inquietud.</p>" +
                                "<p>Su cuenta sera eliminada totalmente</p>" +
                                "<p>Si tiene preguntas o desea más detalles sobre esta decisión, no dude en comunicarse con nuestro equipo de soporte.</p>"
                );

                emailService.enviarCorreo(emailRequest);

                usuariosDao.delete(usuarios);
                personasDao.delete(personas);

                keycloakService.deleteUser(usuarios.getKcUuid());    


                return new UsuarioConCorreoYNombreCompletoYFotoDto();
            }
            //Aceptar la relacion
            usuariosInstituciones.getUsuariosIdusuarios().setActivo(true);
            usuariosInstituciones.getInstitucionesIdinstituciones().setActivo(true);
            usuariosInstituciones.setActivo(true);
            usuariosInstituciones = usuariosInstitucionesDao.save(usuariosInstituciones);

            EmailRequest emailRequest = new EmailRequest();

            emailRequest.setTo(
                    usuariosInstituciones.getUsuariosIdusuarios().getCorreo()
            );

            emailRequest.setSubject(
                    "Aceptación de su cuenta de empresa en nuestro sistema de pasantías"
            );

            emailRequest.setBody(
                    "<p>Estimado/a "+usuariosInstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres()+",</p>" +
                            "<p>Nos complace informarle que su solicitud para registrar la empresa <strong>"+usuariosInstituciones.getInstitucionesIdinstituciones().getNombre()+"</strong> en nuestro sistema de pasantías ha sido aceptada.</p>" +
                            "<p>A partir de ahora, puede acceder a nuestra plataforma y comenzar a publicar oportunidades de pasantía. Estamos entusiasmados de colaborar con usted y de ofrecer a nuestros estudiantes valiosas experiencias laborales en su empresa.</p>" +
                            "<p>Si tiene alguna pregunta o necesita asistencia adicional, no dude en ponerse en contacto con nuestro equipo de soporte.</p>"
            );

            emailService.enviarCorreo(emailRequest);

            return new UsuarioConCorreoYNombreCompletoYFotoDto(usuariosInstituciones);
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al relacionar el usuario con la institucion",e);
        }
    }

    public PasantiasDto aceptarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if (Boolean.TRUE.equals(pasantias.getActivo())) {
                throw new UsuarioYaRelacionadoException("No se puede aceptar una pasantia activa");
            }
            // 1. Activar y persistir primero (corrige B6: el guardado en BD es previo al despacho)
            pasantias.setActivo(true);
            pasantias = pasantiasDao.save(pasantias);

            // 2. Notificación individual al empleador/empresa
            try {
                if (pasantias.getUsuariosIdusuarios() != null && pasantias.getUsuariosIdusuarios().getCorreo() != null) {
                    EmailRequest emailRequest = new EmailRequest();
                    emailRequest.setTo(pasantias.getUsuariosIdusuarios().getCorreo());
                    emailRequest.setSubject("Su solicitud de publicación de una pasantía ha sido aceptada");
                    String nombreContacto = pasantias.getUsuariosIdusuarios().getPersonasIdpersonas() != null
                            ? pasantias.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres()
                            : "representante";
                    emailRequest.setBody(
                            "<p>Estimado/a " + nombreContacto + ",</p>" +
                            "<p>Nos complace informarle que su solicitud de publicación para la pasantía titulada <strong>" + pasantias.getTitulo() + "</strong> ha sido aceptada.</p>" +
                            "<p>Agradecemos tu interés en contribuir a la formación profesional de nuestros estudiantes. La convocatoria ya se encuentra activa en el portal.</p>" +
                            "<p>¡Felicitaciones y mucho éxito!</p>"
                    );
                    emailService.enviarCorreo(emailRequest);
                }
            } catch (Exception ex) {
                logger.error("Error al notificar al empleador sobre aceptación de pasantía ID {}: {}", idPasantias, ex.getMessage());
            }

            // 3. Notificación asíncrona y auditada a los estudiantes habilitados del padrón oficial
            notificacionPasantiaService.notificarEstudiantesPorCarrera(pasantias.getIdpasantias());

            return new PasantiasDto(pasantias);
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al aceptar la pasantia", e);
        }
    }

    public PasantiasDto rechazarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if(pasantias.getActivo()){
                throw new UsuarioYaRelacionadoException("No se puede eliminar una pasantia activa");
            }
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(
                    pasantias.getUsuariosIdusuarios().getCorreo()
            );
            emailRequest.setSubject(
                    "Su solicitud de publicación de una pasantía ha sido rechazada"
            );
            emailRequest.setBody(
                    "<p>Estimado/a " + pasantias.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres() + ",</p>" +
                            "<p>Lamentablemente, debemos informarle que su solicitud de publicación para la pasantía titulada <strong>" + pasantias.getTitulo() + "</strong> ha sido rechazada.</p>" +
                            "<p>Agradecemos su interés en contribuir a la formación profesional de nuestros estudiantes. Aunque esta vez no hemos podido aceptar su solicitud, le animamos a seguir participando en futuras oportunidades.</p>" +
                            "<p>Si tiene alguna pregunta o necesita más detalles, no dude en ponerse en contacto con nosotros.</p>"
            );
            emailService.enviarCorreo(emailRequest);
            pasantiasDao.delete(pasantias);
            return new PasantiasDto();
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e ){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al rechazar la pasantia",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerSuscripcionAInstituciones(String nombreInstitucion){
        try {
            if(nombreInstitucion.isBlank()){
                return usuariosInstitucionesDao.findByActivoFalse()
                        .stream()
                        .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                        .toList();
            }
            return usuariosInstitucionesDao.findByInstitucionesIdinstitucionesNombreContainsAndActivoFalse(nombreInstitucion)
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las suscripciones a las instituciones",e);
        }
    }

    public SolicitudIndormacionDto obtenerTodaLaInformacionDeSolicitud(Integer idInstitucion, Integer idSolicitud, Integer idUsuario) {
        try {
            Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findById(idSolicitud).orElse(null);
            if(usuariosinstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(usuariosinstituciones.getActivo()){
                throw new InstitucionNotFoundException("La relacion entre el usuario y la institucion esta activa, no se puede obtener la informacion");
            }
            Usuarios usuarios = usuariosDao.findById(idUsuario).orElse(null);
            if (usuarios == null) throw new InstitucionNotFoundException("Usuario no encontrado");
            Instituciones instituciones = institucionesDao.findById(idInstitucion).orElse(null);
            if (instituciones == null) throw new InstitucionNotFoundException("Institucion no encontrada");
            if(usuariosinstituciones.getUsuariosIdusuarios().equals(usuarios) && usuariosinstituciones.getInstitucionesIdinstituciones().equals(instituciones)){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            return new SolicitudIndormacionDto(UsuariosDto.fromEntityInstitucion(usuarios),PersonasDto.fromEntity(usuarios.getPersonasIdpersonas()),InstitucionesDto.fromEntity(instituciones),UsuariosInstitucionesDto.fromEntity(usuariosinstituciones));
        }catch (InstitucionNotFoundException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la informacion de la solicitud", e);
        }
    }
    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerSuscripcionAInstitucion(Integer idInstitucion) {
        try {
            return usuariosInstitucionesDao.findByInstitucionesIdinstitucionesIdinstitucionesAndActivoTrue(idInstitucion)
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las suscripciones a la institucion",e);
        }
    }

    public AplicacionPasantiasDto obtenerSolicitudDeAplicacion(Integer idSolicitud) {
        try {
            Aplicacionespasantias aplicacionespasantias = aplicacionPasantiasDao.findById(idSolicitud).orElseThrow(() -> new RuntimeException("Solicitud de aplicacion no encontrada"));
            return AplicacionPasantiasDto.fromEntity(aplicacionespasantias);
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la informacion de la solicitud", e);
        }
    }
    //getAllKPISWithoutParams
    public List<KPIDto> getAllKPISWithoutParams() {
        List<KPIDto> kpis = new ArrayList<>();
        kpis.add(new KPIDto("Total Estudiantes", usuariosDao.countEstudiantes().orElse(0L)));
        kpis.add(new KPIDto("Total Pasantías Activas", pasantiasDao.countPasantiasActivo(true).orElse(0L)));
        kpis.add(new KPIDto("Total Pasantías Inactivas", pasantiasDao.countPasantiasActivo(false).orElse(0L)));
        kpis.add(new KPIDto("Total Aplicaciones", aplicacionPasantiasDao.countByPasantiasIdpasantias().orElse(0L)));
        kpis.add(new KPIDto("Total Aplicaciones Activas", aplicacionPasantiasDao.countByActivo(true).orElse(0L)));
        kpis.add(new KPIDto("Pasantías que no aceptaron estudiantes", pasantiasDao.countPasantiasQueNoAceptaronEstudiantes().orElse(0L)));
        kpis.add(new KPIDto("Total Currículums", curriculumsDao.count()));
        kpis.add(new KPIDto("Promedio Currículums Por Estudiante", Math.round(curriculumsDao.promedioCurriculumsPorEstudiante(1).orElse(0.0))));
        kpis.add(new KPIDto("Total Instituciones Activas", institucionesDao.countAllByActivo()));
        kpis.add(new KPIDto("Total Usuarios con Empresa", usuariosDao.countUsuariosEmpresa().orElse(0L)));
        return kpis;
    }
    //getAllKPISWithCareerParams
    public List<KPIDto> getAllKPISWithCareerParams(Integer idCarrera) {
        List<KPIDto> kpis = new ArrayList<>();
        kpis.add(new KPIDto("Estudiantes por Carrera", usuariosDao.countEstudiantesPorCarrera(idCarrera).orElse(0L)));
        kpis.add(new KPIDto("Pasantías por Carrera", pasantiasDao.countPasantiasPorCarrera(idCarrera).orElse(0L)));
        kpis.add(new KPIDto("Aplicaciones por Carrera", aplicacionPasantiasDao.countByPasantiasIdpasantiasByCarrera(idCarrera).orElse(0L)));
        return kpis;
    }
    //getAllKPISWithDateParams
    public List<KPIDto> getAllKPISWithDateParams(String fechaInicio, String fechaFin){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedFechaInicio = null;
        Date parsedFechaFin = null;
        List<KPIDto> kpis = new ArrayList<>();
        try {
            parsedFechaInicio = format.parse(fechaInicio);
            parsedFechaFin = format.parse(fechaFin);
            kpis.add(new KPIDto("Estudiantes registrados por Fecha", usuariosDao.countEstudiantesPorFecha(parsedFechaInicio,parsedFechaFin).orElse(0L)));
            return kpis;
        }catch (ParseException e){
            throw new RuntimeException("Error al obtener los KPIs", e);
        }
    }
    //getAllKPISWithEmpresaParams
    public List<KPIDto> getAllKPISWithEmpresaParams(Integer idEmpresa){
        List<KPIDto> kpis = new ArrayList<>();
        kpis.add(new KPIDto("Pasantías por Empresa", pasantiasDao.countPasantiasPorEmpresa(idEmpresa).orElse(0L)));
        kpis.add(new KPIDto("Aplicaciones por Empresa", aplicacionPasantiasDao.countByPasantiasIdpasantiasByEmpresa(idEmpresa).orElse(0L)));
        kpis.add(new KPIDto("Usuarios por Empresa", usuariosInstitucionesDao.countUsuariosPorEmpresa(idEmpresa).orElse(0L)));
        return kpis;
    }
    //getAllKPISWithSectorParams
    public List<KPIDto> getAllKPISWithSectorParams(String sector){
        List<KPIDto> kpis = new ArrayList<>();
        kpis.add(new KPIDto("Pasantías por Sector", pasantiasDao.countPasantiasPorSector(sector).orElse(0L)));
        kpis.add(new KPIDto("Aplicaciones por Sector", aplicacionPasantiasDao.countByPasantiasIdpasantiasBySector(sector).orElse(0L)));
        kpis.add(new KPIDto("Instituciones por Sector", institucionesDao.countAllBySector(sector).orElse(0L)));
        return kpis;
    }
    //getAllKPISWithAreaParams
    public List<KPIDto> getAllKPISWithAreaParams(String area){
        List<KPIDto> kpis = new ArrayList<>();
        kpis.add(new KPIDto("Pasantías por Area", pasantiasDao.countPasantiasPorArea(area).orElse(0L)));
        kpis.add(new KPIDto("Aplicaciones por Area", aplicacionPasantiasDao.countByPasantiasIdpasantiasByArea(area).orElse(0L)));
        return kpis;
    }
}
