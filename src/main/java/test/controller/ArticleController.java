package test.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.pojos.Article;
import test.pojos.User;
import test.result.Result;
import test.service.ArticleService;
import test.utils.BCryptUtil;
import test.utils.TokenParseUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        return articleService.addArticle(article);
    }

    //分页查询
    @GetMapping("/list")
    public Result showArticleList(@RequestParam(required = false) Integer categoryId) {
        return articleService.showArticleList(categoryId);

    }

    //根据文章id删除文章
    @PostMapping("/delete")
    public SaResult deleteArticle(Integer id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping("/userArticle")
    public Result searchUserArticle(Integer uid) {
        return articleService.searchUserArticle(uid);
    }

    @PutMapping("/update")
    public Result updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }


    @PostMapping("/select")
    public Result select(@RequestParam Integer id) {
        return articleService.searchArticleById(id);
    }

    //模糊查询
    @PostMapping("/fuzzyQuery")
    public Result fuzzyQuery(String str) {
        return articleService.fuzzyQuery(str);
    }



//    @GetMapping("/openSafe")
//    public SaResult secondaryCertification(String password) {
//
//
//    }
}
