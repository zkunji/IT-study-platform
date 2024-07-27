package test.service;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.User;
import test.result.Result;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Result registration(Map<String, String> param);

    SaResult login(String username, String password/*, String token*/);

    SaResult logout(Integer uid);

    boolean check(String username, String password);

    User findByUsername(String username);

    List<User> findByUserid(Integer uid);

    //boolean check(Map<String, String> param);

    //Result getUserInfo(Map<String, String> param);

    Result updateUserInfo(Map<String, String> user, Integer uid);

    Result updateUserPassword(Map<String, String> param, Integer uid);

    Result updateAvatar(String avatarUrl, Integer uid);

    SaResult secondaryCertification(String password);
}
