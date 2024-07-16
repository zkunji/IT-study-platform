package test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "ABC");
        String token = JWT.create().withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256("XiaoMi-LOVE-XiaoJi"));//过期时间
        System.out.println(token);
    }

    @Test
    public void testLogin() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IkFCQyJ9LCJleHAiOjE3MTg3MjQ4MDl9" +
                ".yXIEw8XunRzk_qrH_I60-jARmisksSxtMYAdWsSFe0E";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("XiaoMi-LOVE-XiaoJi")).build();
        DecodedJWT decode = jwtVerifier.verify(token);
        Map<String, Claim> claims = decode.getClaims();
        System.out.println(claims.get("user"));
    }



}
