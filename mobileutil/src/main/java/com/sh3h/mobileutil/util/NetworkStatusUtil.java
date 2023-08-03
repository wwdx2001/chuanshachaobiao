/**
 * @author panweixing
 */
package com.sh3h.mobileutil.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查网络状态
 */
public class NetworkStatusUtil {

	/**
	 * 没有网络
	 */
	public static int NETWORK_STATUS_NONE = -1;
	/**
	 * WIFI网络
	 */
	public static int NETWORK_STATUS_WIFI = 1;
	/**
	 * wap网络
	 */
	public static int NETWORK_STATUS_CMWAP = 2;
	/**
	 * net网络
	 */
	public static int NETWORK_STATUS_CMNET = 3;

	/**
	 * 检查网络是否连通
	 *
	 * @return True: 正常，False：未连接
	 */
	public static boolean isNetworkJudgment(Context context) {
		int state = NetworkStatusUtil.getNetworkType(context);
		return state == NETWORK_STATUS_NONE ? false : true;
	}

	/**
	 * 获取当前的网络状态
	 *
	 * @param context
	 * @return 没有网络: NETWORK_STATUS_NONE; WIFI: NETWORK_STATUS_WIFI; CMWAP:
	 *         NETWORK_STATUS_CMWAP; CMNET: NETWORK_STATUS_CMNET;
	 */
	public static int getNetworkType(Context context) {
		int networkType = NetworkStatusUtil.NETWORK_STATUS_NONE;

		ConnectivityManager conNetWorkMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = conNetWorkMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return networkType;
		}

		int mobileType = networkInfo.getType();
		if (mobileType == ConnectivityManager.TYPE_MOBILE) {
			// Log.e("UTIL", "networkInfo.getExtraInfo() is " +
			// networkInfo.getExtraInfo());
			// networkType =
			// networkInfo.getExtraInfo().equalsIgnoreCase("CMNET")
			// ? NetworkStatusUtil.NETWORK_STATUS_CMNET :
			// NetworkStatusUtil.NETWORK_STATUS_CMWAP;
			networkType = NetworkStatusUtil.NETWORK_STATUS_CMNET;
//			if (networkInfo.getExtraInfo().equals("CMNET")){
//				networkType = NetworkStatusUtil.NETWORK_STATUS_CMNET;
//			}else{
//				networkType = NetworkStatusUtil.NETWORK_STATUS_CMWAP;
//			} 

		} else if (mobileType == ConnectivityManager.TYPE_WIFI) {
			networkType = NetworkStatusUtil.NETWORK_STATUS_WIFI;
		}

		return networkType;
	}
}
