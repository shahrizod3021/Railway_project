package ali.project.railway_project.Repository;

import ali.project.railway_project.Entity.BotEslatma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotEslatmaRepo extends JpaRepository<BotEslatma, Integer> {
    boolean existsBotEslatmaByMessage(String message);
}
