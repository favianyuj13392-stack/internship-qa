package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;
import java.sql.Time;

public class UsuarioConPersonaEInstitucionDto extends UsuariosDto{
    private PersonasDto persona;
    private UsuariosInstitucionesDto usuarioInstitucion;

    public UsuarioConPersonaEInstitucionDto() {
    }

    public UsuarioConPersonaEInstitucionDto(Integer idUsuarios, String kc_UUID, String correo, Date fechaRegistro, Time horaRegistro, Integer idRoles, Integer idPersonas, Integer idCarreras, PersonasDto persona, UsuariosInstitucionesDto usuarioInstitucion) {
        super(idUsuarios, kc_UUID, correo, fechaRegistro, horaRegistro, idRoles, idPersonas, idCarreras);
        this.persona = persona;
        this.usuarioInstitucion = usuarioInstitucion;
    }

    public UsuarioConPersonaEInstitucionDto(UsuariosDto usuariosDto, PersonasDto persona, UsuariosInstitucionesDto usuarioInstitucion) {
        super(usuariosDto.getIdUsuarios(), usuariosDto.getKc_UUID(), usuariosDto.getCorreo(), usuariosDto.getFechaRegistro(), usuariosDto.getHoraRegistro(), usuariosDto.getIdRoles(), usuariosDto.getIdPersonas(), usuariosDto.getIdCarreras());
        this.persona = persona;
        this.usuarioInstitucion = usuarioInstitucion;
    }

    public PersonasDto getPersona() {
        return persona;
    }

    public void setPersona(PersonasDto persona) {
        this.persona = persona;
    }

    public UsuariosInstitucionesDto getUsuarioInstitucion() {
        return usuarioInstitucion;
    }

    public void setUsuarioInstitucion(UsuariosInstitucionesDto usuarioInstitucion) {
        this.usuarioInstitucion = usuarioInstitucion;
    }
}
