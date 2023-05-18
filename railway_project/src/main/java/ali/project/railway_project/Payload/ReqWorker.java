package ali.project.railway_project.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqWorker {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
}
