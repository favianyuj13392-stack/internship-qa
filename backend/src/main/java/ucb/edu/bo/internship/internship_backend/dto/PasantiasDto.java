package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.sql.Date;
import java.util.List;

public class PasantiasDto {
    private Integer idPasantias;
    private Object areas;
    private String titulo;
    private String descripcion;
    private Object requisitos;
    private Object funciones;
    private Object beneficios;
    private Date fechaCierre;
    private Date fechaIngreso;
    private Boolean sinAplicantes;

    private List<Integer> idCarreras;

    public PasantiasDto() {
    }

    public PasantiasDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso) {
        this.idPasantias = idPasantias;
        this.areas = areas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.funciones = funciones;
        this.beneficios = beneficios;
        this.fechaCierre = fechaCierre;
        this.fechaIngreso = fechaIngreso;
    }

    public PasantiasDto(Pasantias pasantias) {
        this.idPasantias = pasantias.getIdpasantias();
        this.areas = pasantias.getAreas();
        this.titulo = pasantias.getTitulo();
        this.descripcion = pasantias.getDescripcion();
        this.requisitos = pasantias.getRequisitos();
        this.funciones = pasantias.getFunciones();
        this.beneficios = pasantias.getBeneficios();
        this.fechaCierre = new Date(pasantias.getFechacierre().getTime());
        this.fechaIngreso = new Date(pasantias.getFechaingreso().getTime());
    }

    public PasantiasDto(Integer idPasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso, Boolean sinAplicantes, List<Integer> idCarreras) {
        this.idPasantias = idPasantias;
        this.areas = areas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.funciones = funciones;
        this.beneficios = beneficios;
        this.fechaCierre = fechaCierre;
        this.fechaIngreso = fechaIngreso;
        this.sinAplicantes = sinAplicantes;
        this.idCarreras = idCarreras;
    }

    public List<Integer> getIdCarreras() {
        return this.idCarreras;
    }

    public void setIdCarreras(List<Integer> idCarreras) {
        this.idCarreras = idCarreras;
    }

    public Integer getIdPasantias() {
        return this.idPasantias;
    }

    public void setIdPasantias(Integer idPasantias) {
        this.idPasantias = idPasantias;
    }


    public Object getAreas() {
        return this.areas;
    }

    public void setAreas(Object areas) {
        this.areas = areas;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getRequisitos() {
        return this.requisitos;
    }

    public void setRequisitos(Object requisitos) {
        this.requisitos = requisitos;
    }

    public Object getFunciones() {
        return this.funciones;
    }

    public void setFunciones(Object funciones) {
        this.funciones = funciones;
    }

    public Object getBeneficios() {
        return this.beneficios;
    }

    public void setBeneficios(Object beneficios) {
        this.beneficios = beneficios;
    }

    public Date getFechaCierre() {
        return this.fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getSinAplicantes() {
        return sinAplicantes;
    }

    public void setSinAplicantes(Boolean sinAplicantes) {
        this.sinAplicantes = sinAplicantes;
    }

    @Override
    public String toString() {
        return "{" +
            " idPasantias='" + getIdPasantias() + "'" +
            ", areas='" + getAreas() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", requisitos='" + getRequisitos() + "'" +
            ", funciones='" + getFunciones() + "'" +
            ", beneficios='" + getBeneficios() + "'" +
            ", fechaCierre='" + getFechaCierre() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            "}";
    }

    public static PasantiasDto fromEntity(Pasantias pasantias) {
        return new PasantiasDto(
            pasantias.getIdpasantias(),
            pasantias.getAreas(),
            pasantias.getTitulo(),
            pasantias.getDescripcion(),
            pasantias.getRequisitos(),
            pasantias.getFunciones(),
            pasantias.getBeneficios(),
            new Date (pasantias.getFechacierre().getTime()),
            new Date (pasantias.getFechaingreso().getTime())
        );
    }

    public Pasantias toEntity() {
        Pasantias pasantias = new Pasantias();
        pasantias.setIdpasantias(this.idPasantias);
        pasantias.setAreas( (List<String>) this.areas);
        pasantias.setTitulo(this.titulo);
        pasantias.setDescripcion(this.descripcion);
        pasantias.setRequisitos((List<String>) this.requisitos);
        pasantias.setFunciones((List<String>) this.funciones);
        pasantias.setBeneficios((List<String>) this.beneficios);
        pasantias.setFechacierre(this.fechaCierre);
        pasantias.setFechaingreso(this.fechaIngreso);
        return pasantias;
    }
}
