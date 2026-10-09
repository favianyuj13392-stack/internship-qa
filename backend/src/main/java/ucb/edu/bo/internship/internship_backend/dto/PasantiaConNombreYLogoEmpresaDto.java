package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.sql.Date;

public class PasantiaConNombreYLogoEmpresaDto extends PasantiasDto{
    private Integer idInstituciones;
    private String nombre;
    private String logoEmpresa;

    public PasantiaConNombreYLogoEmpresaDto(Integer idInstituciones, String nombre, String logoEmpresa) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.logoEmpresa = logoEmpresa;
    }

    public PasantiaConNombreYLogoEmpresaDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso, Integer idInstituciones, String nombre, String logoEmpresa) {
        super(idPasantias, areas, titulo, descripcion, requisitos, funciones, beneficios, fechaCierre, fechaIngreso);
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.logoEmpresa = logoEmpresa;
    }

    public PasantiaConNombreYLogoEmpresaDto(Pasantias pasantias, Integer idInstituciones, String nombre, String logoEmpresa) {
        super(pasantias);
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.logoEmpresa = logoEmpresa;
    }
    public static PasantiaConNombreYLogoEmpresaDto fromEntity(Pasantias pasantias) {
        return new PasantiaConNombreYLogoEmpresaDto(
                pasantias.getIdpasantias(),
                pasantias.getAreas(),
                pasantias.getTitulo(),
                pasantias.getDescripcion(),
                pasantias.getRequisitos(),
                pasantias.getFunciones(),
                pasantias.getBeneficios(),
                new Date (pasantias.getFechacierre().getTime()),
                new Date (pasantias.getFechaingreso().getTime()),
                pasantias.getInstitucionesIdinstituciones().getIdinstituciones(),
                pasantias.getInstitucionesIdinstituciones().getNombre(),
                pasantias.getInstitucionesIdinstituciones().getLogoempresa()
        );
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

    public String getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    @Override
    public String toString() {
        return "PasantiaConNombreYLogoEmpresaDto{" +
                "idInstituciones=" + idInstituciones +
                ", nombre='" + nombre + '\'' +
                ", logoEmpresa='" + logoEmpresa + '\'' +
                '}';
    }
}
