package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadron;
import java.util.Date;

public class ImportacionPadronDto {
    private Integer idimportacion;
    private String adminKcUuid;
    private String nombreArchivo;
    private Date fecha;
    private String modo;
    private Integer totalNuevos;
    private Integer totalActualizados;
    private Integer totalSinCambios;
    private Integer totalErrores;
    private Integer totalDesactivados;

    public ImportacionPadronDto() {
    }

    public static ImportacionPadronDto fromEntity(ImportacionPadron entity) {
        if (entity == null) return null;
        ImportacionPadronDto dto = new ImportacionPadronDto();
        dto.setIdimportacion(entity.getIdimportacion());
        dto.setAdminKcUuid(entity.getAdminKcUuid());
        dto.setNombreArchivo(entity.getNombreArchivo());
        dto.setFecha(entity.getFecha());
        dto.setModo(entity.getModo());
        dto.setTotalNuevos(entity.getTotalNuevos());
        dto.setTotalActualizados(entity.getTotalActualizados());
        dto.setTotalSinCambios(entity.getTotalSinCambios());
        dto.setTotalErrores(entity.getTotalErrores());
        dto.setTotalDesactivados(entity.getTotalDesactivados());
        return dto;
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
}
