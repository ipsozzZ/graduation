package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import live.ipso.entities.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 通过id获取用户信息
     *
     * @param id 用户id
     * @return UserEnum
     */
    User getUserById(Long id);

    /**
     * 通过手机号/Email登录
     *
     * @param user 包含用户手机号/Email的用户信息
     * @return 成功返回User对象，失败返回null
     */
    User login(User user);

    /**
     * 通过name获取用户信息
     *
     * @param name 用户姓名
     * @return List UserEnum
     */
    User getUserByName(String name);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 返回注册后数据，注册失败返回null
     */
    User signIn(User user);

    /**
     * 用户更新
     *
     * @param user 更新后的用户信息
     * @return UserEnum
     */
    User updateOne(User user);

    /**
     * 删除一条用户记录通过id
     *
     * @param id 用户id
     * @return Bool
     */
    Boolean deleteById(Long id);

    /**
     * change password
     *
     * @param userId  用户id
     * @param newPass 新密码
     * @return UserEnum
     */
    User changePassword(Long userId, String newPass);

    /**
     * 获取当前登录用户信息
     *
     * @return UserEnum
     */
    User getInfo();


    /**
     * 通过Type值获取所有用户数量
     *
     * @param type 用户type
     * @return Integer
     */
    Integer getCountByType(Integer type);


    /**
     * 获取用户列表
     *
     * @param param Map
     * @return IPage
     */
    IPage<User> getList(Map<String, Object> param);

    /**
     * 用户通过手机及手机验证码登录, 验证手机号是否存在数据库
     *
     * @param phone 用户电话
     * @return boolean
     */
    User loginByPhone(String phone);
}
