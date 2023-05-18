package ali.project.railway_project.Payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqLogin {
    private String phoneNumber;
    private String password;
}
