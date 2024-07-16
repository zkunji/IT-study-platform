package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.pojos.Category;
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
