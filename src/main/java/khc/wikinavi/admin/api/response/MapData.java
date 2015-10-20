package khc.wikinavi.admin.api.response;

import khc.wikinavi.admin.domain.IndoorMap;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by miki on 15. 10. 19..
 */
@Data
@NoArgsConstructor
public class MapData {
    private int mapId;
    private String name;
    private String address;

    public MapData(IndoorMap indoorMap) {
        mapId = indoorMap.getId();
        name = indoorMap.getTitle();
        address = indoorMap.getAddress();
    }
}
