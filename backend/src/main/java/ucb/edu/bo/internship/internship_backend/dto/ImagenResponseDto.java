package ucb.edu.bo.internship.internship_backend.dto;

public class ImagenResponseDto {
    private String url;
    private String nombre;
    private String tipo;
    private String size;

    public ImagenResponseDto() {
    }

    public ImagenResponseDto(String url, String nombre, String tipo, String size) {
        this.url = url;
        this.nombre = nombre;
        this.tipo = tipo;
        this.size = size;
    }

    public ImagenResponseDto(NewFileDto newFileDto) {
    }

    public String getUrl() {
        return this.url;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getSize() {
        return this.size;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSize(String size) {
        this.size = size;
    }





}
