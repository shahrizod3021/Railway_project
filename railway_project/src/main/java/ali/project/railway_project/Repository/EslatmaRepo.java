package ali.project.railway_project.Repository;

import ali.project.railway_project.Entity.Eslatma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EslatmaRepo extends JpaRepository<Eslatma, Integer> {

    boolean existsEslatmaByMessage(String message);
}
