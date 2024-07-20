package test.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;

public class TokenParseUtil {

    public static Integer getUID() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String tokenIdStr = (String) tokenInfo.loginId;
        Integer uid = null;
        if (tokenIdStr != null) {
            uid = Integer.valueOf(tokenIdStr);
        }
        return uid;
    }
}
