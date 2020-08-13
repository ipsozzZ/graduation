package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Album;
import live.ipso.entities.Article;

import java.util.Map;

public interface AlbumService {

    /**
     * 添加一条记录
     * @param  album 记录信息
     * @return Album
     */
    Album saveOne(Album album);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Album
     */
    Album getById(Long id);


    /**
     * 根据id更新数据
     * @param  album 记录信息
     * @return Album
     */
    Boolean updateOne(Album album);

    /**
     * 根据id逻辑删除一条数据
     * @param  id id
     */
    Boolean deleteOne(Long id);

    /**
     * 根据标题模糊匹配
     * @param  params  String title, Integer currPage, Integer size
     * @return IPage
     */
    IPage<Album> getList(Map<String, Object> params);
}
