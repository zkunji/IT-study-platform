package test.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
        patterns.add("/user/login");
        patterns.add("/user/register");
        /*registry.addInterceptor()
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);*/

        registry.addInterceptor(new SaInterceptor(handler -> {
            SaRouter
                    .match("/**")
                    .notMatch("/user/login")
                    .notMatch("/user/register")
                    .notMatch("/article/list")
                    .check(r -> StpUtil.checkLogin());
//            SaRouter
//                    .match()

        }).isAnnotation(false)).addPathPatterns("/**");
    }
}
