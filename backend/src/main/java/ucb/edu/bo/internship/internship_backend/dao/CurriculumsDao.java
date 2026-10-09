package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Curriculums;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CurriculumsDao extends JpaRepository<Curriculums, Integer>{
    Curriculums findByTitulo(String titulo);
    Curriculums findByTituloAndUsuariosIdusuarios(String titulo, Usuarios usuario);
    List<Curriculums> findByUsuariosIdusuarios(Usuarios usuario);
    @Query(value = "SELECT AVG(curriculum_count) " +
            "FROM (SELECT COUNT(*) AS curriculum_count " +
            "      FROM curriculums c " +
            "      JOIN usuarios u ON c.usuarios_idusuarios = u.idusuarios " +
            "      WHERE u.roles_idroles = ?1" +
            "      GROUP BY u.idusuarios) subquery",
            nativeQuery = true)
    Optional<Double> promedioCurriculumsPorEstudiante(Integer roleId);
}
