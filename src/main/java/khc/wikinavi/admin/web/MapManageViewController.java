package khc.wikinavi.admin.web;

import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.Edge;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.service.IndoorMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Controller
@RequestMapping("maps")
public class MapManageViewController {

    private static final Logger logger = LoggerFactory.getLogger(MapManageViewController.class);
    @Autowired private  IndoorMapService indoorMapService;

    // 지도 목록 화면
    // DB로부터 모든 indoorMap 검색
    // maps/list.html
    // GET /maps/list
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<IndoorMap> indoorMaps = indoorMapService.findAll();
        model.addAttribute("indoorMaps", indoorMaps);
        return "maps/list";
    }

    // 지도 조회 화면
    // DB로부터 특정 indoorMap 검색
    // maps/view.html
    // GET /maps/{id}/view
    @RequestMapping(value = "{mapId}/view", method = RequestMethod.GET)
    public String view(@PathVariable Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<Beacon> beacons = indoorMap.getBeacons();
        List<Edge> edges = indoorMap.getEdges();

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        model.addAttribute("edges", edges);
        return "maps/view";
    }

}
