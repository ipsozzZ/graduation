package live.ipso.consumerbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.consumerbackend.config.ProviderConfig;
import live.ipso.entities.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ArticleController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String userId, String currPage, String size, Model model){

        if (currPage == null) currPage = "1";
        if (size == null) size = "5";

        Map<String, Object> params = new HashMap<>();
        params.put("currPage", currPage);
        params.put("size", size);
        params.put("userId", userId);

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "article/getPage", params, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/index"; // 数据获取失败

        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
        JSONObject jsonObject2 = jsonObject1.getJSONObject("page");    // 获取响应数据

        IPage page = jsonObject2.toJavaObject(IPage.class);
        List result = page.getRecords();
        model.addAttribute("list", result);
        model.addAttribute("page", page);

        return "article/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Article article){

        if (article.getArticleName() == null){
            return "/article/add";
        }

        article.setCateId(1L);

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "article/add", article, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/add"; // 数据获取失败
        return "article/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(String id, Model model){


        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "article/getById/"+id, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "article/add"; // 数据获取失败

        Article article;
        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
        // 封装响应中的实体数据
        article = jsonObject1.getObject("data", Article.class);
        model.addAttribute("article", article);
        return "article/update";
    }

    @RequestMapping(value = "/toUpdate", method = RequestMethod.POST)
    public String toUpdate(@ModelAttribute Article article, Model model){

        if (article.getArticleName() == null){
            return "/article/add";
        }

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "article/update", article, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/add"; // 数据获取失败

        model.addAttribute("article", article);
        return "article/update";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(String id){

        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "article/deleteById/"+id, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/add"; // 数据获取失败

        return "article/add";
    }

    @RequestMapping(value = "/addLike", method = RequestMethod.GET)
    public String addLike(String id){

        // 向生产者请求数据
        restTemplate.getForObject(REST_URL_PREFIX + "article/like/id="+id, String.class);
        return "article/index";
    }
}
