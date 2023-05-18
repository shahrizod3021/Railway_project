package ali.project.railway_project.Component;

import ali.project.railway_project.Entity.Enums.RoleName;
import ali.project.railway_project.Entity.Role;
import ali.project.railway_project.Entity.User;
import ali.project.railway_project.Repository.AuthRepository;
import ali.project.railway_project.Repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final AuthRepository authRepository;

    private final RoleRepo roleRepo;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;


    @Override
    public void run(String... args) throws Exception {
        if (init.equals("create-drop") || init.equals("create")){
            for (RoleName value : RoleName.values()) {
                roleRepo.save(new Role(value));
            }
            authRepository.save(
                    new User(
                            "shahrizod",
                            "mirzaaliyev",
                            "980009792",
                            passwordEncoder.encode("0009792"),
                            Collections.singletonList(roleRepo.findById(2).get()),
                                "alimirzaaliyevdev@gmail.com")
            );
        }
    }
}
