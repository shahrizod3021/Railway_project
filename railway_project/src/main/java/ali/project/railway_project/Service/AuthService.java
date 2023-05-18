package ali.project.railway_project.Service;
import ali.project.railway_project.Entity.Enums.RoleName;
import ali.project.railway_project.Entity.User;
import ali.project.railway_project.Payload.ApiResponse;
import ali.project.railway_project.Payload.ReqCode;
import ali.project.railway_project.Payload.ReqPassword;
import ali.project.railway_project.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    Integer code = 0;
    private final AuthRepository authRepository;

    private final JavaMailSender javaMailSender;

    private final WorkerService workerService;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDetails getUserById(UUID id) {
        return authRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findUserByPhoneNumber(username).get();
    }

    public ApiResponse getCode(){
        for (User user : authRepository.findAll()) {
            if (user.getRole().get(0).getRoleName().equals(RoleName.ADMIN)){
                code = (int) (Math.random() * 100000);
                String codeString = code.toString();
                System.out.println(codeString);
                workerService.sendToEmail(codeString, user.getEmail());
                return new  ApiResponse("Biz sizning emailingizga tasdiqlash parolini yubordik shuni tasdiqlang", true);
            }
        }
        return new ApiResponse("Emailda hatolik", false);
    }

    public ApiResponse verificate(ReqCode reqCode){
        if (reqCode.getCode().equals(code)) {
            return new ApiResponse(code.toString(), true);
        }
        return new ApiResponse("code hato", false);
    }

    public ApiResponse resetPassword(ReqPassword reqPassword){
        for (User user : authRepository.findAll()) {
            if (user.getRole().get(0).getRoleName().equals(RoleName.ADMIN)){
                user.setPassword(passwordEncoder().encode(reqPassword.getPassword()));
                authRepository.save(user);
                return new ApiResponse("parol o'zgartirildi", true);
            }
            return new ApiResponse("Parolni o'zgartirishda hatolik", false);

        }
        return new ApiResponse("ma'lumotlar bazasida hatolik", false);
    }
}
