package ucb.edu.bo.internship.internship_backend.dto;

public class PasantiaNombreDto {
    private Integer idPasantias;
    private String titulo;
    public PasantiaNombreDto() {
    }
    public PasantiaNombreDto(Integer idPasantias, String titulo) {
        this.idPasantias = idPasantias;
        this.titulo = titulo;
    }

    public Integer getIdPasantias() {
        return idPasantias;
    }

    public void setIdPasantias(Integer idPasantias) {
        this.idPasantias = idPasantias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "PasantiaNombreDto{" +
                "idPasantias=" + idPasantias +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
