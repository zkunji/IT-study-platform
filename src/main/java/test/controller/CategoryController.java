package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.pojos.Category;
import test.result.Result;
import test.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //添加文章分类
    @PostMapping
    public Result addCategory(@RequestBody @Validated Category category) {
        return categoryService.addCategory(category);
    }

    //获取文章列表
    @GetMapping
    public Result showCategoryList() {
        return categoryService.showCategoryList();
    }

    //获取文章分类详细信息
    @GetMapping("/details")
    public Result getDetails(Integer id) {
        return categoryService.getCategoryDetails(id);
    }

    //更新文章分类
    @PutMapping
    public Result updateCategory(@RequestBody @Validated Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping
    public Result deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

}
