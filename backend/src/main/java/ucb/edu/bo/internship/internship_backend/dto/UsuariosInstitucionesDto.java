package ucb.edu.bo.internship.internship_backend.dto;


import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;

public class UsuariosInstitucionesDto {
    private Integer idUsuariosInstituciones;
    private Integer idUsuarios;
    private Integer idInstituciones;
    private String cargo;

    public UsuariosInstitucionesDto() {
    }

    public UsuariosInstitucionesDto(Integer idUsuariosInstituciones, Integer idUsuarios, Integer idInstituciones, String cargo) {
        this.idUsuariosInstituciones = idUsuariosInstituciones;
        this.idUsuarios = idUsuarios;
        this.idInstituciones = idInstituciones;
        this.cargo = cargo;
    }

    public Integer getIdUsuariosInstituciones() {
        return this.idUsuariosInstituciones;
    }

    public void setIdUsuariosInstituciones(Integer idUsuariosInstituciones) {
        this.idUsuariosInstituciones = idUsuariosInstituciones;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Integer getIdInstituciones() {
        return this.idInstituciones;
    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public String getCargo() {
        return this.cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public static UsuariosInstitucionesDto fromEntity(Usuariosinstituciones usuariosinstituciones){
        UsuariosInstitucionesDto usuariosInstitucionesDto = new UsuariosInstitucionesDto();
        usuariosInstitucionesDto.setIdUsuariosInstituciones(usuariosinstituciones.getIdusuariosinstituciones());
        usuariosInstitucionesDto.setIdUsuarios(usuariosinstituciones.getUsuariosIdusuarios().getIdusuarios());
        usuariosInstitucionesDto.setIdInstituciones(usuariosinstituciones.getInstitucionesIdinstituciones().getIdinstituciones());
        usuariosInstitucionesDto.setCargo(usuariosinstituciones.getCargo());
        return usuariosInstitucionesDto;
    }
    
    @Override
    public String toString() {
        return "UsuariosInstitucionesDto [cargo=" + cargo + ", idInstituciones=" + idInstituciones + ", idUsuarios="
                + idUsuarios + ", idUsuariosInstituciones=" + idUsuariosInstituciones + "]";
    }
    
}
