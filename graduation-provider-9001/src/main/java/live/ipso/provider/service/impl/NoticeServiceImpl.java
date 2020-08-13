package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.NewService;
import live.ipso.entities.Notice;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.NoticeMapper;
import live.ipso.provider.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Override
    public Notice saveOne(Notice notice) {
        if (notice == null)
            return null;

        notice.setCreate();
        if (!this.save(notice))
            return null;
        return notice;
    }

    @Override
    public Notice getById(Long id) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Notice::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Notice::getNoticeId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Notice notice) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Notice::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(notice.getNoticeId()!=null, Notice::getNoticeId, notice.getNoticeId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        notice.setUpdatde();
        return this.updateById(notice);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Notice::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Notice::getNoticeId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Notice notice = this.getOne(queryWrapper);
        notice.setDelFlag(DelFlagEnum.DELETE.getCode());
        notice.setUpdatde();
        return this.updateById(notice);
    }

    @Override
    public IPage<Notice> getList(Map<String, Object> params) {
        String name = (String) params.get("noticeName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Notice::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Notice::getUserId, uID)
                .like(name != null, Notice::getNoticeName, name)
                .orderByDesc(Notice::getUpdateTime)
                .orderByDesc(Notice::getCreateTime);
        Page<Notice> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
