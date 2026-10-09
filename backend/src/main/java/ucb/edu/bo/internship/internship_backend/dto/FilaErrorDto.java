package ucb.edu.bo.internship.internship_backend.dto;

public class FilaErrorDto {
    private Integer fila;
    private String campo;
    private String motivo;

    public FilaErrorDto() {
    }

    public FilaErrorDto(Integer fila, String campo, String motivo) {
        this.fila = fila;
        this.campo = campo;
        this.motivo = motivo;
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
