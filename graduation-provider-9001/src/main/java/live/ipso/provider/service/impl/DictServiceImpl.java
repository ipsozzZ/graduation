package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Contact;
import live.ipso.entities.Dict;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.DictMapper;
import live.ipso.provider.service.DictService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Override
    public Dict saveOne(Dict dict) {
        if (dict == null)
            return null;

        dict.setCreate();
        if (!this.save(dict))
            return null;
        return dict;
    }

    @Override
    public Dict getById(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dict::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Dict::getDictId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Dict dict) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dict::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(dict.getDictId()!=null, Dict::getDictId, dict.getDictId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        dict.setUpdatde();
        return this.updateById(dict);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dict::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Dict::getDictId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Dict dict = this.getOne(queryWrapper);
        dict.setDelFlag(DelFlagEnum.DELETE.getCode());
        dict.setUpdatde();
        return this.updateById(dict);
    }

    @Override
    public IPage<Dict> getList(Map<String, Object> params) {
        String name = (String) params.get("dictName");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dict::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .like(name != null, Dict::getDictName, name)
                .orderByDesc(Dict::getUpdateTime)
                .orderByDesc(Dict::getCreateTime);
        Page<Dict> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
