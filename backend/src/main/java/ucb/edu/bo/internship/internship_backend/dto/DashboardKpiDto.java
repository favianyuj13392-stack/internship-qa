package ucb.edu.bo.internship.internship_backend.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardKpiDto {
    private Long totalEstudiantesPadron;
    private Long estudiantesConAcceso;
    private Long estudiantesSinAcceso;
    private Double porcentajeAdopcion;
    private Long totalVistasRegistradas;
    private List<CarreraKpiDto> carreras;

    public DashboardKpiDto() {
        this.carreras = new ArrayList<>();
    }

    public DashboardKpiDto(Long totalEstudiantesPadron, Long estudiantesConAcceso, Long totalVistasRegistradas) {
        this.totalEstudiantesPadron = totalEstudiantesPadron != null ? totalEstudiantesPadron : 0L;
        this.estudiantesConAcceso = estudiantesConAcceso != null ? estudiantesConAcceso : 0L;
        this.totalVistasRegistradas = totalVistasRegistradas != null ? totalVistasRegistradas : 0L;
        this.estudiantesSinAcceso = Math.max(0L, this.totalEstudiantesPadron - this.estudiantesConAcceso);
        
        if (this.totalEstudiantesPadron > 0) {
            this.porcentajeAdopcion = Math.round(((double) this.estudiantesConAcceso / this.totalEstudiantesPadron * 100.0) * 10.0) / 10.0;
        } else {
            this.porcentajeAdopcion = 0.0;
        }
        this.carreras = new ArrayList<>();
    }

    public Long getTotalEstudiantesPadron() {
        return totalEstudiantesPadron;
    }

    public void setTotalEstudiantesPadron(Long totalEstudiantesPadron) {
        this.totalEstudiantesPadron = totalEstudiantesPadron;
    }

    public Long getEstudiantesConAcceso() {
        return estudiantesConAcceso;
    }

    public void setEstudiantesConAcceso(Long estudiantesConAcceso) {
        this.estudiantesConAcceso = estudiantesConAcceso;
    }

    public Long getEstudiantesSinAcceso() {
        return estudiantesSinAcceso;
    }

    public void setEstudiantesSinAcceso(Long estudiantesSinAcceso) {
        this.estudiantesSinAcceso = estudiantesSinAcceso;
    }

    public Double getPorcentajeAdopcion() {
        return porcentajeAdopcion;
    }

    public void setPorcentajeAdopcion(Double porcentajeAdopcion) {
        this.porcentajeAdopcion = porcentajeAdopcion;
    }

    public Long getTotalVistasRegistradas() {
        return totalVistasRegistradas;
    }

    public void setTotalVistasRegistradas(Long totalVistasRegistradas) {
        this.totalVistasRegistradas = totalVistasRegistradas;
    }

    public List<CarreraKpiDto> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<CarreraKpiDto> carreras) {
        this.carreras = carreras;
    }

    public static class CarreraKpiDto {
        private String carrera;
        private Long total;
        private Long accedieron;
        private Double porcentaje;

        public CarreraKpiDto() {
        }

        public CarreraKpiDto(String carrera, Long total, Long accedieron) {
            this.carrera = carrera;
            this.total = total != null ? total : 0L;
            this.accedieron = accedieron != null ? accedieron : 0L;
            if (this.total > 0) {
                this.porcentaje = Math.round(((double) this.accedieron / this.total * 100.0) * 10.0) / 10.0;
            } else {
                this.porcentaje = 0.0;
            }
        }

        public String getCarrera() {
            return carrera;
        }

        public void setCarrera(String carrera) {
            this.carrera = carrera;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Long getAccedieron() {
            return accedieron;
        }

        public void setAccedieron(Long accedieron) {
            this.accedieron = accedieron;
        }

        public Double getPorcentaje() {
            return porcentaje;
        }

        public void setPorcentaje(Double porcentaje) {
            this.porcentaje = porcentaje;
        }
    }
}
