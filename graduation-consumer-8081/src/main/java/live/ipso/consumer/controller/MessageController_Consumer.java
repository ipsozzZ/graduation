package live.ipso.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import live.ipso.consumer.config.ProviderConfig;
import live.ipso.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/message")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class MessageController_Consumer {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(String phone){

        if (phone == null){
            phone = "18064752779";
        }

        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "message/sendSms", param, String.class);
        return "user/login";
    }
}
