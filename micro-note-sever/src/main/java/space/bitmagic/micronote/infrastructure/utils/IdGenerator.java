package space.bitmagic.micronote.infrastructure.utils;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public class IdGenerator {

    public static String genId(String... args) {
        return Md5Utils.getMD5Hash(String.join("-", args));
    }
}
