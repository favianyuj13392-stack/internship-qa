package ucb.edu.bo.internship.internship_backend.dto;

public class InstitucionNombreDto {
private Integer idInstituciones;
    private String nombre;
    private String logoEmpresa;

    public InstitucionNombreDto() {
    }

    public InstitucionNombreDto(Integer idInstituciones, String nombre,String logoEmpresa) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.logoEmpresa = logoEmpresa;
    }

    public Integer getIdInstituciones() {
        return this.idInstituciones;
    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    @Override
    public String toString() {
        return "InstitucionNombreDto{" +
                "idInstituciones=" + idInstituciones +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
