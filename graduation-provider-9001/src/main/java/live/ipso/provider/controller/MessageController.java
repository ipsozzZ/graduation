package live.ipso.provider.controller;


import live.ipso.entities.User;
import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.common.utils.AliyunMessageUtil;
import live.ipso.provider.common.utils.Base64ConvertUtil;
import live.ipso.provider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 短信服务操作接口类
 *
 * @author ipso
 * 2020/01/04
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class MessageController {

    final private UserService userService;

    /**
     * 发送短信验证信息
     *
     * @param map {'phone':'base64'}
     * @return ResponseModel
     * @throws UnsupportedEncodingException e
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public ResponseModel sendSms(@RequestBody Map<String, Object> map) {
        String phone = (String) map.get("phone");

        String sendSmsResponse = AliyunMessageUtil.sendSms(phone, 4);
        if (sendSmsResponse != null) {
            return ResponseModel.success("isSend", sendSmsResponse).addMsg("发送成功");
        } else {
            return ResponseModel.fail("isError", false).addMsg("验证码发送失败，请稍后重试");
        }
    }

    /**
     * 短信验证服务
     *
     * @param map {'phone':String, 'code':String}
     * @return ResponseModel
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseModel verify(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String code = map.get("code");
        // 修改base64为phone
        phone = Base64ConvertUtil.decodeBase64(phone);

        boolean res = AliyunMessageUtil.isExpire(phone, code);

        // 验证验证码是否与缓存一致
        if (!res) {
            return ResponseModel.fail("验证码不正确或不存在");
        }
        return ResponseModel.success("验证码正确");
    }
}
