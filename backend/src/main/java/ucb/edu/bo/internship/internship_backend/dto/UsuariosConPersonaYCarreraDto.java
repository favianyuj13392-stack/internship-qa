package ucb.edu.bo.internship.internship_backend.dto;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UsuariosConPersonaYCarreraDto extends UsuariosDto{

    private PersonasDto persona;
    private CarrerasDto carrera;

    public UsuariosConPersonaYCarreraDto() {
    }

    public UsuariosConPersonaYCarreraDto(Integer idUsuarios, String kc_UUID, String correo, Date fechaRegistro, Time horaRegistro, Integer idRoles, Integer idPersonas, Integer idCarreras, PersonasDto persona, CarrerasDto carrera) {
        super(idUsuarios, kc_UUID, correo, fechaRegistro, horaRegistro, idRoles, idPersonas, idCarreras);
        this.persona = persona;
        this.carrera = carrera;
    }

    public UsuariosConPersonaYCarreraDto(UsuariosDto usuariosDto, PersonasDto persona, CarrerasDto carrera) {
        super(usuariosDto.getIdUsuarios(), usuariosDto.getKc_UUID(), usuariosDto.getCorreo(), usuariosDto.getFechaRegistro(), usuariosDto.getHoraRegistro(), usuariosDto.getIdRoles(), usuariosDto.getIdPersonas(), usuariosDto.getIdCarreras());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String experienciaString  = persona.getExperiencia().toString();
            JsonNode experiencia = objectMapper.readTree(experienciaString);
            persona.setExperiencia(experiencia);

            //now with habilidades,habiidadesseleccionadas and redesSociales
            String habilidadesString = persona.getHabilidades().toString();
            JsonNode habilidades = objectMapper.readTree(habilidadesString);
            persona.setHabilidades(habilidades);

            String habilidadesSeleccionadaString = persona.getHabilidadesSeleccionada().toString();
            JsonNode habilidadesSeleccionada = objectMapper.readTree(habilidadesSeleccionadaString);
            persona.setHabilidadesSeleccionada(habilidadesSeleccionada);

            String redesSocialesString = persona.getRedesSociales().toString();
            JsonNode redesSociales = objectMapper.readTree(redesSocialesString);
            persona.setRedesSociales(redesSociales);


        } catch (IOException e) {
            throw new RuntimeException("Error deserializing malla", e);
        }

        this.persona = persona;
        
        this.carrera = carrera;
    }

    public UsuariosConPersonaYCarreraDto(UsuariosDto usuariosDto, PersonasDto persona) {
        super(usuariosDto.getIdUsuarios(), usuariosDto.getKc_UUID(), usuariosDto.getCorreo(), usuariosDto.getFechaRegistro(), usuariosDto.getHoraRegistro(), usuariosDto.getIdRoles(), usuariosDto.getIdPersonas(),null);
        this.persona = persona;

    }


    public PersonasDto getPersona() {
        return this.persona;
    }

    public void setPersona(PersonasDto persona) {
        this.persona = persona;
    }

    public CarrerasDto getCarrera() {
        return this.carrera;
    }

    public void setCarrera(CarrerasDto carrera) {
        this.carrera = carrera;
    }

}
