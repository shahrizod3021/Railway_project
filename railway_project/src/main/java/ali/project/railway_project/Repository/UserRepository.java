package ali.project.railway_project.Repository;

import ali.project.railway_project.Entity.Clothe;
import ali.project.railway_project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByChatId(Long chatId);

    boolean existsUserByPhoneNumberEqualsIgnoreCase(String phoneNumber);

    Optional<User> findUserByChatId(Long chatId);

}
