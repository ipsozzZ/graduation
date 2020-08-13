package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Album;
import live.ipso.entities.Cate;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.CateMapper;
import live.ipso.provider.service.CateService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CateServiceImpl extends ServiceImpl<CateMapper, Cate> implements CateService {
    @Override
    public Cate saveOne(Cate cate) {
        if (cate == null)
            return null;

        cate.setCreate();
        if (!this.save(cate))
            return null;
        return cate;
    }

    @Override
    public Cate getById(Long id) {
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Cate::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Cate::getCateId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Cate cate) {
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Cate::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(cate.getCateId()!=null, Cate::getCateId, cate.getCateId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        cate.setUpdatde();
        return this.updateById(cate);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Cate::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Cate::getCateId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Cate cate = this.getOne(queryWrapper);
        cate.setDelFlag(DelFlagEnum.DELETE.getCode());
        cate.setUpdatde();
        return this.updateById(cate);
    }

    @Override
    public IPage<Cate> getList(Map<String, Object> params) {
        String name = (String) params.get("cateName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Cate::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Cate::getUserId, uID)
                .like(name != null, Cate::getCateName, name)
                .orderByDesc(Cate::getUpdateTime)
                .orderByDesc(Cate::getCreateTime);
        Page<Cate> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
