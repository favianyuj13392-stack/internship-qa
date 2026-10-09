package ucb.edu.bo.internship.internship_backend.dto;

import java.util.Set;

public class UsuarioRegistroDto {
    private String primerNombre;
    private String apellidoPaterno;

    private String correo;
    private String nombreUsuario;

    private String password;

    private Set<String> roles;

    public UsuarioRegistroDto(String primerNombre, String apellidoPaterno, String correo, String nombreUsuario, String password, Set<String> roles) {
        this.primerNombre = primerNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.roles = roles;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
