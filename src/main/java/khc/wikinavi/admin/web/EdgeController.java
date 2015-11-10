package khc.wikinavi.admin.web;

import khc.wikinavi.admin.api.ApiController;
import khc.wikinavi.admin.domain.*;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.VertexService;
import khc.wikinavi.admin.web.form.EdgeForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by miki on 15. 11. 8..
 */
@Controller
@RequestMapping("maps/{mapId}/modify")
public class EdgeController {

    private static final Logger logger = LoggerFactory.getLogger(EdgeController.class);

    @Autowired private ApiController apiController;
    @Autowired private IndoorMapService indoorMapService;
    @Autowired private VertexService vertexService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("initBinder(WebDataBinder: " + binder + ")");

        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true, 19));
    }

    @ModelAttribute
    public EdgeForm setUpEdgeForm() {
        return new EdgeForm();
    }

    // 길 정보 추가 view
    // GET /maps/1/modify/edge
    @RequestMapping(value = "edge", method = RequestMethod.GET)
    public String edge(@PathVariable("mapId") Integer mapId, Model model) throws IOException {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<Beacon> beacons = indoorMap.getBeacons();
        List<Edge> edges = indoorMap.getEdges();

        Double ratio = apiController.imageRatio(indoorMap.getImagePath());

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        model.addAttribute("edges", edges);
        model.addAttribute("ratio", ratio);

        return "maps/modify/edge";
    }

    // 길 정보 추가 process
    // POST /maps/1/modify/edge
    @RequestMapping(value = "edge", method = RequestMethod.POST)
    public String edge(@PathVariable("mapId") Integer mapId, @Validated EdgeForm form,
                       BindingResult result, Model model) throws IOException {
        logger.info("create(" + form + ", " + result + ")");

        if (result.hasErrors()) {
            logger.error("result.hasError()");
            return edge(mapId, model);
        }

        logger.info(form.toString());

        // indoorMap update
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        Vertex vertex1 = vertexService.findOne(form.getVertexId1());
        Vertex vertex2 = vertexService.findOne(form.getVertexId2());
        Edge edge = new Edge(vertex1, vertex2);

        indoorMap.setModifiedTime(new Date());

        logger.info(form.toString());
        logger.info(indoorMap.toString());

        indoorMapService.update(indoorMap);

        return "redirect:/maps/" + mapId + "/modify/edge";
    }

}
