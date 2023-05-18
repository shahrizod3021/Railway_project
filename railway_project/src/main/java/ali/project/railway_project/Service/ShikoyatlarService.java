package ali.project.railway_project.Service;

import ali.project.railway_project.Entity.Shikoyatlar;
import ali.project.railway_project.Payload.ApiResponse;
import ali.project.railway_project.Payload.ResShikoyat;
import ali.project.railway_project.Repository.ShikoyatlarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShikoyatlarService {

    private final ShikoyatlarRepo shikoyatlarRepo;

    public List<ResShikoyat> getShikoyat() {
        List<ResShikoyat> resShikoyats = new ArrayList<>();
        for (Shikoyatlar shikoyatlar : shikoyatlarRepo.findAll()) {
            ResShikoyat build = ResShikoyat.builder()
                    .id(shikoyatlar.getId())
                    .name(shikoyatlar.getName())
                    .message(shikoyatlar.getMessage())
                    .userPhone(shikoyatlar.getUserPhone())
                    .build();
            resShikoyats.add(build);
        }
        return resShikoyats;
    }
}
