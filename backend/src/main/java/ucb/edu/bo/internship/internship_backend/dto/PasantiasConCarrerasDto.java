package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;
import java.util.List;

public class PasantiasConCarrerasDto extends PasantiasDto{
    private List<CarrerasDto> carreras;

    public PasantiasConCarrerasDto() {
    }

    public PasantiasConCarrerasDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso, List<CarrerasDto> carreras) {
        super(idPasantias, areas, titulo, descripcion, requisitos, funciones, beneficios, fechaCierre, fechaIngreso);
        this.carreras = carreras;
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
            " carreras='" + getCarreras() + "'" +
            "}";
    }
}
