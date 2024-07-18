package test.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.mapper.UserAuthorityMapper;

import test.pojos.UserAuthority;

import java.util.ArrayList;
import java.util.List;


@Component
public class AuthorityConfig implements StpInterface {
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String s) {

        LambdaQueryWrapper<UserAuthority> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserAuthority::getUid, StpUtil.getLoginIdAsInt());
        List<UserAuthority> authorityList = userAuthorityMapper.selectList(lqw);
        List<String> resList = new ArrayList<>();
        for (UserAuthority a : authorityList) {
            resList.add(a.getAuthority());
        }
        return resList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        LambdaQueryWrapper<UserAuthority> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserAuthority::getUid, StpUtil.getLoginIdAsInt());
        List<UserAuthority> authorityList = userAuthorityMapper.selectList(lqw);
        List<String> resList = new ArrayList<>();
        for (UserAuthority a : authorityList) {
            resList.add(a.getRole());
        }
        return resList;
    }
}
