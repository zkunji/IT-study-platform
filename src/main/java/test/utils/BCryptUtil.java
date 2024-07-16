package test.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    public static String hashPassword(String password) {
        // gensalt() 方法自动生成一个盐值，并可以选择性地设置迭代次数（work factor）
        String salt = BCrypt.gensalt(12); // 迭代次数设置为12
        return BCrypt.hashpw(password, salt);
    }

    public static boolean verifyPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
}
