package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadron;
import ucb.edu.bo.internship.internship_backend.entity.ImportacionPadronError;

import java.util.List;

public interface ImportacionPadronErrorDao extends JpaRepository<ImportacionPadronError, Integer> {
    List<ImportacionPadronError> findByImportacionPadronOrderByFilaAsc(ImportacionPadron importacionPadron);
}
