package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;

public class PasantiasConInstitucionDto extends PasantiasDto{
    private InstitucionesDto institucion;

    public PasantiasConInstitucionDto() {
    }

    public PasantiasConInstitucionDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso, InstitucionesDto institucion) {
        super(idPasantias, areas, titulo, descripcion, requisitos, funciones, beneficios, fechaCierre, fechaIngreso);
        this.institucion = institucion;
    }

    public InstitucionesDto getInstitucion() {
        return this.institucion;
    }

    public void setInstitucion(InstitucionesDto institucion) {
        this.institucion = institucion;
    }

    @Override
    public String toString() {
        return "{" +
            " institucion='" + getInstitucion() + "'" +
            "}";
    }
}
