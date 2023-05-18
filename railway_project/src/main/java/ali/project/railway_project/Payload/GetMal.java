package ali.project.railway_project.Payload;

import ali.project.railway_project.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMal {
    private User user;
    private ResToken resToken;
}
