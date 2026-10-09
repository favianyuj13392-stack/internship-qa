package ucb.edu.bo.internship.internship_backend.dto;

import java.util.List;

public class PasantiaConEmpresaYPostulantes {
    private PasantiasDto pasantiasDto;
    private Boolean estadoPasantia;
    private List<PersonasDto> listaPostulantes;
    private InstitucionesDto institucionesDto;
    private List<AplicacionPasantiasDto> aplicacionPasantiasDto;

    public PasantiaConEmpresaYPostulantes(PasantiasDto pasantiasDto, Boolean estadoPasantia, List<PersonasDto> listaPostulantes, InstitucionesDto institucionesDto, List<AplicacionPasantiasDto> aplicacionPasantiasDto) {
        this.pasantiasDto = pasantiasDto;
        this.estadoPasantia = estadoPasantia;
        this.listaPostulantes = listaPostulantes;
        this.institucionesDto = institucionesDto;
        this.aplicacionPasantiasDto = aplicacionPasantiasDto;
    }
    public PasantiaConEmpresaYPostulantes(){}

    public PasantiasDto getPasantiasDto() {
        return pasantiasDto;
    }

    public void setPasantiasDto(PasantiasDto pasantiasDto) {
        this.pasantiasDto = pasantiasDto;
    }

    public Boolean getEstadoPasantia() {
        return estadoPasantia;
    }

    public void setEstadoPasantia(Boolean estadoPasantia) {
        this.estadoPasantia = estadoPasantia;
    }

    public List<PersonasDto> getListaPostulantes() {
        return listaPostulantes;
    }

    public void setListaPostulantes(List<PersonasDto> listaPostulantes) {
        this.listaPostulantes = listaPostulantes;
    }

    public InstitucionesDto getInstitucionesDto() {
        return institucionesDto;
    }

    public void setInstitucionesDto(InstitucionesDto institucionesDto) {
        this.institucionesDto = institucionesDto;
    }

    public List<AplicacionPasantiasDto> getAplicacionPasantiasDto() {
        return aplicacionPasantiasDto;
    }

    public void setAplicacionPasantiasDto(List<AplicacionPasantiasDto> aplicacionPasantiasDto) {
        this.aplicacionPasantiasDto = aplicacionPasantiasDto;
    }

    @Override
    public String toString() {
        return "PasantiaConEmpresaYPostulantes{" +
                "pasantiasDto=" + pasantiasDto +
                ", estadoPasantia=" + estadoPasantia +
                ", listaPostulantes=" + listaPostulantes +
                ", institucionesDto=" + institucionesDto +
                ", aplicacionPasantiasDto=" + aplicacionPasantiasDto +
                '}';
    }
}
