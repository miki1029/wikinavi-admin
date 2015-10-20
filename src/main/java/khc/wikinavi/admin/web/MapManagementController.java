package khc.wikinavi.admin.web;

import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.service.BeaconService;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.service.RoomService;
import khc.wikinavi.admin.web.form.IndoorMapForm;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by miki on 15. 10. 13..
 */
@Controller
@RequestMapping("maps")
public class MapManagementController {

    static final Logger logger = LoggerFactory.getLogger(MapManagementController.class);

    @Autowired IndoorMapService indoorMapService;
    @Autowired RoomService roomService;
    @Autowired BeaconService beaconService;
    @Resource(name = "uploadPath") String uploadPath;

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true, 19));
    }

    @ModelAttribute
    IndoorMapForm setUpForm() {
        return new IndoorMapForm();
    }

    // 지도 목록 보여주기 maps/list.html
    // GET /maps/list
    @RequestMapping(value = "list", method = RequestMethod.GET)
    String list(Model model) {
        List<IndoorMap> indoorMaps = indoorMapService.findAll();
        model.addAttribute("indoorMaps", indoorMaps);
        return "maps/list";
    }

    // 지도 생성 1 - 지도 업로드 maps/upload.html
    // GET /maps/upload
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    String upload() {
        return "maps/upload";
    }

    // 지도 생성 2 - 데이터 입력 maps/create.html
    // POST /maps/upload
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    String upload(@RequestParam MultipartFile file, Model model) throws IOException { // MultipartFile : 업로드된 파일
        logger.info("originalName: " + file.getOriginalFilename());
        logger.info("size: " + file.getSize());
        logger.info("contentType: " + file.getContentType());

        String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
        model.addAttribute("savedName", savedName);

        return "maps/create";
    }

    String uploadFile(String originalName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String savedName = uuid.toString() + "_" + originalName;
        File target = new File(uploadPath, savedName);
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }

    // 지도 이미지 반환
    // GET /maps/image?fileName={파일명}
    @ResponseBody
    @RequestMapping(value = "image", method = RequestMethod.GET)
    ResponseEntity<byte[]> image(@RequestParam String fileName) throws IOException {
        InputStream inputStream = null;
        ResponseEntity<byte[]> entity = null;

        logger.info("FILE NAME : " + fileName);

        try {
            // 확장자 명
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
            MediaType mediaType = null;
            if(formatName.equals("JPG") || formatName.equals("JPEG")) mediaType = MediaType.IMAGE_JPEG;
            else if(formatName.equals("GIF")) mediaType = MediaType.IMAGE_GIF;
            else if(formatName.equals("PNG")) mediaType = MediaType.IMAGE_PNG;

            HttpHeaders headers = new HttpHeaders();

            inputStream = new FileInputStream(uploadPath + fileName);

            if(mediaType != null) {
                headers.setContentType(mediaType);
            }
            else {
                throw new UnsupportedOperationException();
            }

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            inputStream.close();
        }
        return entity;
    }

    // 지도 생성 INSERT
    // POST /maps/create
    @RequestMapping(value = "create", method = RequestMethod.POST)
    String create(@Validated IndoorMapForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return list(model);
        }

        // createdTime, modifiedTime
        form.setCreatedTime(new Date());
        form.setModifiedTime(form.getCreatedTime());

        System.out.println(form);

        IndoorMap indoorMap = new IndoorMap();
        BeanUtils.copyProperties(form, indoorMap);
        indoorMapService.create(indoorMap);
        return "redirect:/maps/list";
    }

    // 지도 조회 maps/view.html
    // GET /maps/view/{id}
    @RequestMapping(value = "{mapId}/view", method = RequestMethod.GET)
    String view(@PathVariable Integer mapId, Model model) {
        IndoorMap indoorMap = indoorMapService.findOne(mapId);
        List<Room> rooms = roomService.findAllByMapId(mapId);
        List<Beacon> beacons = beaconService.findAllByMapId(mapId);

        model.addAttribute("indoorMap", indoorMap);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beacons", beacons);
        return "maps/view";
    }
}
