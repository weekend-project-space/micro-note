package space.bitmagic.micronote.infrastructure.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public class FileUtils {

    public static void write(String fileName, String content) {
        // 清空文件并写入新内容
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean delete(String fileName) {
        return  new File(fileName).delete();
    }

    public static boolean exists(String fileName) {
        return Files.exists(Paths.get(fileName));
    }
}
