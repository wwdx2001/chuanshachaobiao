package com.sh3h.meterreading.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

public class SystemUtil {
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getVersion(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getVersion(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return "";
        }
    }

    public static PackageInfo getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M,G为单位的容量
     */
    public static float getAvailableInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocksLong = statFs.getAvailableBlocksLong();
        }else {
            availableBlocksLong = statFs.getAvailableBlocks();
        }
        long blockSizeLong ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSizeLong = statFs.getBlockSizeLong();
        }else {
            blockSizeLong = statFs.getBlockSize();
        }
      float remainderSpace= (float) (availableBlocksLong * blockSizeLong)/(1024L*1024L*1024L);
        return remainderSpace;
//        return Formatter.formatFileSize(context, availableBlocksLong
//                * blockSizeLong);
    }


}
