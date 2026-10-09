package ucb.edu.bo.internship.internship_backend.dto;

public class PasantiaAlcanceDto {
    private Integer idPasantia;
    private String titulo;
    private Long estudiantesUnicosVieron;
    private Long totalVisualizaciones;
    private Long totalPostulaciones;
    private Double tasaConversion;
    private Long vistasOrigenCorreo;
    private Long vistasOrigenWeb;
    private Long correosEnviados = 0L;
    private Long correosFallidos = 0L;

    public PasantiaAlcanceDto() {
    }

    public PasantiaAlcanceDto(Integer idPasantia, String titulo, Long estudiantesUnicosVieron,
                              Long totalVisualizaciones, Long totalPostulaciones,
                              Long vistasOrigenCorreo, Long vistasOrigenWeb) {
        this(idPasantia, titulo, estudiantesUnicosVieron, totalVisualizaciones, totalPostulaciones, vistasOrigenCorreo, vistasOrigenWeb, 0L, 0L);
    }

    public PasantiaAlcanceDto(Integer idPasantia, String titulo, Long estudiantesUnicosVieron,
                              Long totalVisualizaciones, Long totalPostulaciones,
                              Long vistasOrigenCorreo, Long vistasOrigenWeb,
                              Long correosEnviados, Long correosFallidos) {
        this.idPasantia = idPasantia;
        this.titulo = titulo;
        this.estudiantesUnicosVieron = estudiantesUnicosVieron != null ? estudiantesUnicosVieron : 0L;
        this.totalVisualizaciones = totalVisualizaciones != null ? totalVisualizaciones : 0L;
        this.totalPostulaciones = totalPostulaciones != null ? totalPostulaciones : 0L;
        this.vistasOrigenCorreo = vistasOrigenCorreo != null ? vistasOrigenCorreo : 0L;
        this.vistasOrigenWeb = vistasOrigenWeb != null ? vistasOrigenWeb : 0L;
        this.correosEnviados = correosEnviados != null ? correosEnviados : 0L;
        this.correosFallidos = correosFallidos != null ? correosFallidos : 0L;

        if (this.estudiantesUnicosVieron > 0) {
            this.tasaConversion = Math.round(((double) this.totalPostulaciones / this.estudiantesUnicosVieron * 100.0) * 10.0) / 10.0;
        } else {
            this.tasaConversion = 0.0;
        }
    }

    public Integer getIdPasantia() {
        return idPasantia;
    }

    public void setIdPasantia(Integer idPasantia) {
        this.idPasantia = idPasantia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getEstudiantesUnicosVieron() {
        return estudiantesUnicosVieron;
    }

    public void setEstudiantesUnicosVieron(Long estudiantesUnicosVieron) {
        this.estudiantesUnicosVieron = estudiantesUnicosVieron;
    }

    public Long getTotalVisualizaciones() {
        return totalVisualizaciones;
    }

    public void setTotalVisualizaciones(Long totalVisualizaciones) {
        this.totalVisualizaciones = totalVisualizaciones;
    }

    public Long getTotalPostulaciones() {
        return totalPostulaciones;
    }

    public void setTotalPostulaciones(Long totalPostulaciones) {
        this.totalPostulaciones = totalPostulaciones;
    }

    public Double getTasaConversion() {
        return tasaConversion;
    }

    public void setTasaConversion(Double tasaConversion) {
        this.tasaConversion = tasaConversion;
    }

    public Long getVistasOrigenCorreo() {
        return vistasOrigenCorreo;
    }

    public void setVistasOrigenCorreo(Long vistasOrigenCorreo) {
        this.vistasOrigenCorreo = vistasOrigenCorreo;
    }

    public Long getVistasOrigenWeb() {
        return vistasOrigenWeb;
    }

    public void setVistasOrigenWeb(Long vistasOrigenWeb) {
        this.vistasOrigenWeb = vistasOrigenWeb;
    }

    public Long getCorreosEnviados() {
        return correosEnviados;
    }

    public void setCorreosEnviados(Long correosEnviados) {
        this.correosEnviados = correosEnviados;
    }

    public Long getCorreosFallidos() {
        return correosFallidos;
    }

    public void setCorreosFallidos(Long correosFallidos) {
        this.correosFallidos = correosFallidos;
    }
}
