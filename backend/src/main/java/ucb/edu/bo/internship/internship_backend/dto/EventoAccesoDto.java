package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.EventoAcceso;
import java.util.Date;

public class EventoAccesoDto {
    private Integer idevento;
    private Integer idPadron;
    private String correo;
    private Date fechaHora;
    private String origen;
    private String ip;

    public EventoAccesoDto() {
    }

    public static EventoAccesoDto fromEntity(EventoAcceso entity) {
        if (entity == null) return null;
        EventoAccesoDto dto = new EventoAccesoDto();
        dto.setIdevento(entity.getIdevento());
        if (entity.getPadronEstudiante() != null) {
            dto.setIdPadron(entity.getPadronEstudiante().getIdpadron());
            dto.setCorreo(entity.getPadronEstudiante().getCorreo());
        }
        dto.setFechaHora(entity.getFechaHora());
        dto.setOrigen(entity.getOrigen());
        dto.setIp(entity.getIp());
        return dto;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public Integer getIdPadron() {
        return idPadron;
    }

    public void setIdPadron(Integer idPadron) {
        this.idPadron = idPadron;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
