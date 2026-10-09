package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsuariosInstitucionesDao extends JpaRepository<Usuariosinstituciones, Integer>{
    @Query("SELECT u FROM Usuariosinstituciones u WHERE u.institucionesIdinstituciones.idinstituciones = ?1 and u.usuariosIdusuarios.idusuarios = ?2")
    Optional<Usuariosinstituciones> findByIdInstitucionesAndIdUsuarios(Integer idInstituciones, Integer idUsuarios);
    @Query("SELECT COUNT(u) > 0 FROM Usuariosinstituciones u WHERE u.usuariosIdusuarios.kcUuid = ?1 AND u.institucionesIdinstituciones.idinstituciones = ?2 and u.usuariosIdusuarios.activo = true and u.activo = true")
    Boolean existsByUsuariosUuidAndInstitucionesIdinstituciones(String uuid, Integer idInstitucion);
    @Query("SELECT COUNT(u) > 0 FROM Usuariosinstituciones u WHERE u.usuariosIdusuarios.kcUuid = ?1 and u.usuariosIdusuarios.activo = true")
    Boolean existsByUsuariosUuid(String uuid);
    Usuariosinstituciones findByUsuariosIdusuariosAndInstitucionesIdinstituciones(Usuarios idUsuario, Instituciones idInstitucion);
   
   
    Usuariosinstituciones findByUsuariosIdusuarios(Usuarios usuario);
    //Obtener todas las solicitudes con activo igual a false
    List<Usuariosinstituciones> findByActivoFalse();
    //Obtener todas las instituciones
    List<Usuariosinstituciones> findByInstitucionesIdinstitucionesNombreContainsAndActivoFalse(String nombreInstitucion);

    List<Usuariosinstituciones> findByInstitucionesIdinstitucionesIdinstitucionesAndActivoTrue(Integer idInstitucion);
    @Query("SELECT COUNT(u) from Usuariosinstituciones u where u.activo = true and u.institucionesIdinstituciones.idinstituciones = ?1")
    Optional<Long> countUsuariosPorEmpresa(Integer idEmpresaUsuarios);
}
