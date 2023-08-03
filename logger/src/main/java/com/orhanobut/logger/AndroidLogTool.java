package com.orhanobut.logger;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidLogTool implements LogTool {
    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void w(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void v(String tag, String message) {
        Log.v(tag, message);
    }

    @Override
    public void wtf(String tag, String message) {
        Log.wtf(tag, message);
    }

/*    @Override
    public void initLogFile(String path) {
        filePath = path;
    }

    //打印信息
    private void printInfo(String tag, String message) {
        try {
            if (filePath == null) {
                return;
            }

            File file = new File(filePath);
            Writer writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()), 2048);
            SimpleDateFormat df = new SimpleDateFormat("[yy-MM-dd hh:mm:ss]: ", Locale.CHINESE);
            writer.write(df.format(new Date()));
            writer.write(tag);
            writer.write(message);
            writer.write("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断sdcard是否可用
    private static boolean isSDCardAvailable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                || Environment.getExternalStorageDirectory().exists()) {
            return true;
        } else {
            return false;
        }
    }
*/
}
