package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.VistaPasantia;

import java.util.List;
import java.util.Optional;

public interface VistaPasantiaDao extends JpaRepository<VistaPasantia, Integer> {

    Optional<VistaPasantia> findByPadronEstudianteAndPasantia(PadronEstudiante padronEstudiante, Pasantias pasantia);

    List<VistaPasantia> findByPadronEstudianteOrderByUltimaVistaDesc(PadronEstudiante padronEstudiante);

    List<VistaPasantia> findByPasantia(Pasantias pasantia);

    @Query("SELECT COUNT(v) FROM VistaPasantia v WHERE v.pasantia.idpasantias = :idPasantia")
    Long countEstudiantesUnicosPorPasantia(@Param("idPasantia") Integer idPasantia);

    @Query("SELECT COALESCE(SUM(v.veces), 0) FROM VistaPasantia v WHERE v.pasantia.idpasantias = :idPasantia")
    Long sumTotalVistasPorPasantia(@Param("idPasantia") Integer idPasantia);

    @Query("SELECT COUNT(v) FROM VistaPasantia v WHERE v.pasantia.idpasantias = :idPasantia AND v.origen = 'CORREO'")
    Long countVistasPorOrigenCorreo(@Param("idPasantia") Integer idPasantia);

    @Query("SELECT COUNT(v) FROM VistaPasantia v WHERE v.pasantia.idpasantias = :idPasantia AND (v.origen = 'WEB' OR v.origen IS NULL)")
    Long countVistasPorOrigenWeb(@Param("idPasantia") Integer idPasantia);
}
