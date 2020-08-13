package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.User;
import live.ipso.provider.common.config.Tools;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.UserMapper;
import live.ipso.provider.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserById(Long id) {
        if (id == null || id <= 0 ) return null;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode()).eq(User::getUserId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public User login(User user) {
        // 判断是用户名登录还是手机号登录
        if (user.getUserName() == null && user.getUserPass() == null){
            return null;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode());

        // 用户名登录
        if (user.getUserName() != null){

            // 对用户密码进行加密
            /*String newPass = Tools.dataMD5(user.getUserPass() + user.getSalt());
            user.setUserPass(newPass);*/

            queryWrapper.lambda().eq(User::getUserName, user.getUserName()).eq(User::getUserPass, user.getUserPass());
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(name!=null, User::getUserName, name);

        return this.getOne(queryWrapper);
    }

    @Override
    public User signIn(User user) {

        // 判断用户名/手机号是否被注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(user.getUserName() != null, User::getUserName, user.getUserName())
                .or(e -> e.eq(user.getUserPhone()!=null, User::getUserPhone, user.getUserPhone()));

        User isUser = this.getOne(queryWrapper);
        if (isUser != null){ // 用户存在就返回null
            return null;
        }

        // 用户密码MD5加密
        /*String newPass = Tools.dataMD5(user.getUserPass()+user.getSalt());
        user.setUserPass(newPass);*/
        user.setCreate();
        boolean res = this.save(user);
        if (!res)
            return null;
        return user;
    }

    @Override
    public User updateOne(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(User::getUserId, user.getUserId());

        if (this.getOne(queryWrapper) == null){
            return null;
        }
        user.setUpdatde();
        if (!this.updateById(user))
            return null;
        return user;
    }

    @Override
    public Boolean deleteById(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, User::getUserId, id);

        User user = this.getOne(queryWrapper);
        user.setDelFlag(DelFlagEnum.DELETE.getCode());
        user.setUpdatde();
        this.updateById(user);
        return true;
    }

    @Override
    public IPage<User> getList(Map<String, Object> param) {
        Integer type = (Integer) param.get("type");
        String name = (String) param.get("username");
        Integer currPage = (int) param.get("currPage");
        Integer size = (int) param.get("size");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (param.containsKey("type") && (int) param.get("type") == -1) {
            queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                    .and(e -> e.eq(User::getType, 0).or().eq(User::getType, 1))
                    .like(name != null, User::getUserName, name);
        } else {
            queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                    .eq(type != null, User::getType, type)
                    .like(name != null, User::getUserName, name);
        }
        queryWrapper.lambda().orderByDesc(User::getUpdateTime).orderByDesc(User::getCreateTime);

        Page<User> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }

    @Override
    public User loginByPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(phone!=null, User::getUserPhone, phone);

        User user = this.getOne(queryWrapper);
        return null;
    }

    @Override
    public User changePassword(Long userId, String newPass) {
        return null;
    }

    @Override
    public User getInfo() {
        return null;
    }

    @Override
    public Integer getCountByType(Integer type) {
        return null;
    }
}
