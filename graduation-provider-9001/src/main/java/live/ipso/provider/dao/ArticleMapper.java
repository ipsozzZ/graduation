package live.ipso.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import live.ipso.entities.Article;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据产品Id和评论增加点赞数量
     *
     * @param id
     * @param number
     */
    Integer addLike(@Param("id") Long id, @Param("number") Integer number);
}
