package org.infoHandling.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static String readFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        // Read with UTF-8 encoding explicitly
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    // Alternative method with different encoding
    public static String readFileContent(String filePath, String encoding) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, encoding);
    }

    // Method to read file and handle encoding errors
    public static String readFileContentSafe(String filePath) {
        try {
            // Try UTF-8 first
            return readFileContent(filePath);
        } catch (Exception e1) {
            try {
                // Try Windows-1251 (Cyrillic)
                return readFileContent(filePath, "Windows-1251");
            } catch (Exception e2) {
                try {
                    // Try ISO-8859-1
                    return readFileContent(filePath, "ISO-8859-1");
                } catch (Exception e3) {
                    try {
                        // Try default charset
                        Path path = Paths.get(filePath);
                        return Files.readString(path);
                    } catch (Exception e4) {
                        System.err.println("Failed to read file: " + e4.getMessage());
                        return null;
                    }
                }
            }
        }
    }
}