package ali.project.railway_project.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResShikoyat {
    private Integer id;
    private String name;
    private String message;
    private String userPhone;
}
