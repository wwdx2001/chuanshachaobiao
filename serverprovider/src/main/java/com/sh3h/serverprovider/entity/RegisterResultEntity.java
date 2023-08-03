/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONObject;

/**
 * 注册激活结果信息实体
 */
public class RegisterResultEntity {

	/**
	 * 是否激活成功
	 */
	private boolean _successed;

	/**
	 * 激活码
	 */
	private String _code;

	/**
	 * 返回信息
	 */
	private String _error;

	// / <summary>
	// / 激活状态
	// / </summary>
	private String _JiHuoZT;

	// / <summary>
	// / 激活时间
	// / </summary>
	private long _JiHuoSJ;

	// / <summary>
	// / 设备状态
	// / </summary>
	private String _SheBeiZT;

	/**
	 * 获取激活状态
	 *
	 * @return
	 */
	public String getJiHuoZT() {
		return _JiHuoZT;
	}

	/**
	 * 设置激活状态
	 *
	 * @param _JiHuoZT
	 */
	public void setJiHuoZT(String _JiHuoZT) {
		this._JiHuoZT = _JiHuoZT;
	}

	public long getJiHuoSJ() {
		return _JiHuoSJ;
	}

	/**
	 * 设置激活时间
	 *
	 * @param _JiHuoSJ
	 */
	public void setJiHuoSJ(long _JiHuoSJ) {
		this._JiHuoSJ = _JiHuoSJ;
	}

	public String getSheBeiZT() {
		return _SheBeiZT;
	}

	/**
	 * 设置设备状态
	 *
	 * @param _SheBeiZT
	 */
	public void setSheBeiZT(String _SheBeiZT) {
		this._SheBeiZT = _SheBeiZT;
	}

	public boolean getSuccessed() {
		return _successed;
	}

	public void setSuccessed(boolean successed) {
		_successed = successed;
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
	}

	public String getError() {
		return _error;
	}

	public void setError(String error) {
		this._error = error;
	}

	/**
	 * 转换JsonObject对象为RegisterInfo实体
	 *
	 * @param object
	 * @return
	 */
	public static RegisterResultEntity fromJson(JSONObject object) {
		RegisterResultEntity regis = new RegisterResultEntity();

		regis.setSuccessed(object.optBoolean("successed"));
		regis.setJiHuoSJ(object.optLong("jiHuoSJ"));
		regis.setJiHuoZT(object.optString("jiHuoZT"));
		regis.setSheBeiZT(object.optString("sheBeiZT"));

		if (regis.getSuccessed())
			regis.setCode(object.optString("code"));
		else
			regis.setError(object.optString("error"));
		return regis;
	}

}
