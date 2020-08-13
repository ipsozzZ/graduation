package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Comment;
import live.ipso.entities.Contact;

import java.util.Map;

public interface CommentService {

    /**
     * 添加一条记录
     * @param  comment 记录信息
     * @return Comment
     */
    Comment saveOne(Comment comment);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Comment
     */
    Comment getById(Long id);


    /**
     * 根据id更新数据
     * @param  comment 记录信息
     * @return Dict
     */
    Boolean updateOne(Comment comment);

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
    IPage<Comment> getList(Map<String, Object> params);
}
