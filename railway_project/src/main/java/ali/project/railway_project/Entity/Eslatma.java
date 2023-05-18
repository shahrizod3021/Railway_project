package ali.project.railway_project.Entity;

import ali.project.railway_project.Entity.AbsEntity.AbsNameEntity;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Eslatma extends AbsNameEntity {
    private String message;

    private String userPhone;

    private Integer year;
    private Integer oy;
    private Integer kun;
}
