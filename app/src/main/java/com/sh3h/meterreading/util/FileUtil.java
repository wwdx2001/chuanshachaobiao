package com.sh3h.meterreading.util;

import java.io.File;

/**
 * 文件操作
 * Created by libao on 2018/7/19.
 */

public class FileUtil {

    /**
     * 删除文件夹下面的所有文件
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
}
