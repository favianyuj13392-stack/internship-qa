package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;

import java.util.Date;

public class AplicacionPasantiasDto {
    private Integer idAplicacionPasantias;
    private Integer idUsuarios;
    private Integer idPasantias;
    private Date fechaAplicacion;
    private Boolean activo;
    private String urlCurriculum;


    public AplicacionPasantiasDto() {
    }

    public AplicacionPasantiasDto(Integer idAplicacionPasantias, Integer idUsuarios, Integer idPasantias, Date fechaAplicacion,Boolean activo,String urlCurriculum) {
        this.idAplicacionPasantias = idAplicacionPasantias;
        this.idUsuarios = idUsuarios;
        this.idPasantias = idPasantias;
        this.fechaAplicacion = fechaAplicacion;
        this.activo = activo;
        this.urlCurriculum = urlCurriculum;
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

    public Integer getIdPasantias() {
        return this.idPasantias;
    }

    
    public void setIdPasantias(Integer idPasantias) {
        this.idPasantias = idPasantias;
    }

    public Date getFechaAplicacion() {
        return this.fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getUrlCurriculum() {
        return urlCurriculum;
    }

    public void setUrlCurriculum(String urlCurriculum) {
        this.urlCurriculum = urlCurriculum;
    }

    public static AplicacionPasantiasDto fromEntity(Aplicacionespasantias aplicacionespasantias){
        return new AplicacionPasantiasDto(
            aplicacionespasantias.getIdaplicacionpasantias(),
            aplicacionespasantias.getUsuariosIdusuarios().getIdusuarios(),
            aplicacionespasantias.getPasantiasIdpasantias().getIdpasantias(),
            aplicacionespasantias.getFechaaplicacion(),
            aplicacionespasantias.getActivo(),
            aplicacionespasantias.getCurriculumsIdcurriculums().getPdfcurriculum()
        );
    }

    @Override
    public String toString() {
        return "{" +
            " idAplicacionPasantias='" + getIdAplicacionPasantias() + "'" +
            ", idUsuarios='" + getIdUsuarios() + "'" +
            ", idPasantias='" + getIdPasantias() + "'" +
            ", fechaAplicacion='" + getFechaAplicacion() + "'" +
            ", activo='" + getActivo() + "'"+
            "}";
    }
    
}
