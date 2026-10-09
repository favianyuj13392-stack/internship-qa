package ucb.edu.bo.internship.internship_backend.dto;

public class PasantiasCarrerasDto {
    private Integer idPasantiasCarreras;
    private Integer idCarreras;
    private Integer idPasantias;


    public PasantiasCarrerasDto() {
    }

    public PasantiasCarrerasDto(Integer idPasantiasCarreras, Integer idCarreras, Integer idPasantias) {
        this.idPasantiasCarreras = idPasantiasCarreras;
        this.idCarreras = idCarreras;
        this.idPasantias = idPasantias;
    }



    public Integer getIdPasantiasCarreras() {
        return this.idPasantiasCarreras;
    }

    public void setIdPasantiasCarreras(Integer idPasantiasCarreras) {
        this.idPasantiasCarreras = idPasantiasCarreras;
    }

    public Integer getIdCarreras() {
        return this.idCarreras;
    }

    public void setIdCarreras(Integer idCarreras) {
        this.idCarreras = idCarreras;
    }

    public Integer getIdPasantias() {
        return this.idPasantias;
    }

    public void setIdPasantias(Integer idPasantias) {
        this.idPasantias = idPasantias;
    }

    @Override
    public String toString() {
        return "{" +
            " idPasantiasCarreras='" + getIdPasantiasCarreras() + "'" +
            ", idCarreras='" + getIdCarreras() + "'" +
            ", idPasantias='" + getIdPasantias() + "'" +
            "}";
    }

    

    
}
