package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Notice;
import live.ipso.entities.Picture;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.PictureMapper;
import live.ipso.provider.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
    @Override
    public Picture saveOne(Picture picture) {
        if (picture == null)
            return null;

        picture.setCreate();
        if (!this.save(picture))
            return null;
        return picture;
    }

    @Override
    public Picture getById(Long id) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Picture::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Picture::getPictureId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Picture picture) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Picture::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(picture.getPictureId()!=null, Picture::getPictureId, picture.getPictureId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        picture.setUpdatde();
        return this.updateById(picture);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Picture::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Picture::getPictureId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Picture picture = this.getOne(queryWrapper);
        picture.setDelFlag(DelFlagEnum.DELETE.getCode());
        picture.setUpdatde();
        return this.updateById(picture);
    }

    @Override
    public IPage<Picture> getList(Map<String, Object> params) {
        String name = (String) params.get("pictureName");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Picture::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .like(name != null, Picture::getPictureName, name)
                .orderByDesc(Picture::getUpdateTime)
                .orderByDesc(Picture::getCreateTime);
        Page<Picture> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
