/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONObject;

public class MeterStateEntity {

	private boolean isQianFei;
	private boolean isHuanBiaoZT;

	/**
	 * @return the isQianFei
	 */
	public boolean isQianFei() {
		return isQianFei;
	}

	/**
	 * @param isQianFei
	 *            the isQianFei to set
	 */
	public void setQianFei(boolean isQianFei) {
		this.isQianFei = isQianFei;
	}

	/**
	 * @return the isHuanBiaoZT
	 */
	public boolean isHuanBiaoZT() {
		return isHuanBiaoZT;
	}

	/**
	 * @param isHuanBiaoZT
	 *            the isHuanBiaoZT to set
	 */
	public void setHuanBiaoZT(boolean isHuanBiaoZT) {
		this.isHuanBiaoZT = isHuanBiaoZT;
	}

	/**
	 * 转换JsonObject对象为MeterStateEntity实体
	 *
	 * @param object
	 * @return
	 */
	public static MeterStateEntity fromJSON(JSONObject object) {
		MeterStateEntity meterstate = new MeterStateEntity();
		meterstate.setHuanBiaoZT(object.optBoolean("isHuanBiaoZT"));
		meterstate.setQianFei(object.optBoolean("isQianFei"));

		return meterstate;
	}
}
