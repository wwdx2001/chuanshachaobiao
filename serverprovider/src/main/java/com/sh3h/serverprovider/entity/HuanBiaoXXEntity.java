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

public class HuanBiaoXXEntity {

	private String S_CID;
	private String S_BiaoWeiXX;
	private String S_DengJiR;
	private long D_DengJiRQ;
	private String S_HuanBiaoLX;
	private String S_HuanBiaoYY;
	private String S_JiubiaoBH;
	private String S_JiuBiaoGYH;
	private String S_JiuBiaoCJ;
	private String S_XinBiaoBH;
	private String S_XinBiaoGYH;
	private String S_XinBiaoCJ;
	private String S_JiuBiaoXH;
	private String S_XinBiaoXH;
	private String S_JiuBiaoKJ;
	private String S_XinBiaoKJ;
	private int I_XinBiaoLC;
	private int I_ShangCiCM;
	private int I_JiuBiaoCM;
	private int I_XinBiaoDM;
	private long D_ShiGongRQ;
	private String S_HuiTianR;
	private long D_HuanBiaoHTRQ;
	private String S_HuanBiaoZT;
	private String S_BeiZhu;

	public String getS_CID() {
		return S_CID;
	}

	public void setS_CID(String s_CID) {
		S_CID = s_CID;
	}

	public String getS_BiaoWeiXX() {
		return S_BiaoWeiXX;
	}

	public void setS_BiaoWeiXX(String s_BiaoWeiXX) {
		S_BiaoWeiXX = s_BiaoWeiXX;
	}

	public String getS_DengJiR() {
		return S_DengJiR;
	}

	public void setS_DengJiR(String s_DengJiRBH) {
		S_DengJiR = s_DengJiRBH;
	}

	public long getD_DengJiRQ() {
		return D_DengJiRQ;
	}

	public void setD_DengJiRQ(long d_DengJiRQ) {
		D_DengJiRQ = d_DengJiRQ;
	}

	public String getS_HuanBiaoLX() {
		return S_HuanBiaoLX;
	}

	public void setS_HuanBiaoLX(String s_HuanBiaoLX) {
		S_HuanBiaoLX = s_HuanBiaoLX;
	}

	public String getS_HuanBiaoYY() {
		return S_HuanBiaoYY;
	}

	public void setS_HuanBiaoYY(String s_HuanBiaoYY) {
		S_HuanBiaoYY = s_HuanBiaoYY;
	}

	public String getS_JiubiaoBH() {
		return S_JiubiaoBH;
	}

	public void setS_JiubiaoBH(String s_JiubiaoBH) {
		S_JiubiaoBH = s_JiubiaoBH;
	}

	public String getS_JiuBiaoGYH() {
		return S_JiuBiaoGYH;
	}

	public void setS_JiuBiaoGYH(String s_JiuBiaoGYH) {
		S_JiuBiaoGYH = s_JiuBiaoGYH;
	}

	public String getS_JiuBiaoCJ() {
		return S_JiuBiaoCJ;
	}

	public void setS_JiuBiaoCJ(String s_JiuBiaoCJ) {
		S_JiuBiaoCJ = s_JiuBiaoCJ;
	}

	public String getS_XinBiaoBH() {
		return S_XinBiaoBH;
	}

	public void setS_XinBiaoBH(String s_XinBiaoBH) {
		S_XinBiaoBH = s_XinBiaoBH;
	}

	public String getS_XinBiaoGYH() {
		return S_XinBiaoGYH;
	}

	public void setS_XinBiaoGYH(String s_XinBiaoGYH) {
		S_XinBiaoGYH = s_XinBiaoGYH;
	}

	public String getS_XinBiaoCJ() {
		return S_XinBiaoCJ;
	}

	public void setS_XinBiaoCJ(String s_XinBiaoCJ) {
		S_XinBiaoCJ = s_XinBiaoCJ;
	}

	public String getS_JiuBiaoXH() {
		return S_JiuBiaoXH;
	}

	public void setS_JiuBiaoXH(String s_JiuBiaoXH) {
		S_JiuBiaoXH = s_JiuBiaoXH;
	}

	public String getS_XinBiaoXH() {
		return S_XinBiaoXH;
	}

	public void setS_XinBiaoXH(String s_XinBiaoXH) {
		S_XinBiaoXH = s_XinBiaoXH;
	}

	public String getS_JiuBiaoKJ() {
		return S_JiuBiaoKJ;
	}

	public void setS_JiuBiaoKJ(String s_JiuBiaoKJ) {
		S_JiuBiaoKJ = s_JiuBiaoKJ;
	}

	public String getS_XinBiaoKJ() {
		return S_XinBiaoKJ;
	}

	public void setS_XinBiaoKJ(String s_XinBiaoKJ) {
		S_XinBiaoKJ = s_XinBiaoKJ;
	}

	public int getI_XinBiaoLC() {
		return I_XinBiaoLC;
	}

	public void setI_XinBiaoLC(int i_XinBiaoLC) {
		I_XinBiaoLC = i_XinBiaoLC;
	}

	public int getI_ShangCiCM() {
		return I_ShangCiCM;
	}

	public void setI_ShangCiCM(int i_ShangCiCM) {
		I_ShangCiCM = i_ShangCiCM;
	}

	public int getI_JiuBiaoCM() {
		return I_JiuBiaoCM;
	}

	public void setI_JiuBiaoCM(int i_JiuBiaoCM) {
		I_JiuBiaoCM = i_JiuBiaoCM;
	}

	public int getI_XinBiaoDM() {
		return I_XinBiaoDM;
	}

	public void setI_XinBiaoDM(int i_XinBiaoDM) {
		I_XinBiaoDM = i_XinBiaoDM;
	}

	public long getD_ShiGongRQ() {
		return D_ShiGongRQ;
	}

	public void setD_ShiGongRQ(long d_ShiGongRQ) {
		D_ShiGongRQ = d_ShiGongRQ;
	}

	public String getS_HuiTianR() {
		return S_HuiTianR;
	}

	public void setS_HuiTianR(String s_HuiTianRBH) {
		S_HuiTianR = s_HuiTianRBH;
	}

	public long getD_HuanBiaoHTRQ() {
		return D_HuanBiaoHTRQ;
	}

	public void setD_HuanBiaoHTRQ(long d_HuanBiaoHTRQ) {
		D_HuanBiaoHTRQ = d_HuanBiaoHTRQ;
	}

	public String getS_HuanBiaoZT() {
		return S_HuanBiaoZT;
	}

	public void setS_HuanBiaoZT(String s_HuanBiaoZT) {
		S_HuanBiaoZT = s_HuanBiaoZT;
	}

	public String getS_BeiZhu() {
		return S_BeiZhu;
	}

	public void setS_BeiZhu(String s_BeiZhu) {
		S_BeiZhu = s_BeiZhu;
	}

	/**
	 * 转换JsonObject对象为HuanBiaoXXEntity实体
	 *
	 * @param object
	 * @return
	 */
	public static HuanBiaoXXEntity fromJSON(JSONObject object) {
		HuanBiaoXXEntity huanbiaoxx = new HuanBiaoXXEntity();

		huanbiaoxx.setD_DengJiRQ(object.optLong("d_DengJiRQ"));
		huanbiaoxx.setD_HuanBiaoHTRQ(object.optLong("d_HuanBiaoHTRQ"));
		huanbiaoxx.setD_ShiGongRQ(object.optLong("d_ShiGongRQ"));
		huanbiaoxx.setI_JiuBiaoCM(object.optInt("i_JiuBiaoCM"));
		huanbiaoxx.setI_ShangCiCM(object.optInt("i_ShangCiCM"));
		huanbiaoxx.setI_XinBiaoDM(object.optInt("i_XinBiaoDM"));
		huanbiaoxx.setI_XinBiaoLC(object.optInt("i_XinBiaoLC"));
		huanbiaoxx.setS_BeiZhu(object.optString("s_BeiZhu"));
		huanbiaoxx.setS_BiaoWeiXX(object.optString("s_BiaoWeiXX"));
		huanbiaoxx.setS_CID(object.optString("s_CID"));
		huanbiaoxx.setS_DengJiR(object.optString("s_DengJiR"));
		huanbiaoxx.setS_HuanBiaoLX(object.optString("s_HuanBiaoLX"));
		huanbiaoxx.setS_HuanBiaoYY(object.optString("s_HuanBiaoYY"));
		huanbiaoxx.setS_HuanBiaoZT(object.optString("s_HuanBiaoZT"));
		huanbiaoxx.setS_HuiTianR(object.optString("s_HuiTianR"));
		huanbiaoxx.setS_JiubiaoBH(object.optString("s_JiubiaoBH"));
		huanbiaoxx.setS_JiuBiaoCJ(object.optString("s_JiuBiaoCJ"));
		huanbiaoxx.setS_JiuBiaoGYH(object.optString("s_JiuBiaoGYH"));
		huanbiaoxx.setS_JiuBiaoKJ(object.optString("s_JiuBiaoKJ"));
		huanbiaoxx.setS_JiuBiaoXH(object.optString("s_JiuBiaoXH"));
		huanbiaoxx.setS_XinBiaoBH(object.optString("s_XinBiaoBH"));
		huanbiaoxx.setS_XinBiaoCJ(object.optString("s_XinBiaoCJ"));
		huanbiaoxx.setS_XinBiaoGYH(object.optString("s_XinBiaoGYH"));
		huanbiaoxx.setS_XinBiaoKJ(object.optString("s_XinBiaoKJ"));
		huanbiaoxx.setS_XinBiaoXH(object.optString("s_XinBiaoXH"));

		return huanbiaoxx;
	}

	/**
	 * 转换JsonArray对象为HuanBiaoXXEntity实体集合
	 *
	 * @param object
	 * @return
	 */
	public static List<HuanBiaoXXEntity> fromJSONArray(JSONArray array)
			throws JSONException {

		List<HuanBiaoXXEntity> list = new ArrayList<HuanBiaoXXEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			HuanBiaoXXEntity entity = HuanBiaoXXEntity.fromJSON(object);
			list.add(entity);
		}
		return list;
	}
}
