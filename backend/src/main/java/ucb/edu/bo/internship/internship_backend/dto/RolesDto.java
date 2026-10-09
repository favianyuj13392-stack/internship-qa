package ucb.edu.bo.internship.internship_backend.dto;

public class RolesDto {
    private Integer idRoles;
    private String rol;
    private String descripcion;

    public RolesDto() {
    }

    public RolesDto(Integer idRoles, String rol, String descripcion) {
        this.idRoles = idRoles;
        this.rol = rol;
        this.descripcion = descripcion;
    }

    public Integer getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
            " idRoles='" + getIdRoles() + "'" +
            ", rol='" + getRol() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
    
}
