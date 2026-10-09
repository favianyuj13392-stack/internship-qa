package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "importacion_padron_error")
public class ImportacionPadronError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iderror")
    private Integer iderror;

    @JoinColumn(name = "importacion_padron_id", referencedColumnName = "idimportacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ImportacionPadron importacionPadron;

    @Column(name = "fila")
    private Integer fila;

    @Column(name = "campo", length = 100)
    private String campo;

    @Column(name = "motivo", length = 500)
    private String motivo;

    public ImportacionPadronError() {
    }

    public ImportacionPadronError(Integer iderror) {
        this.iderror = iderror;
    }

    public ImportacionPadronError(ImportacionPadron importacionPadron, Integer fila, String campo, String motivo) {
        this.importacionPadron = importacionPadron;
        this.fila = fila;
        this.campo = campo;
        this.motivo = motivo;
    }

    public Integer getIderror() {
        return iderror;
    }

    public void setIderror(Integer iderror) {
        this.iderror = iderror;
    }

    public ImportacionPadron getImportacionPadron() {
        return importacionPadron;
    }

    public void setImportacionPadron(ImportacionPadron importacionPadron) {
        this.importacionPadron = importacionPadron;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
