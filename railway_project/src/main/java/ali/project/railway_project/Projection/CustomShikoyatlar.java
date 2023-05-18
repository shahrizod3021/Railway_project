package ali.project.railway_project.Projection;

import ali.project.railway_project.Entity.Shikoyatlar;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Shikoyatlar.class, name = "customShikoyatlar")
public interface CustomShikoyatlar {
    Integer getId();

    String getName();

    String getMessage();

    String getUserPhone();
}
