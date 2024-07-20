package test.controller;

import cn.dev33.satoken.util.SaResult;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.pojos.User;
import test.result.Result;
import test.service.UserService;

import test.utils.TokenParseUtil;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "[\\u4e00-\\u9fa5a-zA-Z0-9\\s,.'\";!?()]+")
                           String username,
                           @Pattern(regexp = "^(?=.*[a-zA-Z]).{8,}$")
                           String password) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("username", username);
        param.put("password", password);
        User u = userService.findByUsername(username);
        if (u != null) {
            return Result.fail("用户名已被占用");
        }
        return userService.registration(param);
    }

    @PostMapping("/login")
    public SaResult login(@Pattern(regexp = "[\\u4e00-\\u9fa5a-zA-Z0-9\\s,.'\";!?()]+")
                          String username, String password) {
//        long start = System.currentTimeMillis();
//        User loginUser = userService.findByUsername(username);
////        Map<String, String> param = new HashMap<>();
////        param.put("username", username);
////        param.put("password", password);
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("uid", loginUser.getUid());
//        claims.put("username", loginUser.getUsername());
//        //claims.put("password", loginUser.getPassword());
//        String token = JwtUtil.genToken(claims);
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        operations.set(token, token, 1, TimeUnit.HOURS);
//        Result login = userService.login(username, password, token);
//        log.info("登录时间：{}ms", System.currentTimeMillis() - start);

        return userService.login(username, password);

    }

    @GetMapping("/logout")
    public SaResult logout(Integer uid) {
        return userService.logout(uid);
    }

    @GetMapping("/userInfo")
    public Result showUserInfo() {
//        Map<String, Object> map = ThreadLocalUtil.get();
//        String username = (String) map.get("username");
//        User u = userService.findByUsername(username);
        Integer uid = TokenParseUtil.getUID();
        User u = userService.findByUserid(uid).get(0);
        /*Map<String, String> userInfo = new LinkedHashMap<>();
        userInfo.put("username", u.getUsername());
        userInfo.put("account", u.getAccount());
        userInfo.put("email", u.getEmail());
        userInfo.put("createdTime", String.valueOf(u.getCreatedTime()));*/
        return Result.success(200, "用户信息查询成功", u);
    }

    @PutMapping("/updateInfo")
    public Result updateUserInfo(@RequestBody Map<String, String> param) {
        Integer uid = TokenParseUtil.getUID();
        return userService.updateUserInfo(param, uid);
    }

    @PostMapping("/reset_pwd")
    public Result resetPwd(@RequestBody Map<String, String> param) {
        Integer uid = TokenParseUtil.getUID();
        return userService.updateUserPassword(param, uid);
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody String avatarUrl) {
        Integer uid = TokenParseUtil.getUID();
        return Result.success(userService.updateAvatar(avatarUrl, uid));
    }

}
