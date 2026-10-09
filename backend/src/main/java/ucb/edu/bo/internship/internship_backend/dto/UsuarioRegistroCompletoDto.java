package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;
import java.sql.Time;

public class UsuarioRegistroCompletoDto extends UsuariosDto{
    private PersonasDto persona;
    private InstitucionesDto institucion;
    private String cargo;

    public UsuarioRegistroCompletoDto() {
    }

    public UsuarioRegistroCompletoDto(Integer idUsuarios, String kc_UUID, String correo, Date fechaRegistro, Time horaRegistro, Integer idRoles, Integer idPersonas, Integer idCarreras, PersonasDto persona, InstitucionesDto institucion, String cargo) {
        super(idUsuarios, kc_UUID, correo, fechaRegistro, horaRegistro, idRoles, idPersonas, idCarreras);
        this.persona = persona;
        this.institucion = institucion;
        this.cargo = cargo;
    }

    public UsuarioRegistroCompletoDto(UsuariosDto usuariosDto, PersonasDto persona, InstitucionesDto institucion, String cargo) {
        super(usuariosDto.getIdUsuarios(), usuariosDto.getKc_UUID(), usuariosDto.getCorreo(), usuariosDto.getFechaRegistro(), usuariosDto.getHoraRegistro(), usuariosDto.getIdRoles(), usuariosDto.getIdPersonas(), usuariosDto.getIdCarreras());
        this.persona = persona;
        this.institucion = institucion;
        this.cargo = cargo;
    }

    public PersonasDto getPersona() {
        return persona;
    }

    public void setPersona(PersonasDto persona) {
        this.persona = persona;
    }

    public InstitucionesDto getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionesDto institucion) {
        this.institucion = institucion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
