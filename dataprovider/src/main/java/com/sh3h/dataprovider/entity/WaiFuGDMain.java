package com.sh3h.dataprovider.entity;


public class WaiFuGDMain {

	/**
	 * 任务编号
	 */
	private int I_RENWUBH;

	/**
	 * 工单类型
	 */
	private int I_TYPE;

	/**
	 * 客户号
	 */
	private String S_CID;

	/**
	 * 册号
	 */
	private String S_CH;

	/**
	 * 户名
	 */
	private String S_HM;

	/**
	 * 用水地址
	 */
	private String S_DZ;

	/**
	 * 电话号码
	 */
	private String S_DIANHUAHM;

	/**
	 * 原用水性质
	 */
	private String S_JH_OLD;

	/**
	 * 新用水性质
	 */
	private String S_JH_NEW;

	/**
	 * 提交日期
	 */
	private long D_TIJIAORQ;

	/**
	 * 申请人
	 */
	private String S_SHENQINGR;

	/**
	 * 申请原因
	 */
	private String S_SHENQINGYY;

	/**
	 * 申请人口数
	 */
	private int I_RENKOUS;

	/**
	 * 优惠水量
	 */
	private int I_YOUHUISL;

	/**
	 * 蹲位数
	 */
	private int I_DUNWEIS;

	/**
	 * 是否全额
	 */
	private int B_QUANER;

	/**
	 * 来源编号
	 */
	private int I_LAIYUANBH;

	/**
	 * 上传标志
	 */
	private int I_SHANGCHUANBZ;

	/**
	 * 操作人
	 */
	private String S_CAOZUOR;

	/**
	 * 完成标志
	 */
	private int I_WANCHENGBZ;

	/**
	 * 申请人核实人口数【人口性质变更；】
	 */
	private int I_HESHIRKS;

	/**
	 * 申请人核实蹲位数【公测审批；】
	 */
	private int I_HESHIDWS;

	/**
	 * 施工日期
	 */
	private long D_SHIGONGRQ;

	/**
	 * 核查情况【水表自转核查】
	 */
	private String S_HECHAQK;

	/**
	 * 审核意见【用水比例调整；水表自转核查】
	 */
	private String S_SHENHEYJ;

	/**
	 * 1:同意,2:不同意，【用水性质变更用-复核意见；用水比例调整-审批意见；调减数量-复核意见】
	 */
	private int I_SHENPIJG;
	/**
	 * 复核意见原因【调减水量-】
	 */
	private String S_FUHEYJYY;

	/**
	 * 备注
	 */
	private String S_BEIZHU;

	/**
	 * 水表号（条形码）
	 */
	private String S_TIAOXINGM;

	/**
	 * 客户签名
	 */
	private String S_KEHUQM;

	/**
	 * 照片名称
	 */
	private String S_ZHAOPIANMC;

	/**
	 * 经度
	 */
	private String S_X1;

	/**
	 * 维度
	 */
	private String S_Y1;

	// 返回结果
	private ResultInfo resultInfo;

	public ResultInfo get_resultInfo() {
		return resultInfo;
	}

	public void set_resultInfo(ResultInfo _resultInfo) {
		this.resultInfo = _resultInfo;
	}

	public String getX1() {
		return S_X1;
	}

	public void setX1(String X1) {
		S_X1 = X1;
	}

	public String getY1() {
		return S_Y1;
	}

	public void setY1(String Y1) {
		S_Y1 = Y1;
	}

	public String getKEHUQM() {
		return S_KEHUQM;
	}

	public void setKEHUQM(String KEHUQM) {
		S_KEHUQM = KEHUQM;
	}

	public String getZHAOPIANMC() {
		return S_ZHAOPIANMC;
	}

	public void setZHAOPIANMC(String ZHAOPIANMC) {
		S_ZHAOPIANMC = ZHAOPIANMC;
	}

	public String getBEIZHU() {
		return S_BEIZHU;
	}

	public void setBEIZHU(String BEIZHU) {
		S_BEIZHU = BEIZHU;
	}

	public String getTIAOXINGM() {
		return S_TIAOXINGM;
	}

	public void setTIAOXINGM(String TIAOXINGM) {
		S_TIAOXINGM = TIAOXINGM;
	}

	public int getHESHIRKS() {
		return I_HESHIRKS;
	}

	public void setHESHIRKS(int HESHIRKS) {
		I_HESHIRKS = HESHIRKS;
	}

	public int getHESHIDWS() {
		return I_HESHIDWS;
	}

	public void setHESHIDWS(int HESHIDWS) {
		I_HESHIDWS = HESHIDWS;
	}

	public long getSHIGONGRQ() {
		return D_SHIGONGRQ;
	}

	public void setSHIGONGRQ(long SHIGONGRQ) {
		D_SHIGONGRQ = SHIGONGRQ;
	}

	public String getHECHAQK() {
		return S_HECHAQK;
	}

	public void setHECHAQK(String HECHAQK) {
		S_HECHAQK = HECHAQK;
	}

	public String getSHENHEYJ() {
		return S_SHENHEYJ;
	}

	public void setSHENHEYJ(String SHENHEYJ) {
		S_SHENHEYJ = SHENHEYJ;
	}

	public int getSHENPIJG() {
		return I_SHENPIJG;
	}

	public void setSHENPIJG(int SHENPIJG) {
		I_SHENPIJG = SHENPIJG;
	}

	public String getFUHEYJYY() {
		return S_FUHEYJYY;
	}

	public void setFUHEYJYY(String FUHEYJYY) {
		S_FUHEYJYY = FUHEYJYY;
	}

	public int getWANCHENGBZ() {
		return I_WANCHENGBZ;
	}

	public void setWANCHENGBZ(int WANCHENGBZ) {
		I_WANCHENGBZ = WANCHENGBZ;
	}

	public String getCAOZUOR() {
		return S_CAOZUOR;
	}

	public void setCAOZUOR(String s_CAOZUOR) {
		S_CAOZUOR = s_CAOZUOR;
	}

	public int getSHANGCHUANBZ() {
		return I_SHANGCHUANBZ;
	}

	public void setSHANGCHUANBZ(int SHANGCHUANBZ) {
		I_SHANGCHUANBZ = SHANGCHUANBZ;
	}

	public int getRENWUBH() {
		return I_RENWUBH;
	}

	public void setRENWUBH(int i_RENWUBH) {
		I_RENWUBH = i_RENWUBH;
	}

	public int getTYPE() {
		return I_TYPE;
	}

	public void setTYPE(int i_TYPE) {
		I_TYPE = i_TYPE;
	}

	public String getCID() {
		return S_CID;
	}

	public void setCID(String s_CID) {
		S_CID = s_CID;
	}

	public String getCH() {
		return S_CH;
	}

	public void setCH(String s_CH) {
		S_CH = s_CH;
	}

	public String getHM() {
		return S_HM;
	}

	public void setHM(String s_HM) {
		S_HM = s_HM;
	}

	public String getDZ() {
		return S_DZ;
	}

	public void setDZ(String s_DZ) {
		S_DZ = s_DZ;
	}

	public String getDIANHUAHM() {
		return S_DIANHUAHM;
	}

	public void setDIANHUAHM(String s_DIANHUAHM) {
		S_DIANHUAHM = s_DIANHUAHM;
	}

	public String getJH_OLD() {
		return S_JH_OLD;
	}

	public void setJH_OLD(String s_JH_OLD) {
		S_JH_OLD = s_JH_OLD;
	}

	public String getJH_NEW() {
		return S_JH_NEW;
	}

	public void setJH_NEW(String s_JH_NEW) {
		S_JH_NEW = s_JH_NEW;
	}

	public long getTIJIAORQ() {
		return D_TIJIAORQ;
	}

	public void setTIJIAORQ(long d_TIJIAORQ) {
		D_TIJIAORQ = d_TIJIAORQ;
	}

	public String getSHENQINGR() {
		return S_SHENQINGR;
	}

	public void setSHENQINGR(String s_SHENQINGR) {
		S_SHENQINGR = s_SHENQINGR;
	}

	public String getSHENQINGYY() {
		return S_SHENQINGYY;
	}

	public void setSHENQINGYY(String s_SHENQINGYY) {
		S_SHENQINGYY = s_SHENQINGYY;
	}

	public int getRENKOUS() {
		return I_RENKOUS;
	}

	public void setRENKOUS(int i_RENKOUS) {
		I_RENKOUS = i_RENKOUS;
	}

	public int getYOUHUISL() {
		return I_YOUHUISL;
	}

	public void setYOUHUISL(int i_YOUHUISL) {
		I_YOUHUISL = i_YOUHUISL;
	}

	public int getDUNWEIS() {
		return I_DUNWEIS;
	}

	public void setDUNWEIS(int i_DUNWEIS) {
		I_DUNWEIS = i_DUNWEIS;
	}

	public int getisQUANER() {
		return B_QUANER;
	}

	public void setisQUANER(int b_QUANER) {
		B_QUANER = b_QUANER;
	}

	public int getLAIYUANBH() {
		return I_LAIYUANBH;
	}

	public void setLAIYUANBH(int i_LAIYUANBH) {
		I_LAIYUANBH = i_LAIYUANBH;
	}

}
