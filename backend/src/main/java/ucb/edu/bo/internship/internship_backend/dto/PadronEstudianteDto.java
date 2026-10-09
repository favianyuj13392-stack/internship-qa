package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;
import java.util.Date;

public class PadronEstudianteDto {
    private Integer idpadron;
    private String correo;
    private String nombres;
    private String apellidos;
    private Integer idCarrera;
    private String nombreCarrera;
    private String estado;
    private Integer anioIngreso;
    private String codigoEstudiante;
    private String kcUuid;
    private Integer idUsuario;
    private Date primerAcceso;
    private Date ultimoAcceso;

    public PadronEstudianteDto() {
    }

    public static PadronEstudianteDto fromEntity(PadronEstudiante entity) {
        if (entity == null) return null;
        PadronEstudianteDto dto = new PadronEstudianteDto();
        dto.setIdpadron(entity.getIdpadron());
        dto.setCorreo(entity.getCorreo());
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        if (entity.getCarrerasIdcarreras() != null) {
            dto.setIdCarrera(entity.getCarrerasIdcarreras().getIdcarreras());
            dto.setNombreCarrera(entity.getCarrerasIdcarreras().getNombre());
        }
        dto.setEstado(entity.getEstado());
        dto.setAnioIngreso(entity.getAnioIngreso());
        dto.setCodigoEstudiante(entity.getCodigoEstudiante());
        dto.setKcUuid(entity.getKcUuid());
        if (entity.getUsuariosIdusuarios() != null) {
            dto.setIdUsuario(entity.getUsuariosIdusuarios().getIdusuarios());
        }
        dto.setPrimerAcceso(entity.getPrimerAcceso());
        dto.setUltimoAcceso(entity.getUltimoAcceso());
        return dto;
    }

    public Integer getIdpadron() {
        return idpadron;
    }

    public void setIdpadron(Integer idpadron) {
        this.idpadron = idpadron;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getKcUuid() {
        return kcUuid;
    }

    public void setKcUuid(String kcUuid) {
        this.kcUuid = kcUuid;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getPrimerAcceso() {
        return primerAcceso;
    }

    public void setPrimerAcceso(Date primerAcceso) {
        this.primerAcceso = primerAcceso;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
}
