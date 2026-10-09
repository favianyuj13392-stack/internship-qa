package ucb.edu.bo.internship.internship_backend.dto;

public class NotificacionesDto {
    private Integer idNotificaciones;
    private String mensaje;
    private String nombreEntidad;
    private Integer idEntidad;
    private String fechaEnvio;
    private Boolean leido;
    private Integer idTipoNotificacion;
    private Integer idUsuario;

    public NotificacionesDto() {
    }

    public NotificacionesDto(Integer idNotificaciones, String mensaje, String nombreEntidad, Integer idEntidad, String fechaEnvio, Boolean leido, Integer idTipoNotificacion, Integer idUsuario) {
        this.idNotificaciones = idNotificaciones;
        this.mensaje = mensaje;
        this.nombreEntidad = nombreEntidad;
        this.idEntidad = idEntidad;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
        this.idTipoNotificacion = idTipoNotificacion;
        this.idUsuario = idUsuario;
    }

    public Integer getIdNotificaciones() {
        return this.idNotificaciones;
    }

    public void setIdNotificaciones(Integer idNotificaciones) {
        this.idNotificaciones = idNotificaciones;
    }
    
    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreEntidad() {
        return this.nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public Integer getIdEntidad() {
        return this.idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getFechaEnvio() {
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Boolean isLeido() {
        return this.leido;
    }

    public Boolean getLeido() {
        return this.leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Integer getIdTipoNotificacion() {
        return this.idTipoNotificacion;
    }

    public void setIdTipoNotificacion(Integer idTipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "{" +
            " idNotificaciones='" + getIdNotificaciones() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", nombreEntidad='" + getNombreEntidad() + "'" +
            ", idEntidad='" + getIdEntidad() + "'" +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", leido='" + isLeido() + "'" +
            ", idTipoNotificacion='" + getIdTipoNotificacion() + "'" +
            ", idUsuario='" + getIdUsuario() + "'" +
            "}";
    }
}
