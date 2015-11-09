package khc.wikinavi.admin.web;

import khc.wikinavi.admin.api.ApiController;
import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.Edge;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.web.form.IndoorMapForm;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by miki on 15. 11. 8..
 */
@Controller
@RequestMapping("maps/{mapId}/modify")
public class MapModifyController {

    private static final Logger logger = LoggerFactory.getLogger(MapManageViewController.class);

    @Autowired                          private ApiController apiController;
    @Autowired                          private MapCreateController mapCreateController;
    @Autowired                          private IndoorMapService indoorMapService;
    @Resource(name = "uploadPath")      private String uploadPath;
    @Resource(name = "fileDateFormat")  private DateFormat fileDateFormat;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("initBinder(WebDataBinder: " + binder + ")");

        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true, 19));
    }

    @ModelAttribute
    public IndoorMapForm setUpForm() {
        logger.info("setupForm()");
        return new IndoorMapForm();
    }

    // 기본 정보 수정 view
    // maps/modify/modify.html
    // GET /maps/1/modify
    @RequestMapping(method = RequestMethod.GET)
    public String modify(@PathVariable("mapId") Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        Double ratio = (double) indoorMap.getTileHeight() / indoorMap.getTileWidth();

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("ratio", ratio);

        return "maps/modify/modify";
    }

    // 기본 정보 수정 process
    // POST /maps/1/modify
    @RequestMapping(method = RequestMethod.POST)
    public String modify(@PathVariable Integer mapId, @Validated IndoorMapForm form,
                         BindingResult result, Model model) {
        if (form.getTileHeight() == null) {
            form.setTileHeight((int) (form.getTileWidth() * form.getRatio()));
        }
        logger.info("modify(" + form + ", " + result + ")");

        if (result.hasErrors()) {
            logger.error("result.hasError()");
            return modify(mapId, model);
        }

        // indoorMap update
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        indoorMap.setTitle(form.getTitle());
        indoorMap.setAddress(form.getAddress());
        indoorMap.setTileWidth(form.getTileWidth());
        indoorMap.setTileHeight(form.getTileHeight());
        indoorMap.setModifiedTime(new Date());

        logger.info(form.toString());
        logger.info(indoorMap.toString());

        indoorMapService.update(indoorMap);

        return "redirect:/maps/" + mapId + "/view";
    }

    // 지도 이미지 변경 view
    // maps/modify/image.html
    // GET /maps/1/modify/image
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public String image(@PathVariable("mapId") Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);

        model.addAttribute("indoorMap", indoorMap);
        return "maps/modify/image";
    }

    // 지도 이미지 변경 process
    // POST /maps/1/modify/image
    @RequestMapping(value = "image", method = RequestMethod.POST)
    public String image(@PathVariable("mapId") Integer mapId, @RequestParam MultipartFile file, Model model) throws IOException {
        logger.info("originalName: " + file.getOriginalFilename());
        logger.info("size: " + file.getSize());
        logger.info("contentType: " + file.getContentType());

        // upload
        String savedName = mapCreateController.uploadFile(file.getOriginalFilename(), file.getBytes());
        model.addAttribute("savedName", savedName);
        logger.info("savedName: " + savedName);

        // save
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        indoorMap.setImagePath(savedName);
        indoorMapService.update(indoorMap);

        return "redirect:/maps/" + mapId + "/view";
    }

    @RequestMapping(value = "room", method = RequestMethod.GET)
    public String room(@PathVariable("mapId") Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<Beacon> beacons = indoorMap.getBeacons();
        List<Edge> edges = indoorMap.getEdges();

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        model.addAttribute("edges", edges);
        return "maps/modify/room";
    }
    @RequestMapping(value = "beacon", method = RequestMethod.GET)
    public String beacon(@PathVariable("mapId") Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<Beacon> beacons = indoorMap.getBeacons();
        List<Edge> edges = indoorMap.getEdges();

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        model.addAttribute("edges", edges);
        return "maps/modify/beacon";
    }
    @RequestMapping(value = "edge", method = RequestMethod.GET)
    public String edge(@PathVariable("mapId") Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = indoorMap.getRooms();
        List<Beacon> beacons = indoorMap.getBeacons();
        List<Edge> edges = indoorMap.getEdges();

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        model.addAttribute("edges", edges);
        return "maps/modify/edge";
    }
}
