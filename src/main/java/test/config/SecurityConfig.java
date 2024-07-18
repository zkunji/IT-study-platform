package test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

//private final LogoutHandler logoutHandler;
    @Bean
    public HttpSecurity filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz)->authz
                .requestMatchers(
                        "/user/register",
                        "/user/login",
                        "/user/logout")
                .permitAll()
                .anyRequest()
                .authenticated()
        ).httpBasic(Customizer.withDefaults());
        return http.csrf(AbstractHttpConfigurer::disable);
    }

}
