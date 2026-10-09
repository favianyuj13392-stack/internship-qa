package ucb.edu.bo.internship.internship_backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstudianteFichaActividadDto {
    private PadronEstudianteDto padron;
    private Integer totalAccesos;
    private Date primerAcceso;
    private Date ultimoAcceso;
    private List<EventoAccesoDto> historialAccesos;
    private List<VistaPasantiaDto> pasantiasVistas;
    private List<AplicacionPasantiasInformacionPasantiasDto> postulaciones;

    public EstudianteFichaActividadDto() {
        this.historialAccesos = new ArrayList<>();
        this.pasantiasVistas = new ArrayList<>();
        this.postulaciones = new ArrayList<>();
    }

    public EstudianteFichaActividadDto(PadronEstudianteDto padron, Integer totalAccesos,
                                       Date primerAcceso, Date ultimoAcceso) {
        this.padron = padron;
        this.totalAccesos = totalAccesos != null ? totalAccesos : 0;
        this.primerAcceso = primerAcceso;
        this.ultimoAcceso = ultimoAcceso;
        this.historialAccesos = new ArrayList<>();
        this.pasantiasVistas = new ArrayList<>();
        this.postulaciones = new ArrayList<>();
    }

    public PadronEstudianteDto getPadron() {
        return padron;
    }

    public void setPadron(PadronEstudianteDto padron) {
        this.padron = padron;
    }

    public Integer getTotalAccesos() {
        return totalAccesos;
    }

    public void setTotalAccesos(Integer totalAccesos) {
        this.totalAccesos = totalAccesos;
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

    public List<EventoAccesoDto> getHistorialAccesos() {
        return historialAccesos;
    }

    public void setHistorialAccesos(List<EventoAccesoDto> historialAccesos) {
        this.historialAccesos = historialAccesos;
    }

    public List<VistaPasantiaDto> getPasantiasVistas() {
        return pasantiasVistas;
    }

    public void setPasantiasVistas(List<VistaPasantiaDto> pasantiasVistas) {
        this.pasantiasVistas = pasantiasVistas;
    }

    public List<AplicacionPasantiasInformacionPasantiasDto> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<AplicacionPasantiasInformacionPasantiasDto> postulaciones) {
        this.postulaciones = postulaciones;
    }
}
