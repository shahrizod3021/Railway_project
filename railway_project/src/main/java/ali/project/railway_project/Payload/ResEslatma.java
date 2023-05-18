package ali.project.railway_project.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.IntegerType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResEslatma {
    private Integer id;
    private String name;
    private String message;
    private Integer year;
    private Integer oy;
    private Integer kun;
}
