package ucb.edu.bo.internship.internship_backend.dto;

public class SolicitudIndormacionDto {
    private UsuariosDto usuario;
    private PersonasDto persona;
    private InstitucionesDto institucion;
    private UsuariosInstitucionesDto usuarioInstitucion;

    public SolicitudIndormacionDto(UsuariosDto usuario, PersonasDto persona, InstitucionesDto institucion, UsuariosInstitucionesDto usuarioInstitucion) {
        this.usuario = usuario;
        this.persona = persona;
        this.institucion = institucion;
        this.usuarioInstitucion = usuarioInstitucion;
    }
    public SolicitudIndormacionDto(){
    }

    public UsuariosDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDto usuario) {
        this.usuario = usuario;
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

    public UsuariosInstitucionesDto getUsuarioInstitucion() {
        return usuarioInstitucion;
    }

    public void setUsuarioInstitucion(UsuariosInstitucionesDto usuarioInstitucion) {
        this.usuarioInstitucion = usuarioInstitucion;
    }

    @Override
    public String toString() {
        return "SolicitudIndormacionDto{" +
                "usuario=" + usuario +
                ", persona=" + persona +
                ", institucion=" + institucion +
                ", usuarioInstitucion=" + usuarioInstitucion +
                '}';
    }
}
