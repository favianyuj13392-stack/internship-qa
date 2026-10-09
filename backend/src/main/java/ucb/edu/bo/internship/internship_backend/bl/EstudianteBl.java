package ucb.edu.bo.internship.internship_backend.bl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EstudianteBl {
    private MinioBl minioBl;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final RolesDao rolesDao;

    private final CurriculumsDao curriculumsDao;

    private final AplicacionPasantiasDao aplicacionPasantiasDao;

    private Logger logger = LoggerFactory.getLogger(EstudianteBl.class);
    private final PasantiasDao pasantiasDao;
    private final MinioClient minioClient;

    public EstudianteBl(UsuariosDao usuariosDao, PersonasDao personasDao, RolesDao rolesDao, CurriculumsDao curriculumsDao, MinioBl minioBl, AplicacionPasantiasDao aplicacionPasantiasDao,
                        PasantiasDao pasantiasDao, MinioClient minioClient) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.rolesDao = rolesDao;
        this.curriculumsDao = curriculumsDao;
        this.minioBl = minioBl;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.pasantiasDao = pasantiasDao;
        this.minioClient = minioClient;
    }

    public UsuariosDto obtenerEstudianteByUuid(String uuid) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        logger.info("Usuario encontrado: " + uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");

        return new UsuariosConPersonaYCarreraDto(UsuariosDto.fromEntity(usuario),
                 PersonasDto.fromEntity(usuario.getPersonasIdpersonas()), CarrerasDto.fromEntity(usuario.getCarrerasIdcarreras()));
    }

    public PersonasDto actualizarEstudianteByUuid(String uuid, PersonasDto personasDto) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");

        Personas persona = usuario.getPersonasIdpersonas();
        persona.setNombres(personasDto.getNombre());
        persona.setApellidomaterno(personasDto.getApellidoMaterno());
        persona.setApellidopaterno(personasDto.getApellidoPaterno());
        persona.setCi(personasDto.getCi());
        persona.setTelefono(personasDto.getTelefono());
        persona.setFotoperfil(personasDto.getFotoPerfil());
        persona.setBannerperfil(personasDto.getBannerPerfil());
        persona.setFechadenacimiento(personasDto.getFechaDeNacimiento());
        persona.setAnioingresouniversidad(personasDto.getAnioIngresoUniversidad());
        persona.setDescripcion(personasDto.getDescripcion());
        persona.setHabilidades(personasDto.getHabilidades());
        persona.setHabilidadesseleccionadas(personasDto.getHabilidadesSeleccionada());
        persona.setExperiencia(personasDto.getExperiencia());
        persona.setRedessociales(personasDto.getRedesSociales());
        Personas personaRetunr = personasDao.save(persona);
        PersonasDto personaDto = PersonasDto.fromEntity(personaRetunr);

        return personaDto;
    }

    public CurriculumsDto agregarCurriculum(String uuid, MultipartFile curriculum,String fullUrl) {
        try {
            System.out.println("subiendo pdf");
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if (!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE"))  throw new RuntimeException("El usuario no es un estudiante");
            NewFileDto newFileDto = minioBl.uploadFile(curriculum, "internship-cv");
            if (newFileDto == null) throw new RuntimeException("Error al subir el curriculum");
            String downloadUrl = generateDownloadUrl("internship-cv", newFileDto.getFileName(),fullUrl);
            Curriculums curriculumEntity = new Curriculums();
            curriculumEntity.setFechacargado(new Date());
            curriculumEntity.setTitulo(curriculum.getOriginalFilename());
            curriculumEntity.setPdfcurriculum(downloadUrl);
            curriculumEntity.setUsuariosIdusuarios(usuario);
            Curriculums curriculumReturn=curriculumsDao.save(curriculumEntity);
            CurriculumsDto curriculumDto = CurriculumsDto.fromEntity(curriculumReturn);
            return curriculumDto;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error al subir el curriculum", e);
        }
    }
    private String generateDownloadUrl(String bucket, String fileName,String fullUrl) {
        return fullUrl +"/api/v1/estudiante/public/files/download/" + fileName;
    }

    public String obtenerCurriculum(String uuid, String curriculumPdf) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        if (!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        Curriculums curriculum = curriculumsDao.findByTituloAndUsuariosIdusuarios(curriculumPdf, usuario);
        if (curriculum == null) throw new RuntimeException("Curriculum no encontrado");
        return curriculum.getPdfcurriculum();
    }

    //TODO: Seleccionar curriculum
    public Boolean postularPasantia(String uuid, Integer curriculumId, Integer idPasantia) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        Pasantias pasantia = pasantiasDao.findById(idPasantia).orElseThrow(() -> new RuntimeException("Pasantia no encontrada"));
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        Curriculums curriculum = curriculumsDao.findById(curriculumId).orElseThrow(() -> new RuntimeException("Curriculum no encontrado"));
        List<Aplicacionespasantias> aplicacionespasantias = aplicacionPasantiasDao.findByUsuariosIdusuariosAndPasantiasIdpasantias(usuario,pasantia);
        if(!aplicacionespasantias.isEmpty()) throw new RuntimeException("Ya aplicaste a esta pasantia");
        
        Aplicacionespasantias aplicacion = new Aplicacionespasantias();
        aplicacion.setUsuariosIdusuarios(usuario);
        aplicacion.setPasantiasIdpasantias(pasantia);
        aplicacion.setFechaaplicacion(new Date());
        aplicacion.setActivo(false);
        aplicacionPasantiasDao.save(aplicacion);
        return true;
    }

    public List<CurriculumsDto> obtenerTodosLosCurriculums(String uuid) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            return curriculumsDao.findByUsuariosIdusuarios(usuario).stream().map(CurriculumsDto::fromEntity).toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener los curriculums",e);
        }
    }
    public CurriculumsDto eliminarCurriculum(String uuid, Integer curriculumId) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            Curriculums curriculum = curriculumsDao.findById(curriculumId).orElseThrow(() -> new RuntimeException("Curriculum no encontrado"));
            if(!curriculum.getUsuariosIdusuarios().equals(usuario)) throw new RuntimeException("El curriculum no pertenece al usuario");
            //Validar que no tenga aplicaciones
            List<Aplicacionespasantias> aplicaciones = aplicacionPasantiasDao.findByCurriculumsIdcurriculums(curriculum);
            if(!aplicaciones.isEmpty()) throw new RuntimeException("El curriculum tiene aplicaciones a pasantias");
            curriculumsDao.delete(curriculum);
            return CurriculumsDto.fromEntity(curriculum);
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el curriculum",e);
        }
    }

    public AplicacionPasantiasDto aplicarPasantia(String uuid, Integer pasantiaId, Integer idCurriculum) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            Curriculums curriculum = curriculumsDao.findById(idCurriculum).orElseThrow(() -> new RuntimeException("Curriculum no encontrado"));
            if(!curriculum.getUsuariosIdusuarios().equals(usuario)) throw new RuntimeException("El curriculum no pertenece al usuario");
            Pasantias pasantia = pasantiasDao.findById(pasantiaId).orElseThrow(() -> new RuntimeException("Pasantia no encontrada"));
            List<Aplicacionespasantias> aplicacionespasantias = aplicacionPasantiasDao.findByUsuariosIdusuariosAndPasantiasIdpasantias(usuario,pasantia);
            if(!aplicacionespasantias.isEmpty()) throw new RuntimeException("Ya aplicaste a esta pasantia");
            Aplicacionespasantias aplicacion = new Aplicacionespasantias();
            aplicacion.setUsuariosIdusuarios(usuario);
            aplicacion.setPasantiasIdpasantias(pasantia);
            aplicacion.setFechaaplicacion(new Date());
            aplicacion.setActivo(false);
            aplicacion.setCurriculumsIdcurriculums(curriculum);
            aplicacion = aplicacionPasantiasDao.save(aplicacion);
            return AplicacionPasantiasDto.fromEntity(aplicacion);
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al aplicar a la pasantia",e);
        }
    }

    public ResponseEntity<byte[]> downloadFile(String idFile) {
        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket("internship-cv")
                            .object(idFile)
                            .build()
            );
            GetObjectResponse fileContent = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("internship-cv")
                            .object(idFile)
                            .build()
            );
            byte[] fileBytes = IOUtils.toByteArray(fileContent);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + stat.object() + "\"")
                    .body(fileBytes);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public UsuarioCompletoDto obtenerEstudianteCompleto(Integer idEstudiante) {
        try{
            Personas estudiante = personasDao.findById(idEstudiante).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            ObjectMapper objectMapper = new ObjectMapper();
            String experienciaString  = estudiante.getExperiencia().toString();
            JsonNode experiencia = objectMapper.readTree(experienciaString);
            estudiante.setExperiencia(experiencia);

            //now with habilidades,habiidadesseleccionadas and redesSociales
            String habilidadesString = estudiante.getHabilidades().toString();
            JsonNode habilidades = objectMapper.readTree(habilidadesString);
            estudiante.setHabilidades(habilidades);

            String habilidadesSeleccionadaString = estudiante.getHabilidadesseleccionadas().toString();
            JsonNode habilidadesSeleccionada = objectMapper.readTree(habilidadesSeleccionadaString);
            estudiante.setHabilidadesseleccionadas(habilidadesSeleccionada);

            String redesSocialesString = estudiante.getRedessociales().toString();
            JsonNode redesSociales = objectMapper.readTree(redesSocialesString);
            estudiante.setRedessociales(redesSociales);
            return new UsuarioCompletoDto(PersonasDto.fromEntity(estudiante),UsuariosDto.fromEntity(estudiante.getUsuariosList().get(0)));
        }catch (RuntimeException e) {
            throw e;
        }catch (Exception e) {
            throw new RuntimeException("Error al obtener el estudiante",e);
        }
    }

    public List<AplicacionPasantiasInformacionPasantiasDto> obtenerAplicacionesPasantias(String uuid) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            List<Aplicacionespasantias> aplicaciones = aplicacionPasantiasDao.findByUsuariosIdusuarios(usuario);
            return aplicaciones.stream().map(AplicacionPasantiasInformacionPasantiasDto::fromEntity).toList();
            
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las aplicaciones a pasantias",e);
        }
    }
}
