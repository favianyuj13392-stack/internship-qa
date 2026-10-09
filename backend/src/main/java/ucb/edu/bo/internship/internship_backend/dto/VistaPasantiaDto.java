package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.VistaPasantia;
import java.util.Date;

public class VistaPasantiaDto {
    private Integer idvista;
    private Integer idPadron;
    private String correoEstudiante;
    private Integer idPasantia;
    private String tituloPasantia;
    private Date primeraVista;
    private Date ultimaVista;
    private Integer veces;
    private String origen;

    public VistaPasantiaDto() {
    }

    public static VistaPasantiaDto fromEntity(VistaPasantia entity) {
        if (entity == null) return null;
        VistaPasantiaDto dto = new VistaPasantiaDto();
        dto.setIdvista(entity.getIdvista());
        if (entity.getPadronEstudiante() != null) {
            dto.setIdPadron(entity.getPadronEstudiante().getIdpadron());
            dto.setCorreoEstudiante(entity.getPadronEstudiante().getCorreo());
        }
        if (entity.getPasantia() != null) {
            dto.setIdPasantia(entity.getPasantia().getIdpasantias());
            dto.setTituloPasantia(entity.getPasantia().getTitulo());
        }
        dto.setPrimeraVista(entity.getPrimeraVista());
        dto.setUltimaVista(entity.getUltimaVista());
        dto.setVeces(entity.getVeces());
        dto.setOrigen(entity.getOrigen());
        return dto;
    }

    public Integer getIdvista() {
        return idvista;
    }

    public void setIdvista(Integer idvista) {
        this.idvista = idvista;
    }

    public Integer getIdPadron() {
        return idPadron;
    }

    public void setIdPadron(Integer idPadron) {
        this.idPadron = idPadron;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public Integer getIdPasantia() {
        return idPasantia;
    }

    public void setIdPasantia(Integer idPasantia) {
        this.idPasantia = idPasantia;
    }

    public String getTituloPasantia() {
        return tituloPasantia;
    }

    public void setTituloPasantia(String tituloPasantia) {
        this.tituloPasantia = tituloPasantia;
    }

    public Date getPrimeraVista() {
        return primeraVista;
    }

    public void setPrimeraVista(Date primeraVista) {
        this.primeraVista = primeraVista;
    }

    public Date getUltimaVista() {
        return ultimaVista;
    }

    public void setUltimaVista(Date ultimaVista) {
        this.ultimaVista = ultimaVista;
    }

    public Integer getVeces() {
        return veces;
    }

    public void setVeces(Integer veces) {
        this.veces = veces;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
