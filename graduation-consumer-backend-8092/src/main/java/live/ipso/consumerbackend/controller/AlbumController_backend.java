package live.ipso.consumerbackend.controller;

import live.ipso.consumerbackend.config.ProviderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/album")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AlbumController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Long uid, String currpage, String size){

        return "album/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(){

        return "album/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(){

        return "album/update";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(){

        return "album/index";
    }
}
