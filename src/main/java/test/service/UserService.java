package test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.User;
import test.result.Result;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Result registration(Map<String, String> param);

    Result login(String username, String password,String token);

    boolean check(String username, String password);

    User findByUsername(String username);

    List<User> findByUserid(Integer uid);

    //boolean check(Map<String, String> param);

    //Result getUserInfo(Map<String, String> param);

    Result updateUserInfo(Map<String, String> user, Integer uid);

    Result updateUserPassword(Map<String, String> param, Integer uid, String token);

    Result updateAvatar(String avatarUrl, Integer uid);
}
