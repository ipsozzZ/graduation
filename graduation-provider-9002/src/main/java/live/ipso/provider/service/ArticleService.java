package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Article;

import java.util.Map;

public interface ArticleService {

    /**
     * 添加一条记录
     * @param  article 记录信息
     * @return Article
     */
    Article saveOne(Article article);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Article
     */
    Article getDataById(Long id);


    /**
     * 根据id更新数据
     * @param  article 记录信息
     * @return Article
     */
    Boolean updateOne(Article article);

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
    IPage<Article> getList(Map<String, Object> params);

    /**
     * 添加点赞数
     * @param id id
     */
    void addLike(Long id);
}
