package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.NewService;
import live.ipso.entities.Notice;

import java.util.Map;

public interface NewServiceService {

    /**
     * 添加一条记录
     * @param  newService 记录信息
     * @return NewService
     */
    NewService saveOne(NewService newService);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return NewService
     */
    NewService getById(Long id);


    /**
     * 根据id更新数据
     * @param  newService 记录信息
     * @return NewService
     */
    Boolean updateOne(NewService newService);

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
    IPage<NewService> getList(Map<String, Object> params);

    /**
     * 添加点赞数
     * @param id id
     */
    void addLike(Long id);
}
