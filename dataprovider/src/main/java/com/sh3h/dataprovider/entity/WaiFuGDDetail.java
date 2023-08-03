package com.sh3h.dataprovider.entity;

public class WaiFuGDDetail {

	/**
	 * 任务编号
	 */
	private int I_RENWUBH;

	/**
	 * 操作人
	 */
	private String S_CAOZUOR;

	/**
	 * 序号
	 */
	private int I_XH;

	/**
	 * 简号
	 */
	private String S_JH;

	/**
	 * 简号比率
	 */
	private double N_BILI;

	/**
	 * 简号类型(0:原简号 1：新简号)
	 */
	private int I_JHTYPE;

	/**
	 * 客户号
	 */
	private String S_CID;

	/**
	 * 抄表年月
	 */
	private int I_CHAOBIAONY;

	/**
	 * 栋单元房
	 */
	private String S_DZ;

	/**
	 * 水表号
	 */
	private String S_TIAOXINGM;

	/**
	 * 原水量
	 */
	private int I_YONGSHUIL;

	/**
	 * 调减水量
	 */
	private int I_TIAOJIANSL;

	/**
	 * 调整金额
	 */
	private double N_TIAOZHENGJE;

	/**
	 * 申请缘由
	 */
	private String S_SHENQINGYY;

	/**
	 * 备注
	 */
	private String S_BEIZHU;

	/**
	 * 来源编号
	 */
	private int I_LAIYUANBH;

	/**
	 * 复查结果【远传表水量-外复核查】
	 */
	private String S_FUCHAJG;

	/**
	 * 完成标志
	 */
	private int I_WANCHENGBZ;

	/**
	 * 隐藏标志
	 */
	private int I_YINGCANGBZ;

	/**
	 * 原抄码
	 */
	private int I_YUANCHAOM;



	public int getYUANCHAOM() {
		return I_YUANCHAOM;
	}

	public void setYUANCHAOM(int YUANCHAOM) {
		I_YUANCHAOM = YUANCHAOM;
	}

	public String getCAOZUOR() {
		return S_CAOZUOR;
	}

	public void setCAOZUOR(String CAOZUOR) {
		S_CAOZUOR = CAOZUOR;
	}

	public int getWANCHENGBZ() {
		return I_WANCHENGBZ;
	}

	public void setWANCHENGBZ(int WANCHENGBZ) {
		I_WANCHENGBZ = WANCHENGBZ;
	}

	public int getYINGCANGBZ() {
		return I_YINGCANGBZ;
	}

	public void setYINGCANGBZ(int YINGCANGBZ) {
		I_YINGCANGBZ = YINGCANGBZ;
	}

	public String getFUCHAJG() {
		return S_FUCHAJG;
	}

	public void setFUCHAJG(String FUCHAJG) {
		S_FUCHAJG = FUCHAJG;
	}

	public int getRENWUBH() {
		return I_RENWUBH;
	}

	public void setRENWUBH(int i_RENWUBH) {
		I_RENWUBH = i_RENWUBH;
	}

	public int getXH() {
		return I_XH;
	}

	public void setXH(int i_XH) {
		I_XH = i_XH;
	}

	public String getS_JH() {
		return S_JH;
	}

	public void setJH(String s_JH) {
		S_JH = s_JH;
	}

	public double getBILI() {
		return N_BILI;
	}

	public void setBILI(double n_BILI) {
		N_BILI = n_BILI;
	}

	public int getJHTYPE() {
		return I_JHTYPE;
	}

	public void setJHTYPE(int i_JHTYPE) {
		I_JHTYPE = i_JHTYPE;
	}

	public String getS_CID() {
		return S_CID;
	}

	public void setCID(String s_CID) {
		S_CID = s_CID;
	}

	public int getCHAOBIAONY() {
		return I_CHAOBIAONY;
	}

	public void setCHAOBIAONY(int i_CHAOBIAONY) {
		I_CHAOBIAONY = i_CHAOBIAONY;
	}

	public String getS_DZ() {
		return S_DZ;
	}

	public void setDZ(String s_DZ) {
		S_DZ = s_DZ;
	}

	public String getS_TIAOXINGM() {
		return S_TIAOXINGM;
	}

	public void setTIAOXINGM(String s_TIAOXINGM) {
		S_TIAOXINGM = s_TIAOXINGM;
	}

	public int getYONGSHUIL() {
		return I_YONGSHUIL;
	}

	public void setYONGSHUIL(int i_YONGSHUIL) {
		I_YONGSHUIL = i_YONGSHUIL;
	}

	public int getTIAOJIANSL() {
		return I_TIAOJIANSL;
	}

	public void setTIAOJIANSL(int i_TIAOJIANSL) {
		I_TIAOJIANSL = i_TIAOJIANSL;
	}

	public double getTIAOZHENGJE() {
		return N_TIAOZHENGJE;
	}

	public void setTIAOZHENGJE(double n_TIAOZHENGJE) {
		N_TIAOZHENGJE = n_TIAOZHENGJE;
	}

	public String getS_SHENQINGYY() {
		return S_SHENQINGYY;
	}

	public void setSHENQINGYY(String s_SHENQINGYY) {
		S_SHENQINGYY = s_SHENQINGYY;
	}

	public String getS_BEIZHU() {
		return S_BEIZHU;
	}

	public void setBEIZHU(String s_BEIZHU) {
		S_BEIZHU = s_BEIZHU;
	}

	public int getLAIYUANBH() {
		return I_LAIYUANBH;
	}

	public void setLAIYUANBH(int i_LAIYUANBH) {
		I_LAIYUANBH = i_LAIYUANBH;
	}
}
