package ucb.edu.bo.internship.internship_backend.dto;

public class TipoNotificacionDto {
    private Integer idTipoNotificacion;
    private String nombreNotificacion;

    public TipoNotificacionDto() {
    }

    public TipoNotificacionDto(Integer idTipoNotificacion, String nombreNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
        this.nombreNotificacion = nombreNotificacion;
    }

    public Integer getIdTipoNotificacion() {
        return this.idTipoNotificacion;
    }

    public void setIdTipoNotificacion(Integer idTipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
    }

    public String getNombreNotificacion() {
        return this.nombreNotificacion;
    }

    public void setNombreNotificacion(String nombreNotificacion) {
        this.nombreNotificacion = nombreNotificacion;
    }

    @Override
    public String toString() {
        return "{" +
            " idTipoNotificacion='" + getIdTipoNotificacion() + "'" +
            ", nombreNotificacion='" + getNombreNotificacion() + "'" +
            "}";
    }
    
}
