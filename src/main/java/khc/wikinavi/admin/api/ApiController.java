package khc.wikinavi.admin.api;

import khc.wikinavi.admin.api.response.MapData;
import khc.wikinavi.admin.api.response.Response;
import khc.wikinavi.admin.api.response.VertexData;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Vertex;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.VertexService;
import khc.wikinavi.admin.util.ShortestPathUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired                      private IndoorMapService indoorMapService;
    @Autowired                      private VertexService vertexService;
    @Resource(name = "uploadPath")  private String uploadPath;

    // 건물 검색 API
    // GET /api/maps?title={title}&address={address}
    @RequestMapping(value = "maps", method = RequestMethod.GET)
    public Response<MapData> getMaps(@RequestParam(required = false, defaultValue = "") String title,
                              @RequestParam(required = false, defaultValue = "") String address) {
        logger.info("건물 검색 API : title=" + title + "&address=" + address);
        List<IndoorMap> indoorMaps = indoorMapService.findLikeTitleAndAddress(title, address);
        List<MapData> mapDatas = new ArrayList<>(indoorMaps.size());

        mapDatas.addAll(indoorMaps.stream().map(MapData::new).collect(Collectors.toList()));

        return new Response<>(mapDatas);
    }

    // Vertex 검색 API
    // GET /api/maps/{mapId}/vertexes?type=[room,beacon]&name={name}
    @RequestMapping(value = "maps/{mapId}/vertexes", method = RequestMethod.GET)
    public Response<VertexData> getVertexes(@PathVariable Integer mapId,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String name) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<VertexData> vertexDatas = new ArrayList<>();
        List<? extends Vertex> vertexes = null;

        if (type == null) {
            vertexes = indoorMap.getVertexes();
        } else if (type.equals("room")) {
            vertexes = indoorMap.getRooms();
        } else if (type.equals("beacon")) {
            vertexes = indoorMap.getBeacons();
        } else {
            vertexes = indoorMap.getVertexes();
        }

        for (Vertex vertex : vertexes) {
            if (name == null || vertex.getName().contains(name)) {
                vertexDatas.add(new VertexData(vertex));
            }
        }

        return new Response<>(vertexDatas);
    }

    // 최단 경로 검색 API - (x, y) ~ Vertex
    // GET /api/maps/{mapId}/vertexes/end/{vertexId}/start?x={x}&y={y}
    @RequestMapping(value = "maps/{mapId}/vertexes/end/{vertexId}/start", method = RequestMethod.GET)
    public Response<List<VertexData>> getRouteVertexes(@PathVariable Integer mapId,
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

    // 최단 경로 검색 API - Vertex ~ Vertex
    // GET /api/maps/{mapId}/vertexes/end/{endVertexId}/start/{startVertexId}
    @RequestMapping(value = "maps/{mapId}/vertexes/end/{endVertexId}/start/{startVertexId}", method = RequestMethod.GET)
    public Response<VertexData> getRouteVertexes(@PathVariable Integer mapId,
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

    // 이미지 반환 API - mapId
    // GET /api/maps/{mapId}/image
    @ResponseBody
    @RequestMapping(value = "maps/{mapId}/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> image(@PathVariable Integer mapId) throws IOException {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        String fileName = indoorMap.getImagePath();
        return image(fileName);
    }

    // 이미지 반환 API - fileName
    // GET /api/images?fileName={fileName}
    @ResponseBody
    @RequestMapping(value = "images", method = RequestMethod.GET)
    public ResponseEntity<byte[]> image(@RequestParam String fileName) throws IOException {

        ResponseEntity<byte[]> entity = null;

        logger.info("fileName: " + fileName);

        try (InputStream inputStream = new FileInputStream(uploadPath + fileName)) {
            // 확장자 명
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
            MediaType mediaType = null;
            if (formatName.equals("JPG") || formatName.equals("JPEG")) mediaType = MediaType.IMAGE_JPEG;
            else if (formatName.equals("GIF")) mediaType = MediaType.IMAGE_GIF;
            else if (formatName.equals("PNG")) mediaType = MediaType.IMAGE_PNG;

            HttpHeaders headers = new HttpHeaders();

            if (mediaType != null) {
                headers.setContentType(mediaType);
            } else {
                throw new UnsupportedOperationException();
            }

            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 이미지 비율
    // GET /api/images/ratio?fileName={fileName}
    @RequestMapping(value = "images/ratio", method = RequestMethod.GET)
    public Double imageRatio(@RequestParam String fileName) throws IOException {
        logger.info("fileName: " + fileName);

        BufferedImage image = ImageIO.read(new File(uploadPath + fileName));
        return (double)image.getHeight() / image.getWidth();
    }

}
