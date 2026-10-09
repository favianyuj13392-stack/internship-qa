package ucb.edu.bo.internship.internship_backend.dto;

public class RecuentoPaginaInicioDto {
    private Long cantidadInstituciones;
    private Long cantidadEstudiantes;
    private Long cantidadPasantias;

    public RecuentoPaginaInicioDto() {
    }

    public RecuentoPaginaInicioDto(Long cantidadInstituciones, Long cantidadEstudiantes, Long cantidadPasantias) {
        this.cantidadInstituciones = cantidadInstituciones;
        this.cantidadEstudiantes = cantidadEstudiantes;
        this.cantidadPasantias = cantidadPasantias;
    }

    public Long getCantidadInstituciones() {
        return cantidadInstituciones;
    }

    public void setCantidadInstituciones(Long cantidadInstituciones) {
        this.cantidadInstituciones = cantidadInstituciones;
    }

    public Long getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public void setCantidadEstudiantes(Long cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    public Long getCantidadPasantias() {
        return cantidadPasantias;
    }

    public void setCantidadPasantias(Long cantidadPasantias) {
        this.cantidadPasantias = cantidadPasantias;
    }

    @Override
    public String toString() {
        return "RecuentoPaginaInicioDto{" +
                "cantidadInstituciones=" + cantidadInstituciones +
                ", cantidadEstudiantes=" + cantidadEstudiantes +
                ", cantidadPasantias=" + cantidadPasantias +
                '}';
    }
}
