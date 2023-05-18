package ali.project.railway_project.Entity;

import ali.project.railway_project.Entity.AbsEntity.AbsNameEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shikoyatlar extends AbsNameEntity {

    @Column(length = 100000)
    private String message;

    private String userPhone;

    public Shikoyatlar(String name, String message, String userPhone) {
        super(name);
        this.message = message;
        this.userPhone = userPhone;
    }
}
