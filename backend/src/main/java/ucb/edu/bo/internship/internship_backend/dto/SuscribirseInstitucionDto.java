package ucb.edu.bo.internship.internship_backend.dto;

public class SuscribirseInstitucionDto {
    private String cargo;

    public SuscribirseInstitucionDto(String cargo) {
        this.cargo = cargo;
    }
    public SuscribirseInstitucionDto() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "SuscribirseInstitucionDto{" +
                "cargo='" + cargo + '\'' +
                '}';
    }
}
