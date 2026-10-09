package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Roles;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.service.IKeycloakService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuariosBL {

    private final Logger logger = LoggerFactory.getLogger(UsuariosBL.class);

    private final PasantiaBl pasantiasBl;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final InstitucionesDao institucionesDao;
    private final RolesDao rolesDao;
    private final CarrerasDao carreasDao;
    private final IKeycloakService keycloakService;

    public UsuariosBL(UsuariosDao usuariosDao,
                      PersonasDao personasDao, UsuariosInstitucionesDao usuariosInstitucionesDao,
                      InstitucionesDao institucionesDao, RolesDao rolesDao, IKeycloakService keycloakService, CarrerasDao carreasDao,
                      PasantiaBl pasantiasBl) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.institucionesDao = institucionesDao;
        this.rolesDao = rolesDao;
        this.keycloakService = keycloakService;
        this.carreasDao = carreasDao;
        this.pasantiasBl = pasantiasBl;
    }

    public UsuariosDto obtenerUsuario(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return UsuariosDto.fromEntity(usuario);
    }


    public Boolean obtenerUsuarioExistencia(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return usuario != null;
    }

    public Boolean userIs(String kcUuid,String role){
        return usuariosDao.userIs(kcUuid,role);
    }

    public PersonasDto agregarPersona(PersonasDto personasDto){
        if(personasDto.getFotoPerfil() == null){
            //TODO: Cambiar la foto por defecto con el url de la imagen por defecto en minio
            personasDto.setFotoPerfil("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png");
        }
        if(personasDto.getBannerPerfil()==null){
            //TODO: Cambiar la foto por defecto con el url de la imagen por defecto en minio
            personasDto.setBannerPerfil("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png");
        }
       return PersonasDto.fromEntity(personasDao.save(personasDto.toEntity()));
    }

    private String getAuthenticatedKcUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getSubject();
            } else if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                return jwtAuth.getToken().getSubject();
            }
        }
        return null;
    }

    public UsuariosDto agregarUsuario(UsuariosDto usuariosDto){
        String authUuid = getAuthenticatedKcUuid();
        if (authUuid != null && !authUuid.isBlank()) {
            usuariosDto.setKc_UUID(authUuid);
        }

        Roles roles = null;
        if (usuariosDto.getIdRoles() != null) {
            roles = rolesDao.findById(usuariosDto.getIdRoles()).orElse(null);
            if (roles != null && "ADMIN".equalsIgnoreCase(roles.getRol())) {
                logger.warn("Attempt to register with ADMIN role blocked for user {}", usuariosDto.getKc_UUID());
                roles = rolesDao.findByRol("ESTUDIANTE");
            }
        }
        if (roles == null) {
            roles = rolesDao.findByRol("ESTUDIANTE");
        }

        if (roles != null && usuariosDto.getKc_UUID() != null) {
            keycloakService.addRealmRoleToUser(usuariosDto.getKc_UUID(), roles.getRol());
            usuariosDto.setIdRoles(roles.getIdroles());
        }

        Date date = new Date(System.currentTimeMillis());
        usuariosDto.setFechaRegistro(date);
        Time time = new Time(System.currentTimeMillis());
        usuariosDto.setHoraRegistro(time);

        Usuarios usuario = usuariosDto.toEntity();
        usuario.setPersonasIdpersonas(personasDao.findById(usuariosDto.getIdPersonas()).orElse(null));
        usuario.setRolesIdroles(roles);
        usuario.setCarrerasIdcarreras(carreasDao.findById(usuariosDto.getIdCarreras()).orElse(null));

        return UsuariosDto.fromEntity(usuariosDao.save(usuario));
    }

    private UsuariosDto agregarUsuarioInstitucion(UsuariosDto usuariosDto)
    {
        String authUuid = getAuthenticatedKcUuid();
        if (authUuid != null && !authUuid.isBlank()) {
            usuariosDto.setKc_UUID(authUuid);
        }

        Roles roles = rolesDao.findByRol("EMPRESA");
        if (roles != null && usuariosDto.getKc_UUID() != null) {
            keycloakService.addRealmRoleToUser(usuariosDto.getKc_UUID(), roles.getRol());
            usuariosDto.setIdRoles(roles.getIdroles());
        }

        Date date = new Date(System.currentTimeMillis());
        usuariosDto.setFechaRegistro(date);
        Time time = new Time(System.currentTimeMillis());
        usuariosDto.setHoraRegistro(time);

        Usuarios usuario = usuariosDto.toEntity();
        usuario.setPersonasIdpersonas(personasDao.findById(usuariosDto.getIdPersonas()).orElse(null));
        usuario.setRolesIdroles(roles);
        return UsuariosDto.fromEntityInstitucion(usuariosDao.save(usuario));
    }

    public UsuariosInstitucionesDto agregarUsuarioInstitucion(String kcUuid, Integer idInstitucion, String cargo){
    try {
        String authUuid = getAuthenticatedKcUuid();
        String targetUuid = (authUuid != null && !authUuid.isBlank()) ? authUuid : kcUuid;
        Usuarios usuario = usuariosDao.findByKcUuid(targetUuid);
        Instituciones institucion = institucionesDao.findById(idInstitucion).orElse(null);
        if(usuario != null && institucion != null){
            Usuariosinstituciones usuariosinstituciones = new Usuariosinstituciones();
            usuariosinstituciones.setUsuariosIdusuarios(usuario);
            usuariosinstituciones.setInstitucionesIdinstituciones(institucion);
            usuariosinstituciones.setCargo(cargo);
            usuariosinstituciones.setActivo(false);
            usuariosinstituciones = usuariosInstitucionesDao.save(usuariosinstituciones);
            return UsuariosInstitucionesDto.fromEntity(usuariosinstituciones);
        }
        return null;
    }catch (Exception e){
        logger.error("Error al agregar usuario institucion",e);
        return null;
    }
    }

    public UsuarioRegistroCompletoDto agregarUsuarioCompleto(UsuarioRegistroCompletoDto usuarioRegistroCompletoDto){
        String authUuid = getAuthenticatedKcUuid();
        if (authUuid != null && !authUuid.isBlank()) {
            usuarioRegistroCompletoDto.setKc_UUID(authUuid);
        }

        PersonasDto persona_aux = usuarioRegistroCompletoDto.getPersona();
        persona_aux.setHabilidades(persona_aux.getHabilidades().toString());
        persona_aux.setHabilidadesSeleccionada(persona_aux.getHabilidadesSeleccionada().toString());
        persona_aux.setExperiencia(persona_aux.getExperiencia().toString());
        persona_aux.setRedesSociales(persona_aux.getRedesSociales().toString());
        usuarioRegistroCompletoDto.setPersona(persona_aux);
        PersonasDto personaAgregada = agregarPersona(usuarioRegistroCompletoDto.getPersona());
        usuarioRegistroCompletoDto.setPersona(personaAgregada);      
        usuarioRegistroCompletoDto.setIdPersonas(personaAgregada.getIdPersona());

        InstitucionesDto institucion = usuarioRegistroCompletoDto.getInstitucion();
        Roles targetRole = (institucion == null) ? rolesDao.findByRol("ESTUDIANTE") : rolesDao.findByRol("EMPRESA");
        if (targetRole != null) {
            usuarioRegistroCompletoDto.setIdRoles(targetRole.getIdroles());
        }

        UsuariosDto usuarioAgregado = agregarUsuario(usuarioRegistroCompletoDto);

        if(institucion == null){
            return new UsuarioRegistroCompletoDto(usuarioAgregado, personaAgregada, null, null);
        }else{
            Instituciones instituciones = institucionesDao.findByNombre(institucion.getNombre());
            if(instituciones == null){
                instituciones = institucionesDao.save(institucion.toEntity());
            }
            UsuariosInstitucionesDto usuarioInstitucionAgregado = agregarUsuarioInstitucion(usuarioRegistroCompletoDto.getKc_UUID(), instituciones.getIdinstituciones(), usuarioRegistroCompletoDto.getCargo());
            return new UsuarioRegistroCompletoDto(usuarioAgregado, personaAgregada, institucion, usuarioInstitucionAgregado != null ? usuarioInstitucionAgregado.getCargo() : null);
        }
    }

    @Transactional
    public UsuarioRegistroCompletoDto agregarUsuarioCompletoInstitucion(UsuarioRegistroCompletoDto usuarioRegistroCompletoDto) {
        try {
            String authUuid = getAuthenticatedKcUuid();
            if (authUuid != null && !authUuid.isBlank()) {
                usuarioRegistroCompletoDto.setKc_UUID(authUuid);
            }

            PersonasDto persona_aux = usuarioRegistroCompletoDto.getPersona();
            persona_aux.setHabilidades(persona_aux.getHabilidades().toString());
            persona_aux.setHabilidadesSeleccionada(persona_aux.getHabilidadesSeleccionada().toString());
            persona_aux.setExperiencia(persona_aux.getExperiencia().toString());
            persona_aux.setRedesSociales(persona_aux.getRedesSociales().toString());
            usuarioRegistroCompletoDto.setPersona(persona_aux);
            //Agregar Persona
            usuarioRegistroCompletoDto.setPersona(agregarPersona(usuarioRegistroCompletoDto.getPersona()));
            //Poner la persona agregada en el usuario
            usuarioRegistroCompletoDto.setIdPersonas(usuarioRegistroCompletoDto.getPersona().getIdPersona());
            //Agregar Usuario
            usuarioRegistroCompletoDto.setCargo(usuarioRegistroCompletoDto.getCargo());
            Roles roleEmpresa = rolesDao.findByRol("EMPRESA");
            if (roleEmpresa != null) {
                usuarioRegistroCompletoDto.setIdRoles(roleEmpresa.getIdroles());
            }
            usuarioRegistroCompletoDto = new UsuarioRegistroCompletoDto(agregarUsuarioInstitucion(usuarioRegistroCompletoDto), usuarioRegistroCompletoDto.getPersona(), usuarioRegistroCompletoDto.getInstitucion(), usuarioRegistroCompletoDto.getCargo());
            //Agregar Institucion

            InstitucionesDto institucionesDto = usuarioRegistroCompletoDto.getInstitucion();
            Instituciones instituciones = new Instituciones();
            if(institucionesDto.getIdInstituciones()==null){
                Instituciones instituciones1 = institucionesDto.toEntity();
                instituciones1.setActivo(false);
                instituciones = institucionesDao.save(instituciones1);
            }else{
                instituciones = institucionesDao.findById(institucionesDto.getIdInstituciones()).orElse(null);
            }

            if(instituciones == null){
                instituciones = institucionesDao.save(institucionesDto.toEntity());
            }           

            institucionesDto = InstitucionesDto.fromEntity(instituciones);
            usuarioRegistroCompletoDto.setInstitucion(institucionesDto);

            //Agregar UsuarioInstitucion
            UsuariosInstitucionesDto usuariosInstitucionesDto = agregarUsuarioInstitucion(usuarioRegistroCompletoDto.getKc_UUID(),
                    usuarioRegistroCompletoDto.getInstitucion().getIdInstituciones(),
                    usuarioRegistroCompletoDto.getCargo()
            );

            if (usuariosInstitucionesDto != null) {
                usuarioRegistroCompletoDto.setCargo(usuariosInstitucionesDto.getCargo());
            }

            return usuarioRegistroCompletoDto;
        }catch (Exception e){
            logger.error("Error al agregar usuario completo institucion",e);
            return null;
        }
    }

    public InstitucionesDto obtenerInstitucionPorUsuario(String uuid) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        //also return usuarioInstitucion

        if(usuario != null){
            Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findByUsuariosIdusuarios(usuario);

            if(usuariosinstituciones != null){
                InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(usuariosinstituciones.getInstitucionesIdinstituciones());
                institucionesDto.setCantidadPasantias(pasantiasBl.obtenerCantidadPasantiasPorInstitucion(institucionesDto.getIdInstituciones()));
                return institucionesDto;
            }
        }
        return null;
    }

    public Boolean obtenerUsuarioAprobado(String uuid) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(usuario != null){
            Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findByUsuariosIdusuarios(usuario);
            if(usuariosinstituciones != null){
                if(usuariosinstituciones.getActivo()==true && usuario.getActivo()==true){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
}
