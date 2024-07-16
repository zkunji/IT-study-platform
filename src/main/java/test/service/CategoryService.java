package test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.Category;
import test.result.Result;

import java.util.List;


public interface CategoryService extends IService<Category> {
    Result addCategory(Category category);

    Result showCategoryList();

    Result getCategoryDetails(Integer id);

    Result updateCategory(Category category);

    Result deleteCategory(Integer id);
}
