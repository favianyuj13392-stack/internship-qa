package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vista_pasantia", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"padron_id", "pasantias_idpasantias"})
})
public class VistaPasantia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvista")
    private Integer idvista;

    @JoinColumn(name = "padron_id", referencedColumnName = "idpadron")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PadronEstudiante padronEstudiante;

    @JoinColumn(name = "pasantias_idpasantias", referencedColumnName = "idpasantias")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pasantias pasantia;

    @Column(name = "primera_vista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date primeraVista;

    @Column(name = "ultima_vista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaVista;

    @Column(name = "veces")
    private Integer veces = 1;

    @Column(name = "origen", length = 30)
    private String origen; // WEB / CORREO

    public VistaPasantia() {
    }

    public VistaPasantia(PadronEstudiante padronEstudiante, Pasantias pasantia, Date fecha, String origen) {
        this.padronEstudiante = padronEstudiante;
        this.pasantia = pasantia;
        this.primeraVista = fecha;
        this.ultimaVista = fecha;
        this.veces = 1;
        this.origen = origen;
    }

    public Integer getIdvista() {
        return idvista;
    }

    public void setIdvista(Integer idvista) {
        this.idvista = idvista;
    }

    public PadronEstudiante getPadronEstudiante() {
        return padronEstudiante;
    }

    public void setPadronEstudiante(PadronEstudiante padronEstudiante) {
        this.padronEstudiante = padronEstudiante;
    }

    public Pasantias getPasantia() {
        return pasantia;
    }

    public void setPasantia(Pasantias pasantia) {
        this.pasantia = pasantia;
    }

    public Date getPrimeraVista() {
        return primeraVista;
    }

    public void setPrimeraVista(Date primeraVista) {
        this.primeraVista = primeraVista;
    }

    public Date getUltimaVista() {
        return ultimaVista;
    }

    public void setUltimaVista(Date ultimaVista) {
        this.ultimaVista = ultimaVista;
    }

    public Integer getVeces() {
        return veces;
    }

    public void setVeces(Integer veces) {
        this.veces = veces;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
