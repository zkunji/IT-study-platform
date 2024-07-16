package test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import test.pojos.User;
import test.service.UserService;

@Component
@Slf4j
public class InitRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    /**
     * 在项目启动成功之后会运行这个方法
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 帮我在项目启动的时候查询一次数据库，防止数据库的懒加载
        User user = userService.getById(1);
        log.info("启动项目数据库连接查询成功");
    }

}

