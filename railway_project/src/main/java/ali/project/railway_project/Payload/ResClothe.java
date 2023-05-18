package ali.project.railway_project.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResClothe {
    private Integer id;
    private String name;
    private Integer giveDay;
    private Integer giveMonth;
    private Integer giveYear;
    private Integer awaredDay;
    private Integer awaredMonth;
    private Integer awaredYear;
    private UUID photoId;

}
