package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "importacion_padron")
public class ImportacionPadron implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idimportacion")
    private Integer idimportacion;

    @Column(name = "admin_kcuuid", length = 100)
    private String adminKcUuid;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    @Column(name = "hash_archivo", length = 64)
    private String hashArchivo;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "modo", length = 20)
    private String modo; // SIMULACION / APLICADO

    @Column(name = "total_nuevos")
    private Integer totalNuevos = 0;

    @Column(name = "total_actualizados")
    private Integer totalActualizados = 0;

    @Column(name = "total_sin_cambios")
    private Integer totalSinCambios = 0;

    @Column(name = "total_errores")
    private Integer totalErrores = 0;

    @Column(name = "total_desactivados")
    private Integer totalDesactivados = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "importacionPadron", fetch = FetchType.LAZY)
    private List<ImportacionPadronError> errores;

    public ImportacionPadron() {
    }

    public ImportacionPadron(Integer idimportacion) {
        this.idimportacion = idimportacion;
    }

    public Integer getIdimportacion() {
        return idimportacion;
    }

    public void setIdimportacion(Integer idimportacion) {
        this.idimportacion = idimportacion;
    }

    public String getAdminKcUuid() {
        return adminKcUuid;
    }

    public void setAdminKcUuid(String adminKcUuid) {
        this.adminKcUuid = adminKcUuid;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getHashArchivo() {
        return hashArchivo;
    }

    public void setHashArchivo(String hashArchivo) {
        this.hashArchivo = hashArchivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Integer getTotalNuevos() {
        return totalNuevos;
    }

    public void setTotalNuevos(Integer totalNuevos) {
        this.totalNuevos = totalNuevos;
    }

    public Integer getTotalActualizados() {
        return totalActualizados;
    }

    public void setTotalActualizados(Integer totalActualizados) {
        this.totalActualizados = totalActualizados;
    }

    public Integer getTotalSinCambios() {
        return totalSinCambios;
    }

    public void setTotalSinCambios(Integer totalSinCambios) {
        this.totalSinCambios = totalSinCambios;
    }

    public Integer getTotalErrores() {
        return totalErrores;
    }

    public void setTotalErrores(Integer totalErrores) {
        this.totalErrores = totalErrores;
    }

    public Integer getTotalDesactivados() {
        return totalDesactivados;
    }

    public void setTotalDesactivados(Integer totalDesactivados) {
        this.totalDesactivados = totalDesactivados;
    }

    public List<ImportacionPadronError> getErrores() {
        return errores;
    }

    public void setErrores(List<ImportacionPadronError> errores) {
        this.errores = errores;
    }
}
