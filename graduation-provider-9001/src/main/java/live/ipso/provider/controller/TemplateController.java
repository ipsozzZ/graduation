package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Template;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TemplateController {

    final private TemplateService templateService;

    /**
     * 新增信息
     *
     * @param template 信息
     * @return ResponseModel
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseModel add(@RequestBody Template template){

        Template res = templateService.saveOne(template);

        if (res == null)
            return ResponseModel.fail("已存在");
        return ResponseModel.success("成功");
    }

    /**
     * 更新信息
     *
     * @param template 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Template template) {

        if (!templateService.updateOne(template)) {
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

        Long new_id = Long.valueOf(id);
        Boolean isSuccess = templateService.deleteOne(new_id);
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
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public ResponseModel getPage(@RequestBody Map<String, Object> params) {

        if (!params.containsKey("currPage") && !params.containsKey("size")){
            return ResponseModel.fail("缺少必要参数");
        }

        IPage<Template> page = templateService.getList(params);
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
        Template res = templateService.getById(new_id);
        if (res == null) {
            return ResponseModel.fail("信息不存在");
        }

        return ResponseModel.success("data", res);
    }
}
