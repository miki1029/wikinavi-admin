package khc.wikinavi.admin.api.response;

import khc.wikinavi.admin.domain.Edge;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by miki on 15. 10. 19..
 */
@Data
@NoArgsConstructor
public class EdgeData {
    private int weight;
    private int vertexId1;
    private int vertexId2;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public EdgeData(Edge edge) {
        weight = edge.getWeight();
        vertexId1 = edge.getVertex1().getId();
        vertexId2 = edge.getVertex2().getId();
        x1 = edge.getVertex1().getX();
        y1 = edge.getVertex1().getY();
        x2 = edge.getVertex2().getX();
        y2 = edge.getVertex2().getY();
    }
}
