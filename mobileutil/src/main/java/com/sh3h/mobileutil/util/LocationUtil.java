/**
 *
 */
package com.sh3h.mobileutil.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengdezhi
 *
 */
public class LocationUtil {

	public static final String PARAM = "CORPCODE: ZLSGS";
	public static final String METHOD = "getPositionByIdentifier";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";

	private static final String URL = "http://xsgjinter.doone.com.cn:8082/ws/lcploc.php?";

	/**
	 *
	 */
	public LocationUtil() {

	}

	public static String getLocationUtil(String mbn) {
		// 拼接josn
		StringBuilder parameters = new StringBuilder();
		parameters.append("{");
		parameters.append("mbn:").append("\"").append(mbn).append("\"")
				.append(",");
		parameters.append("corpcode:").append("\"").append("zlsgs")
				.append("\"");
		parameters.append("}");
		String data = null;
		try {
			URL url = null;
			InputStream is = null;
			String path = URL + "method=getPositionByIdentifier&param="
					+ parameters.toString();
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();

			data = createString(is);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Map<String, Object> getLocation(final String mbn) {

		HashMap<String, Object> result = null;

		String data = getLocationUtil(mbn);

		if (data != null) {
			String[] location = data.split(",");
			if (location != null && location.length == 2) {
				result = new HashMap<String, Object>();

				result.put(LONGITUDE,
						Double.valueOf(tryPaserDouble(location[0], 0)));
				result.put(LATITUDE,
						Double.valueOf(tryPaserDouble(location[1], 0)));
			}
		}

		return result;
	}

	/**
	 * 判断是否为数字
	 *
	 * @param value
	 *            字符串
	 * @return boolean
	 */
	public static double tryPaserDouble(String value, double defaultValue) {

		double result = defaultValue;

		try {
			result = Double.parseDouble(value);
		} catch (NumberFormatException e) {
		}

		return result;
	}

	private static String createString(InputStream is) throws Exception {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		byte[] data = bos.toByteArray();
		String str = new String(data);

		return str;
	}

}
