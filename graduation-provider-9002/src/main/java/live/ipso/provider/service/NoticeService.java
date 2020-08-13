package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Notice;

import java.util.Map;

public interface NoticeService {

    /**
     * 添加一条记录
     * @param  notice 记录信息
     * @return Notice
     */
    Notice saveOne(Notice notice);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Notice
     */
    Notice getById(Long id);


    /**
     * 根据id更新数据
     * @param  notice 记录信息
     * @return Notice
     */
    Boolean updateOne(Notice notice);

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
    IPage<Notice> getList(Map<String, Object> params);

}
