package test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UUIDTest {
    @Test
    public void test() {
        String string = UUID.randomUUID().toString();
        System.out.println(string);
    }
}