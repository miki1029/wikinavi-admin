package khc.wikinavi.admin.web;

import khc.wikinavi.admin.api.ApiController;
import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.service.IndoorMapService;
import khc.wikinavi.admin.web.form.IndoorMapForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by miki on 15. 11. 8..
 */
@Controller
@RequestMapping("maps")
public class MapCreateController {

    private static final Logger logger = LoggerFactory.getLogger(MapManageViewController.class);

    @Autowired                          private ApiController apiController;
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

    // 지도 이미지 업로드 화면
    // maps/upload.html
    // GET /maps/upload
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String upload() {
        return "maps/upload";
    }

    // 지도 이미지 업로드 + 지도 데이터 입력 화면 redirect
    // POST /maps/upload
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file, Model model) throws IOException { // MultipartFile : 업로드된 파일
        logger.info("originalName: " + file.getOriginalFilename());
        logger.info("size: " + file.getSize());
        logger.info("contentType: " + file.getContentType());

        String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
        model.addAttribute("savedName", savedName);

        logger.info("savedName: " + savedName);

        return "redirect:/maps/create?savedName=" + savedName;
    }

    // 업로드 process
    public String uploadFile(String originalName, byte[] fileData) throws IOException {
        String today = fileDateFormat.format(new Date());
        UUID uuid = UUID.randomUUID();
        String formatName = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

        String savedName = today + "_" + uuid.toString() + "." + formatName;
        File target = new File(uploadPath, savedName);
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }

    // 지도 생성 화면
    // 업로드된 파일 의존
    // maps/create.html
    // GET /maps/create?savedName={savedName}
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model, @RequestParam String savedName) throws IOException {
        Double ratio = apiController.imageRatio(savedName);

        model.addAttribute("savedName", savedName);
        model.addAttribute("ratio", ratio);

        return "maps/create";
    }

    // 지도 생성
    // form 데이터 의존, DB에 indoorMap 삽입
    // POST /maps/create
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Validated IndoorMapForm form, BindingResult result, Model model) throws IOException {
        if (form.getTileHeight() == null) {
            form.setTileHeight((int) (form.getTileWidth() * form.getRatio()));
        }
        logger.info("create(" + form + ", " + result + ")");

        if (result.hasErrors()) {
            logger.error("result.hasError()");
            return create(model, form.getImagePath());
        }

        // createdTime, modifiedTime
        form.setCreatedTime(new Date());
        form.setModifiedTime(form.getCreatedTime());

        logger.info(form.toString());

        IndoorMap indoorMap = new IndoorMap();
        BeanUtils.copyProperties(form, indoorMap);
        indoorMapService.create(indoorMap);
        return "redirect:/maps/list";
    }
}
