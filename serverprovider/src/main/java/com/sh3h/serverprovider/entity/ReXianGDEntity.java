package com.sh3h.serverprovider.entity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ReXianGDEntity {

	private String _gongDanBH;
	private String _cID;
	private String _kaiDanRBH;
	private String _fanYinGR;
	private String _dianHua;
	private Date _faShengSJ;
	private String _faShengDZ;
	private String _fanYingLB;
	private String _fanYingNR;
	private String _jinJiCD;
	private String _shouLiBZ;

	public String getS_GongDanBH() {
		return _gongDanBH;
	}

	public void setS_GongDanBH(String s_GongDanBH) {
		_gongDanBH = s_GongDanBH;
	}

	public String getS_CID() {
		return _cID;
	}

	public void setS_CID(String s_CID) {
		_cID = s_CID;
	}

	public String getS_KaiDanRBH() {
		return _kaiDanRBH;
	}

	public void setS_KaiDanRBH(String s_KaiDanRBH) {
		_kaiDanRBH = s_KaiDanRBH;
	}

	public String getS_FanYinGR() {
		return _fanYinGR;
	}

	public void setS_FanYinGR(String s_FanYinGR) {
		_fanYinGR = s_FanYinGR;
	}

	public String getS_DianHua() {
		return _dianHua;
	}

	public void setS_DianHua(String s_DianHua) {
		_dianHua = s_DianHua;
	}

	public Date getD_FaShengSJ() {
		return _faShengSJ;
	}

	public void setD_FaShengSJ(Date d_FaShengSJ) {
		_faShengSJ = d_FaShengSJ;
	}

	public String getS_FaShengDZ() {
		return _faShengDZ;
	}

	public void setS_FaShengDZ(String s_FaShengDZ) {
		_faShengDZ = s_FaShengDZ;
	}

	public String getS_FanYingLB() {
		return _fanYingLB;
	}

	public void setS_FanYingLB(String s_FanYingLB) {
		_fanYingLB = s_FanYingLB;
	}

	public String getS_FanYingNR() {
		return _fanYingNR;
	}

	public void setS_FanYingNR(String s_FanYingNR) {
		_fanYingNR = s_FanYingNR;
	}

	public String getS_JinJiCD() {
		return _jinJiCD;
	}

	public void setS_JinJiCD(String s_JinJiCD) {
		_jinJiCD = s_JinJiCD;
	}

	public String getS_ShouLiBZ() {
		return _shouLiBZ;
	}

	public void setS_ShouLiBZ(String s_ShouLiBZ) {
		_shouLiBZ = s_ShouLiBZ;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();

		try {
			obj.put("S_GongDanBH", getS_GongDanBH());
			obj.put("S_CID", getS_CID());
			obj.put("S_KaiDanRBH", getS_KaiDanRBH());
			obj.put("S_FanYinGR", getS_FanYinGR());
			obj.put("S_DianHua", getS_DianHua());
			obj.put("D_FaShengSJ", getD_FaShengSJ().getTime());
			obj.put("S_FaShengDZ", getS_FaShengDZ());
			obj.put("S_FanYingLB", getS_FanYingLB());
			obj.put("S_FanYingNR", getS_FanYingNR());
			obj.put("S_JinJiCD", getS_JinJiCD());
			obj.put("S_ShouLiBZ", getS_ShouLiBZ());
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}

}
