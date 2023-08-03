package com.sh3h.mobileutil.util;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class ApplicationsUtil {

    /**
     * 消息提示
     *
     * @param message
     *            消息
     */
    public static void showMessage(Context context, int resId) {
        showMessage(context, context.getString(resId));
    }

    /**
     * 消息提示
     *
     * @param message
     *            消息
     */
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // 单击时间限制, 默认1秒
    private static long _timeLimit = 1000;
    // 最后一次点击时间
    private static long _lastedCheckTime = System.currentTimeMillis()
            - _timeLimit;

    /**
     * 检查是否是重复点击，重复点击的定义是1000毫秒内第二之后都算重复。
     *
     * @return
     */
    public static boolean isFastClick() {
        long now = System.currentTimeMillis();

        boolean flag = false;
        //if (now - _lastedCheckTime < _timeLimit) {
        //	flag = true;
        //} else {
        //	_lastedCheckTime = now;
        //}
        return flag;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter(){

                @Override
                public boolean accept(File pathname) {
                    //Check if filename is "cpu", followed by a single digit number
                    if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            });

            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Default to return 1 core
            return 1;
        }
    }
}
