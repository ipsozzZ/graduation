package live.ipso.consumerbackend.controller;

import com.alibaba.fastjson.JSONObject;
import live.ipso.consumerbackend.config.ProviderConfig;
import live.ipso.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class IndexController {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String name, String pass, HttpSession session){
        if (name == null && pass == null){
            return "index/index";
        }

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

        session.setAttribute("userName", name);
        session.setAttribute("userId", user.getUserId());
        return "index/index";
    }
}
