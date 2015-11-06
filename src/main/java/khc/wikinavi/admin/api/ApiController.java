package khc.wikinavi.admin.api;

import khc.wikinavi.admin.api.response.MapData;
import khc.wikinavi.admin.api.response.Response;
import khc.wikinavi.admin.api.response.VertexData;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.domain.Vertex;
import khc.wikinavi.admin.service.BeaconService;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.RoomService;
import khc.wikinavi.admin.service.VertexService;
import khc.wikinavi.admin.util.ShortestPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by miki on 15. 10. 19..
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired IndoorMapService indoorMapService;
    @Autowired VertexService vertexService;
    @Autowired RoomService roomService;
    @Autowired BeaconService beaconService;

    // 건물 검색 API
    // GET /{contextRoot}/maps?title={title}&address={address}
    @RequestMapping(value = "maps", method = RequestMethod.GET)
    Response<MapData> getMaps(@RequestParam(required = false, defaultValue = "") String title,
                              @RequestParam(required = false, defaultValue = "") String address) {
        logger.info("건물 검색 API : title=" + title + "&address=" + address);
        List<IndoorMap> indoorMaps = indoorMapService.findLikeTitleAndAddress(title, address);
        List<MapData> mapDatas = new ArrayList<>(indoorMaps.size());

        mapDatas.addAll(indoorMaps.stream().map(MapData::new).collect(Collectors.toList()));

        return new Response<>(mapDatas);
    }

    // Vertex 검색 API
    // GET /{contextRoot}/maps/{mapId}/vertexes
    @RequestMapping(value = "maps/{mapId}/vertexes", method = RequestMethod.GET)
    Response<VertexData> getVertexes(@PathVariable Integer mapId,
                                     @RequestParam(required = false) String name) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<VertexData> vertexDatas = new ArrayList<>(indoorMap.getVertexes().size());

        vertexDatas.addAll(indoorMap.getVertexes().stream().map(VertexData::new).collect(Collectors.toList()));

        return new Response<>(vertexDatas);
    }

    // Room 검색 API
    // GET /{contextRoot}/maps/{mapId}/rooms?name={name}
    @RequestMapping(value = "maps/{mapId}/rooms", method = RequestMethod.GET)
    Response<VertexData> getRooms(@PathVariable Integer mapId, @RequestParam(required = false) String name) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
//        List<Room> rooms = roomService.findByMapId(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<VertexData> vertexDatas = new ArrayList<>(rooms.size());

        vertexDatas.addAll(rooms.stream().map(VertexData::new).collect(Collectors.toList()));

        return new Response<>(vertexDatas);
    }

    // (x, y)에서 vertex까지 탐색하여 vertex list 반환
    // GET /{contextRoot}/maps/{mapId}/vertexes/end/{vertexId}/start?x={x}&y={y}
    @RequestMapping(value = "maps/{mapId}/vertexes/end/{vertexId}/start", method = RequestMethod.GET)
    Response<List<VertexData>> getRouteVertexes(@PathVariable Integer mapId,
                                                @PathVariable Integer vertexId,
                                                @RequestParam Integer x, @RequestParam Integer y) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        Vertex end = vertexService.findOne(vertexId);
        List<Vertex> nearbyVertexes = indoorMap.findNearbyVertexes(x, y);

        List<List<VertexData>> resultList = new ArrayList<>(nearbyVertexes.size());
        for (Vertex start : nearbyVertexes) {
            List<Vertex> path = ShortestPathUtil.shortestPath(indoorMap, start, end);
            List<VertexData> pathData = new ArrayList<>(path.size());

            pathData.addAll(path.stream().map(VertexData::new).collect(Collectors.toList()));

            resultList.add(pathData);
        }

        return new Response<>(resultList);
    }

    // GET /{contextRoot}/maps/{mapId}/vertexes/end{endVertexId}/start/{startVertexId}
    @RequestMapping(value = "maps/{mapId}/vertexes/end/{endVertexId}/start/{startVertexId}", method = RequestMethod.GET)
    Response<VertexData> getRouteVertexes(@PathVariable Integer mapId,
                                          @PathVariable Integer startVertexId,
                                          @PathVariable Integer endVertexId) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        Vertex start = vertexService.findOne(startVertexId);
        Vertex end = vertexService.findOne(endVertexId);

        List<Vertex> path = ShortestPathUtil.shortestPath(indoorMap, start, end);
        List<VertexData> vertexDatas = new ArrayList<>(path.size());

        vertexDatas.addAll(path.stream().map(VertexData::new).collect(Collectors.toList()));

        return new Response<>(vertexDatas);
    }
}
