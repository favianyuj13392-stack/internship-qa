package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.util.Date;

public class AplicacionPasantiasInformacionPasantiasDto {
    private Integer idAplicacionPasantias;
    private Integer idUsuarios;
    private Integer idPasantias;
    private Date fechaAplicacion;
    private Boolean activo;
    private String urlCurriculum;
    private SeleccionAplicanteDto seleccionAplicante;
    private PasantiasConInstitucionDto pasantiasDto;


    public AplicacionPasantiasInformacionPasantiasDto() {
    }

    public AplicacionPasantiasInformacionPasantiasDto(Integer idAplicacionPasantias, Integer idUsuarios, Integer idPasantias, Date fechaAplicacion,Boolean activo,String urlCurriculum, PasantiasConInstitucionDto pasantiasDto, SeleccionAplicanteDto seleccionAplicante) {
        this.idAplicacionPasantias = idAplicacionPasantias;
        this.idUsuarios = idUsuarios;
        this.idPasantias = idPasantias;
        this.fechaAplicacion = fechaAplicacion;
        this.activo = activo;
        this.urlCurriculum = urlCurriculum;
        this.pasantiasDto = pasantiasDto;
        this.seleccionAplicante = seleccionAplicante;
    }



    public PasantiasDto getPasantiasDto() {
        return this.pasantiasDto;
    }

    public void setPasantiasDto(PasantiasConInstitucionDto pasantiasDto) {
        this.pasantiasDto = pasantiasDto;
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

    public SeleccionAplicanteDto getSeleccionAplicante() {
        return seleccionAplicante;
    }

    public void setSeleccionAplicante(SeleccionAplicanteDto seleccionAplicante) {
        this.seleccionAplicante = seleccionAplicante;
    }

    public static AplicacionPasantiasInformacionPasantiasDto fromEntity(Aplicacionespasantias aplicacionespasantias){
        AplicacionPasantiasInformacionPasantiasDto aplicacionPasantiasDto = new AplicacionPasantiasInformacionPasantiasDto();
        aplicacionPasantiasDto.setIdAplicacionPasantias(aplicacionespasantias.getIdaplicacionpasantias());
        aplicacionPasantiasDto.setIdUsuarios(aplicacionespasantias.getUsuariosIdusuarios().getIdusuarios());
        aplicacionPasantiasDto.setIdPasantias(aplicacionespasantias.getPasantiasIdpasantias().getIdpasantias());
        aplicacionPasantiasDto.setFechaAplicacion(aplicacionespasantias.getFechaaplicacion());
        aplicacionPasantiasDto.setActivo(aplicacionespasantias.getActivo());
        if (aplicacionespasantias.getCurriculumsIdcurriculums() != null) {
            aplicacionPasantiasDto.setUrlCurriculum(aplicacionespasantias.getCurriculumsIdcurriculums().getPdfcurriculum());
        }
        PasantiasDto pasantiasDto = PasantiasConInstitucionDto.fromEntity(aplicacionespasantias.getPasantiasIdpasantias());

        PasantiasConInstitucionDto pasantiasConInstitucionDto = new PasantiasConInstitucionDto();
        if (aplicacionespasantias.getPasantiasIdpasantias() != null && aplicacionespasantias.getPasantiasIdpasantias().getInstitucionesIdinstituciones() != null) {
            InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(aplicacionespasantias.getPasantiasIdpasantias().getInstitucionesIdinstituciones());
            pasantiasConInstitucionDto.setInstitucion(institucionesDto);
        }
        if (pasantiasDto != null) {
            pasantiasConInstitucionDto.setIdPasantias(pasantiasDto.getIdPasantias());
            pasantiasConInstitucionDto.setAreas(pasantiasDto.getAreas());
            pasantiasConInstitucionDto.setTitulo(pasantiasDto.getTitulo());
            pasantiasConInstitucionDto.setDescripcion(pasantiasDto.getDescripcion());
            pasantiasConInstitucionDto.setRequisitos(pasantiasDto.getRequisitos());
            pasantiasConInstitucionDto.setFunciones(pasantiasDto.getFunciones());
            pasantiasConInstitucionDto.setBeneficios(pasantiasDto.getBeneficios());
            pasantiasConInstitucionDto.setFechaCierre(pasantiasDto.getFechaCierre());
            pasantiasConInstitucionDto.setFechaIngreso(pasantiasDto.getFechaIngreso());
        }
        aplicacionPasantiasDto.setPasantiasDto(pasantiasConInstitucionDto);
        if(Boolean.TRUE.equals(aplicacionespasantias.getActivo()) && aplicacionespasantias.getSeleccionaplicanteList() != null && !aplicacionespasantias.getSeleccionaplicanteList().isEmpty()){
            aplicacionPasantiasDto.setSeleccionAplicante(SeleccionAplicanteDto.fromEntityWithOutHora(aplicacionespasantias.getSeleccionaplicanteList().get(0)));
        }







        return aplicacionPasantiasDto;
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
