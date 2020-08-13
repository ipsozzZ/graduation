package live.ipso.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import live.ipso.entities.Article;
import live.ipso.provider.common.enums.DelFlagEnum;
import live.ipso.provider.dao.ArticleMapper;
import live.ipso.provider.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public Article saveOne(Article article) {
        if (article == null)
            return null;

        article.setCreate();
        if (!this.save(article))
            return null;
        return article;
    }

    @Override
    public Article getDataById(Long id) {
        if (id == null || id <= 0) return null;
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Article::getDelFlag, DelFlagEnum.NORMAL.getCode()).eq(Article::getArticleId, id);

        Article article = this.getOne(queryWrapper);
        return article;
    }

    @Override
    public Boolean updateOne(Article article) {
        System.out.println(article.toString());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Article::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(article.getArticleId()!=null, Article::getArticleId, article.getArticleId());

        if (this.getOne(queryWrapper) == null){
            return false;
        }

        article.setUpdatde();
        return this.updateById(article);
    }

    @Override
    public Boolean deleteOne(Long id) {

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Article::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .eq(id!=null, Article::getArticleId, id);

        if (this.getOne(queryWrapper) == null)
            return false;

        Article article = this.getOne(queryWrapper);
        article.setUpdatde();
        article.setDelFlag(DelFlagEnum.DELETE.getCode());
        return this.updateById(article);
    }

    @Override
    public IPage<Article> getList(Map<String, Object> params) {
        String name = (String) params.get("articleName");
        Long uid;
        if (params.get("userId") == null){
            uid = null;
        }else {
            uid = Long.valueOf ((String) params.get("userId"));
        }
        String cp = params.get("currPage").toString();
        String len = params.get("size").toString();
        int currPage = 1;
        int size = 5;
        if (cp != null && len != null){
            currPage = Integer.parseInt(cp);
            size = Integer.parseInt(len);
        }


        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Article::getDelFlag, DelFlagEnum.NORMAL.getCode())
                .like(name != null, Article::getArticleName, name)
                .eq(params.get("userId") != null, Article::getUserId, uid)
                .orderByDesc(Article::getLikeNum).orderByDesc(Article::getUpdateTime)
                .orderByDesc(Article::getCreateTime);
        Page<Article> page = new Page<>(currPage, size);
        return this.page(page, queryWrapper);
    }

    final private ArticleMapper articleMapper;

    @Override
    public void addLike(Long id) {
        articleMapper.addLike(id, 1);
    }
}
