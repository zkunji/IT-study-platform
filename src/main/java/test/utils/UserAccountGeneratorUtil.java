package test.utils;

public class UserAccountGeneratorUtil {

    public static String GenAccId() {
        String[] str = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        StringBuilder resValue = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randNum = (int) (Math.random() * 10);
            resValue.append(str[randNum]);
        }
        return resValue.toString();
    }
}
