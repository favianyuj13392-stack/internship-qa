package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "evento_acceso")
public class EventoAcceso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevento")
    private Integer idevento;

    @JoinColumn(name = "padron_id", referencedColumnName = "idpadron")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PadronEstudiante padronEstudiante;

    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    @Column(name = "origen", length = 30)
    private String origen; // WEB / CORREO

    @Column(name = "ip", length = 45)
    private String ip;

    public EventoAcceso() {
    }

    public EventoAcceso(PadronEstudiante padronEstudiante, Date fechaHora, String origen, String ip) {
        this.padronEstudiante = padronEstudiante;
        this.fechaHora = fechaHora;
        this.origen = origen;
        this.ip = ip;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public PadronEstudiante getPadronEstudiante() {
        return padronEstudiante;
    }

    public void setPadronEstudiante(PadronEstudiante padronEstudiante) {
        this.padronEstudiante = padronEstudiante;
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
