package khc.wikinavi.admin.web;

import khc.wikinavi.admin.api.ApiController;
import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.web.form.BeaconForm;
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
public class BeaconController {

    private static final Logger logger = LoggerFactory.getLogger(BeaconController.class);

    @Autowired private ApiController apiController;
    @Autowired private IndoorMapService indoorMapService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("initBinder(WebDataBinder: " + binder + ")");

        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true, 19));
    }

    @ModelAttribute
    public BeaconForm setUpBeaconForm() {
        return new BeaconForm();
    }

    // 비콘 정보 추가 view
    // GET /maps/1/modify/beacon
    @RequestMapping(value = "beacon", method = RequestMethod.GET)
    public String beacon(@PathVariable("mapId") Integer mapId, Model model) throws IOException {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Beacon> beacons = indoorMap.getBeacons();
        Double ratio = apiController.imageRatio(indoorMap.getImagePath());

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("beacons", beacons);
        model.addAttribute("ratio", ratio);
        return "maps/modify/beacon";
    }

    // 비콘 정보 추가 process
    // POST /maps/1/modify/beacon
    @RequestMapping(value = "beacon", method = RequestMethod.POST)
    public String beacon(@PathVariable("mapId") Integer mapId, @Validated BeaconForm form,
                         BindingResult result, Model model) throws IOException {
        logger.info("create(" + form + ", " + result + ")");

        if (result.hasErrors()) {
            logger.error("result.hasError()");
            return beacon(mapId, model);
        }

        logger.info(form.toString());

        // indoorMap update
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        Beacon beacon = new Beacon(indoorMap, form.getX(), form.getY(), form.getName(), form.getMacAddr());
        indoorMap.setModifiedTime(new Date());

        logger.info(form.toString());
        logger.info(indoorMap.toString());

        indoorMapService.update(indoorMap);

        return "redirect:/maps/" + mapId + "/modify/beacon";
    }

}
