package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Dict;
import live.ipso.entities.Link;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.LinkMapper;
import live.ipso.provider.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public Link saveOne(Link link) {
        if (link == null)
            return null;

        link.setCreate();
        if (!this.save(link))
            return null;
        return link;
    }

    @Override
    public Link getById(Long id) {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Link::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Link::getLinkId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Link link) {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Link::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(link.getLinkId()!=null, Link::getLinkId, link.getLinkId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        link.setUpdatde();
        return this.updateById(link);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Link::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Link::getLinkId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Link link = this.getOne(queryWrapper);
        link.setDelFlag(DelFlagEnum.DELETE.getCode());
        link.setUpdatde();
        return this.updateById(link);
    }

    @Override
    public IPage<Link> getList(Map<String, Object> params) {
        String name = (String) params.get("linkName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Link::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Link::getUserId, uID)
                .like(name != null, Link::getLinkName, name)
                .orderByDesc(Link::getUpdateTime)
                .orderByDesc(Link::getCreateTime);
        Page<Link> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
