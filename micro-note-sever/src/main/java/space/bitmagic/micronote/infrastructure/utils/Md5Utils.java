package space.bitmagic.micronote.infrastructure.utils;

import org.springframework.util.DigestUtils;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public class Md5Utils {


    public static String getMD5Hash(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }
}
