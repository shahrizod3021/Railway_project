package ali.project.railway_project.Payload;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqClothe {

    private String name;
    private Date olishVaqti;
    private Date eslatishVaqti;

}
