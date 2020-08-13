package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Album;
import live.ipso.entities.Article;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.AlbumMapper;
import live.ipso.provider.service.AlbumService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Override
    public Album saveOne(Album album) {
        if (album == null)
            return null;

        album.setCreate();
        if (!this.save(album))
            return null;
        return album;
    }

    @Override
    public Album getById(Long id) {
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Album::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Album::getAlbumId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Album album) {
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Album::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(album.getAlbumId()!=null, Album::getAlbumId, album.getAlbumId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        album.setUpdatde();
        return this.updateById(album);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Album::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Album::getAlbumId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Album album = this.getOne(queryWrapper);
        album.setDelFlag(DelFlagEnum.DELETE.getCode());
        album.setUpdatde();
        return this.updateById(album);
    }

    @Override
    public IPage<Album> getList(Map<String, Object> params) {
        String name = (String) params.get("albumName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Album::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Album::getUserId, uID)
                .like(name != null, Album::getAlbumName, name)
                .orderByDesc(Album::getUpdateTime)
                .orderByDesc(Album::getCreateTime);
        Page<Album> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
