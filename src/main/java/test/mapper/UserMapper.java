package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.pojos.User;
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
