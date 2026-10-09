package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Pasantiascarreras;

import java.util.List;

public interface PasantiasCarrerasDao extends JpaRepository<Pasantiascarreras, Integer>{

    List<Pasantiascarreras> findAllByPasantiasIdpasantias(Pasantias pasantiasIdpasantias);
    
}
