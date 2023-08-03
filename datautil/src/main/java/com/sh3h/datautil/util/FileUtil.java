package com.sh3h.datautil.util;


import java.io.File;

public class FileUtil {
    /**
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }

                for (File f : childFile) {
                    deleteFile(f);
                }

                file.delete();
            }
        }
    }
}
