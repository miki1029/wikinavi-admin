package khc.wikinavi.admin.api;

import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.service.IndoorMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
// indoor_map 테이블 REST 컨트롤러
@RestController
@RequestMapping("api/indoor_maps")
public class IndoorMapRestController {
    @Autowired
    IndoorMapService indoorMapService;

    // GET /api/indoor_maps
    // SELECT all
    @RequestMapping(method = RequestMethod.GET)
    List<IndoorMap> getIndoorMaps() {
        return indoorMapService.findAll();
    }

    // GET /api/indoor_maps/{id}
    // SELECT {id}
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    IndoorMap getIndoorMap(@PathVariable Integer id) {
        return indoorMapService.findOne(id);
    }

    // POST /api/indoor_maps
    // INSERT
    @RequestMapping(method = RequestMethod.POST)
    IndoorMap postIndoorMaps(@RequestBody IndoorMap indoorMap) {
        return indoorMapService.create(indoorMap);
    }

    // PUT /api/indoor_maps/{id}
    // UPDATE {id}
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    IndoorMap putIndoorMap(@PathVariable Integer id, @RequestBody IndoorMap indoorMap) {
        indoorMap.setId(id);
        return indoorMapService.update(indoorMap);
    }

    // DELETE /api/indoor_maps/{id}
    // DELETE {id}
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteIndoorMap(@PathVariable Integer id) {
        indoorMapService.delete(id);
    }
}
