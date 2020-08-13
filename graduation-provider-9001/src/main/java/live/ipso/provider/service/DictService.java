package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Dict;

import java.util.Map;

public interface DictService {

    /**
     * 添加一条记录
     * @param  dict 记录信息
     * @return Dict
     */
    Dict saveOne(Dict dict);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Dict
     */
    Dict getById(Long id);


    /**
     * 根据id更新数据
     * @param  dict 记录信息
     * @return Dict
     */
    Boolean updateOne(Dict dict);

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
    IPage<Dict> getList(Map<String, Object> params);
}
