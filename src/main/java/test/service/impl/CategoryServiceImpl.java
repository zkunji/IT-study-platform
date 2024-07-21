package test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import test.mapper.CategoryMapper;
import test.pojos.Category;
import test.result.Result;
import test.service.CategoryService;
import test.utils.TokenParseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //添加文章分类
    @Override
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
//        Map<String, Object> map = ThreadLocalUtil.get();
//        Integer uid = (Integer) map.get("uid");
        Integer uid = TokenParseUtil.getUID();
        category.setCreateUser(uid);
        int flag = categoryMapper.insert(category);
        if (flag >= 1) {
            return Result.success("新增类别成功");
        }
        return Result.fail("类别新增失败");
    }

    //显示文章分类列表
    @Override
    public Result showCategoryList() {
        return Result.success(categoryMapper.selectList(null));
    }

    //查询分类详细信息
    @Override
    public Result getCategoryDetails(Integer id) {
        List<Category> list = findByCategoryId(id);
        return Result.success(list);
    }

    //更新文章分类
    @Override
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {
        Category oldCate = findByCategoryId(category.getId()).get(0);
        if (oldCate == null) {
            return Result.fail("更新失败无该类别");
        }
        LambdaUpdateWrapper<Category> qw = new LambdaUpdateWrapper<>();
        qw.eq(Category::getId, category.getId());
        qw.set(Category::getCategoryName, category.getCategoryName());
        qw.set(Category::getCategoryAlias, category.getCategoryAlias());
        qw.set(Category::getUpdatedTime, LocalDateTime.now());
        boolean res = categoryMapper.update(qw) > 0;
        if (res) {
            return Result.success(200, "文章分类更新成功", categoryMapper.selectById(category.getId()));
        } else {
            return Result.fail("更新文章分类失败");
        }

    }

    //根据文章id查询
    public List<Category> findByCategoryId(Integer id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getId, id);
        return categoryMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Result deleteCategory(Integer id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getId, id);
        int f = categoryMapper.delete(lambdaQueryWrapper);
        if (f>0){
            return Result.success(200,"删除成功",null);
        }
        return Result.fail("删除失败");
    }
}
