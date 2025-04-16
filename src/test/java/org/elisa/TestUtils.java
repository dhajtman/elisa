package org.elisa;

import java.io.*;

public class TestUtils {
    public static final String UTF8_BOM = "\uFEFF";

    public static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }

    public static String getFirstLineOf(String filePath) throws IOException {
        File file = new File("src/main/resources" + filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.readLine();
    }
}
