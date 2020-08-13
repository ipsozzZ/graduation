package live.ipso.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import live.ipso.entities.Template;
import org.apache.ibatis.annotations.Param;

public interface TemplateMapper extends BaseMapper<Template> {

    /**
     * 根据产品Id和评论增加点赞数量
     *
     * @param id
     * @param number
     */
    Integer addLike(@Param("id") Long id, @Param("number") Integer number);
}
