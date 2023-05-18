package ali.project.railway_project.Controller;

import ali.project.railway_project.Entity.User;
import ali.project.railway_project.Payload.*;
import ali.project.railway_project.Repository.AuthRepository;
import ali.project.railway_project.Security.JwtTokenProvider;
import ali.project.railway_project.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody ReqLogin request) {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        User user = authRepository.findUserByPhoneNumber(request.getPhoneNumber()).get();
        ResToken resToken = new ResToken(generateToken(request.getPhoneNumber()));
        System.out.println(ResponseEntity.ok(getmalumot(user, resToken)));
        return ResponseEntity.ok(getmalumot(user, resToken));
    }

    @PostMapping("/verification")
    public HttpEntity<?> getCode(){
        ApiResponse code = authService.getCode();
        return ResponseEntity.status(code.isSuccess() ? 200 : 409).body(code);
    }

    @PostMapping("/code")
    public HttpEntity<?> verification(@RequestBody ReqCode reqCode){
        ApiResponse verificate = authService.verificate(reqCode);
        return ResponseEntity.status(verificate.isSuccess() ? 200 : 409).body(verificate);
    }

    @PostMapping("/reset")
    public HttpEntity<?> resetPassword(@RequestBody ReqPassword reqPassword){
        ApiResponse apiResponse = authService.resetPassword(reqPassword);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    public GetMal getmalumot(User user, ResToken resToken) {
        return new GetMal(user, resToken);
    }


    public String generateToken(String phoneNumber) {
        User user = authRepository.findUserByPhoneNumber(phoneNumber).get();
        return jwtTokenProvider.generateToken(user.getId());
    }
}
