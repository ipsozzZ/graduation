package live.ipso.consumerbackend.controller;

import com.alibaba.fastjson.JSONObject;
import live.ipso.consumerbackend.config.ProviderConfig;
import live.ipso.entities.Article;
import live.ipso.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){

        return "user/index";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(String id, Model model){

        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "user/getById/"+id, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/add"; // 数据获取失败

        User user;
        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
        // 封装响应中的实体数据
        user = jsonObject1.getObject("user", User.class);
        model.addAttribute("user", user);
        return "user/update";

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute User user, Model model, HttpSession session){

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "user/update", user, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/add"; // 数据获取失败

        model.addAttribute("article", user);
        session.setAttribute("userName", user.getUserName());
        return "user/update";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(){

        return "user/add";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(){

        return "user/index";
    }
}
