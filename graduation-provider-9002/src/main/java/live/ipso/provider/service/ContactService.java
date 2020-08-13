package live.ipso.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import live.ipso.entities.Contact;

import java.util.Map;

public interface ContactService {

    /**
     * 添加一条记录
     * @param  contact 记录信息
     * @return Contact
     */
    Contact saveOne(Contact contact);

    /**
     * 通过id获取记录
     * @param  id  记录id
     * @return Contact
     */
    Contact getById(Long id);


    /**
     * 根据id更新数据
     * @param  contact 记录信息
     * @return Dict
     */
    Boolean updateOne(Contact contact);

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
    IPage<Contact> getList(Map<String, Object> params);
}
