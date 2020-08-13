package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Cate;
import live.ipso.entities.Contact;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.service.CateService;
import live.ipso.provider.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ContactController {

    final private ContactService contactService;

    /**
     * 新增信息
     *
     * @param contact 信息
     * @return ResponseModel
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseModel add(@RequestBody Contact contact){

        Contact res = contactService.saveOne(contact);

        if (res == null)
            return ResponseModel.fail("已存在");
        return ResponseModel.success("成功");
    }

    /**
     * 更新信息
     *
     * @param contact 信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Contact contact) {

        if (!contactService.updateOne(contact)) {
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
        Boolean isSuccess = contactService.deleteOne(new_id);
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

        IPage<Contact> page = contactService.getList(params);
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
        Contact res = contactService.getById(new_id);
        if (res == null) {
            return ResponseModel.fail("信息不存在");
        }

        return ResponseModel.success("data", res);
    }
}
