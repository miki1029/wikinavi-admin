package khc.wikinavi.admin.api.response;

import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.Room;
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

    public VertexData(Room room) {
        vertexId = room.getId();
        name = room.getName();
        x = room.getX();
        y = room.getY();
        type = "room";
        macAddr = "";
    }

    public VertexData(Beacon beacon) {
        vertexId = beacon.getId();
        name = beacon.getName();
        x = beacon.getX();
        y = beacon.getY();
        type = "beacon";
        macAddr = beacon.getMacAddr();
    }
}
