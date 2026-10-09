package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Roles;

public interface RolesDao extends JpaRepository<Roles, Integer>{
    
    @Query("SELECT r FROM Roles r WHERE r.rol = :role")
    Roles findByRol(String role);
}
