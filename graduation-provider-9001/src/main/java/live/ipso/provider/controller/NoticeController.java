package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Dict;
import live.ipso.entities.Notice;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.service.DictService;
import live.ipso.provider.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class NoticeController {

    final private NoticeService noticeService;

    /**
     * 新增信息
     *
     * @param notice 信息
     * @return ResponseModel
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseModel add(@RequestBody Notice notice){

        Notice res = noticeService.saveOne(notice);

        if (res == null)
            return ResponseModel.fail("已存在");
        return ResponseModel.success("成功");
    }

    /**
     * 更新信息
     *
     * @param notice 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Notice notice) {

        if (!noticeService.updateOne(notice)) {
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
        Boolean isSuccess = noticeService.deleteOne(new_id);
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

        IPage<Notice> page = noticeService.getList(params);
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
        Notice res = noticeService.getById(new_id);
        if (res == null) {
            return ResponseModel.fail("信息不存在");
        }

        return ResponseModel.success("data", res);
    }
}
