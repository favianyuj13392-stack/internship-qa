package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadron;

public interface ImportacionPadronDao extends JpaRepository<ImportacionPadron, Integer> {
    Page<ImportacionPadron> findAllByOrderByFechaDesc(Pageable pageable);
}
