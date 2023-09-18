package is.infostms.isc.util;

import java.io.*;
import java.nio.file.Files;

public class FileUtils {

    public static void copy(File f1, File f2) {
        try (InputStream is = new FileInputStream(f1);
             OutputStream os = new FileOutputStream(f2)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException ignored) {
        }
    }
}
