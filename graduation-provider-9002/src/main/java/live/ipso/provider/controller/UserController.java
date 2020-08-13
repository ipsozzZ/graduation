package live.ipso.provider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.User;
import live.ipso.provider.common.FormatCheck;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.common.utils.AliyunMessageUtil;
import live.ipso.provider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户接口类
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserController {
    final private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseModel add(@RequestBody User user){

        if (user.getType() == 1){
            user.setSalt("admin");
        }else {
            user.setSalt("user");
        }

        // 验证手机格式
        if (!FormatCheck.isPhoneLegal(user.getUserPhone())){
            return ResponseModel.fail("用户手机格式不合法");
        }

        // 手机短信验证
        /*if (!AliyunMessageUtil.isExpire(user.getUserPhone(), user.getCode())){
            return ResponseModel.fail("用户验证码不正确");
        }*/

        user.setCode(null);

        User res = userService.signIn(user);

        if (res == null)
            return ResponseModel.fail("用户已存在");
        return ResponseModel.success("注册成功");
    }

    /**
     * 用户名/密码登录
     * @param loginInfo 登录用户
     * @return ResponseModel
     */
    @PostMapping(value = "/login")
    public ResponseModel login(@RequestBody Map<String, Object> loginInfo) {

        User user = new User();
        // 判断用户是手机登录还是邮箱登录
        user.setUserName((String) loginInfo.get("username"));
        user.setUserPass((String) loginInfo.get("password"));
        if (user.getUserName() == null || user.getUserPass() == null) {
            return ResponseModel.fail("登陆失败，请输入规范的手机号或密码");
        }

        User loginUser = userService.login(user);

        return ResponseModel.success("data", loginUser);
    }

    /**
     * 手机号/验证码登录
     * @param loginInfo 登录用户
     * @return ResponseModel
     */
    @PostMapping(value = "/loginByPhone")
    public ResponseModel loginByPhone(@RequestBody Map<String, Object> loginInfo) {

        User user = new User();
        // 判断用户是手机登录还是邮箱登录
        user.setUserPhone((String) loginInfo.get("phone"));
        user.setCode((String) loginInfo.get("code"));

        // 手机格式验证
        if (user.getUserPhone() == null || !FormatCheck.isPhoneLegal(user.getUserPhone()) || user.getCode() == null) {
            return ResponseModel.fail("登陆失败，请输入规范的手机号或密码");
        }

        // 验证码验证
        if (!AliyunMessageUtil.isExpire(user.getUserPhone(), user.getCode())){
            return ResponseModel.fail("验证码不正确");
        }

        User loginUser = userService.login(user);

        if (loginUser == null){
            return ResponseModel.fail("登录信息不存在");
        }
        return ResponseModel.success("data", loginUser);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return ResponseModel
     */
    @PostMapping("/update")
    public ResponseModel update(@RequestBody User user) {
        User resUser = userService.updateOne(user);

        if (resUser == null) {
            return ResponseModel.fail("更新失败");
        }
        return ResponseModel.success("更新成功");
    }

    /**
     * 根据用户id删除一条用户信息（逻辑删除）
     *
     * @param id 用户id
     * @return ResponseModel
     */
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public ResponseModel deleteById(@PathVariable("id") String id) {

        Long new_id = Long.valueOf(id);
        Boolean isSuccess = userService.deleteById(new_id);
        if (!isSuccess)
            return ResponseModel.fail("删除用户失败");
        return ResponseModel.success("删除用户成功");
    }

    /**
     * 分页获取所有用户信息(包含管理员)
     *
     * @param params Map
     * @return ResponseModel
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public ResponseModel getPage(@RequestBody Map<String, Object> params) {

        if (!params.containsKey("currPage") && !params.containsKey("size")){
            return ResponseModel.fail("缺少必要参数");
        }

        IPage<User> page = userService.getList(params);
        return ResponseModel.success("page", page);
    }

    /**
     * 通过id获取一条用户信息
     *
     * @param id 用户id
     * @return ResponseModel
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseModel getById(@PathVariable("id") String id) {
        Long new_id = Long.valueOf(id);
        User user = userService.getUserById(new_id);
        if (user == null) {
            return ResponseModel.fail("用户信息不存在");
        }
        user.setType(null);
        return ResponseModel.success("user", user);
    }

    /**
     * 通过id获取一条用户信息
     *
     * @param userName 用户名
     * @return ResponseModel
     */
    @RequestMapping(value = "/getByName/{userName}", method = RequestMethod.GET)
    public ResponseModel getByName(@PathVariable("userName") String userName) {
        User user = userService.getUserByName(userName);
        if (user == null) {
            return ResponseModel.fail("用户信息不存在");
        }
        user.setType(null);
        return ResponseModel.success("userID", user.getUserId());
    }

}
