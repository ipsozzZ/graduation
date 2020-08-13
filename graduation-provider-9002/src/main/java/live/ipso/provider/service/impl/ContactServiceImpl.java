package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Comment;
import live.ipso.entities.Contact;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.ContactMapper;
import live.ipso.provider.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
    @Override
    public Contact saveOne(Contact contact) {
        if (contact == null)
            return null;

        contact.setCreate();
        if (!this.save(contact))
            return null;
        return contact;
    }

    @Override
    public Contact getById(Long id) {
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Contact::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Contact::getContactId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Contact contact) {
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Contact::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(contact.getContactId()!=null, Contact::getContactId, contact.getContactId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        contact.setUpdatde();
        return this.updateById(contact);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Contact::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Contact::getContactId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Contact contact = this.getOne(queryWrapper);
        contact.setDelFlag(DelFlagEnum.DELETE.getCode());
        contact.setUpdatde();
        return this.updateById(contact);
    }

    @Override
    public IPage<Contact> getList(Map<String, Object> params) {
        String name = (String) params.get("contactName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Contact::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Contact::getUserId, uID)
                .like(name != null, Contact::getContactName, name)
                .orderByDesc(Contact::getUpdateTime)
                .orderByDesc(Contact::getCreateTime);
        Page<Contact> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
