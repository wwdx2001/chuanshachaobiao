/**
 *
 */
package com.sh3h.serverprovider.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author liukaiyu
 *
 */
public class JiaoFeiXXEntity {
	private String _cId;
	private int _zhangWuNY;
	private int _feEID;
	private long _chaoBiaoRQ;
	private long _kaiZhangRQ;
	private long _shouFeiRQ;
	private double _jE;
	private double _shiShouWYJ;
	private double _shiShouZJE;
	private String _shouFeiTJ;

	/**
	 * 构造函数
	 */
	public JiaoFeiXXEntity() {

	}

	/**
	 * @return the cId
	 */
	public String getcId() {
		return _cId;
	}

	/**
	 * @param cId
	 *            the cId to set
	 */
	public void setcId(String cId) {
		this._cId = cId;
	}

	/**
	 * @return the zhangWuNY
	 */
	public int getZhangWuNY() {
		return _zhangWuNY;
	}

	/**
	 * @param zhangWuNY
	 *            the zhangWuNY to set
	 */
	public void setZhangWuNY(int zhangWuNY) {
		this._zhangWuNY = zhangWuNY;
	}

	/**
	 * @return the feEID
	 */
	public int getFeEID() {
		return _feEID;
	}

	/**
	 * @param feEID
	 *            the feEID to set
	 */
	public void setFeEID(int feEID) {
		this._feEID = feEID;
	}

	/**
	 * @return the chaoBiaoRQ
	 */
	public long getChaoBiaoRQ() {
		return _chaoBiaoRQ;
	}

	/**
	 * @param chaoBiaoRQ
	 *            the chaoBiaoRQ to set
	 */
	public void setChaoBiaoRQ(long chaoBiaoRQ) {
		this._chaoBiaoRQ = chaoBiaoRQ;
	}

	/**
	 * @return the kaiZhangRQ
	 */
	public long getKaiZhangRQ() {
		return _kaiZhangRQ;
	}

	/**
	 * @param kaiZhangRQ
	 *            the kaiZhangRQ to set
	 */
	public void setKaiZhangRQ(long kaiZhangRQ) {
		this._kaiZhangRQ = kaiZhangRQ;
	}

	/**
	 * @return the shouFeiRQ
	 */
	public long getShouFeiRQ() {
		return _shouFeiRQ;
	}

	/**
	 * @param shouFeiRQ
	 *            the shouFeiRQ to set
	 */
	public void setShouFeiRQ(long shouFeiRQ) {
		this._shouFeiRQ = shouFeiRQ;
	}

	/**
	 * @return the jE
	 */
	public double getjE() {
		return _jE;
	}

	/**
	 * @param jE
	 *            the jE to set
	 */
	public void setjE(double jE) {
		this._jE = jE;
	}

	/**
	 * @return the shiShouWYJ
	 */
	public double getShiShouWYJ() {
		return _shiShouWYJ;
	}

	/**
	 * @param shiShouWYJ
	 *            the shiShouWYJ to set
	 */
	public void setShiShouWYJ(double shiShouWYJ) {
		this._shiShouWYJ = shiShouWYJ;
	}

	/**
	 * @return the shiShouZJE
	 */
	public double getShiShouZJE() {
		return _shiShouZJE;
	}

	/**
	 * @param shiShouZJE
	 *            the shiShouZJE to set
	 */
	public void setShiShouZJE(double shiShouZJE) {
		this._shiShouZJE = shiShouZJE;
	}

	/**
	 * @return the shouFeiTJ
	 */
	public String getShouFeiTJ() {
		return _shouFeiTJ;
	}

	/**
	 * @param shouFeiTJ
	 *            the shouFeiTJ to set
	 */
	public void setShouFeiTJ(String shouFeiTJ) {
		this._shouFeiTJ = shouFeiTJ;
	}

	/**
	 * 转换JsonObject对象为JiaoFeiXXEntity实体
	 *
	 * @param object
	 * @return
	 */
	public static JiaoFeiXXEntity fromJSON(JSONObject object) {
		JiaoFeiXXEntity jiaofei = new JiaoFeiXXEntity();
		jiaofei.setChaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
		jiaofei.setcId(object.optString("s_CID"));
		jiaofei.setFeEID(object.optInt("i_FEEID"));
		jiaofei.setjE(object.optDouble("n_JE"));
		jiaofei.setKaiZhangRQ(object.optLong("d_KAIZHANGRQ"));
		jiaofei.setShiShouWYJ(object.optDouble("n_SHISHOUWYJ"));
		jiaofei.setShiShouZJE(object.optDouble("n_SHISHOUZJE"));
		jiaofei.setShouFeiRQ(object.optLong("d_SHOUFEIRQ"));
		jiaofei.setShouFeiTJ(object.optString("s_SHOUFEITJ"));
		jiaofei.setZhangWuNY(object.optInt("i_ZhangWuNY"));

		return jiaofei;
	}

	/**
	 * 转换JsonArray对象为List<JiaoFeiXXEntity>实体
	 *
	 * @param object
	 * @return
	 */
	public static List<JiaoFeiXXEntity> fromJSONArray(JSONArray array)
			throws JSONException {

		List<JiaoFeiXXEntity> list = new ArrayList<JiaoFeiXXEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			JiaoFeiXXEntity jiaofei = JiaoFeiXXEntity.fromJSON(object);
			list.add(jiaofei);
		}
		return list;
	}
}
