package ali.project.railway_project.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Clothe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false)
    private Date kiyimOlishvaqti;

    @Column(nullable = false)
    private Date eslatishVaqti;

    private UUID photoId;


}
