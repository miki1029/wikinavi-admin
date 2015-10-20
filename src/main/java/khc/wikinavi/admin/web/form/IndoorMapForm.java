package khc.wikinavi.admin.web.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by miki on 15. 10. 14..
 */
@Data
public class IndoorMapForm {
    @NotNull
    @Size(min = 1, max = 44)
    private String title;

    @NotNull
    @Size(min = 1, max = 127)
    private String address;

    @NotNull
    @Size(min = 1, max = 2047)
    private String imagePath;

    @NotNull
    @Min(10)
    @Max(500)
    private Integer tileWidth;

    @NotNull
    private Integer tileHeight;

    private Date createdTime;

    private Date modifiedTime;
}
