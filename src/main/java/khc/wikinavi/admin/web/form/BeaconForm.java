package khc.wikinavi.admin.web.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by miki on 15. 10. 14..
 */
@Data
public class BeaconForm {
    @NotNull
    @Min(1)
    private Integer x;

    @NotNull
    @Min(1)
    private Integer y;

    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    @Size(min = 17, max = 17)
    private String macAddr;
}
