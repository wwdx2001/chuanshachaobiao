/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设备信息
 */
public class DeviceInfoEntity {
	// / <summary>
	// / 设备编号
	// / </summary>
	private String _DeviceID;

	public String getDeviceID() {
		return _DeviceID;
	}

	/**
	 * 设置设备编号
	 *
	 * @param _DeviceID
	 */
	public void setDeviceID(String _DeviceID) {
		this._DeviceID = _DeviceID;
	}

	private String _mACAddress;

	/**
	 * 获取MAC地址
	 *
	 * @return
	 */
	public String getMACAddress() {
		return _mACAddress;
	}

	/**
	 * 设置MAC地址
	 *
	 * @param mACAddress
	 */
	public void setMACAddress(String mACAddress) {
		_mACAddress = mACAddress;
	}

	public JSONObject toJson() {
		JSONObject object = new JSONObject();
		try {
			object.put("DeviceID", getDeviceID());
			object.put("MACAddress", getMACAddress());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
}
