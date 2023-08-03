package com.sh3h.meterreading.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 类说明：  apk 签名信息获取工具类
 * 
 * @author 	Cundong
 * @date 	2013-9-6
 * @version 1.0
 */
public class SignUtils {

	/**
	 * 获取未安装Apk的签名
	 * 
	 * @param apkPath
	 * @return
	 */
	public static String getUnInstalledApkSignature(Context context,String apkPath) {
		File apkFile = new File(apkPath);
		String sign = "";
		if (apkFile != null && apkFile.exists()) {
			PackageManager pm = context.getPackageManager();
			PackageInfo pkgInfo = pm.getPackageArchiveInfo(
					apkFile.getAbsolutePath(), PackageManager.GET_SIGNATURES);
			if (pkgInfo != null && pkgInfo.signatures != null
					&& pkgInfo.signatures.length > 0) {
				sign = pkgInfo.signatures[0].toCharsString();
			}
		}
		return sign;
	}

	/**
	 * 获取已安装apk签名
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getInstalledApkSignature(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> apps = pm
				.getInstalledPackages(PackageManager.GET_SIGNATURES);

		Iterator<PackageInfo> iter = apps.iterator();
		while (iter.hasNext()) {
			PackageInfo packageinfo = iter.next();
			String thisName = packageinfo.packageName;
			if (thisName.equals(packageName)) {
				return packageinfo.signatures[0].toCharsString();
			}
		}

		return null;
	}
}