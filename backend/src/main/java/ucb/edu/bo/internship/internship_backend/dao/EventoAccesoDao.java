package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.EventoAcceso;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;

import java.util.Date;
import java.util.List;

public interface EventoAccesoDao extends JpaRepository<EventoAcceso, Integer> {

    List<EventoAcceso> findByPadronEstudianteOrderByFechaHoraDesc(PadronEstudiante padronEstudiante);

    Page<EventoAcceso> findByPadronEstudianteOrderByFechaHoraDesc(PadronEstudiante padronEstudiante, Pageable pageable);

    @Query("SELECT e FROM EventoAcceso e WHERE e.padronEstudiante = :padron AND e.fechaHora >= :desde ORDER BY e.fechaHora DESC")
    List<EventoAcceso> findRecientesPorEstudiante(@Param("padron") PadronEstudiante padron, @Param("desde") Date desde);

    @Query("SELECT COUNT(DISTINCT e.padronEstudiante.idpadron) FROM EventoAcceso e")
    Long countEstudiantesDistintosConAcceso();

    @Query("SELECT COUNT(DISTINCT e.padronEstudiante.idpadron) FROM EventoAcceso e WHERE e.padronEstudiante.carrerasIdcarreras.idcarreras = :carreraId")
    Long countEstudiantesDistintosConAccesoPorCarrera(@Param("carreraId") Integer carreraId);

    Long countByPadronEstudiante(PadronEstudiante padronEstudiante);
}
