package test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.mapper.ArticleMapper;
import test.mapper.CategoryMapper;
import test.pojos.Article;
import test.pojos.PageBean;
import test.result.Result;
import test.service.ArticleService;
import test.utils.TokenParseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    public static final String STATE = "已发布";

    //添加文章
    @Override
    public Result addArticle(Article article) {
//        Map<String, Object> map = ThreadLocalUtil.get();
//        Integer uid = (Integer) map.get("uid");
        Integer uid = TokenParseUtil.getUID();
        //LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        article.setCreateUser(uid);
        //System.out.println(article.getCoverImg());
        article.setUpdateTime(LocalDateTime.now());
        int flag = articleMapper.insert(article);
        if (flag > 0) {
            return Result.success(article);
        }
        return Result.fail("发布文章失败");
    }

    //构造分页查询
    private PageBean<Article> fetchArticles(LambdaQueryWrapper<Article> queryWrapper, Page<Article> page) {
        IPage<Article> articleIPage = articleMapper.selectPage(page, queryWrapper);
        PageBean<Article> list = new PageBean<>();
        list.setTotal(articleIPage.getTotal());
        list.setItems(articleIPage.getRecords());
        return list;
    }

    //根据类别展示所有文章
    @Override
    public Result showArticleList(Integer categoryId) {
        if (categoryId != null) {
            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = Wrappers.lambdaQuery();
            articleLambdaQueryWrapper.allEq(Map.of(Article::getState, STATE, Article::getCategoryId, categoryId));
            Page<Article> articlePage = new Page<>(1, 10);
            PageBean<Article> list = fetchArticles(articleLambdaQueryWrapper, articlePage);
            return Result.success(200, "文章查询成功", list);
        } else {
            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = Wrappers.lambdaQuery();
            articleLambdaQueryWrapper.like(Article::getState, "已发布");
            Page<Article> articlePage = new Page<>(1, 10);
            PageBean<Article> list = fetchArticles(articleLambdaQueryWrapper, articlePage);
            return Result.success(200, "文章查询成功", list);
        }

    }

    //查询用户文章
    @Override
    public Result searchUserArticle(Integer uId) {
        Integer uid = TokenParseUtil.getUID();
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = Wrappers.lambdaQuery();
        articleLambdaQueryWrapper.allEq(Map.of(Article::getCreateUser, uid, Article::getState, STATE));
        Page<Article> articlePage = new Page<>(1, 10);
        PageBean<Article> list = fetchArticles(articleLambdaQueryWrapper, articlePage);
        return Result.success(200, "文章查询成功", list);
    }


    //删除文章
    @Override
    public Result deleteArticle(Integer articleId) {
        LambdaQueryWrapper<Article> delete = new LambdaQueryWrapper<>();
        delete.eq(Article::getId, articleId);
        int flag = articleMapper.delete(delete);
        if (flag > 0) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    //更新文章
    @Override
    public Result updateArticle(Article article) {
        LambdaUpdateWrapper<Article> update = new LambdaUpdateWrapper<>();
        update.eq(Article::getId, article.getId());
        if (article.getTitle() != null) {
            update.set(Article::getTitle, article.getTitle());
        }
        if (article.getContent() != null) {
            update.set(Article::getContent, article.getContent());
        }
        if (article.getCoverImg() != null) {
            update.set(Article::getCoverImg, article.getCoverImg());
        }
        /*if (article.getState() != null) {
            update.set(Article::getState, article.getState());
        }*/
        if (article.getCategoryId() != null) {
            update.set(Article::getCategoryId, article.getCategoryId());
        }
        update.set(Article::getUpdateTime, article.getUpdateTime());
        int flag = articleMapper.update(update);
        if (flag > 0) {
            return Result.success(200, "更新成功", articleMapper.selectById(article.getId()));
        }
        return Result.fail("更新失败");
    }


    @Override
    public Result searchArticleById(Integer id) {
        return Result.success(categoryMapper.selectById(id));
    }


    //模糊查询
    @Override
    public Result fuzzyQuery(String fields) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.like(Article::getTitle, fields).and(lqw1 -> lqw1.eq(Article::getState, STATE));
        List<Article> articles = articleMapper.selectList(lqw);
        return Result.success(articles);
    }
}
