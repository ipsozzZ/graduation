package live.ipso.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import live.ipso.consumer.config.ProviderConfig;
import live.ipso.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserController_Consumer {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    /**
     * 根据用户id获取信息
     * @param  model model
     * @return 结果渲染页
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Long id, Model model){

        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "user/getById/" + id, String.class);
        User user;
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");    // 获取响应数据
        if (!jsonObject.get("code").equals(200)) return "/index/index"; // 数据获取失败

        // 封装响应中的实体数据
        user = jsonObject1.getObject("user", User.class);

        // 向页面传递数据
        model.addAttribute("user", user);
        return "/index/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String type){
        if(type != null) return "user/backend";
        return "user/login";
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(String name, String pass, Model model){

        Map<String, Object> param = new HashMap<>();
        param.put("username", name);
        param.put("password", pass);
        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "user/login", param, String.class) ;
         JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "user/login"; // 请求失败，数据获取失败

        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");    // 获取响应数据

        User user = jsonObject2.toJavaObject(User.class);
        if (user == null) return "user/login"; // ,请求成功，数据获取失败
        model.addAttribute("user", user);
        return "user/info";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute User user){
        user.setType(0);
        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "user/add", user, String.class) ;
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/index/login#toregister"; // 注册失败
        return "user/login";
    }
}
