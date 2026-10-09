package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.EnvioCorreoPasantia;

import java.util.List;

public interface EnvioCorreoPasantiaDao extends JpaRepository<EnvioCorreoPasantia, Integer> {

    List<EnvioCorreoPasantia> findByPasantia_IdpasantiasOrderByFechaEnvioDesc(Integer idPasantia);

    boolean existsByPasantia_IdpasantiasAndCorreoDestinatario(Integer idPasantia, String correoDestinatario);

    @Query("SELECT COUNT(e) FROM EnvioCorreoPasantia e WHERE e.pasantia.idpasantias = :idPasantia AND e.estado = 'ENVIADO'")
    Long countCorreosEnviados(@Param("idPasantia") Integer idPasantia);

    @Query("SELECT COUNT(e) FROM EnvioCorreoPasantia e WHERE e.pasantia.idpasantias = :idPasantia AND e.estado = 'FALLIDO'")
    Long countCorreosFallidos(@Param("idPasantia") Integer idPasantia);

    @Query("SELECT COUNT(e) FROM EnvioCorreoPasantia e WHERE e.pasantia.idpasantias = :idPasantia")
    Long countTotalIntentosPorPasantia(@Param("idPasantia") Integer idPasantia);
}
