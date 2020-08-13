package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Link;
import live.ipso.entities.NewService;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.NewServiceMapper;
import live.ipso.provider.exception.SystemException;
import live.ipso.provider.service.NewServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class NewServiceServiceImpl extends ServiceImpl<NewServiceMapper, NewService> implements NewServiceService {
    @Override
    public NewService saveOne(NewService newService) {
        if (newService == null)
            return null;

        newService.setCreate();
        if (!this.save(newService))
            return null;
        return newService;
    }

    @Override
    public NewService getById(Long id) {
        QueryWrapper<NewService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NewService::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, NewService::getNewId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(NewService newService) {
        QueryWrapper<NewService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NewService::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(newService.getNewId()!=null, NewService::getNewId, newService.getNewId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        newService.setUpdatde();
        return this.updateById(newService);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<NewService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NewService::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, NewService::getNewId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        NewService newService = this.getOne(queryWrapper);
        newService.setDelFlag(DelFlagEnum.DELETE.getCode());
        newService.setUpdatde();
        return this.updateById(newService);
    }

    @Override
    public IPage<NewService> getList(Map<String, Object> params) {
        String name = (String) params.get("newName");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<NewService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NewService::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .like(name != null, NewService::getNewName, name)
                .orderByDesc(NewService::getUpdateTime)
                .orderByDesc(NewService::getCreateTime);
        Page<NewService> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }

    final private NewServiceMapper newServiceMapper;

    @Override
    public void addLike(Long id) {
        newServiceMapper.addLike(id, 1);
    }
}
