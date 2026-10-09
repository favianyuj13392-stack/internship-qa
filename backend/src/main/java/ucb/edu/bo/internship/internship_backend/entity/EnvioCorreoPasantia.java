package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "envio_correo_pasantia")
public class EnvioCorreoPasantia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idenvio")
    private Integer idenvio;

    @JoinColumn(name = "pasantias_idpasantias", referencedColumnName = "idpasantias")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pasantias pasantia;

    @JoinColumn(name = "padron_id", referencedColumnName = "idpadron")
    @ManyToOne(fetch = FetchType.LAZY)
    private PadronEstudiante padronEstudiante;

    @Basic(optional = false)
    @Column(name = "correo_destinatario", nullable = false, length = 150)
    private String correoDestinatario;

    @Column(name = "estado", length = 30)
    private String estado; // ENCOLADO, ENVIADO, FALLIDO

    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;

    @Column(name = "intentos")
    private Integer intentos = 1;

    @Column(name = "error_mensaje", columnDefinition = "TEXT")
    private String errorMensaje;

    public EnvioCorreoPasantia() {
    }

    public EnvioCorreoPasantia(Pasantias pasantia, PadronEstudiante padronEstudiante, String correoDestinatario, String estado, Date fechaEnvio) {
        this.pasantia = pasantia;
        this.padronEstudiante = padronEstudiante;
        this.correoDestinatario = correoDestinatario;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.intentos = 1;
    }

    public Integer getIdenvio() {
        return idenvio;
    }

    public void setIdenvio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public Pasantias getPasantia() {
        return pasantia;
    }

    public void setPasantia(Pasantias pasantia) {
        this.pasantia = pasantia;
    }

    public PadronEstudiante getPadronEstudiante() {
        return padronEstudiante;
    }

    public void setPadronEstudiante(PadronEstudiante padronEstudiante) {
        this.padronEstudiante = padronEstudiante;
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
