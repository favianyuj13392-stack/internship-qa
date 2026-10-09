package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "padron_estudiante")
public class PadronEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpadron")
    private Integer idpadron;

    @Basic(optional = false)
    @Column(name = "correo", unique = true, nullable = false, length = 150)
    private String correo;

    @Column(name = "nombres", length = 150)
    private String nombres;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @JoinColumn(name = "carreras_idcarreras", referencedColumnName = "idcarreras")
    @ManyToOne(fetch = FetchType.EAGER)
    private Carreras carrerasIdcarreras;

    @Column(name = "estado", length = 30)
    private String estado; // ACTIVO, INACTIVO, EGRESADO

    @Column(name = "anio_ingreso")
    private Integer anioIngreso;

    @Column(name = "codigo_estudiante", length = 50)
    private String codigoEstudiante;

    @Column(name = "kc_uuid", length = 100)
    private String kcUuid;

    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;

    @Column(name = "primer_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date primerAcceso;

    @Column(name = "ultimo_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcceso;

    @JoinColumn(name = "importacion_padron_id", referencedColumnName = "idimportacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private ImportacionPadron importacionPadron;

    public PadronEstudiante() {
    }

    public PadronEstudiante(Integer idpadron) {
        this.idpadron = idpadron;
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

    public Carreras getCarrerasIdcarreras() {
        return carrerasIdcarreras;
    }

    public void setCarrerasIdcarreras(Carreras carrerasIdcarreras) {
        this.carrerasIdcarreras = carrerasIdcarreras;
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

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
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

    public ImportacionPadron getImportacionPadron() {
        return importacionPadron;
    }

    public void setImportacionPadron(ImportacionPadron importacionPadron) {
        this.importacionPadron = importacionPadron;
    }
}
