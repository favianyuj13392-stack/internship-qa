package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Personas;

import java.sql.Date;

public class PersonasDto {
    private Integer idPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer telefono;
    private String ci;
    private String fotoPerfil;
    private String bannerPerfil;


    private Integer anioIngresoUniversidad;
    private String descripcion;
    private Object habilidades;
    private Object habilidadesSeleccionada;
    private Object experiencia;
    private Date fechaDeNacimiento;
    private Object redesSociales;

    public PersonasDto() {
    }

    
    public PersonasDto(Integer idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, Integer telefono, String ci, String fotoPerfil, Integer anioIngresoUniversidad, String descripcion, Object habilidades, Object habilidadesSeleccionada, Object experiencia, Date fechaDeNacimiento, Object redesSociales, String bannerPerfil) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.ci = ci;
        this.fotoPerfil = fotoPerfil;
        this.anioIngresoUniversidad = anioIngresoUniversidad;
        this.descripcion = descripcion;
        this.habilidades = habilidades;
        this.habilidadesSeleccionada = habilidadesSeleccionada;
        this.experiencia = experiencia;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.redesSociales = redesSociales;
        this.bannerPerfil = bannerPerfil;
    }

    public Integer getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCi() {
        return this.ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFotoPerfil() {
        return this.fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBannerPerfil() {
        return this.bannerPerfil;
    }

    public void setBannerPerfil(String bannerperfil) {
        this.bannerPerfil = bannerperfil;
    }

    public Integer getAnioIngresoUniversidad() {
        return this.anioIngresoUniversidad;
    }

    public void setAnioIngresoUniversidad(Integer anioIngresoUniversidad) {
        this.anioIngresoUniversidad = anioIngresoUniversidad;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getHabilidades() {
        return this.habilidades;
    }

    public void setHabilidades(Object habilidades) {
        this.habilidades = habilidades;
    }

    public Object getHabilidadesSeleccionada() {
        return this.habilidadesSeleccionada;
    }

    public void setHabilidadesSeleccionada(Object habilidadesSeleccionada) {
        this.habilidadesSeleccionada = habilidadesSeleccionada;
    }

    public Object getExperiencia() {
        return this.experiencia;
    }

    public void setExperiencia(Object experiencia) {
        this.experiencia = experiencia;
    }

    public Date getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Object getRedesSociales() {
        return this.redesSociales;
    }

    public void setRedesSociales(Object redesSociales) {
        this.redesSociales = redesSociales;
    }

    @Override
    public String toString() {
        return "{" +
            " idPersona='" + getIdPersona() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", ci='" + getCi() + "'" +
            ", fotoPerfil='" + getFotoPerfil() + "'" +
            ", anioIngresoUniversidad='" + getAnioIngresoUniversidad() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", habilidades='" + getHabilidades() + "'" +
            ", habilidadesSeleccionada='" + getHabilidadesSeleccionada() + "'" +
            ", experiencia='" + getExperiencia() + "'" +
            ", fechaDeNacimiento='" + getFechaDeNacimiento() + "'" +
            ", redesSociales='" + getRedesSociales() + "'" +
            "}";
    }
    
    public  Personas toEntity (){
        Personas personas = new Personas();
        personas.setIdpersonas(this.getIdPersona());
        personas.setNombres(this.getNombre());
        personas.setApellidopaterno(this.getApellidoPaterno());
        personas.setApellidomaterno(this.getApellidoMaterno());
        personas.setTelefono(this.getTelefono());
        personas.setCi(this.getCi());
        personas.setFotoperfil(this.getFotoPerfil());
        personas.setBannerperfil(this.getBannerPerfil());
        personas.setAnioingresouniversidad(this.getAnioIngresoUniversidad());
        personas.setDescripcion(this.getDescripcion());
        personas.setHabilidades(this.getHabilidades());
        personas.setHabilidadesseleccionadas(this.getHabilidadesSeleccionada());
        personas.setExperiencia(this.getExperiencia());
        personas.setFechadenacimiento(this.getFechaDeNacimiento());
        personas.setRedessociales(this.getRedesSociales());
        return personas;
    }

    public static PersonasDto fromEntity(Personas personas){
        PersonasDto personasDto = new PersonasDto();
        personasDto.setIdPersona(personas.getIdpersonas());
        personasDto.setNombre(personas.getNombres());
        personasDto.setApellidoPaterno(personas.getApellidopaterno());
        personasDto.setApellidoMaterno(personas.getApellidomaterno());
        personasDto.setTelefono(personas.getTelefono());
        personasDto.setCi(personas.getCi());
        personasDto.setFotoPerfil(personas.getFotoperfil());
        personasDto.setBannerPerfil(personas.getBannerperfil());
        personasDto.setAnioIngresoUniversidad(personas.getAnioingresouniversidad());
        personasDto.setDescripcion(personas.getDescripcion());
        personasDto.setHabilidades(personas.getHabilidades());
        personasDto.setHabilidadesSeleccionada(personas.getHabilidadesseleccionadas());
        personasDto.setExperiencia(personas.getExperiencia());
        personasDto.setFechaDeNacimiento(new Date(personas.getFechadenacimiento().getTime()));
        personasDto.setRedesSociales(personas.getRedessociales());
        return personasDto;
    }

}
