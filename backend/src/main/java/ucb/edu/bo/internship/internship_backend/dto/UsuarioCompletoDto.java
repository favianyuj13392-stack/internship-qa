package ucb.edu.bo.internship.internship_backend.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;
import ucb.edu.bo.internship.internship_backend.entity.Personas;
import ucb.edu.bo.internship.internship_backend.entity.Seleccionaplicante;

public class UsuarioCompletoDto {
    private PersonasDto persona;
    private UsuariosDto usuario;
    private AplicacionPasantiasDto aplicacionPasantia;
    private SeleccionAplicanteDto seleccionAplicante;

    public UsuarioCompletoDto(PersonasDto persona, UsuariosDto usuario, AplicacionPasantiasDto aplicacionPasantia) {
        this.persona = persona;
        this.usuario = usuario;
        this.aplicacionPasantia = aplicacionPasantia;
    }
    public UsuarioCompletoDto(PersonasDto persona, UsuariosDto usuario){
        this.persona = persona;
        this.usuario = usuario;
    }
    public UsuarioCompletoDto(PersonasDto persona, UsuariosDto usuario, AplicacionPasantiasDto aplicacionPasantia, SeleccionAplicanteDto seleccionAplicante) {
        this.persona = persona;
        this.usuario = usuario;
        this.aplicacionPasantia = aplicacionPasantia;
        this.seleccionAplicante = seleccionAplicante;
    }
    public UsuarioCompletoDto(){}

    public PersonasDto getPersona() {
        return persona;
    }

    public void setPersona(PersonasDto persona) {
        this.persona = persona;
    }

    public UsuariosDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDto usuario) {
        this.usuario = usuario;
    }

    public AplicacionPasantiasDto getAplicacionPasantia() {
        return aplicacionPasantia;
    }

    public void setAplicacionPasantia(AplicacionPasantiasDto aplicacionPasantia) {
        this.aplicacionPasantia = aplicacionPasantia;
    }

    public SeleccionAplicanteDto getSeleccionAplicante() {
        return seleccionAplicante;
    }

    public void setSeleccionAplicante(SeleccionAplicanteDto seleccionAplicante) {
        this.seleccionAplicante = seleccionAplicante;
    }

    public static UsuarioCompletoDto fromEntity(Aplicacionespasantias aplicacionespasantias){
        try{
            SeleccionAplicanteDto seleccionAplicanteDto = null;
            if(!aplicacionespasantias.getSeleccionaplicanteList().isEmpty()){
                Seleccionaplicante seleccionaplicante = aplicacionespasantias.getSeleccionaplicanteList().get(0);
                seleccionAplicanteDto = SeleccionAplicanteDto.fromEntityWithOutHora(seleccionaplicante);
            }
            Personas estudiante = aplicacionespasantias.getUsuariosIdusuarios().getPersonasIdpersonas();
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
            return new UsuarioCompletoDto(
                    PersonasDto.fromEntity(estudiante),
                    UsuariosDto.fromEntity(aplicacionespasantias.getUsuariosIdusuarios()),
                    AplicacionPasantiasDto.fromEntity(aplicacionespasantias),
                    seleccionAplicanteDto
            );
        }catch (Exception e) {
            throw new RuntimeException("Error al convertir Aplicacionespasantias a UsuarioCompletoDto: "+e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "UsuarioCompletoDto{" +
                "persona=" + persona +
                ", usuario=" + usuario +
                ", aplicacionPasantia=" + aplicacionPasantia +
                ", seleccionAplicante=" + seleccionAplicante +
                '}';
    }
}
