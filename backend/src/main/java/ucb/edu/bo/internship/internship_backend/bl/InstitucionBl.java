package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosInstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;
import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitucionBl {
    private final InstitucionesDao institucionesDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final UsuariosDao usuariosDao;
    private final UsuariosBL usuariosBL;
    private final AplicacionPasantiasDao aplicacionPasantiasDao;
    private final SeleccionAplicanteDao seleccionAplicanteDao;
    private final PersonasDao personasDao;

    private final Logger logger = LoggerFactory.getLogger(InstitucionBl.class);
    private final PasantiasCarrerasDao pasantiasCarrerasDao;
    private final CarrerasDao carrerasDao;
    private final EmailService emailService;

    public InstitucionBl(InstitucionesDao institucionesDao, PasantiasDao pasantiasDao, UsuariosInstitucionesDao usuariosInstitucionesDao, UsuariosDao usuariosDao, UsuariosBL usuariosBL, AplicacionPasantiasDao aplicacionPasantiasDao, SeleccionAplicanteDao seleccionAplicanteDao,
                         PersonasDao personasDao,
                         PasantiasCarrerasDao pasantiasCarrerasDao,
                         CarrerasDao carrerasDao, EmailService emailService) {
        this.institucionesDao = institucionesDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.usuariosDao = usuariosDao;
        this.usuariosBL = usuariosBL;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.seleccionAplicanteDao = seleccionAplicanteDao;
        this.personasDao = personasDao;
        this.pasantiasCarrerasDao = pasantiasCarrerasDao;
        this.carrerasDao = carrerasDao;
        this.emailService = emailService;
    }
    /*@Transactional
    public UsuarioRegistroCompletoDto agregarInstitucionConUsuario(UsuariosInstitucionesDto usuariosInstitucionesDto){
        try {
            //Guardar Persona


        }catch (Exception e){
            throw new InstitucionServiceExcepcion("Error al agregar la institucion",e);
        }
    }
*/


    //Agregar una institucion
    @Transactional
    public InstitucionesDto agregarInstitucion(@NotNull InstitucionesDto institucionesDto){
        try {
            institucionesDto.setIdInstituciones(null);
            institucionesDto.setActivo(false);
            Instituciones instituciones = new Instituciones(institucionesDto);
            instituciones = institucionesDao.save(instituciones);
            return new InstitucionesDto(instituciones);
        }catch (Exception e){
            throw new InstitucionServiceExcepcion("Error al agregar la institucion",e);
        }
    }
    //Obtener todas las instituciones
    public Page<InstitucionesDto> obtenerInstituciones(Integer page, Integer size, String search,String sort,String active, String sector){
        try{
            Page<InstitucionesDto> instituciones;
            Pageable pageable = buildPageable(page, size, sort);
            if(Objects.equals(active, ""))
            {
                if (search == null && sector == null) {
                    instituciones = institucionesDao.findAllWithCountPasantias(pageable);
                } else if (search == null && sector!=null) {
                    instituciones = institucionesDao.findAllBySector(sector, pageable).map(InstitucionesDto::fromEntity);
                } else if (search != null && sector == null) {
                    instituciones = institucionesDao.findAllWithCountPasantiasAndNombreContaining(search, pageable);
                } else {
                    instituciones = institucionesDao.findAllWithCountPasantiasAndNombreAndSectores(search, sector, pageable).map(
                            InstitucionesDto::fromEntity
                    );
                }
            }else{
                if(!active.equals("true") && !active.equals("false")){
                    throw new UsuarioYaRelacionadoException("El parametro active debe ser true o false");
                }else{
                    Boolean activeBoolean = Boolean.parseBoolean(active);
                    if (search == null && sector == null) {
                        instituciones = institucionesDao.findAllWithCountPasantiasAndActivo(activeBoolean,pageable);
                    } else if (search == null && sector!=null) {
                        instituciones = institucionesDao.findAllBySectorAndActivo(sector,activeBoolean, pageable).map(InstitucionesDto::fromEntity);
                    } else if (search != null && sector == null) {
                        instituciones = institucionesDao.findAllWithCountPasantiasAndNombreContainingAndActivo(search,activeBoolean, pageable);
                    } else {
                        instituciones = institucionesDao.findAllWithCountPasantiasAndNombreAndSectoresAndActivo(search, sector, activeBoolean, pageable).map(
                                InstitucionesDto::fromEntity
                        );
                    }
                }
            }
            return instituciones;
        }catch (UsuarioYaRelacionadoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las instituciones: "+e.getMessage());
        }
    }
    @NotNull
    private Pageable buildPageable(Integer page, Integer size, String sort){
        Sort.Order order = Sort.Order.desc("idinstituciones");
        if(Objects.equals(sort, "nombre")){
             order = Sort.Order.asc("nombre");
        }
        return PageRequest.of(page, size, Sort.by(order));
    }
    @Transactional
    public InstitucionConPasantiasDto obtenerInstitucionById(Integer id) {
        try {
            Instituciones instituciones = institucionesDao.findByIdinstitucionesAndActivo(id, true);
            if (instituciones == null) {
                throw new InstitucionNotFoundException("Institucion no encontrada");
            } else {
                List<Pasantias> institucionPasantias = pasantiasDao.findPasantiasByInstitucionesIdinstitucionesAndActivoIsTrueAndSinaplicantesIsNotTrue(id);
                List<PasantiasDto> pasantias = new ArrayList<>();
                for (Pasantias pasantia : institucionPasantias) {
                    pasantias.add(PasantiasDto.fromEntity(pasantia));
                }
                return new InstitucionConPasantiasDto(instituciones, pasantias);
            }
        } catch (InstitucionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la institucion " +e.getMessage());
        }
    }

    public List<InstitucionNombreDto> obtenerInstitucionesNombre() {
        try {
            return institucionesDao.getAllIdAndNameByActivo(true);
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones", e);
        }
    }
    @Transactional
    public List<InstitucionConPasantiasDto> obtenerCuatroInstitucionesRelacionadas(Integer id) {
        try {
            List<Instituciones> instituciones = institucionesDao.findTop4InstitucionesBySectoresAndPasantias(id);
            List<InstitucionConPasantiasDto> institucionConPasantiasDtos = new ArrayList<>();
            for (Instituciones institucion : instituciones) {
                List<Pasantias> institucionPasantias = pasantiasDao.findPasantiasByInstitucionesIdinstituciones(institucion);
                List<PasantiasDto> pasantias = new ArrayList<>();
                for (Pasantias pasantia : institucionPasantias) {
                    pasantias.add(PasantiasDto.fromEntity(pasantia));
                }
                institucionConPasantiasDtos.add(new InstitucionConPasantiasDto(institucion, pasantias));
            }
            return institucionConPasantiasDtos;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones relacionadas", e);
        }
    }

    public Page<InstitucionesConCOUNTPasantiasDto> obtenerInstitucionesDestacadas(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return institucionesDao.getAllNameAndCountPasantiasByActivo(true, pageable);
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones destacadas", e);
        }
    }
    @Transactional
    public InstitucionesDto actualizarInstitucion(String uuid,InstitucionesDto institucionesDto, Integer id) {
        try {
            if (!validarRelacionUsuarioInstitucion(uuid, id)) {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
            Instituciones instituciones = institucionesDao.findByIdinstitucionesAndActivo(id, true);
            if (instituciones != null) {
                instituciones.setCorreo(institucionesDto.getCorreo());
                instituciones.setDescripcion(institucionesDto.getDescripcion());
                instituciones.setDireccion(institucionesDto.getDireccion());
                instituciones.setFotoinstitucion(institucionesDto.getFotoInstitucion());
                instituciones.setFotos(institucionesDto.getFotos());
                instituciones.setLogoempresa(institucionesDto.getLogoEmpresa());
                instituciones.setNombre(institucionesDto.getNombre());
                instituciones.setRedessociales(institucionesDto.getRedesSociales());
                instituciones.setSectores(institucionesDto.getSectores());
                instituciones = institucionesDao.save(instituciones);
                return new InstitucionesDto(instituciones);
            } else {
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al actualizar la institucion", e);
        }
    }
    @Transactional
    public InstitucionesDto suscribirseEmpresa(Integer idInstitucion, String uuid, SuscribirseInstitucionDto suscribirseInstitucionDto) {
        try {
            //Validar que el usuario no este relacionado con ninguna institucion
            if(!validarQueUsuarioNoEsteRelacionadoConCualquierInstitucion(uuid)) {
                //Validar que el usuario sea EMPRESA
                if(usuariosBL.userIs(uuid, "EMPRESA")){
                    if (institucionesDao.existsByIdinstitucionesAndActivoIsTrue(idInstitucion)) {
                        Usuariosinstituciones usuariosInstituciones = new Usuariosinstituciones();
                        usuariosInstituciones.setCargo(suscribirseInstitucionDto.getCargo());
                        usuariosInstituciones.setInstitucionesIdinstituciones(institucionesDao.findByIdinstitucionesAndActivo(idInstitucion, true));
                        usuariosInstituciones.setUsuariosIdusuarios(usuariosDao.findByKcUuid(uuid));
                        usuariosInstituciones.setActivo(false);
                        usuariosInstitucionesDao.save(usuariosInstituciones);
                        return new InstitucionesDto(institucionesDao.findByIdinstitucionesAndActivo(idInstitucion, true));
                    } else {
                        throw new InstitucionNotFoundException("Institucion no encontrada");
                    }
                }else{
                    throw new UsuarioYaRelacionadoException("El usuario no es una empresa");
                }
            }else{
                throw new UsuarioYaRelacionadoException("El usuario ya esta relacionado con una institucion o en espera de aprobacion para relacionarse con una institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al suscribirse a la institucion", e);
        }
    }

    public Page<PasantiasDto> obtenerPasantiasPorInstitucion(String uuid, Integer institucionId, Integer page, Integer size, String search, String sort) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pageable pageable = buildPageable(page, size, sort);
                Page<PasantiasDto> pasantias;
                Instituciones instituciones = institucionesDao.findByIdinstitucionesAndActivo(institucionId, true);
                if (search != null && !search.isEmpty()) {
                    pasantias = pasantiasDao.findAllByInstitucionesIdinstitucionesAndTituloContainingIgnoreCase(
                            instituciones, search, pageable
                    ).map(PasantiasDto::fromEntity);
                } else {
                    pasantias = pasantiasDao.findAllByInstitucionesIdinstituciones(instituciones, pageable).map(PasantiasDto::fromEntity);
                }
                return pasantias;
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las pasantias de la institucion", e);
        }
    }

    public Boolean eliminarPasantia(String uuid, Integer institucionId, Integer pasantiaId) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pasantias pasantias = pasantiasDao.findById(pasantiaId).orElse(null);
                if(pasantias != null){
                    if(!Objects.equals(pasantias.getInstitucionesIdinstituciones().getIdinstituciones(), institucionId)){
                        throw new InstitucionNotFoundException("Pasantia no encontrada");
                    }
                    pasantias.setActivo(false);
                    pasantiasDao.save(pasantias);
                    return true;
                } else {
                    throw new InstitucionNotFoundException("Pasantia no encontrada");
                }
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al eliminar la pasantia", e);
        }
    }

    public PasantiasDto agregarPasantia(String uuid, Integer institucionId, PasantiasDto pasantiasDto) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pasantias pasantias = pasantiasDto.toEntity();
                pasantias.setActivo(false);
                pasantias.setInstitucionesIdinstituciones(institucionesDao.findByIdinstitucionesAndActivo(institucionId, true));
                pasantias.setUsuariosIdusuarios(usuariosDao.findByKcUuid(uuid));
                pasantias.setFechaingreso(new Date());
                pasantias.setActivo(false);
                pasantias.setIdpasantias(null);
                pasantias.setSinaplicantes(false);

                pasantias = pasantiasDao.save(pasantias);
                for (Integer idCarrera : pasantiasDto.getIdCarreras()){
                    logger.info("Carrera: "+idCarrera);
                    Pasantiascarreras pasantiasCarreras = new Pasantiascarreras();
                    Carreras carrera = carrerasDao.findById(idCarrera).orElse(null);
                    if(carrera != null) {
                        pasantiasCarreras.setCarrerasIdcarreras(carrera);
                        pasantiasCarreras.setPasantiasIdpasantias(pasantias);
                        pasantiasCarrerasDao.save(pasantiasCarreras);
                    }
                }
                return PasantiasDto.fromEntity(pasantias);
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al agregar la pasantia", e);
        }
    }

    public PasantiasDto actualizarPasantia(String uuid, Integer institucionId, Integer pasantiaId, PasantiasDto pasantiasDto) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pasantias pasantias = pasantiasDao.findById(pasantiaId).orElse(null);
                if(pasantias != null){
                    if(!Objects.equals(pasantias.getInstitucionesIdinstituciones().getIdinstituciones(), institucionId)){
                        throw new InstitucionNotFoundException("Pasantia no encontrada");
                    }
                    pasantias.setTitulo(pasantiasDto.getTitulo());
                    pasantias.setDescripcion(pasantiasDto.getDescripcion());
                    pasantias.setFechaingreso(pasantiasDto.getFechaIngreso());
                    pasantias.setFechacierre(pasantiasDto.getFechaCierre());
                    pasantias.setRequisitos((List<String>) pasantiasDto.getRequisitos());
                    pasantias.setAreas((List<String>)  pasantiasDto.getAreas());
                    pasantias.setFunciones( (List<String>)  pasantiasDto.getFunciones());
                    pasantias.setBeneficios( (List<String>)  pasantiasDto.getBeneficios());
                    pasantias = pasantiasDao.save(pasantias);
                    return PasantiasDto.fromEntity(pasantias);
                } else {
                    throw new InstitucionNotFoundException("Pasantia no encontrada");
                }
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al actualizar la pasantia", e);
        }
    }

    public Page<UsuariosConPersonaYCarreraDto> obtenerAplicantes(String uuid, Integer institucionId, Integer pasantiaId, Integer page, Integer size, String search, String sort) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
                Pasantias pasantias = pasantiasDao.findById(pasantiaId).orElse(null);
                if(pasantias != null){
                    if(!Objects.equals(pasantias.getInstitucionesIdinstituciones().getIdinstituciones(), institucionId)){
                        throw new InstitucionNotFoundException("Pasantia no encontrada");
                    }
                    return aplicacionPasantiasDao.findAllByPasantiasIdpasantias(pasantias, pageable).
                            map(
                            aplicacion -> new UsuariosConPersonaYCarreraDto(
                                    UsuariosDto.fromEntity(aplicacion.getUsuariosIdusuarios()),
                                    PersonasDto.fromEntity(aplicacion.getUsuariosIdusuarios().getPersonasIdpersonas()),
                                    CarrerasDto.fromEntity(aplicacion.getUsuariosIdusuarios().getCarrerasIdcarreras())));
                } else {
                    throw new InstitucionNotFoundException("Pasantia no encontrada");
                }
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener los aplicantes", e);
        }
    }

    public Boolean aceptarAplicante(String uuid, Integer institucionId, Integer pasantiaId, Integer aplicanteId) {
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pasantias pasantias = pasantiasDao.findById(pasantiaId).orElse(null);
                if(pasantias != null){
                    if(!Objects.equals(pasantias.getInstitucionesIdinstituciones().getIdinstituciones(), institucionId)){
                        throw new InstitucionNotFoundException("Pasantia no encontrada");
                    }
                    Usuarios usuarios = usuariosDao.findById(aplicanteId).orElse(null);
                    if(usuarios != null){
                        Aplicacionespasantias aplicacionpasantias = aplicacionPasantiasDao.findByPasantiasIdpasantiasAndUsuariosIdusuarios(pasantias, usuarios);
                        if(aplicacionpasantias != null){
                            Seleccionaplicante seleccionaplicante = new Seleccionaplicante();
                            seleccionaplicante.setAplicacionespasantiasIdaplicacionpasantias(aplicacionpasantias);
                            seleccionaplicante.setUsuariosinstitucionesIdusuariosinstituciones(usuariosInstitucionesDao.findByUsuariosIdusuariosAndInstitucionesIdinstituciones(usuarios, pasantias.getInstitucionesIdinstituciones()));
                            seleccionaplicante.setFechaseleccion(new Date());
                            seleccionaplicante.setHoraseleccion(new Time(new Date().getTime()));
                            seleccionaplicante.setComentarios("");
                            seleccionaplicante.setActivo(false);
                            seleccionAplicanteDao.save(seleccionaplicante);
                            return true;
                        } else {
                            throw new InstitucionNotFoundException("Aplicante no encontrado");
                        }
                    } else {
                        throw new InstitucionNotFoundException("Aplicante no encontrado");
                    }
                } else {
                    throw new InstitucionNotFoundException("Pasantia no encontrada");
                }
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al aceptar el aplicante", e);
        }
    }

    //marcar una pasantia como que no se selecciono ningun aplicante guardando un SeleccionAplicante con AplicacionesPasantiasIdAplicacionPasantias = null
    public Boolean pasantiaSinAplicante(String uuid, Integer institucionId, Integer pasantiaId){
        try {
            if (validarRelacionUsuarioInstitucion(uuid, institucionId)) {
                Pasantias pasantias = pasantiasDao.findById(pasantiaId).orElse(null);
                if(pasantias != null){
                    if(!Objects.equals(pasantias.getInstitucionesIdinstituciones().getIdinstituciones(), institucionId)){
                        throw new InstitucionNotFoundException("Pasantia no encontrada");
                    }
                    Seleccionaplicante seleccionaplicante = new Seleccionaplicante();
                    seleccionaplicante.setAplicacionespasantiasIdaplicacionpasantias(null);
                    seleccionaplicante.setUsuariosinstitucionesIdusuariosinstituciones(usuariosInstitucionesDao.findByUsuariosIdusuariosAndInstitucionesIdinstituciones(usuariosDao.findByKcUuid(uuid), pasantias.getInstitucionesIdinstituciones()));
                    seleccionaplicante.setFechaseleccion(new Date());
                    seleccionaplicante.setHoraseleccion(new Time(new Date().getTime()));
                    seleccionaplicante.setComentarios("");
                    seleccionaplicante.setActivo(false);
                    seleccionAplicanteDao.save(seleccionaplicante);
                    return true;
                } else {
                    throw new InstitucionNotFoundException("Pasantia no encontrada");
                }
            } else {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al marcar la pasantia como sin aplicantes", e);
        }
    }

    public UsuarioConPersonaEInstitucionDto obtenerUsuarioInstitucionByUuid(String uuid){
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "EMPRESA")) throw new RuntimeException("El usuario no es una empresa");
        return new UsuarioConPersonaEInstitucionDto(
                UsuariosDto.fromEntity(usuario),
                PersonasDto.fromEntity(usuario.getPersonasIdpersonas()),
                UsuariosInstitucionesDto.fromEntity(
                        usuariosInstitucionesDao.findByUsuariosIdusuarios(usuario)
                )
        );
    }

    public Boolean actualizarUsuarioInstitucion(String uuid, UsuarioConPersonaEInstitucionDto usuario){
        Usuarios usuarioEntity = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuarioEntity.getRolesIdroles().getRol(), "EMPRESA")) throw new RuntimeException("El usuario no es una empresa");
        Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findByUsuariosIdusuarios(usuarioEntity);
        if(usuariosinstituciones == null) throw new RuntimeException("El usuario no esta relacionado con ninguna institucion");
        usuariosinstituciones.setCargo(usuario.getUsuarioInstitucion().getCargo());
        usuarioEntity.setCorreo(usuario.getCorreo());

        Personas persona = usuarioEntity.getPersonasIdpersonas();
        persona.setNombres(usuario.getPersona().getNombre());
        persona.setApellidopaterno(usuario.getPersona().getApellidoPaterno());
        persona.setApellidomaterno(usuario.getPersona().getApellidoMaterno());
        persona.setCi(usuario.getPersona().getCi());
        persona.setFechadenacimiento(usuario.getPersona().getFechaDeNacimiento());
        persona.setTelefono(usuario.getPersona().getTelefono());

        personasDao.save(persona);
        usuariosDao.save(usuarioEntity);
        usuariosInstitucionesDao.save(usuariosinstituciones);
        return true;
    }

    public Set<String> obtenerSectores(){
        return institucionesDao.findAllByActivoIsTrue().stream()
                .map(Instituciones::getSectores)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Boolean validarRelacionUsuarioInstitucion(String uuid, Integer idInstitucion){
        return usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones(uuid, idInstitucion);
    }
    private Boolean validarQueUsuarioNoEsteRelacionadoConCualquierInstitucion(String uuid){
        return usuariosInstitucionesDao.existsByUsuariosUuid(uuid);
    }

}
