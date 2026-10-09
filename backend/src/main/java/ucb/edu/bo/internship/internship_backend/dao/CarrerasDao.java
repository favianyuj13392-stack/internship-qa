package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Carreras;

import java.util.List;
import java.util.Optional;

public interface CarrerasDao extends JpaRepository<Carreras, Integer>{
    Optional<Carreras> findByNombreIgnoreCase(String nombre);
    List<Carreras> findAllByOrderByNombreAsc();
}
