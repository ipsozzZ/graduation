package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Cate;
import live.ipso.entities.Comment;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.CommentMapper;
import live.ipso.provider.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public Comment saveOne(Comment comment) {
        if (comment == null)
            return null;

        comment.setCreate();
        if (!this.save(comment))
            return null;
        return comment;
    }

    @Override
    public Comment getById(Long id) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Comment::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Comment::getCommentId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean updateOne(Comment comment) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Comment::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(comment.getCommentId()!=null, Comment::getCommentId, comment.getCommentId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        comment.setUpdatde();
        return this.updateById(comment);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Comment::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Comment::getCommentId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Comment cate = this.getOne(queryWrapper);
        cate.setDelFlag(DelFlagEnum.DELETE.getCode());
        cate.setUpdatde();
        return this.updateById(cate);
    }

    @Override
    public IPage<Comment> getList(Map<String, Object> params) {
        String name = (String) params.get("commentName");
        String uID = (String) params.get("userId");
        Integer currPage = (int) params.get("currPage");
        Integer size = (int) params.get("size");

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Comment::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(uID!=null, Comment::getUserId, uID)
                .like(name != null, Comment::getCommentName, name)
                .orderByDesc(Comment::getUpdateTime)
                .orderByDesc(Comment::getCreateTime);
        Page<Comment> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }
}
