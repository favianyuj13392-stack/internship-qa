package ucb.edu.bo.internship.internship_backend.dto;

public class ChangeEstadoDto {
    private Boolean estado;

    public ChangeEstadoDto(Boolean estado) {
        this.estado = estado;
    }
    public ChangeEstadoDto() {
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ChangeEstadoDto{" +
                "estado=" + estado +
                '}';
    }
}
