package test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import test.pojos.UserAuthority;

@Mapper
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {
}
