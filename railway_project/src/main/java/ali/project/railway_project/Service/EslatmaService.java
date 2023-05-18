package ali.project.railway_project.Service;

import ali.project.railway_project.Entity.Eslatma;
import ali.project.railway_project.Payload.ResEslatma;
import ali.project.railway_project.Repository.EslatmaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EslatmaService {

    private final EslatmaRepo eslatmaRepo;


    public List<ResEslatma> getEslatma(){
        List<ResEslatma> resEslatmas = new ArrayList<>();
        for (Eslatma eslatma : eslatmaRepo.findAll()) {
            ResEslatma build = ResEslatma.builder()
                    .id(eslatma.getId())
                    .name(eslatma.getName())
                    .message(eslatma.getMessage())
                    .year(eslatma.getYear())
                    .kun(eslatma.getKun())
                    .oy(eslatma.getOy())
                    .build();
            resEslatmas.add(build);
            eslatmaRepo.save(eslatma);
        }
        return resEslatmas;
    }
}
