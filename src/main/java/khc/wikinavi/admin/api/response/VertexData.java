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
    private String type;
    private String macAddr;

    public VertexData(Room room) {
        vertexId = room.getId();
        name = room.getName();
        type = "room";
        macAddr = "";
    }

    public VertexData(Beacon beacon) {
        vertexId = beacon.getId();
        name = beacon.getName();
        type = "beacon";
        macAddr = beacon.getMacAddr();
    }
}
