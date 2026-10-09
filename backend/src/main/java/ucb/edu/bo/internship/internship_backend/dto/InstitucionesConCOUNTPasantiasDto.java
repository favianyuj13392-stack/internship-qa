package ucb.edu.bo.internship.internship_backend.dto;


public class InstitucionesConCOUNTPasantiasDto {
    private Integer idInstituciones;
    private String nombre;
    private Long cantidadPasantias;
    private String logoEmpresa;

    public InstitucionesConCOUNTPasantiasDto() {
    }

    public InstitucionesConCOUNTPasantiasDto(Integer idInstituciones, String nombre, Long cantidadPasantias, String logoEmpresa) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.cantidadPasantias = cantidadPasantias;
        this.logoEmpresa = logoEmpresa;
    }

    public String getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public Integer getIdInstituciones() {
        return idInstituciones;

    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidadPasantias() {
        return cantidadPasantias;
    }

    public void setCantidadPasantias(Long cantidadPasantias) {
        this.cantidadPasantias = cantidadPasantias;
    }

    @Override
    public String toString() {
        return "InstitucionesConCOUNTPasantiasDto{" +
                "idInstituciones=" + idInstituciones +
                ", nombre='" + nombre + '\'' +
                ", cantidadPasantias=" + cantidadPasantias +
                '}';
    }
}

