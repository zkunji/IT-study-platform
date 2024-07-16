package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.pojos.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
