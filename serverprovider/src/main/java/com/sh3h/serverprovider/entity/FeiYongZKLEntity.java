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
public class FeiYongZKLEntity {
	private String S_CID;
	private String S_JianHao;
	private int I_FeiYongDLBH;
	private double N_ZheKouL;

	public String getS_CID() {
		return S_CID;
	}

	public void setS_CID(String s_CID) {
		S_CID = s_CID;
	}

	public String getS_JianHao() {
		return S_JianHao;
	}

	public void setS_JianHao(String s_JianHao) {
		S_JianHao = s_JianHao;
	}

	public int getI_FeiYongDLBH() {
		return I_FeiYongDLBH;
	}

	public void setI_FeiYongDLBH(int i_FeiYongDLBH) {
		I_FeiYongDLBH = i_FeiYongDLBH;
	}

	public double getN_ZheKouL() {
		return N_ZheKouL;
	}

	public void setN_ZheKouL(double n_ZheKouL) {
		N_ZheKouL = n_ZheKouL;
	}

	/**
	 * 转换jsonobject对象为FeiYongZKLEntity
	 *
	 * @param object
	 * @return
	 */
	public static FeiYongZKLEntity fromJSON(JSONObject object) {
		FeiYongZKLEntity fyzkl = new FeiYongZKLEntity();

		fyzkl.setI_FeiYongDLBH(object.optInt("i_FeiYongDLBH"));
		fyzkl.setN_ZheKouL(object.optDouble("n_ZheKouL"));
		fyzkl.setS_CID(object.optString("s_CID"));
		fyzkl.setS_JianHao(object.optString("s_JianHao"));

		return fyzkl;
	}

	/**
	 * 转换JsonArray对象为FeiYongZKLEntity实体集合
	 *
	 * @param object
	 * @return
	 */
	public static List<FeiYongZKLEntity> fromJSONArray(JSONArray array)
			throws JSONException {
		List<FeiYongZKLEntity> list = new ArrayList<FeiYongZKLEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			FeiYongZKLEntity entity = FeiYongZKLEntity.fromJSON(object);
			list.add(entity);
		}
		return list;

	}

}
