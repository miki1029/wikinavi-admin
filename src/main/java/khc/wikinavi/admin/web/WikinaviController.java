package khc.wikinavi.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WikinaviController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/maps/list";
    }

}
