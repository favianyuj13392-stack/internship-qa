package ucb.edu.bo.internship.internship_backend.dto;

public class TituloCurriculumDto {
    private String titulo;

    public TituloCurriculumDto(String titulo) {
        this.titulo = titulo;
    }
    public TituloCurriculumDto(){}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
