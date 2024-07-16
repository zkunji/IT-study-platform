package test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.Article;
import test.result.Result;


public interface ArticleService extends IService<Article> {
    Result addArticle(Article article);

    Result showArticleList(Integer categoryId);

    Result deleteArticle(Integer categoryId);

    Result searchUserArticle(Integer uId);

    Result updateArticle(Article article);

    Result searchArticleById(Integer id);
    //模糊查询
    Result fuzzyQuery(String fields);
}
