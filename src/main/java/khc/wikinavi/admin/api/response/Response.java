package khc.wikinavi.admin.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by miki on 15. 10. 19..
 */
@Data
@NoArgsConstructor
public class Response<T> {
    @Data
    class Result {
        private int value;
        private String description;
    }
    private Result result;
    private List<T> dataSet;

    public Response(List<T> tList) {
        result = new Result();
        result.value = 0;
        result.description = "success";
        dataSet = tList;
    }
}
