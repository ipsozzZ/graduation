package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Article;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ArticleController {


    final private ArticleService articleService;

    /**
     * 新增信息
     *
     * @param article 信息
     * @return ResponseModel
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseModel add(@RequestBody Article article){

        Article res = articleService.saveOne(article);

        if (res == null)
            return ResponseModel.fail("已存在");
        return ResponseModel.success("成功");
    }

    /**
     * 更新信息
     *
     * @param article 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Article article) {

        if (!articleService.updateOne(article)) {
            return ResponseModel.fail("更新失败");
        }
        return ResponseModel.success("更新成功");
    }

    /**
     * 根据id删除一条信息（逻辑删除）
     *
     * @param id id
     * @return ResponseModel
     */
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public ResponseModel deleteById(@PathVariable("id") String id) {

        System.out.println(id);
        Long new_id = Long.valueOf(id);
        Boolean isSuccess = articleService.deleteOne(new_id);
        if (!isSuccess)
            return ResponseModel.fail("删除失败");
        return ResponseModel.success("删除成功");
    }

    /**
     * 分页获取所有信息
     *
     * @param params Map
     * @return ResponseModel
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    public ResponseModel getPage(@RequestBody Map<String, Object> params) {

        if (!params.containsKey("currPage") && !params.containsKey("size")){
            return ResponseModel.fail("缺少必要参数");
        }

        IPage<Article> page = articleService.getList(params);

        return ResponseModel.success("page", page);
    }

    /**
     * 通过id获取一条信息
     *
     * @param id id
     * @return ResponseModel
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseModel getById(@PathVariable("id") String id) {
        Long new_id = Long.valueOf(id);
        Article article = articleService.getDataById(new_id);
        if (article == null) {
            return ResponseModel.fail("信息不存在");
        }

        return ResponseModel.success("data", article);
    }

    /**
     * 通过id获取一条信息
     *
     * @param id id
     * @return ResponseModel
     */
    @RequestMapping(value = "/like/{id}", method = RequestMethod.GET)
    public ResponseModel addLike(@PathVariable("id") String id) {
        Long new_id = Long.valueOf(id);
        articleService.addLike(new_id);
        return ResponseModel.success("data", "ok");
    }
}
