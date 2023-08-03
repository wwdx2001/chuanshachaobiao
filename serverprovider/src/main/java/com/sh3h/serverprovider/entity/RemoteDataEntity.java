package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RemoteDataEntity {
	// 任务编号
	private int _RENWUBH;
	// 抄表ID
	private int _CHAOBIAOID;
	// 客户号
	private String _CID;
	// 抄见水量
	private int _ChaoJianSL;
	// 抄回抄码
	private int _CHAOHUICM;
	// 抄表状态编码
	private int _ZHUANGTAIBM;
	// 量高量低编码
	private int _LIANGGAOLDBM;
	// 抄表日期
	private long _ChaoBiaoRQ;
	// 0用水量说明(系统词语表41)
	private int _LINYONGSLSM;
	// 上传类型(0:抄表上传 1：开帐上传)
	private int _UploadType;

	// 0:抄表上传
	public static final int UPLOADTYPE_CBSC = 0;
	// 1：开帐上传
	public static final int UPLOADTYPE_KZSC = 1;

	public RemoteDataEntity() {

	}

	public void setRENWUBH(int RENWUBH) {
		_RENWUBH = RENWUBH;
	}

	public int getRENWUBH() {
		return _RENWUBH;
	}

	public void setCHAOBIAOID(int CHAOBIAOID) {
		_CHAOBIAOID = CHAOBIAOID;
	}

	public int getCHAOBIAOID() {
		return _CHAOBIAOID;
	}

	public void setCID(String CID) {
		_CID = CID;
	}

	public String getCID() {
		return _CID;
	}

	public void setChaoJianSL(int ChaoJianSL) {
		_ChaoJianSL = ChaoJianSL;
	}

	public int getChaoJianSL() {
		return _ChaoJianSL;
	}

	public void setCHAOHUICM(int CHAOHUICM) {
		_CHAOHUICM = CHAOHUICM;
	}

	public int getCHAOHUICM() {
		return _CHAOHUICM;
	}

	public void setZHUANGTAIBM(int ZHUANGTAIBM) {
		_ZHUANGTAIBM = ZHUANGTAIBM;
	}

	public int getZHUANGTAIBM() {
		return _ZHUANGTAIBM;
	}

	public void setLIANGGAOLDBM(int LIANGGAOLDBM) {
		_LIANGGAOLDBM = LIANGGAOLDBM;
	}

	public int getLIANGGAOLDBM() {
		return _LIANGGAOLDBM;
	}

	public void setChaoBiaoRQ(long ChaoBiaoRQ) {
		_ChaoBiaoRQ = ChaoBiaoRQ;
	}

	public long getChaoBiaoRQ() {
		return _ChaoBiaoRQ;
	}

	public void setLINYONGSLSM(int LINYONGSLSM) {
		_LINYONGSLSM = LINYONGSLSM;
	}

	public int getLINYONGSLSM() {
		return _LINYONGSLSM;
	}

	public void setUploadType(int UploadType) {
		_UploadType = UploadType;
	}

	public int getUploadType() {
		return _UploadType;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();

		try {
			obj.put("I_RENWUBH", getRENWUBH());
			obj.put("I_CHAOBIAOID", getCHAOBIAOID());
			obj.put("S_CID", getCID());
			obj.put("I_ChaoJianSL", getChaoJianSL());
			obj.put("I_CHAOHUICM", getCHAOHUICM());
			obj.put("I_ZHUANGTAIBM", getZHUANGTAIBM());
			obj.put("I_LIANGGAOLDBM", getLIANGGAOLDBM());
			obj.put("D_ChaoBiaoRQ", getChaoBiaoRQ());
			obj.put("I_LINYONGSLSM", getLINYONGSLSM());
			obj.put("I_UploadType", getUploadType());
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}

	public static JSONObject toJSON(RemoteDataEntity entity) {
		JSONObject obj = new JSONObject();

		try {
			obj.put("I_RENWUBH", entity.getRENWUBH());
			obj.put("I_CHAOBIAOID", entity.getCHAOBIAOID());
			obj.put("S_CID", entity.getCID());
			obj.put("I_ChaoJianSL", entity.getChaoJianSL());
			obj.put("I_CHAOHUICM", entity.getCHAOHUICM());
			obj.put("I_ZHUANGTAIBM", entity.getZHUANGTAIBM());
			obj.put("I_LIANGGAOLDBM", entity.getLIANGGAOLDBM());
			obj.put("D_ChaoBiaoRQ", entity.getChaoBiaoRQ());
			obj.put("I_LINYONGSLSM", entity.getLINYONGSLSM());
			obj.put("I_UploadType", entity.getUploadType());
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}

	public static JSONArray toJSONArray(List<RemoteDataEntity> list)
			throws JSONException {
		JSONArray array = new JSONArray();

		for (RemoteDataEntity entity : list) {
			JSONObject object = toJSON(entity);
			array.put(object);
		}

		return array;
	}
}
