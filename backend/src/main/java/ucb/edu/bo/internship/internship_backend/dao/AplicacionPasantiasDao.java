package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;
import ucb.edu.bo.internship.internship_backend.entity.Curriculums;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.List;
import java.util.Optional;

public interface AplicacionPasantiasDao extends JpaRepository<Aplicacionespasantias, Integer>{

    Page<Aplicacionespasantias> findAllByPasantiasIdpasantias(Pasantias pasantias, Pageable pageable);

    Aplicacionespasantias findByPasantiasIdpasantiasAndUsuariosIdusuarios(Pasantias idpasantias, Usuarios idusuarios);

    List<Aplicacionespasantias> findByUsuariosIdusuariosAndPasantiasIdpasantias(Usuarios usuario, Pasantias pasantia);

    List<Aplicacionespasantias> findByCurriculumsIdcurriculums(Curriculums curriculum);

    @Query("SELECT COUNT(a) FROM Aplicacionespasantias a WHERE a.pasantiasIdpasantias.idpasantias = :idPasantia")
    Long countPorPasantia(@Param("idPasantia") Integer idPasantia);

    List<Aplicacionespasantias> findByUsuariosIdusuarios(Usuarios usuario);
    /*KPIS*/
    //Obtener la cantidad de aplicaciones de pasantias
    @Query("SELECT COUNT(a) FROM Aplicacionespasantias a")
    Optional<Long> countByPasantiasIdpasantias();
    // Obtener la cantidad de aplicaciones de pasantias por carrera
    @Query("SELECT COUNT(a) FROM Aplicacionespasantias a JOIN a.pasantiasIdpasantias p JOIN p.pasantiascarrerasList pc WHERE pc.carrerasIdcarreras.idcarreras = ?1")
    Optional<Long> countByPasantiasIdpasantiasByCarrera(Integer idcarrera);
    //Obtener la cantidad de aplicaciones activas de pasantias por empresa
    @Query("SELECT COUNT(a) FROM Aplicacionespasantias a JOIN a.pasantiasIdpasantias p JOIN p.institucionesIdinstituciones e WHERE e.idinstituciones = ?1 and a.activo = true")
    Optional<Long> countByPasantiasIdpasantiasByEmpresa(Integer idempresa);
    //Obtener la cantidad de aplicaciones de pasantias por sector
    @Query(value = "" +
            "SELECT COUNT(a) " +
            "FROM aplicacionespasantias a " +
            "JOIN pasantias p ON a.pasantias_idpasantias = p.idpasantias " +
            "JOIN instituciones e ON p.instituciones_idinstituciones = e.idinstituciones " +
            "WHERE a.activo = true AND exists(" +
            "SELECT 1 FROM jsonb_array_elements_text(e.sectores) as sector WHERE LOWER(sector) = LOWER(?1))"
            ,nativeQuery = true)
    Optional<Long> countByPasantiasIdpasantiasBySector(String sector);
    //Obtener la cantidad de aplicaciones a pasantia por area
    @Query(value = "" +
            "SELECT COUNT(a) " +
            "FROM aplicacionespasantias a " +
            "JOIN pasantias p ON a.pasantias_idpasantias = p.idpasantias " +
            "WHERE a.activo = true AND exists(" +
            "SELECT 1 FROM jsonb_array_elements_text(p.areas) as area WHERE LOWER(area) = LOWER(?1))"
            ,nativeQuery = true)
    Optional<Long> countByPasantiasIdpasantiasByArea(String area);
    //Obtener cantidad de aplicaciones aceptadas y pendientes
    @Query("SELECT COUNT(a) FROM Aplicacionespasantias a WHERE a.activo = ?1")
    Optional<Long> countByActivo(Boolean activo);
    /*FIN KPIS*/
}
