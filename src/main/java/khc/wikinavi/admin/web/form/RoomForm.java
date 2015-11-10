package khc.wikinavi.admin.web.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by miki on 15. 10. 14..
 */
@Data
public class RoomForm {
    @NotNull
    @Min(1)
    private Integer x;

    @NotNull
    @Min(1)
    private Integer y;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    private Date modifiedTime;
}
