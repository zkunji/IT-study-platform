package test.service;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.Article;
import test.result.Result;


public interface ArticleService extends IService<Article> {
    Result addArticle(Article article);

    Result showArticleList(Integer categoryId);

    SaResult deleteArticle(Integer categoryId);

    Result searchUserArticle(Integer uId);

    //SaResult searchOtherUserArticle(Integer uid);

    Result updateArticle(Article article);

    Result searchArticleById(Integer id);
    //模糊查询
    Result fuzzyQuery(String fields);
}
