package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Cate;

import java.util.Map;

public interface CateService {

    /**
     * 添加一条记录
     * @param  cate 记录信息
     * @return Cate
     */
    Cate saveOne(Cate cate);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Cate
     */
    Cate getById(Long id);


    /**
     * 根据id更新数据
     * @param  cate 记录信息
     * @return Cate
     */
    Boolean updateOne(Cate cate);

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
    IPage<Cate> getList(Map<String, Object> params);
}
