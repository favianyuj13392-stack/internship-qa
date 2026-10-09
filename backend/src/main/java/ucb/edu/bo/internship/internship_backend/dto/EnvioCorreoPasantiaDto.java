package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.EnvioCorreoPasantia;
import java.util.Date;

public class EnvioCorreoPasantiaDto {
    private Integer idenvio;
    private Integer idPasantia;
    private String tituloPasantia;
    private Integer idPadron;
    private String correoDestinatario;
    private String estado;
    private Date fechaEnvio;
    private Integer intentos;
    private String errorMensaje;

    public EnvioCorreoPasantiaDto() {
    }

    public static EnvioCorreoPasantiaDto fromEntity(EnvioCorreoPasantia entity) {
        if (entity == null) return null;
        EnvioCorreoPasantiaDto dto = new EnvioCorreoPasantiaDto();
        dto.setIdenvio(entity.getIdenvio());
        if (entity.getPasantia() != null) {
            dto.setIdPasantia(entity.getPasantia().getIdpasantias());
            dto.setTituloPasantia(entity.getPasantia().getTitulo());
        }
        if (entity.getPadronEstudiante() != null) {
            dto.setIdPadron(entity.getPadronEstudiante().getIdpadron());
        }
        dto.setCorreoDestinatario(entity.getCorreoDestinatario());
        dto.setEstado(entity.getEstado());
        dto.setFechaEnvio(entity.getFechaEnvio());
        dto.setIntentos(entity.getIntentos());
        dto.setErrorMensaje(entity.getErrorMensaje());
        return dto;
    }

    public Integer getIdenvio() {
        return idenvio;
    }

    public void setIdenvio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public Integer getIdPasantia() {
        return idPasantia;
    }

    public void setIdPasantia(Integer idPasantia) {
        this.idPasantia = idPasantia;
    }

    public String getTituloPasantia() {
        return tituloPasantia;
    }

    public void setTituloPasantia(String tituloPasantia) {
        this.tituloPasantia = tituloPasantia;
    }

    public Integer getIdPadron() {
        return idPadron;
    }

    public void setIdPadron(Integer idPadron) {
        this.idPadron = idPadron;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public String getErrorMensaje() {
        return errorMensaje;
    }

    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }
}
