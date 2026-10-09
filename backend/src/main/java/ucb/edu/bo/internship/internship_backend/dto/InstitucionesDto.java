package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import java.util.LinkedHashMap;
import java.util.List;

public class InstitucionesDto {
    private Integer idInstituciones;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String fotoInstitucion;
    private String correo;
    private List<String> sectores;
    private String logoEmpresa;
    private List<String> fotos;
    private LinkedHashMap<String,String> redesSociales;
    private Boolean activo;
    private Long cantidadPasantias;

    public InstitucionesDto() {
    }

    public InstitucionesDto(Integer idInstituciones, String nombre, String descripcion, String direccion, String fotoInstitucion, String correo, List<String> sectores, String logoEmpresa, List<String> fotos, LinkedHashMap<String,String> redesSociales, Boolean activo) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoInstitucion = fotoInstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoEmpresa = logoEmpresa;
        this.fotos = fotos;
        this.redesSociales = redesSociales;
        this.activo = activo;
    }
    public InstitucionesDto(Integer idInstituciones, String nombre, String descripcion, String direccion, String fotoInstitucion, String correo, List<String> sectores, String logoEmpresa, List<String> fotos, LinkedHashMap<String,String> redesSociales, Boolean activo, Long cantidadPasantias)
    {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoInstitucion = fotoInstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoEmpresa = logoEmpresa;
        this.fotos = fotos;
        this.redesSociales = redesSociales;
        this.activo = activo;
        this.cantidadPasantias = cantidadPasantias;
    }

    public InstitucionesDto(Instituciones instituciones) {
        this.idInstituciones = instituciones.getIdinstituciones();
        this.nombre = instituciones.getNombre();
        this.descripcion = instituciones.getDescripcion();
        this.direccion = instituciones.getDireccion();
        this.fotoInstitucion = instituciones.getFotoinstitucion();
        this.correo = instituciones.getCorreo();
        this.sectores = instituciones.getSectores();
        this.logoEmpresa = instituciones.getLogoempresa();
        this.fotos = instituciones.getFotos();
        this.redesSociales = instituciones.getRedessociales();
        this.activo = instituciones.getActivo();
    }
    public InstitucionesDto(Instituciones instituciones, Long cantidadPasantias) {
        this.idInstituciones = instituciones.getIdinstituciones();
        this.nombre = instituciones.getNombre();
        this.descripcion = instituciones.getDescripcion();
        this.direccion = instituciones.getDireccion();
        this.fotoInstitucion = instituciones.getFotoinstitucion();
        this.correo = instituciones.getCorreo();
        this.sectores = instituciones.getSectores();
        this.logoEmpresa = instituciones.getLogoempresa();
        this.fotos = instituciones.getFotos();
        this.redesSociales = instituciones.getRedessociales();
        this.activo = instituciones.getActivo();
        this.cantidadPasantias = cantidadPasantias;
    }

    public InstitucionesDto(Integer idinstituciones, String nombre, String descripcion, String direccion, String fotoinstitucion, String correo, List<String> sectores, String logoempresa, List<String> fotos, LinkedHashMap<String, String> redessociales) {
        this.idInstituciones = idinstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoInstitucion = fotoinstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoEmpresa = logoempresa;
        this.fotos = fotos;
        this.redesSociales = redessociales;
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoInstitucion() {
        return this.fotoInstitucion;
    }

    public void setFotoInstitucion(String fotoInstitucion) {
        this.fotoInstitucion = fotoInstitucion;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<String> getSectores() {
        return this.sectores;
    }

    public void setSectores(List<String> sectores) {
        this.sectores = sectores;
    }

    public String getLogoEmpresa() {
        return this.logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public List<String> getFotos() {
        return this.fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public LinkedHashMap<String,String> getRedesSociales() {
        return this.redesSociales;
    }

    public void setRedesSociales(LinkedHashMap<String,String> redesSociales) {
        this.redesSociales = redesSociales;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getCantidadPasantias() {
        return cantidadPasantias;
    }

    public void setCantidadPasantias(Long cantidadPasantias) {
        this.cantidadPasantias = cantidadPasantias;
    }

    @Override
    public String toString() {
        return "{" +
            " idInstituciones='" + getIdInstituciones() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fotoInstitucion='" + getFotoInstitucion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", sectores='" + getSectores() + "'" +
            ", logoEmpresa='" + getLogoEmpresa() + "'" +
            ", fotos='" + getFotos() + "'" +
            ", redesSociales='" + getRedesSociales() + "'" +
            "}";
    }

    public static InstitucionesDto fromEntity(Instituciones institucion){
        return new InstitucionesDto(
                institucion.getIdinstituciones(),
                institucion.getNombre(),
                institucion.getDescripcion(),
                institucion.getDireccion(),
                institucion.getFotoinstitucion(),
                institucion.getCorreo(),
                institucion.getSectores(),
                institucion.getLogoempresa(),
                institucion.getFotos(),
                institucion.getRedessociales(),
                institucion.getActivo());
    }

    public Instituciones toEntity(){
        Instituciones institucion = new Instituciones();
        institucion.setIdinstituciones(this.idInstituciones);
        institucion.setNombre(this.nombre);
        institucion.setDescripcion(this.descripcion != null ? this.descripcion : "");
        institucion.setDireccion(this.direccion);
        institucion.setFotoinstitucion(this.fotoInstitucion != null ? this.fotoInstitucion : "");
        institucion.setCorreo(this.correo);
        institucion.setSectores(this.sectores != null ? this.sectores : new java.util.ArrayList<>());
        institucion.setLogoempresa(this.logoEmpresa != null ? this.logoEmpresa : "");
        institucion.setFotos(this.fotos != null ? this.fotos : new java.util.ArrayList<>());
        institucion.setRedessociales(this.redesSociales != null ? this.redesSociales : new LinkedHashMap<>());
        institucion.setActivo(this.activo != null ? this.activo : false);
        return institucion;
    }
    
    
}
