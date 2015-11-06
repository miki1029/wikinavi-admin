package khc.wikinavi.admin.api.response;

import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.domain.Vertex;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by miki on 15. 10. 19..
 */
@Data
@NoArgsConstructor
public class VertexData {
    private int vertexId;
    private String name;
    private int x;
    private int y;
    private String type;
    private String macAddr;

    public VertexData(Vertex vertex) {
        if(vertex instanceof Room) {
            Room room = (Room) vertex;
            vertexId = room.getId();
            name = room.getName();
            x = room.getX();
            y = room.getY();
            type = "room";
            macAddr = "";
        }
        else if(vertex instanceof Beacon) {
            Beacon beacon = (Beacon) vertex;
            vertexId = beacon.getId();
            name = beacon.getName();
            x = beacon.getX();
            y = beacon.getY();
            type = "beacon";
            macAddr = beacon.getMacAddr();
        }
    }
}
