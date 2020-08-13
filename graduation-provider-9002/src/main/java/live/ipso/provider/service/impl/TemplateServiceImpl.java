package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Link;
import live.ipso.entities.Picture;
import live.ipso.entities.Template;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.TemplateMapper;
import live.ipso.provider.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {
    @Override
    public Template saveOne(Template template) {
        if (template == null)
            return null;

        template.setCreate();
        if (!this.save(template))
            return null;
        return template;
    }

    @Override
    public Template getById(Long id) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Template::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Template::getTemplateId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Template template) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Template::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(template.getTemplateId()!=null, Template::getTemplateId, template.getTemplateId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        template.setUpdatde();
        return this.updateById(template);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Template::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Template::getTemplateId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Template template = this.getOne(queryWrapper);
        template.setDelFlag(DelFlagEnum.DELETE.getCode());
        template.setUpdatde();
        return this.updateById(template);
    }

    @Override
    public IPage<Template> getList(Map<String, Object> params) {
        String name = (String) params.get("templateName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Template::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Template::getUserId, uID)
                .like(name != null, Template::getTemplateName, name)
                .orderByDesc(Template::getLikeNum)
                .orderByDesc(Template::getUpdateTime)
                .orderByDesc(Template::getCreateTime);
        Page<Template> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }

    final private TemplateMapper templateMapper;

    @Override
    public void addLike(Long id) {
        templateMapper.addLike(id, 1);
    }
}
