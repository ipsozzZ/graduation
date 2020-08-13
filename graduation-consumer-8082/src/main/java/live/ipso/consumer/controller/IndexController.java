package live.ipso.consumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap map){
        map.addAttribute("name", "Graduation Controller");
        return "login";
    }
}
