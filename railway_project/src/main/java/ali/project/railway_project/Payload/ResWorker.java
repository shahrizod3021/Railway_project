package ali.project.railway_project.Payload;

import ali.project.railway_project.Entity.Clothe;
import ali.project.railway_project.Service.ClotheService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResWorker {
    private UUID id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;

    private List<ResClothe> clothe = new ArrayList<>();

    private Long chatId;

}
