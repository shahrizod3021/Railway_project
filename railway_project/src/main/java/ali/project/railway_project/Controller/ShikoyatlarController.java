package ali.project.railway_project.Controller;

import ali.project.railway_project.Payload.ResShikoyat;
import ali.project.railway_project.Repository.ShikoyatlarRepo;
import ali.project.railway_project.Service.ShikoyatlarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shikoyatlar")
@RequiredArgsConstructor
public class ShikoyatlarController {

    private final ShikoyatlarService shikoyatlarService;

    @GetMapping
    public HttpEntity<?> getShikoyat(){
        List<ResShikoyat> shikoyat = shikoyatlarService.getShikoyat();
        return ResponseEntity.ok(shikoyat);
    }
}
