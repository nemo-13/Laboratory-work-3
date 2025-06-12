package util;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class FileUtils {
    public static boolean hasValidExtension(File file, String... extensions) {
        if (file == null) return false;
        String fileName = file.getName().toLowerCase();
        return Arrays.stream(extensions)
                .anyMatch(ext -> fileName.endsWith("." + ext.toLowerCase()));
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }
}