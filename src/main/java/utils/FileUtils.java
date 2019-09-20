package utils;

public class FileUtils {
    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
