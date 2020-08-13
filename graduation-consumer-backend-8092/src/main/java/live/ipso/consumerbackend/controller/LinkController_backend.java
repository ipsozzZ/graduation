package live.ipso.consumerbackend.controller;

import live.ipso.consumerbackend.config.ProviderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/link")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LinkController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){

        return "link/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(){

        return "link/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(){

        return "link/update";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(){

        return "link/index";
    }
}
