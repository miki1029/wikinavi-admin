package khc.wikinavi.admin.api;

import khc.wikinavi.admin.api.response.Response;
import khc.wikinavi.admin.api.response.VertexData;
import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.service.BeaconService;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 15. 10. 19..
 */
@RestController
@RequestMapping(value = "api/maps")
public class ApiController {
    @Autowired
    IndoorMapService indoorMapService;

    @Autowired
    RoomService roomService;

    @Autowired
    BeaconService beaconService;

    // 건물 검색 API
    // GET /{contextRoot}/maps
    @RequestMapping(method = RequestMethod.GET)
    Response<IndoorMap> getMaps(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String address) {
        List<IndoorMap> indoorMaps = indoorMapService.findAll();
/*        List<MapData> mapDatas = new ArrayList<>(indoorMaps.size());

        for (IndoorMap indoorMap : indoorMaps) {
            mapDatas.add(new MapData(indoorMap));
        }*/

        return new Response<>(indoorMaps);
    }

    // Vertex 검색 API
    // GET /{contextRoot}/maps/{mapId}/vertexes
    @RequestMapping(value = "{mapId}/vertexes", method = RequestMethod.GET)
    Response<VertexData> getVertexes(@PathVariable Integer mapId,
                                     @RequestParam(required = false) String name) {
        List<Room> rooms = roomService.findAllByMapId(mapId);
        List<Beacon> beacons = beaconService.findAllByMapId(mapId);
        List<VertexData> vertexDatas = new ArrayList<>(rooms.size() + beacons.size());

        for (Room room : rooms) {
            vertexDatas.add(new VertexData(room));
        }
        for (Beacon beacon : beacons) {
            vertexDatas.add(new VertexData(beacon));
        }

        return new Response<>(vertexDatas);
    }
}
