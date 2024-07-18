package test.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import test.mapper.UserAuthorityMapper;
import test.mapper.UserMapper;
import test.pojos.User;
import test.pojos.UserAuthority;
import test.result.Result;
import test.service.UserService;
import test.utils.BCryptUtil;
import test.utils.UserAccountGeneratorUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result registration(Map<String, String> param) {
        if (param == null || !param.containsKey("password") || param.get("password").isEmpty()) {
            logger.error("注册失败：用户名或密码为空");
            return Result.fail("注册失败：用户名或密码为空");
        }
        try {
            String username = param.get("username");
            String password = BCryptUtil.hashPassword(param.get("password"));
            User user = new User(username, password);
            user.setAccount(UserAccountGeneratorUtil.GenAccId());
            UserAuthority au = new UserAuthority(user.getUid(), "user.add,user.update,user.get,user.delete", "user");
            int res = userMapper.insert(user);
            userAuthorityMapper.insert(au);
            if (res > 0) {
                return Result.success(user);
            } else {
                logger.error("注册失败：数据库插入失败");
                return Result.fail("注册失败：数据库插入失败");
            }
        } catch (Exception e) {
            logger.error("注册失败：系统异常", e);
            return Result.fail("注册失败：系统异常");
        }
    }

    @Override
    public SaResult login(String username, String password/*, String token*/) {
//        if (check(username, password)) {
//            return Result.success(200, "登录成功", token);
//        }
//        return Result.fail("登陆失败");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        User user = userMapper.selectList(lqw).get(0);
        if (check(username, password)) {
            StpUtil.login(user.getUid());
            //System.out.println(StpUtil.getTokenValue());
            System.out.println(StpUtil.getTokenInfo());
            System.out.println(StpUtil.getTokenSession());
            //System.out.println(StpUtil.getPermissionList());//获取权限测试
            return SaResult.ok("登录成功");
        }
        return SaResult.error("用户名或密码错误");

    }

    @Override
    public SaResult logout(Integer uid) {
        if (StpUtil.isLogin()) {
            StpUtil.logout(uid);
            return SaResult.ok("登出成功");
        }
        return SaResult.error("用户未登录");
    }

    @Override
    public boolean check(String username, String password) {
        User userInfo = findByUsername(username);
        String encodePwd = findByUsername(username).getPassword();
        if (userInfo == null) {
            return false;
        }
        return BCryptUtil.verifyPassword(password, encodePwd);

    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (userMapper.selectList(queryWrapper).isEmpty()) {
            return null;
        }
        return userMapper.selectList(queryWrapper).get(0);
    }

    @Override
    public Result updateUserInfo(Map<String, String> param, Integer uid) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid); // 添加UID条件

        // 更新用户名
        if (param.containsKey("username")) {
            String username = param.get("username");
            updateWrapper.set("username", username);
        }

        // 更新邮箱
        if (param.containsKey("email")) {
            String email = param.get("email");
            updateWrapper.set("email", email);
        }
        updateWrapper.set("created_time", LocalDateTime.now());
        // 执行更新操作
        boolean updated = userMapper.update(updateWrapper) > 0;

        if (updated) {
            // 如果更新成功，返回成功结果
            return Result.success(200, "更新成功", userMapper.selectById(uid));
        } else {
            // 如果更新失败，返回失败结果
            return Result.fail("更新失败");
        }
    }

    //更新用户密码
    @Override
    public Result updateUserPassword(Map<String, String> param, Integer uid, String token) {
        String oldPwd = param.get("old_pwd");
        String newPwd = param.get("new_pwd");
        String reNewPwd = param.get("re_new_pwd");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid); // 添加UID条件
        System.out.println("old:" + oldPwd);
        boolean flag = BCryptUtil.verifyPassword(oldPwd, findByUserid(uid).get(0).getPassword());
        //System.out.println(flag);
        if (!flag) {
            return Result.fail("密码错误");
        }
        if (!Objects.equals(newPwd, reNewPwd)) {
            return Result.fail("两次输入的密码不同");
        }
        updateWrapper.set("password", BCryptUtil.hashPassword(newPwd));
        boolean update_pwd = userMapper.update(updateWrapper) > 0;
        if (update_pwd) {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.getOperations().delete(token);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            return Result.success(200, "密码更新成功", formattedDateTime);
        }
        return Result.fail("密码更新失败");
    }

    @Override
    public List<User> findByUserid(Integer uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public Result updateAvatar(String avatarUrl, Integer uid) {
        try {
            UpdateWrapper<User> uda = new UpdateWrapper<>();
            uda.eq("uid", uid);
            uda.set("user_avatar", avatarUrl);
            return Result.success(200, "头像更新成功", avatarUrl);
        } catch (Exception e) {
            logger.error("[ERROR]: {}", e.getMessage());
            return Result.fail("操作失败，系统故障");
        }

    }
}
