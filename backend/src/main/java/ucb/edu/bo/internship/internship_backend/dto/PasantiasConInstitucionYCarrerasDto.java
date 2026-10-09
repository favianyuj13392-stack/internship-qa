package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.sql.Date;
import java.util.List;

public class PasantiasConInstitucionYCarrerasDto extends PasantiasDto{
    private InstitucionesDto institucion;
    private List<CarrerasDto> carreras;

    public PasantiasConInstitucionYCarrerasDto() {
    }

    public PasantiasConInstitucionYCarrerasDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso, InstitucionesDto institucion, List<CarrerasDto> carreras) {
        super(idPasantias, areas, titulo, descripcion, requisitos, funciones, beneficios, fechaCierre, fechaIngreso);
        this.institucion = institucion;
        this.carreras = carreras;
    }

    public PasantiasConInstitucionYCarrerasDto(PasantiasDto pasantiasDto, InstitucionesDto institucion, List<CarrerasDto> carreras) {
        super(pasantiasDto.getIdPasantias(), pasantiasDto.getAreas(), pasantiasDto.getTitulo(), pasantiasDto.getDescripcion(), pasantiasDto.getRequisitos(), pasantiasDto.getFunciones(), pasantiasDto.getBeneficios(), pasantiasDto.getFechaCierre(), pasantiasDto.getFechaIngreso());
        this.institucion = institucion;
        this.carreras = carreras;
    }

    public InstitucionesDto getInstitucion() {
        return this.institucion;
    }

    public void setInstitucion(InstitucionesDto institucion) {
        this.institucion = institucion;
    }

    public List<CarrerasDto> getCarreras() {
        return this.carreras;
    }

    public void setCarreras(List<CarrerasDto> carreras) {
        this.carreras = carreras;
    }

    @Override
    public String toString() {
        return "{" +
            " institucion='" + getInstitucion() + "'" +
            " carreras='" + getCarreras() + "'" +
            "}";
    }

}
