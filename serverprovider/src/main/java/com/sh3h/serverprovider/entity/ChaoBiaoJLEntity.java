/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChaoBiaoJLEntity {
	/**
	 * 抄表任务id，自增长ID
	 */
	private int _id;

	/**
	 * 用户号
	 */
	private String _cId;

	/**
	 * 抄表年
	 */
	private int _chaoBiaoN;

	/**
	 * 抄表月
	 */
	private int _chaoBiaoY;

	/**
	 * 抄次
	 */
	private int _chaoCi;

	/**
	 * 抄表日期
	 */
	private long _chaoBiaoRQ;

	/**
	 * 上次抄码
	 */
	private int _shangCiCM;

	/**
	 * 本次抄码
	 */
	private int _benCiCM;

	/**
	 * 抄表状态名称
	 */
	private String _chaoBiaoZT;

	/**
	 * 抄见水量
	 */
	private int _chaoJianSL;

	/**
	 * 抄表备注
	 */
	private String _chaoBiaoBZ;

	/**
	 * 抄表状态编码
	 */
	private int _CHAOBIAOZTBM;

	/**
	 * 量高量低原因编码
	 */
	private int _LIANGGAOLDYYBM;

	private String S_ChaoBiaoY;

	public String getS_ChaoBiaoY() {
		return S_ChaoBiaoY;
	}

	public void setS_ChaoBiaoY(String s_ChaoBiaoY) {
		S_ChaoBiaoY = s_ChaoBiaoY;
	}

	public int get_CHAOBIAOZTBM() {
		return _CHAOBIAOZTBM;
	}

	public void set_CHAOBIAOZTBM(int _CHAOBIAOZTBM) {
		this._CHAOBIAOZTBM = _CHAOBIAOZTBM;
	}

	public int get_LIANGGAOLDYYBM() {
		return _LIANGGAOLDYYBM;
	}

	public void set_LIANGGAOLDYYBM(int _LIANGGAOLDYYBM) {
		this._LIANGGAOLDYYBM = _LIANGGAOLDYYBM;
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getcId() {
		return _cId;
	}

	public void setcId(String cId) {
		this._cId = cId;
	}

	public int getChaoBiaoN() {
		return _chaoBiaoN;
	}

	public void setChaoBiaoN(int chaoBiaoN) {
		this._chaoBiaoN = chaoBiaoN;
	}

	public int getChaoBiaoY() {
		return _chaoBiaoY;
	}

	public void setChaoBiaoY(int chaoBiaoY) {
		this._chaoBiaoY = chaoBiaoY;
	}

	public int getChaoCi() {
		return _chaoCi;
	}

	public void setChaoCi(int chaoCi) {
		this._chaoCi = chaoCi;
	}

	public long getChaoBiaoRQ() {
		return _chaoBiaoRQ;
	}

	public void setChaoBiaoRQ(long chaoBiaoRQ) {
		this._chaoBiaoRQ = chaoBiaoRQ;
	}

	public int getShangCiCM() {
		return _shangCiCM;
	}

	public void setShangCiCM(int shangCiCM) {
		this._shangCiCM = shangCiCM;
	}

	public int getBenCiCM() {
		return _benCiCM;
	}

	public void setBenCiCM(int benCiCM) {
		this._benCiCM = benCiCM;
	}

	public String getChaoBiaoZT() {
		return _chaoBiaoZT;
	}

	public void setChaoBiaoZT(String chaoBiaoZT) {
		this._chaoBiaoZT = chaoBiaoZT;
	}

	public int getChaoJianSL() {
		return _chaoJianSL;
	}

	public void setChaoJianSL(int chaoJianSL) {
		this._chaoJianSL = chaoJianSL;
	}

	public String getChaoBiaoBZ() {
		return _chaoBiaoBZ;
	}

	public void setChaoBiaoBZ(String chaoBiaoBZ) {
		this._chaoBiaoBZ = chaoBiaoBZ;
	}

	/**
	 * 转换JsonObject对象为ChaoBiaoXXEntity实体
	 *
	 * @param object
	 * @return
	 */
	public static ChaoBiaoJLEntity fromJSON(JSONObject object) {
		ChaoBiaoJLEntity chaobiaojl = new ChaoBiaoJLEntity();

		chaobiaojl.set_CHAOBIAOZTBM(object.optInt("i_CHAOBIAOZTBM"));
		chaobiaojl.set_LIANGGAOLDYYBM(object.optInt("i_LIANGGAOLDYYBM"));
		chaobiaojl.setBenCiCM(object.optInt("i_BenCiCM"));
		chaobiaojl.setChaoBiaoBZ(object.optString("s_CHAOBIAOBZ"));
		chaobiaojl.setChaoBiaoN(object.optInt("i_CHAOBIAON"));
		chaobiaojl.setChaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
		chaobiaojl.setChaoBiaoY(object.optInt("i_CHAOBIAOY"));
		chaobiaojl.setChaoBiaoZT(object.optString("s_ChaoBiaoZT"));
		chaobiaojl.setChaoCi(object.optInt("i_ChaoCi"));
		chaobiaojl.setChaoJianSL(object.optInt("i_CHAOJIANSL"));
		chaobiaojl.setcId(object.optString("s_CID"));
		chaobiaojl.setId(object.optInt("iD"));
		chaobiaojl.setShangCiCM(object.optInt("i_ShangCiCM"));
		chaobiaojl.setS_ChaoBiaoY(object.optString("s_ChaoBiaoY"));

		return chaobiaojl;
	}

	/**
	 * 转换JsonArray对象为ChaoBiaoXXEntity实体集合
	 *
	 * @param object
	 * @return
	 */
	public static List<ChaoBiaoJLEntity> fromJSONArray(JSONArray array)
			throws JSONException {

		List<ChaoBiaoJLEntity> list = new ArrayList<ChaoBiaoJLEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			ChaoBiaoJLEntity entity = ChaoBiaoJLEntity.fromJSON(object);
			list.add(entity);
		}
		return list;
	}
}
