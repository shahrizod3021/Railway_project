package ali.project.railway_project.Repository;

import ali.project.railway_project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    boolean existsUserByChatId(Long chatId);

}
