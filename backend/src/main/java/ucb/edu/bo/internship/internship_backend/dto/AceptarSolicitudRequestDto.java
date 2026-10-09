package ucb.edu.bo.internship.internship_backend.dto;

public class AceptarSolicitudRequestDto {
    private String comentarios;

    public AceptarSolicitudRequestDto(String comentarios) {
        this.comentarios = comentarios;
    }
    public AceptarSolicitudRequestDto(){}

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "AceptarSolicitudRequestDto{" +
                "comentarios='" + comentarios + '\'' +
                '}';
    }
}
