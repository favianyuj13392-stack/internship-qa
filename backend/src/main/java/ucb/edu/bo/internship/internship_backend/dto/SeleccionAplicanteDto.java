package ucb.edu.bo.internship.internship_backend.dto;
import ucb.edu.bo.internship.internship_backend.entity.Seleccionaplicante;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
public class SeleccionAplicanteDto {
    private Integer idSeleccionAplicante;
    private Integer idAplicacionPasantias;
    private Integer idUsuarios;
    private Date fechaSeleccion;
    private LocalTime horaSeleccion;
    private String comentarios;

    public SeleccionAplicanteDto() {
    }

    public SeleccionAplicanteDto(Integer idSeleccionAplicante, Integer idAplicacionPasantias, Integer idUsuarios, Date fechaSeleccion, LocalTime horaSeleccion, String comentarios) {
        this.idSeleccionAplicante = idSeleccionAplicante;
        this.idAplicacionPasantias = idAplicacionPasantias;
        this.idUsuarios = idUsuarios;
        this.fechaSeleccion = fechaSeleccion;
        this.horaSeleccion = horaSeleccion;
        this.comentarios = comentarios;
    }

    public static SeleccionAplicanteDto fromEntity(Seleccionaplicante seleccionaplicante) {
        LocalTime horaseleccion = seleccionaplicante.getHoraseleccion().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        return new SeleccionAplicanteDto(
            seleccionaplicante.getIdseleccionaplicante(),
            seleccionaplicante.getAplicacionespasantiasIdaplicacionpasantias().getIdaplicacionpasantias(),
            seleccionaplicante.getUsuariosinstitucionesIdusuariosinstituciones().getIdusuariosinstituciones(),
            seleccionaplicante.getFechaseleccion(),
            horaseleccion,
            seleccionaplicante.getComentarios()
        );
    }
    public static SeleccionAplicanteDto fromEntityWithOutAplicacionesPasantia(Seleccionaplicante seleccionaplicante) {
        LocalTime horaseleccion = seleccionaplicante.getHoraseleccion().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        return new SeleccionAplicanteDto(
                seleccionaplicante.getIdseleccionaplicante(),
                null,
                seleccionaplicante.getUsuariosinstitucionesIdusuariosinstituciones().getIdusuariosinstituciones(),
                seleccionaplicante.getFechaseleccion(),
                horaseleccion,
                seleccionaplicante.getComentarios()
        );
    }
    public static SeleccionAplicanteDto fromEntityWithOutHora(Seleccionaplicante seleccionaplicante) {
        return new SeleccionAplicanteDto(
            seleccionaplicante.getIdseleccionaplicante(),
            seleccionaplicante.getAplicacionespasantiasIdaplicacionpasantias().getIdaplicacionpasantias(),
            seleccionaplicante.getUsuariosinstitucionesIdusuariosinstituciones().getIdusuariosinstituciones(),
            seleccionaplicante.getFechaseleccion(),
            null,
            seleccionaplicante.getComentarios()
        );
    }

    public Integer getIdSeleccionAplicante() {
        return this.idSeleccionAplicante;
    }

    public void setIdSeleccionAplicante(Integer idSeleccionAplicante) {
        this.idSeleccionAplicante = idSeleccionAplicante;
    }

    public Integer getIdAplicacionPasantias() {
        return this.idAplicacionPasantias;
    }

    public void setIdAplicacionPasantias(Integer idAplicacionPasantias) {
        this.idAplicacionPasantias = idAplicacionPasantias;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Date getFechaSeleccion() {
        return this.fechaSeleccion;
    }

    public void setFechaSeleccion(Date fechaSeleccion) {
        this.fechaSeleccion = fechaSeleccion;
    }

    public LocalTime getHoraSeleccion() {
        return this.horaSeleccion;
    }

    public void setHoraSeleccion(LocalTime horaSeleccion) {
        this.horaSeleccion = horaSeleccion;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "{" +
            " idSeleccionAplicante='" + getIdSeleccionAplicante() + "'" +
            ", idAplicacionPasantias='" + getIdAplicacionPasantias() + "'" +
            ", idUsuarios='" + getIdUsuarios() + "'" +
            ", fechaSeleccion='" + getFechaSeleccion() + "'" +
            ", horaSeleccion='" + getHoraSeleccion() + "'" +
            ", comentarios='" + getComentarios() + "'" +
            "}";
    }
    
    
}
