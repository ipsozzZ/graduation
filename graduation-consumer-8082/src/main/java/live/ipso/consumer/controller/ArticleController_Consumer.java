package live.ipso.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.consumer.config.ProviderConfig;
import live.ipso.entities.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ArticleController_Consumer {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    /**
     * 根据id获取详情
     * @param  model model
     * @return 结果渲染页
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(String id, Model model){

        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "article/getById/" + id, String.class);
        Article article;
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");    // 获取响应数据
        if (!jsonObject.get("code").equals(200)) return "/index/show"; // 数据获取失败

        // 封装响应中的实体数据
        article = jsonObject1.getObject("data", Article.class);

        // 向页面传递数据
        model.addAttribute("article", article);
        return "/article/show";
    }

    /**
     * 添加文章
     * @param  model model
     * @return 结果渲染页
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Article article, Model model){

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "article/add", article,  String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/index/add"; // 数据获取失败

        // 向页面传递数据
        model.addAttribute("data", jsonObject.get("msg"));
        return "/article/index";
    }

    /**
     * 更新信息
     *
     * @param article 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public String update(@ModelAttribute Article article, Model model) {
        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "user/update", article,  String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/index/update"; // 数据获取失败

        // 向页面传递数据
        model.addAttribute("data", jsonObject.get("msg"));
        return "/article/index";
    }

    /**
     * 根据id删除一条信息（逻辑删除）
     *
     * @param id id
     * @return ResponseModel
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(String id) {

        // 向生产者请求数据
        String res = restTemplate.getForObject(REST_URL_PREFIX + "article/update/" + id,  String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/index/index"; // 数据获取失败

        return "/article/index";
    }

    /**
     * 分页获取所有信息
     *
     * @param currPage currPage
     * @param size size
     * @return ResponseModel
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public String getPage(String currPage, String size, Model model) {

        if (currPage == null) currPage = "1";
        if (size == null) size = "5";

        Map<String, Object> params = new HashMap<>();
        params.put("currPage", currPage);
        params.put("size", size);

        // 向生产者请求数据
        String res = restTemplate.postForObject(REST_URL_PREFIX + "article/getPage", params, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (!jsonObject.get("code").equals(200)) return "/article/index"; // 数据获取失败

        JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
        JSONObject jsonObject2 = jsonObject1.getJSONObject("page");    // 获取响应数据

        IPage page = jsonObject2.toJavaObject(IPage.class);
        List result = page.getRecords();
        System.out.println(result.toString());
        model.addAttribute("list", result);
        model.addAttribute("page", page);
        System.out.println(page.toString());
        return "/article/index";
    }

}
