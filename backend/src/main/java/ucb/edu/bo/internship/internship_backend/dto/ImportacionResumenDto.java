package ucb.edu.bo.internship.internship_backend.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportacionResumenDto {
    private Integer idImportacion;
    private String modo;
    private Integer totalNuevos = 0;
    private Integer totalActualizados = 0;
    private Integer totalSinCambios = 0;
    private Integer totalErrores = 0;
    private Integer totalDesactivados = 0;
    private List<FilaErrorDto> errores = new ArrayList<>();

    public ImportacionResumenDto() {
    }

    public Integer getIdImportacion() {
        return idImportacion;
    }

    public void setIdImportacion(Integer idImportacion) {
        this.idImportacion = idImportacion;
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

    public List<FilaErrorDto> getErrores() {
        return errores;
    }

    public void setErrores(List<FilaErrorDto> errores) {
        this.errores = errores;
    }
}
