package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Cate;
import live.ipso.entities.Comment;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.service.CateService;
import live.ipso.provider.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CommentController {

    final private CommentService commentService;

    /**
     * 新增信息
     *
     * @param comment 信息
     * @return ResponseModel
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseModel add(@RequestBody Comment comment){

        Comment res = commentService.saveOne(comment);

        if (res == null)
            return ResponseModel.fail("已存在");
        return ResponseModel.success("成功");
    }

    /**
     * 更新信息
     *
     * @param comment 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Comment comment) {

        if (!commentService.updateOne(comment)) {
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
        Boolean isSuccess = commentService.deleteOne(new_id);
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

        IPage<Comment> page = commentService.getList(params);
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
        Comment res = commentService.getById(new_id);
        if (res == null) {
            return ResponseModel.fail("信息不存在");
        }

        return ResponseModel.success("data", res);
    }
}
