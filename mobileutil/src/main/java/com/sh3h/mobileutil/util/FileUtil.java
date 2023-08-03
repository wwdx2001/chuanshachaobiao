package com.sh3h.mobileutil.util;

import java.io.File;
import java.util.Locale;

import android.os.Environment;

public class FileUtil {

	/**
	 * 判断是否插入sd卡
	 *
	 */
	public static boolean isSdCardExit() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	};

	/**
	 * 检查文件是否存在
	 *
	 * @param path
	 * @return true 存在 false 不存在
	 */
	public static boolean isFileExit(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 检查文件类型是否在允许的范围内，通过文件扩展名判断
	 *
	 * @param Name
	 *            要检查的文件名
	 * @param check
	 *            检查列表
	 * @return true 表示符合 false 表示不符合
	 */
	public static boolean allowedFileType(String Name, String[] check) {
		boolean isFile = true;
		String FileEnd = Name.substring(Name.lastIndexOf(".") + 1, Name.length()).toLowerCase(Locale.getDefault());
		for (int i = 0; i < check.length; i++) {
			isFile = FileEnd.equals(check[i]);
			if (isFile == false) {
				break;
			}
		}
		return isFile;
	}
}
