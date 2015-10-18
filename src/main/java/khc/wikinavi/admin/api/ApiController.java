package khc.wikinavi.admin.api;

import khc.wikinavi.admin.api.response.MapData;
import khc.wikinavi.admin.api.response.Response;
import khc.wikinavi.admin.api.response.VertexData;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.service.BeaconService;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 15. 10. 19..
 */
@RestController
@RequestMapping("map")
public class ApiController {
    @Autowired
    IndoorMapService indoorMapService;

    @Autowired
    RoomService roomService;

    @Autowired
    BeaconService beaconService;

    // 건물 검색 API
    // GET /{contextRoot}/map
    @RequestMapping(method = RequestMethod.GET)
    Response<MapData> searchMaps(@RequestParam String name, @RequestParam String address) {
        List<IndoorMap> indoorMaps = indoorMapService.findAll();
        List<MapData> mapDatas = new ArrayList<>(indoorMaps.size());

        for (IndoorMap indoorMap : indoorMaps) {
            mapDatas.add(new MapData(indoorMap));
        }

        return new Response<>(mapDatas);
    }

    // Vertex 검색 API
    // GET /{contextRoot}/map/{mapId}/search
    @RequestMapping(value = "{mapId}/search", method = RequestMethod.GET)
    Response<VertexData> searchVertexes(@RequestParam String name) {
        List<Room> vertexes = roomService.findAll();
        return null;
    }
}
