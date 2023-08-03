/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * KeepAliveInfo
 */
public class KeepAliveInfoEntity {

	// / <summary>
	// / 用户编号
	// / </summary>
	private String _userID;
	// / <summary>
	// / 设备编号
	// / </summary>
	private String _deviceID;
	// / <summary>
	// / 应用程序版本
	// / </summary>
	private String _appVersion;
	// / <summary>
	// / 数据版本
	// / </summary>
	private String _dataVersion;

	// / <summary>
	// / 发送呼吸包时间
	// / </summary>
	private long SendTime;

	// / <summary>
	// / X坐标
	// / </summary>
	private String S_X;

	// / <summary>
	// / Y坐标
	// / </summary>
	private String S_Y;

	public KeepAliveInfoEntity() {
	}

	public KeepAliveInfoEntity(String _userID,
							   String _deviceID,
							   String _appVersion,
							   String _dataVersion,
							   long sendTime,
							   String s_X,
							   String s_Y) {
		this._userID = _userID;
		this._deviceID = _deviceID;
		this._appVersion = _appVersion;
		this._dataVersion = _dataVersion;
		SendTime = sendTime;
		S_X = s_X;
		S_Y = s_Y;
	}

	public long getSendTime() {
		return SendTime;
	}

	public void setSendTime(long sendTime) {
		SendTime = sendTime;
	}

	public String getS_X() {
		return S_X;
	}

	public void setS_X(String s_X) {
		S_X = s_X;
	}

	public String getS_Y() {
		return S_Y;
	}

	public void setS_Y(String s_Y) {
		S_Y = s_Y;
	}

	public String getUserID() {
		return _userID;
	}

	public void setUserID(String userID) {
		_userID = userID;
	}

	public String getDeviceID() {
		return _deviceID;
	}

	public void setDeviceID(String deviceID) {
		_deviceID = deviceID;
	}

	public String getAppVersion() {
		return _appVersion;
	}

	public void setAppVersion(String appVersion) {
		_appVersion = appVersion;
	}

	public String getDataVersion() {
		return _dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		_dataVersion = dataVersion;
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();

		try {
			result.put("UserID", getUserID());
			result.put("DeviceID", getDeviceID());
			result.put("AppVersion", getAppVersion());
			result.put("DataVersion", getDataVersion());
			result.put("SendTime", getSendTime());
			result.put("S_X", getS_X());
			result.put("S_Y", getS_Y());

		} catch (JSONException e) {
			return null;
		}

		return result;
	}

}
