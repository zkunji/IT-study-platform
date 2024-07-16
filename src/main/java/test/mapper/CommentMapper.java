package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.pojos.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
