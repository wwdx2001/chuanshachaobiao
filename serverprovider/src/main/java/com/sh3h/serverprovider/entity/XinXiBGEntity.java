package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class XinXiBGEntity {
	private int ID;
	private String S_CH;
	private String S_CID;
	private String S_KeHuBH;
	private String S_KeHuMC;
	private String S_ST;
	private String S_DiZhi;
	private String S_LianXiR;
	private String S_LIANXISJ;
	private String S_LianXiDH;
	private String S_ShouFeiFS;
	private String S_JianHao;
	private String S_JianHaoMC;
	private String S_BiaoWei;
	private String S_BEIZHU;
	private long D_ShenQingRQ;
	private String S_ShenQingR;

	public long getD_ShenQingRQ() {
		return D_ShenQingRQ;
	}

	public void setD_ShenQingRQ(long d_ShenQingRQ) {
		D_ShenQingRQ = d_ShenQingRQ;
	}

	public String getS_ShenQingR() {
		return S_ShenQingR;
	}

	public void setS_ShenQingR(String s_ShenQingR) {
		S_ShenQingR = s_ShenQingR;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getS_CH() {
		return S_CH;
	}

	public void setS_CH(String s_CH) {
		S_CH = s_CH;
	}

	public String getS_CID() {
		return S_CID;
	}

	public void setS_CID(String s_CID) {
		S_CID = s_CID;
	}

	public String getS_KeHuBH() {
		return S_KeHuBH;
	}

	public void setS_KeHuBH(String s_KeHuBH) {
		S_KeHuBH = s_KeHuBH;
	}

	public String getS_KeHuMC() {
		return S_KeHuMC;
	}

	public void setS_KeHuMC(String s_KeHuMC) {
		S_KeHuMC = s_KeHuMC;
	}

	public String getS_ST() {
		return S_ST;
	}

	public void setS_ST(String s_ST) {
		S_ST = s_ST;
	}

	public String getS_DiZhi() {
		return S_DiZhi;
	}

	public void setS_DiZhi(String s_DiZhi) {
		S_DiZhi = s_DiZhi;
	}

	public String getS_LianXiR() {
		return S_LianXiR;
	}

	public void setS_LianXiR(String s_LianXiR) {
		S_LianXiR = s_LianXiR;
	}

	public String getS_LIANXISJ() {
		return S_LIANXISJ;
	}

	public void setS_LIANXISJ(String s_LIANXISJ) {
		S_LIANXISJ = s_LIANXISJ;
	}

	public String getS_LianXiDH() {
		return S_LianXiDH;
	}

	public void setS_LianXiDH(String s_LianXiDH) {
		S_LianXiDH = s_LianXiDH;
	}

	public String getS_ShouFeiFS() {
		return S_ShouFeiFS;
	}

	public void setS_ShouFeiFS(String s_ShouFeiFS) {
		S_ShouFeiFS = s_ShouFeiFS;
	}

	public String getS_JianHao() {
		return S_JianHao;
	}

	public void setS_JianHao(String s_JianHao) {
		S_JianHao = s_JianHao;
	}

	public String getS_JianHaoMC() {
		return S_JianHaoMC;
	}

	public void setS_JianHaoMC(String s_JianHaoMC) {
		S_JianHaoMC = s_JianHaoMC;
	}

	public String getS_BiaoWei() {
		return S_BiaoWei;
	}

	public void setS_BiaoWei(String s_BiaoWei) {
		S_BiaoWei = s_BiaoWei;
	}

	public String getS_BEIZHU() {
		return S_BEIZHU;
	}

	public void setS_BEIZHU(String s_BEIZHU) {
		S_BEIZHU = s_BEIZHU;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();

		try {
			obj.put("ID", getID());
			obj.put("S_BEIZHU", getS_BEIZHU());
			obj.put("S_BiaoWei", getS_BiaoWei());
			obj.put("S_CH", getS_CH());
			obj.put("S_CID", getS_CID());
			obj.put("S_DiZhi", getS_DiZhi());
			obj.put("S_JianHao", getS_JianHao());
			obj.put("S_JianHaoMC", getS_JianHaoMC());
			obj.put("S_KeHuBH", getS_KeHuBH());
			obj.put("S_KeHuMC", getS_KeHuMC());
			obj.put("S_LianXiDH", getS_LianXiDH());
			obj.put("S_LianXiR", getS_LianXiR());
			obj.put("S_LIANXISJ", getS_LIANXISJ());
			obj.put("S_ShouFeiFS", getS_ShouFeiFS());
			obj.put("S_ST", getS_ST());
			obj.put("D_ShenQingRQ", getD_ShenQingRQ());
			obj.put("S_ShenQingR", getS_ShenQingR());

		} catch (JSONException e) {
			return null;
		}

		return obj;
	}
}
