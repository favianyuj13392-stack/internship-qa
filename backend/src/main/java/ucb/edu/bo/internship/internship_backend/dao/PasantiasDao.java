package ucb.edu.bo.internship.internship_backend.dao;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.dto.PasantiaNombreDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PasantiasDao extends JpaRepository<Pasantias, Integer>{

    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfter(
            Date fechacierre,
            Pageable pageable
    );
    List<Pasantias> findAllByActivoIsTrueAndFechacierreAfter(
            Date fechacierre
    );

    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
            Date fechacierre,
            String titulo,
            Pageable pageable
    );
    @Query("SELECT p FROM Pasantias p WHERE p.activo = true AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) AND (p.sinaplicantes IS NULL OR p.sinaplicantes = false)")
    Page<Pasantias> findAllByActivoIsTrueAndTituloContainingIgnoreCase(
            String titulo,
            Pageable pageable
    );

    @Query(value = """
            SELECT DISTINCT p.*\s
            FROM pasantias p
            JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
            WHERE p.activo = true\s
            AND p.fechacierre > :fechacierre\s
            AND pc.carreras_idcarreras = :idCarrera
            """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndCarreras(
            Date fechacierre,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        WHERE p.activo = true
          AND p.fechacierre > :fechacierre
          AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(p.areas) AS area
              WHERE area IN (:areas)
          )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndAreas(
            Date fechacierre,
            @Param("areas") List<String> areas,
            Pageable pageable
    );


    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
          AND p.fechacierre > :fechacierre
          AND pc.carreras_idcarreras = :idCarrera
          AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(p.areas) AS area
              WHERE area IN (:areas)
          )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndAreasAndCarreras(
            Date fechacierre,
            @Param("areas") List<String> areas,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND pc.carreras_idcarreras = :idCarrera
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndCarreras(
            Date fechacierre,
            String titulo,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements_text(p.areas) AS area
                WHERE area IN (:areas)
            )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreas(
            Date fechacierre,
            String titulo,
            @Param("areas") List<String> areas,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND pc.carreras_idcarreras = :idCarrera
            AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements_text(p.areas) AS area
                WHERE area IN (:areas)
            )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
            Date fechacierre,
            String titulo,
            @Param("areas") List<String> areas,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
    WITH pasantia_carreras AS (
        SELECT carreras_idcarreras
        FROM pasantiascarreras
        WHERE pasantias_idpasantias = :pasantiaId
    ),
    pasantias_comunes AS (
        SELECT pc.pasantias_idpasantias
        FROM pasantiascarreras pc
        JOIN pasantia_carreras pc_target ON pc.carreras_idcarreras = pc_target.carreras_idcarreras
        WHERE pc.pasantias_idpasantias <> :pasantiaId
        GROUP BY pc.pasantias_idpasantias
    )
    SELECT p.*
    FROM pasantias p
    JOIN pasantias_comunes pc ON p.idpasantias = pc.pasantias_idpasantias
    WHERE p.activo = true and p.sinaplicantes is not true
    ORDER BY (SELECT COUNT(*) FROM aplicacionespasantias ap WHERE ap.pasantias_idpasantias = p.idpasantias) DESC
    LIMIT 3;
""", nativeQuery = true)
    List<Pasantias> findTop3RelatedPasantias(
            @Param("pasantiaId") Integer pasantiaId
    );

    Pasantias findByIdpasantiasAndActivoIsTrue(Integer idpasantias);
    @Query("select count (*) from Pasantias p where p.activo = true")
    Long countAllByActivo();
    @Query(value = "" +
            "select count (*) from Pasantias p " +
            "where p.activo = true " +
            "AND p.sinaplicantes is not true", nativeQuery = true)
    Long countAllByActivoAndSinaplicantesIsNotTrue();

    Page<Pasantias> findAllByInstitucionesIdinstitucionesAndTituloContainingIgnoreCase(
            Instituciones instituciones,
            String titulo,
            Pageable pageable
    );

    Page<Pasantias> findAllByInstitucionesIdinstituciones(
            Instituciones instituciones,
            Pageable pageable
    );







    //@Query("SELECT Pasantias FROM Pasantias p WHERE p.institucionesIdinstituciones.idinstituciones = ?1")
    List<Pasantias> findPasantiasByInstitucionesIdinstituciones(Instituciones idInstituciones);
    List<Pasantias> findPasantiasByInstitucionesIdinstitucionesAndActivoIsTrue(Instituciones idInstituciones);
    //Obtener todas las pasantias activas y con sinaplicates diferentes de true por id de institucion
    @Query( value = """
            SELECT p
            FROM Pasantias p
            WHERE p.institucionesIdinstituciones.idinstituciones = ?1
            AND p.activo = true
            AND (p.sinaplicantes IS NULL OR p.sinaplicantes = false)
            """ )
    List<Pasantias> findPasantiasByInstitucionesIdinstitucionesAndActivoIsTrueAndSinaplicantesIsNotTrue(Integer idInstituciones);

    @Query("SELECT p FROM Pasantias p WHERE p.activo = false AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :search, '%')) AND (p.sinaplicantes IS NULL OR p.sinaplicantes = false)")
    Page<Pasantias> findAllByActivoIsFalseAndTituloContainingIgnoreCase(String search, Pageable pageable);
    @Query("SELECT p FROM Pasantias p WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :search, '%')) AND (p.sinaplicantes IS NULL OR p.sinaplicantes = false)")
    Page<Pasantias> findAllByTituloContainingIgnoreCase(String search, Pageable pageable);


    @Query("SELECT COUNT(p) FROM Pasantias p WHERE p.institucionesIdinstituciones.idinstituciones = ?1")
Long countAllByInstitucionesIdinstituciones(Integer idInstituciones);


//obtener las pasantias que conecten con el usuario institucion y el campo uuid de la tabla usuario hazlo query nativo
@Query(value = """
    SELECT
	pasantias.*
FROM
	pasantias
	INNER JOIN
	usuarios
	ON 
		pasantias.usuarios_idusuarios = usuarios.idusuarios
WHERE
	usuarios.kc_uuid = :uuid
    ORDER BY pasantias.idpasantias DESC
""", nativeQuery = true)
List<Pasantias> findPasantiasByInstitucionesIdinstitucionesUsuarioUUID(String uuid);

    /*KPIS*/
    //Cantidad de pasantias activas y pendientes
    @Query("SELECT COUNT(p) FROM Pasantias p WHERE p.activo = ?1 AND p.sinaplicantes = false")
    Optional<Long> countPasantiasActivo(Boolean activo);
    //Cantidad de pasantias por carrera
    @Query("SELECT COUNT(p) FROM Pasantias p JOIN Pasantiascarreras pc ON p.idpasantias = pc.pasantiasIdpasantias.idpasantias WHERE pc.carrerasIdcarreras.idcarreras = ?1 AND p.activo = true AND p.sinaplicantes = false")
    Optional<Long> countPasantiasPorCarrera(Integer idCarrera);
    //Cantidad de pasantias por empresa
    @Query("SELECT COUNT(p) FROM Pasantias p WHERE p.institucionesIdinstituciones.idinstituciones = ?1 AND p.activo = true AND p.sinaplicantes = false")
    Optional<Long> countPasantiasPorEmpresa(Integer idEmpresa);
    //Cantidad de pasantias por sector
    @Query(value = "" +
            "select count (p)" +
            "from Pasantias p " +
            "inner join public.instituciones a on p.instituciones_idinstituciones = a.idinstituciones " +
            "WHERE p.activo = true " +
            "AND p.sinaplicantes is not true " +
            "AND EXISTS(" +
            "SELECT 1 FROM jsonb_array_elements_text(a.sectores) AS sector WHERE sector = ?1" +
            ")",nativeQuery = true)
    Optional<Long> countPasantiasPorSector(String sector);
    //Cantidad de pasantias por area
    @Query(value = "" +
            "select count (p)" +
            "from Pasantias p " +
            "WHERE p.activo = true " +
            "AND p.sinaplicantes is not true " +
            "AND EXISTS(" +
            "SELECT 1 FROM jsonb_array_elements_text(p.areas) AS area WHERE area = ?1" +
            ")",nativeQuery = true)
    Optional<Long> countPasantiasPorArea(String area);
    @Query("SELECT COUNT(p) FROM Pasantias p WHERE p.sinaplicantes = true")
    Optional<Long> countPasantiasQueNoAceptaronEstudiantes();

    Page<Pasantias> findAllByActivoIsTrueAndSinaplicantesIsTrueAndTituloContainingIgnoreCase(String search, Pageable pageable);
    /*FIN KPIS*/
}
