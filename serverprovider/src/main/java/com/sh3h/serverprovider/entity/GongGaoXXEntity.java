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
public class GongGaoXXEntity {
	private int _gongGaoBH;
	private String _gongGaoBT;
	private String _gongGaoNR;
	private String _faBuR;
	private long _faBuSJ;
	private String _jieShouR;
	private String _beiZhu;
	private int _siFouYD;

	/**
	 * @return the gongGaoBH
	 */
	public int getGongGaoBH() {
		return _gongGaoBH;
	}

	/**
	 * @param gongGaoBH
	 *            the gongGaoBH to set
	 */
	public void setGongGaoBH(int gongGaoBH) {
		this._gongGaoBH = gongGaoBH;
	}

	/**
	 * @return the gongGaoBT
	 */
	public String getGongGaoBT() {
		return _gongGaoBT;
	}

	/**
	 * @param gongGaoBT
	 *            the gongGaoBT to set
	 */
	public void setGongGaoBT(String gongGaoBT) {
		this._gongGaoBT = gongGaoBT;
	}

	/**
	 * @return the gongGaoNR
	 */
	public String getGongGaoNR() {
		return _gongGaoNR;
	}

	/**
	 * @param gongGaoNR
	 *            the gongGaoNR to set
	 */
	public void setGongGaoNR(String gongGaoNR) {
		this._gongGaoNR = gongGaoNR;
	}

	/**
	 * @return the faBuR
	 */
	public String getFaBuR() {
		return _faBuR;
	}

	/**
	 * @param faBuR
	 *            the faBuR to set
	 */
	public void setFaBuR(String faBuR) {
		this._faBuR = faBuR;
	}

	/**
	 * @return the faBuSJ
	 */
	public long getFaBuSJ() {
		return _faBuSJ;
	}

	/**
	 * @param faBuSJ
	 *            the faBuSJ to set
	 */
	public void setFaBuSJ(long faBuSJ) {
		this._faBuSJ = faBuSJ;
	}

	/**
	 * @return the jieShouR
	 */
	public String getJieShouR() {
		return _jieShouR;
	}

	/**
	 * @param jieShouR
	 *            the jieShouR to set
	 */
	public void setJieShouR(String jieShouR) {
		this._jieShouR = jieShouR;
	}

	/**
	 * @return the beiZhu
	 */
	public String getBeiZhu() {
		return _beiZhu;
	}

	/**
	 * @param beiZhu
	 *            the beiZhu to set
	 */
	public void setBeiZhu(String beiZhu) {
		this._beiZhu = beiZhu;
	}

	/**
	 * @return the siFouYD
	 */
	public int getSiFouYD() {
		return _siFouYD;
	}

	/**
	 * @param siFouYD
	 *            the siFouYD to set
	 */
	public void setSiFouYD(int siFouYD) {
		this._siFouYD = siFouYD;
	}

	/**
	 * 将JSONObject对象转换为GongGaoXXEntity实体
	 *
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static GongGaoXXEntity fromJSON(JSONObject object)
			throws JSONException {
		GongGaoXXEntity ggxx = new GongGaoXXEntity();
		if (object != null) {
			ggxx.setBeiZhu(object.optString("s_BeiZhu"));
			ggxx.setFaBuR(object.optString("s_FaBuR"));
			ggxx.setFaBuSJ(object.optLong("d_FaBuSJ"));
			ggxx.setGongGaoBH(object.optInt("i_GongGaoBH"));
			ggxx.setGongGaoBT(object.optString("s_GongGaoBT"));
			ggxx.setGongGaoNR(object.optString("s_GongGaoNR"));
			ggxx.setJieShouR(object.optString("s_JieShouR"));
			ggxx.setSiFouYD(object.optInt("i_SiFouYD"));
		}
		return ggxx;
	}

	/**
	 * 转换JsonArray对象为List<GongGaoXXEntity>实体
	 *
	 * @param object
	 * @return
	 */
	public static List<GongGaoXXEntity> fromJSONArray(JSONArray array)
			throws JSONException {

		List<GongGaoXXEntity> list = new ArrayList<GongGaoXXEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			GongGaoXXEntity gonggao = GongGaoXXEntity.fromJSON(object);
			list.add(gonggao);
		}
		return list;
	}

}
