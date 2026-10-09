package ucb.edu.bo.internship.internship_backend.dao;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface InstitucionesDao extends JpaRepository<Instituciones, Integer>{
    @Query("SELECT i FROM Instituciones i WHERE i.activo = ?1")
    Page<Instituciones> findAllByActivo(Boolean activo, Pageable pageable);
    List<Instituciones> findAllByActivoIsTrue();
    @Query("SELECT i FROM Instituciones i WHERE i.idinstituciones = ?1 AND i.activo = ?2")
    Instituciones findByIdinstitucionesAndActivo(Integer idinstituciones, Boolean activo);
    @Query("SELECT i FROM Instituciones i WHERE i.nombre ILIKE CONCAT('%', ?1, '%') AND i.activo = ?2")
    Page<Instituciones> findAllByNombreContainingAndActivo(String search, Boolean activo, Pageable pageable);
    @Query("SELECT i FROM Instituciones i WHERE i.nombre ILIKE CONCAT('%', ?1, '%')")
    Page<Instituciones> findAllByNombreContaining(String search, Pageable pageable);

    @Query("select new ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto(i,COUNT(p.idpasantias)) from Instituciones i left join Pasantias p on i.idinstituciones = p.institucionesIdinstituciones.idinstituciones where i.activo = ?1 group by i.idinstituciones")
    Page<InstitucionesDto> findAllByActivoWithCountPasantias(Boolean activo, Pageable pageable);

    @Query("select new ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto(i,COUNT(p.idpasantias)) from Instituciones i left join Pasantias p on i.idinstituciones = p.institucionesIdinstituciones.idinstituciones where i.nombre ILIKE CONCAT('%', ?1, '%') and i.activo = ?2 and p.activo=true and p.sinaplicantes=false group by i.idinstituciones")
    Page<InstitucionesDto> findAllWithCountPasantiasAndNombreContainingAndActivo(String search,Boolean active, Pageable pageable);

    @Query("select new ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto(i,COUNT(p.idpasantias)) from Instituciones i left join Pasantias p on i.idinstituciones = p.institucionesIdinstituciones.idinstituciones where i.nombre ILIKE CONCAT('%', ?1, '%') group by i.idinstituciones")
    Page<InstitucionesDto> findAllWithCountPasantiasAndNombreContaining(String search, Pageable pageable);
    @Query("select new ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto(i,COUNT(p.idpasantias)) from Instituciones i left join Pasantias p on i.idinstituciones = p.institucionesIdinstituciones.idinstituciones where i.activo=true and p.activo=true and p.sinaplicantes=false group by i.idinstituciones")
    Page<InstitucionesDto> findAllWithCountPasantias(Pageable pageable);
    @Query("select new ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto(i,COUNT(p.idpasantias)) from Instituciones i left join Pasantias p on i.idinstituciones = p.institucionesIdinstituciones.idinstituciones where i.activo = ?1 and p.activo=true and p.sinaplicantes=false group by i.idinstituciones")
    Page<InstitucionesDto> findAllWithCountPasantiasAndActivo(Boolean activo,Pageable pageable);

    @Query("SELECT new ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto(i.idinstituciones, i.nombre,i.logoempresa) FROM Instituciones i WHERE i.activo = ?1")
    List<InstitucionNombreDto> getAllIdAndNameByActivo(Boolean activo);
    @Query(value = """
        SELECT i.*, COUNT(p.idpasantias) as pasantias_count
        FROM instituciones i
        LEFT JOIN pasantias p ON i.idinstituciones = p.instituciones_idinstituciones
        WHERE EXISTS (
            SELECT 1
            FROM jsonb_array_elements_text(i.sectores) as sector
            WHERE LOWER(sector) IN (
                SELECT LOWER(jsonb_array_elements_text(sectores)::text)
                FROM instituciones
                WHERE idinstituciones = ?1
            )
        )
        AND i.idinstituciones != ?1
        AND i.activo = true
        GROUP BY i.idinstituciones
        ORDER BY pasantias_count DESC
        LIMIT 4
    """, nativeQuery = true)
    List<Instituciones> findTop4InstitucionesBySectoresAndPasantias(Integer idInstitucion);
    @Query("SELECT new ucb.edu.bo.internship.internship_backend.dto.InstitucionesConCOUNTPasantiasDto(i.idinstituciones,i.nombre, COUNT(p.idpasantias),i.logoempresa) FROM Instituciones i JOIN Pasantias p ON i.idinstituciones = p.institucionesIdinstituciones.idinstituciones WHERE i.activo = ?1 AND p.activo = true AND p.sinaplicantes=false GROUP BY i.idinstituciones order by COUNT(p.idpasantias) DESC")
    Page<InstitucionesConCOUNTPasantiasDto> getAllNameAndCountPasantiasByActivo(boolean b, Pageable pageable);
    @Query("select count (*) from Instituciones i where i.activo = true")
    Long countAllByActivo();

    Boolean existsByIdinstitucionesAndActivoIsTrue(Integer idInstitucion);

//    @Query("SELECT i.idinstituciones,i.nombre FROM Instituciones i WHERE i.activo = ?1")
//    List<Object[]> getAllIdAndNameByActivo(Boolean activo);

    Instituciones findByNombre(String nombre);

    @Query(value = """
    SELECT DISTINCT i.*
    FROM instituciones i
    WHERE i.activo = true
    AND LOWER(i.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
    AND EXISTS(
        SELECT 1
        FROM jsonb_array_elements_text(i.sectores) AS sector
        WHERE sector = (:sectorParam)
    )
    """, nativeQuery = true)
    Page<Instituciones> findAllWithCountPasantiasAndNombreAndSectores(String search, String sectorParam, Pageable pageable);


    @Query(value = """
    SELECT DISTINCT i.*
    FROM instituciones i
    WHERE i.activo = :activo
    AND LOWER(i.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
    AND EXISTS(
        SELECT 1
        FROM jsonb_array_elements_text(i.sectores) AS sector
        WHERE sector = (:sectorParam)
    )
    """, nativeQuery = true)
    Page<Instituciones> findAllWithCountPasantiasAndNombreAndSectoresAndActivo(String search, String sectorParam,Boolean activo, Pageable pageable);
    @Query(value = """
    SELECT DISTINCT i.*
    FROM instituciones i
    WHERE i.activo = true
    AND EXISTS(
        SELECT 1
        FROM jsonb_array_elements_text(i.sectores) AS sector
        WHERE sector = (:sectorParam)
    )
    """, nativeQuery = true)
    Page<Instituciones> findAllBySector(
            String sectorParam,
            Pageable pageable
    );

    @Query(value = """
    SELECT DISTINCT i.*
    FROM instituciones i
    WHERE i.activo = :activo
    AND EXISTS(
        SELECT 1
        FROM jsonb_array_elements_text(i.sectores) AS sector
        WHERE sector = (:sectorParam)
    )
    """, nativeQuery = true)
    Page<Instituciones> findAllBySectorAndActivo(
            String sectorParam,
            Boolean activo,
            Pageable pageable
    );
    //Funcion que devuelve un long con la cantidad de instituciones que tienen un sector especifico
    @Query(value = """
    SELECT COUNT(i.idinstituciones)
    FROM instituciones i
    WHERE EXISTS(
        SELECT 1
        FROM jsonb_array_elements_text(i.sectores) AS sector
        WHERE lower(sector) = lower(?1)
    )
    """, nativeQuery = true)
    Optional<Long> countAllBySector(String sector);
}
