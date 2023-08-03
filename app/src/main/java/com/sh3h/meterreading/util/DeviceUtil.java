package com.sh3h.meterreading.util;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.sh3h.mobileutil.util.TextUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceUtil {
    private static String macAddress = null;
    private static String deviceId = null;

    /**
     * get mac address
     *
     * @return mac address
     */
    public static String getMacAddress() {
        if (!TextUtil.isNullOrEmpty(macAddress)) {
            return macAddress;
        }

        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macAddress = str.trim();
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return macAddress;
    }

    public static String getLocalMacAddress(Context context) {
        if (!TextUtil.isNullOrEmpty(macAddress)) {
            return macAddress;
        }

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        return macAddress;
    }

    /**
     * get device identification
     * @return device identification
     */
    public static String getDeviceID(Context context) {
        // 已加
        if (!TextUtil.isNullOrEmpty(deviceId)) {
            return deviceId;
        }

        // SharedPreferences sp = this.getSharedPreferences(SPF_UUID,
        // MODE_PRIVATE);
        // this._uuid = sp.getString(KEY_UUID, TextUtil.EMPTY);

        // 已生
        // if (!TextUtil.isNullOrEmpty(_uuid)) {
        // return this._uuid;
        // }
        // 生成
        deviceId = generateUUID(context);

        // Editor ed = sp.edit();
        // ed.putString(KEY_UUID, this._uuid);
        // ed.apply();
        // ed.commit();
        return deviceId;
    }

    private static String generateUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;

        tmDevice = tm.getDeviceId();
        if (!TextUtil.isNullOrEmpty(tmDevice) && !tmDevice.equals("null")) {
            return tmDevice;
        }

        tmSerial = tm.getSimSerialNumber();
        if (!TextUtil.isNullOrEmpty(tmSerial) && !tmSerial.equals("null")) {
            return tmSerial;
        }

        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        return androidId;

		/*
		 * tmSerial = "" + tm.getSimSerialNumber(); androidId = "" +
		 * android.provider.Settings.Secure.getString(getContentResolver(),
		 * android.provider.Settings.Secure.ANDROID_ID);
		 *
		 * UUID deviceUuid = new UUID(androidId.hashCode(), ((long)
		 * tmDevice.hashCode() << 32) | tmSerial.hashCode());
		 *
		 * return deviceUuid.toString();
		 */
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }
}
