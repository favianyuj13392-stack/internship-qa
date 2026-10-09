package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Carreras;
import ucb.edu.bo.internship.internship_backend.entity.Roles;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.List;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.Date;
import java.util.Optional;


public interface UsuariosDao extends JpaRepository<Usuarios, Integer>{

    Usuarios findByKcUuid(String kcUuid);
    @Query("SELECT COUNT(u) from Usuarios u where u.rolesIdroles.rol = 'ESTUDIANTE'")
    Long countAllByActivoAndRolesIdrolesRolEqualsESTUDIANTE();

    @Query("SELECT COUNT(u)>0 from Usuarios u where u.activo = true and u.kcUuid = ?1 and u.rolesIdroles.rol = ?2")
    Boolean userIs(String kcUuid,String role);


    List<Usuarios> findAllByRolesIdrolesAndCarrerasIdcarrerasIn(
            Roles rol,
            List<Carreras> carreras);
    /*KPIS*/
    //Count estudiantes
    @Query("SELECT COUNT(u) from Usuarios u where  u.rolesIdroles.rol = 'ESTUDIANTE'")
    Optional<Long> countEstudiantes();
    //Count estudiantes por carrera
    @Query("SELECT COUNT(u) from Usuarios u where u.rolesIdroles.rol = 'ESTUDIANTE' and u.carrerasIdcarreras.idcarreras = ?1")
    Optional<Long> countEstudiantesPorCarrera(Integer idcarreras);
    //Count estudiantes por fecha inicio y fin
    @Query("SELECT COUNT(u) from Usuarios u where u.rolesIdroles.rol = 'ESTUDIANTE' and u.fecharegistro BETWEEN :fechaInicio AND :fechaFin")
    Optional<Long> countEstudiantesPorFecha(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
    //Count usuarios empresa
    @Query("SELECT COUNT(u) from Usuarios u where u.activo = true and u.rolesIdroles.rol = 'EMPRESA'")
    Optional<Long> countUsuariosEmpresa();
    /*FIN KPIS*/
}
