package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Picture;

import java.util.Map;

public interface PictureService {

    /**
     * 添加一条记录
     * @param  picture 记录信息
     * @return Template
     */
    Picture saveOne(Picture picture);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Picture
     */
    Picture getById(Long id);


    /**
     * 根据id更新数据
     * @param  picture 记录信息
     * @return Picture
     */
    Boolean updateOne(Picture picture);

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
    IPage<Picture> getList(Map<String, Object> params);

}
