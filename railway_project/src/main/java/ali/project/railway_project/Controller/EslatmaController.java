package ali.project.railway_project.Controller;

import ali.project.railway_project.Payload.ResEslatma;
import ali.project.railway_project.Service.EslatmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aware")
@RequiredArgsConstructor
public class EslatmaController {

    private final EslatmaService eslatmaService;

    @GetMapping
    public HttpEntity<?> getEslatma(){
        List<ResEslatma> eslatma = eslatmaService.getEslatma();
        return ResponseEntity.ok(eslatma);
    }
}
