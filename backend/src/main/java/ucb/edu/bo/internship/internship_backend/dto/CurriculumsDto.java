package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Curriculums;

import java.util.Date;

public class CurriculumsDto {
    private Integer idCurriculums;
    private Integer idUsuarios;
    private Date fechaCreacion;
    private String titulo;
    private String pdfCurriculum;

    public CurriculumsDto() {
    }

    public CurriculumsDto(Integer idCurriculums, Integer idUsuarios, Date fechaCreacion, String titulo, String pdfCurriculum) {
        this.idCurriculums = idCurriculums;
        this.idUsuarios = idUsuarios;
        this.fechaCreacion = fechaCreacion;
        this.titulo = titulo;
        this.pdfCurriculum = pdfCurriculum;
    }

    public Integer getIdCurriculums() {
        return this.idCurriculums;
    }

    public void setIdCurriculums(Integer idCurriculums) {
        this.idCurriculums = idCurriculums;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public String getPdfCurriculum() {
        return this.pdfCurriculum;
    }

    public void setPdfCurriculum(String pdfCurriculum) {
        this.pdfCurriculum = pdfCurriculum;
    }

    @Override
    public String toString() {
        return "{" +
            " idCurriculums='" + getIdCurriculums() + "'" +
            ", idUsuarios='" + getIdUsuarios() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", pdfCurriculum='" + getPdfCurriculum() + "'" +
            "}";
    }


    public static CurriculumsDto fromEntity(Curriculums curriculums) {
        return new CurriculumsDto(
            curriculums.getIdcurriculums(),
            curriculums.getUsuariosIdusuarios().getIdusuarios(),
            curriculums.getFechacargado(),
            curriculums.getTitulo(),
            curriculums.getPdfcurriculum()
        );
    }
}
