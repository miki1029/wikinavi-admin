package khc.wikinavi.admin.web.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by miki on 15. 10. 14..
 */
@Data
public class EdgeForm {
    @NotNull
    @Min(1)
    private Integer vertexId1;

    @NotNull
    @Min(1)
    private Integer vertexId2;

}
