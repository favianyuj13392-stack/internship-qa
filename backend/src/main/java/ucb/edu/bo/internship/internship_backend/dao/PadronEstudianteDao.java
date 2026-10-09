package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.entity.PadronEstudiante;

import java.util.List;
import java.util.Optional;

public interface PadronEstudianteDao extends JpaRepository<PadronEstudiante, Integer> {

    Optional<PadronEstudiante> findByCorreoIgnoreCase(String correo);

    Optional<PadronEstudiante> findByKcUuid(String kcUuid);

    boolean existsByCorreoIgnoreCase(String correo);

    @Query("SELECT p FROM PadronEstudiante p WHERE " +
           "(:carreraId IS NULL OR p.carrerasIdcarreras.idcarreras = :carreraId) AND " +
           "(:estado IS NULL OR p.estado = :estado) AND " +
           "(:anioIngreso IS NULL OR p.anioIngreso = :anioIngreso) AND " +
           "(:search IS NULL OR :search = '' OR " +
           " LOWER(p.correo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(p.nombres) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(p.codigoEstudiante) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<PadronEstudiante> findByFiltros(
            @Param("carreraId") Integer carreraId,
            @Param("estado") String estado,
            @Param("anioIngreso") Integer anioIngreso,
            @Param("search") String search,
            Pageable pageable
    );

    List<PadronEstudiante> findByPrimerAccesoIsNullOrderByApellidosAsc();

    @Query("SELECT COUNT(p) FROM PadronEstudiante p WHERE p.carrerasIdcarreras.idcarreras = :carreraId")
    Long countPorCarrera(@Param("carreraId") Integer carreraId);

    @Query("SELECT p FROM PadronEstudiante p WHERE p.carrerasIdcarreras.idcarreras IN :carreraIds AND UPPER(p.estado) = 'ACTIVO'")
    List<PadronEstudiante> findEstudiantesActivosPorCarreras(@Param("carreraIds") List<Integer> carreraIds);
}
