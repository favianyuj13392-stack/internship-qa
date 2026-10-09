package ucb.edu.bo.internship.internship_backend.dto;

import java.util.List;

public class PasantiaConPostulantesDto {
    private PasantiasDto pasantiasDto;
    private List<UsuarioCompletoDto> postulantes;
    private InstitucionesDto institucion;
    private Boolean activoPasantia;

    public PasantiaConPostulantesDto(PasantiasDto pasantiasDto, List<UsuarioCompletoDto> postulantes,InstitucionesDto institucion,Boolean activoPasantia) {
        this.pasantiasDto = pasantiasDto;
        this.postulantes = postulantes;
        this.institucion = institucion;
        this.activoPasantia = activoPasantia;
    }
    public PasantiaConPostulantesDto(){}

    public PasantiasDto getPasantiasDto() {
        return pasantiasDto;
    }

    public void setPasantiasDto(PasantiasDto pasantiasDto) {
        this.pasantiasDto = pasantiasDto;
    }

    public List<UsuarioCompletoDto> getPostulantes() {
        return postulantes;
    }

    public void setPostulantes(List<UsuarioCompletoDto> postulantes) {
        this.postulantes = postulantes;
    }

    public InstitucionesDto getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionesDto institucion) {
        this.institucion = institucion;
    }

    public Boolean getActivoPasantia() {
        return activoPasantia;
    }

    public void setActivoPasantia(Boolean activoPasantia) {
        this.activoPasantia = activoPasantia;
    }

    @Override
    public String toString() {
        return "PasantiaConPostulantesDto{" +
                "pasantiasDto=" + pasantiasDto +
                ", postulantes=" + postulantes +
                ", institucion=" + institucion +
                ", activoPasantia=" + activoPasantia +
                '}';
    }
}
