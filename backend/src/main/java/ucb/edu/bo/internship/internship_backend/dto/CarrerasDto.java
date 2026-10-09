package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Carreras;

public class CarrerasDto {
    private Integer idCarreras;
    private String nombre;
    private String descripcion;
    
    public CarrerasDto() {
    }

    public CarrerasDto(Integer idCarreras, String nombre, String descripcion) {
        this.idCarreras = idCarreras;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdCarreras() {
        return this.idCarreras;
    }

    public void setIdCarreras(Integer idCarreras) {
        this.idCarreras = idCarreras;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "{" +
            " idCarreras='" + getIdCarreras() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }

    public static CarrerasDto fromEntity(Carreras carreras) {
        CarrerasDto carrerasDto = new CarrerasDto();
        carrerasDto.setIdCarreras(carreras.getIdcarreras());
        carrerasDto.setNombre(carreras.getNombre());
        carrerasDto.setDescripcion(carreras.getDescripcion());
        return carrerasDto;
    }
}
