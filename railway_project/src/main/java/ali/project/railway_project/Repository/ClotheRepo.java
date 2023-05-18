package ali.project.railway_project.Repository;

import ali.project.railway_project.Entity.Clothe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin

public interface ClotheRepo extends JpaRepository<Clothe, Integer> {
}
