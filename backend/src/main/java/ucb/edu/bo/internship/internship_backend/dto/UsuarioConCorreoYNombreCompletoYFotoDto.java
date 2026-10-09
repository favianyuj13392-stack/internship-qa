package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;

public class UsuarioConCorreoYNombreCompletoYFotoDto {
    private Integer idUsuariosInstitucion;
    private Integer idUsuarios;
    private Integer idPersona;
    private String correo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fotoPerfil;
    private Integer idInstituciones;
    private String nombreInstitucion;
    private String direccion;
    private String logoEmpresa;
    private String correoInstitucion;
    private Boolean activoEmpresa;
    public UsuarioConCorreoYNombreCompletoYFotoDto(Integer idUsuarios, Integer idPersona, String correo, String nombre, String apellidoPaterno, String apellidoMaterno, String fotoPerfil) {
        this.idUsuarios = idUsuarios;
        this.idPersona = idPersona;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fotoPerfil = fotoPerfil;
    }
    public UsuarioConCorreoYNombreCompletoYFotoDto() {
    }

    public UsuarioConCorreoYNombreCompletoYFotoDto(Integer idUsuariosInstitucion, Integer idUsuarios, Integer idPersona, String correo, String nombre, String apellidoPaterno, String apellidoMaterno, String fotoPerfil, Integer idInstituciones, String nombreInstitucion, String direccion, String logoEmpresa, String correoInstitucion, Boolean activoEmpresa) {
        this.idUsuariosInstitucion = idUsuariosInstitucion;
        this.idUsuarios = idUsuarios;
        this.idPersona = idPersona;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fotoPerfil = fotoPerfil;
        this.idInstituciones = idInstituciones;
        this.nombreInstitucion = nombreInstitucion;
        this.direccion = direccion;
        this.logoEmpresa = logoEmpresa;
        this.correoInstitucion = correoInstitucion;
        this.activoEmpresa = activoEmpresa;
    }

    public UsuarioConCorreoYNombreCompletoYFotoDto(Usuariosinstituciones usuariosinstituciones) {
        this.idUsuariosInstitucion = usuariosinstituciones.getIdusuariosinstituciones();
        this.idUsuarios = usuariosinstituciones.getUsuariosIdusuarios().getIdusuarios();
        this.idPersona = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getIdpersonas();
        this.correo = usuariosinstituciones.getUsuariosIdusuarios().getCorreo();
        this.nombre = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres();
        this.apellidoPaterno = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getApellidopaterno();
        this.apellidoMaterno = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getApellidomaterno();
        this.fotoPerfil = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getFotoperfil();
        this.idInstituciones = usuariosinstituciones.getInstitucionesIdinstituciones().getIdinstituciones();
        this.nombreInstitucion = usuariosinstituciones.getInstitucionesIdinstituciones().getNombre();
        this.direccion = usuariosinstituciones.getInstitucionesIdinstituciones().getDireccion();
        this.logoEmpresa = usuariosinstituciones.getInstitucionesIdinstituciones().getLogoempresa();
        this.correoInstitucion = usuariosinstituciones.getInstitucionesIdinstituciones().getCorreo();
        this.activoEmpresa = usuariosinstituciones.getActivo();
    }

    public Integer getIdUsuariosInstitucion() {
        return idUsuariosInstitucion;
    }

    public void setIdUsuariosInstitucion(Integer idUsuariosInstitucion) {
        this.idUsuariosInstitucion = idUsuariosInstitucion;
    }

    public Integer getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Integer getIdInstituciones() {
        return idInstituciones;
    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public String getCorreoInstitucion() {
        return correoInstitucion;
    }

    public void setCorreoInstitucion(String correoInstitucion) {
        this.correoInstitucion = correoInstitucion;
    }

    public Boolean getActivoEmpresa() {
        return activoEmpresa;
    }

    public void setActivoEmpresa(Boolean activoEmpresa) {
        this.activoEmpresa = activoEmpresa;
    }

    @Override
    public String toString() {
        return "UsuarioConCorreoYNombreCompletoYFotoDto{" +
                "idUsuariosInstitucion=" + idUsuariosInstitucion +
                "idUsuarios=" + idUsuarios +
                ", idPersona=" + idPersona +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", idInstituciones=" + idInstituciones +
                ", nombreInstitucion='" + nombreInstitucion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", logoEmpresa='" + logoEmpresa + '\'' +
                ", correoInstitucion='" + correoInstitucion + '\'' +
                ", activoEmpresa=" + activoEmpresa +
                '}';
    }
}
