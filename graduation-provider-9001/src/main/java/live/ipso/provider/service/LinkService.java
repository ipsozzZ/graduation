package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Link;
import live.ipso.entities.NewService;

import java.util.Map;

public interface LinkService {

    /**
     * 添加一条记录
     * @param  newService 记录信息
     * @return Link
     */
    Link saveOne(Link newService);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Link
     */
    Link getById(Long id);


    /**
     * 根据id更新数据
     * @param  newService 记录信息
     * @return Link
     */
    Boolean updateOne(Link newService);

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
    IPage<Link> getList(Map<String, Object> params);
}
