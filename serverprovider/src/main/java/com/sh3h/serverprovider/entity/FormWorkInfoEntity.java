package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FormWorkInfoEntity {
	// ID
	private int _ID;
	// 任务编号
	private int _RENWUBH;
	// 用户号
	private String _CID;
	// 条形码
	private String _SHUIBIAOTXM;
	// 站点
	private String _ST;
	// 口径
	private String _KOUJING;
	// 表型
	private String _BIAOXINGID;
	// 厂家
	private String _SHUIBIAOCJID;
	// 户名
	private String _HM;
	// 地址
	private String _DZ;
	// 表务工单类型(belongid=1301)(1故障换表；2定期换表；
	// 3注销拆表；4暂缓供水拆表；5欠费拆表；
	// 6暂缓拆表复装；7验表)
	private int _BIAOWUGDLX;
	// 状态(belongid=1302)(-1取消，0审批，1登记，2派工，3打印，4回填)
	private int _ZT;
	// 登记日期
	private long _DENGJIRQ;
	// 登记人
	private String _DENGJIR;
	// 打印次数
	private int _DAYINCS;
	// 施工日期
	private long _SHIGONGRQ;
	// 施工人(派单接收人）
	private String _SHIGONGR;
	// 复合日期
	private long _FUHERQ;
	// 复合人
	private String _FUHER;
	// 备注
	private String _BEIZHU;
	// 单据回填结果(S_Words103)(1正常完成-1无法完成)
	private int _JIEGUO;
	// 回填日期
	private long _HUITIANRQ;
	// 回填人
	private String _HUITIANR;
	// 联系人（登记时填写的预约人）
	private String _LIANXIR;
	// 预约日期
	private long _YUYUERQ;
	// 联系电话（登记时填写的预约电话）
	private String _LIANXIDH;
	// 上次装表日期
	private long _SHANGCIHBRQ;
	// 流程ID
	private int _KID;
	// 操作人
	private String _CAOZUOR;
	// 操作时间
	private long _CAOZUOSJ;
	// 旧表读数
	private int _JIUBIAODS;
	// 新表条形码
	private String _XINBIAOTXM;
	// 新表读数
	private int _XINBIAODS;
	// 表务申请批号
	private int _BIAOWUSQPH;
	// 册本号
	private String _CH;
	// 派单人
	private String _PAIDANR;
	// 派单时间
	private long _PAIDANSJ;
	// 接单人
	private String _JIEDANR;
	// 最后施工日期（要求日期）
	private long _ZUIHOUSGRQ;
	// x坐标
	private String _X1;
	// y坐标
	private String _Y1;
	// 工单编号
	private String _GONGDANBH;
	// 客户签名（图片名称）
	private String _KeHuQM;
	// 照片名称（name1,name2,...）
	private String _ZhaoPianMC;

	// / <summary>
	// / 换表原因°
	// / </summary>
	private String _GUZHANGYY;
	// / <summary>
	// / 上抄表状态 原始状态
	// / </summary>
	private String _CHAOBIAOZT;
	// / <summary>
	// / 换表说明
	// / </summary>
	private String _BEIZHU1;
	// / <summary>
	// / 水表钢印号
	// / </summary>
	private String _SHUIBIAOGYH;
	// / <summary>
	// / 新表钢印号
	// / </summary>
	private String _XINBIAOGYH;
	// / <summary>
	// / 欠费金额
	// / </summary>
	private double _QIANFEIJE;
	// / <summary>
	// / 检测结果
	// / </summary>
	private String _JIANCEJG;
	// / <summary>
	// / 拆表原因
	// / </summary>
	private String _CHAIBIAOYY;
	// / <summary>
	// / 合同号
	// / </summary>
	private String _HETONGH;
	// / <summary>
	// / 水表规格
	// / </summary>
	private String _SHUIBIAOGG;
	// / <summary>
	// / 用水性质
	// / </summary>
	private String _YONGSHUIXZ;
	// / <summary>
	// / 审?¨?核?人¨?
	// / </summary>
	private String _SHENHER;
	// / <summary>
	// / 审核日期
	// / </summary>
	private long _SHENHERQ;

	// 返回结果
	private ResultInfoEntity _resultInfo;

	public FormWorkInfoEntity() {

	}

	public int getID() {
		return _ID;
	}

	public void setID(int ID) {
		_ID = ID;
	}

	public int getRENWUBH() {
		return _RENWUBH;
	}

	public void setRENWUBH(int RENWUBH) {
		_RENWUBH = RENWUBH;
	}

	public String getCID() {
		return _CID;
	}

	public void setCID(String CID) {
		_CID = CID;
	}

	public String getSHUIBIAOTXM() {
		return _SHUIBIAOTXM;
	}

	public void setSHUIBIAOTXM(String SHUIBIAOTXM) {
		_SHUIBIAOTXM = SHUIBIAOTXM;
	}

	public String getST() {
		return _ST;
	}

	public void setST(String ST) {
		_ST = ST;
	}

	public String getKOUJING() {
		return _KOUJING;
	}

	public void setKOUJING(String KOUJING) {
		_KOUJING = KOUJING;
	}

	public String getBIAOXINGID() {
		return _BIAOXINGID;
	}

	public void setBIAOXINGID(String BIAOXINGID) {
		_BIAOXINGID = BIAOXINGID;
	}

	public String getSHUIBIAOCJID() {
		return _SHUIBIAOCJID;
	}

	public void setSHUIBIAOCJID(String SHUIBIAOCJID) {
		_SHUIBIAOCJID = SHUIBIAOCJID;
	}

	public String getHM() {
		return _HM;
	}

	public void setHM(String HM) {
		_HM = HM;
	}

	public String getDZ() {
		return _DZ;
	}

	public void setDZ(String DZ) {
		_DZ = DZ;
	}

	public int getBIAOWUGDLX() {
		return _BIAOWUGDLX;
	}

	public void setBIAOWUGDLX(int BIAOWUGDLX) {
		_BIAOWUGDLX = BIAOWUGDLX;
	}

	public int getZT() {
		return _ZT;
	}

	public void setZT(int ZT) {
		_ZT = ZT;
	}

	public long getDENGJIRQ() {
		return _DENGJIRQ;
	}

	public void setDENGJIRQ(long DENGJIRQ) {
		_DENGJIRQ = DENGJIRQ;
	}

	public String getDENGJIR() {
		return _DENGJIR;
	}

	public void setDENGJIR(String DENGJIR) {
		_DENGJIR = DENGJIR;
	}

	public int getDAYINCS() {
		return _DAYINCS;
	}

	public void setDAYINCS(int DAYINCS) {
		_DAYINCS = DAYINCS;
	}

	public long getSHIGONGRQ() {
		return _SHIGONGRQ;
	}

	public void setSHIGONGRQ(long SHIGONGRQ) {
		_SHIGONGRQ = SHIGONGRQ;
	}

	public String getSHIGONGR() {
		return _SHIGONGR;
	}

	public void setSHIGONGR(String SHIGONGR) {
		_SHIGONGR = SHIGONGR;
	}

	public long getFUHERQ() {
		return _FUHERQ;
	}

	public void setFUHERQ(long FUHERQ) {
		_FUHERQ = FUHERQ;
	}

	public String getFUHER() {
		return _FUHER;
	}

	public void setFUHER(String FUHER) {
		_FUHER = FUHER;
	}

	public String getBEIZHU() {
		return _BEIZHU;
	}

	public void setBEIZHU(String BEIZHU) {
		_BEIZHU = BEIZHU;
	}

	public int getJIEGUO() {
		return _JIEGUO;
	}

	public void setJIEGUO(int JIEGUO) {
		_JIEGUO = JIEGUO;
	}

	public long getHUITIANRQ() {
		return _HUITIANRQ;
	}

	public void setHUITIANRQ(long HUITIANRQ) {
		_HUITIANRQ = HUITIANRQ;
	}

	public String getHUITIANR() {
		return _HUITIANR;
	}

	public void setHUITIANR(String HUITIANR) {
		_HUITIANR = HUITIANR;
	}

	public String getLIANXIR() {
		return _LIANXIR;
	}

	public void setLIANXIR(String LIANXIR) {
		_LIANXIR = LIANXIR;
	}

	public long getYUYUERQ() {
		return _YUYUERQ;
	}

	public void setYUYUERQ(long YUYUERQ) {
		_YUYUERQ = YUYUERQ;
	}

	public String getLIANXIDH() {
		return _LIANXIR;
	}

	public void setLIANXIDH(String LIANXIDH) {
		_LIANXIDH = LIANXIDH;
	}

	public long getSHANGCIHBRQ() {
		return _SHANGCIHBRQ;
	}

	public void setSHANGCIHBRQ(long SHANGCIHBRQ) {
		_SHANGCIHBRQ = SHANGCIHBRQ;
	}

	public int getKID() {
		return _KID;
	}

	public void setKID(int KID) {
		_KID = KID;
	}

	public String getCAOZUOR() {
		return _CAOZUOR;
	}

	public void setCAOZUOR(String CAOZUOR) {
		_CAOZUOR = CAOZUOR;
	}

	public long getCAOZUOSJ() {
		return _CAOZUOSJ;
	}

	public void setCAOZUOSJ(long CAOZUOSJ) {
		_CAOZUOSJ = CAOZUOSJ;
	}

	public int getJIUBIAODS() {
		return _JIUBIAODS;
	}

	public void setJIUBIAODS(int JIUBIAODS) {
		_JIUBIAODS = JIUBIAODS;
	}

	public String getXINBIAOTXM() {
		return _XINBIAOTXM;
	}

	public void setXINBIAOTXM(String XINBIAOTXM) {
		_XINBIAOTXM = XINBIAOTXM;
	}

	public int getXINBIAODS() {
		return _XINBIAODS;
	}

	public void setXINBIAODS(int XINBIAODS) {
		_XINBIAODS = XINBIAODS;
	}

	public int getBIAOWUSQPH() {
		return _BIAOWUSQPH;
	}

	public void setBIAOWUSQPH(int BIAOWUSQPH) {
		_BIAOWUSQPH = BIAOWUSQPH;
	}

	public String getCH() {
		return _CH;
	}

	public void setCH(String CH) {
		_CH = CH;
	}

	public String getPAIDANR() {
		return _PAIDANR;
	}

	public void setPAIDANR(String PAIDANR) {
		_PAIDANR = PAIDANR;
	}

	public long getPAIDANSJ() {
		return _PAIDANSJ;
	}

	public void setPAIDANSJ(long PAIDANSJ) {
		_PAIDANSJ = PAIDANSJ;
	}

	public String getJIEDANR() {
		return _JIEDANR;
	}

	public void setJIEDANR(String JIEDANR) {
		_JIEDANR = JIEDANR;
	}

	public long getZUIHOUSGRQ() {
		return _ZUIHOUSGRQ;
	}

	public void setZUIHOUSGRQ(long ZUIHOUSGRQ) {
		_ZUIHOUSGRQ = ZUIHOUSGRQ;
	}

	public String getX1() {
		return _X1;
	}

	public void setX1(String X1) {
		_X1 = X1;
	}

	public String getY1() {
		return _Y1;
	}

	public void setY1(String Y1) {
		_Y1 = Y1;
	}

	public String getGONGDANBH() {
		return _GONGDANBH;
	}

	public void setGONGDANBH(String GONGDANBH) {
		_GONGDANBH = GONGDANBH;
	}

	public String getKeHuQM() {
		return _KeHuQM;
	}

	public void setKeHuQM(String KeHuQM) {
		_KeHuQM = KeHuQM;
	}

	public String getZhaoPianMC() {
		return _ZhaoPianMC;
	}

	public void setZhaoPianMC(String ZhaoPianMC) {
		_ZhaoPianMC = ZhaoPianMC;
	}

	public String getGUZHANGYY() {
		return _GUZHANGYY;
	}

	public void setGUZHANGYY(String GUZHANGYY) {
		_GUZHANGYY = GUZHANGYY;
	}

	public String getCHAOBIAOZT() {
		return _CHAOBIAOZT;
	}

	public void setCHAOBIAOZT(String CHAOBIAOZT) {
		_CHAOBIAOZT = CHAOBIAOZT;
	}

	public String getBEIZHU1() {
		return _BEIZHU1;
	}

	public void setBEIZHU1(String BEIZHU1) {
		_BEIZHU1 = BEIZHU1;
	}

	public String getSHUIBIAOGYH() {
		return _SHUIBIAOGYH;
	}

	public void setSHUIBIAOGYH(String SHUIBIAOGYH) {
		_SHUIBIAOGYH = SHUIBIAOGYH;
	}

	public String getXINBIAOGYH() {
		return _XINBIAOGYH;
	}

	public void setXINBIAOGYH(String XINBIAOGYH) {
		_XINBIAOGYH = XINBIAOGYH;
	}

	public double getQIANFEIJE() {
		return _QIANFEIJE;
	}

	public void setQIANFEIJE(double QIANFEIJE) {
		_QIANFEIJE = QIANFEIJE;
	}

	public String getJIANCEJG() {
		return _JIANCEJG;
	}

	public void setJIANCEJG(String JIANCEJG) {
		_JIANCEJG = JIANCEJG;
	}

	public String getCHAIBIAOYY() {
		return _CHAIBIAOYY;
	}

	public void setCHAIBIAOYY(String CHAIBIAOYY) {
		_CHAIBIAOYY = CHAIBIAOYY;
	}

	public String getHETONGH() {
		return _HETONGH;
	}

	public void setHETONGH(String HETONGH) {
		_HETONGH = HETONGH;
	}

	public String getSHUIBIAOGG() {
		return _SHUIBIAOGG;
	}

	public void setSHUIBIAOGG(String SHUIBIAOGG) {
		_SHUIBIAOGG = SHUIBIAOGG;
	}

	public String getYONGSHUIXZ() {
		return _YONGSHUIXZ;
	}

	public void setYONGSHUIXZ(String YONGSHUIXZ) {
		_YONGSHUIXZ = YONGSHUIXZ;
	}

	public String getSHENHER() {
		return _SHENHER;
	}

	public void setSHENHER(String SHENHER) {
		_SHENHER = SHENHER;
	}

	public long getSHENHERQ() {
		return _SHENHERQ;
	}

	public void setSHENHERQ(long SHENHERQ) {
		_SHENHERQ = SHENHERQ;
	}

	public ResultInfoEntity getResultInfo() {
		return _resultInfo;
	}

	public void setResultInfo(ResultInfoEntity resultInfo) {
		_resultInfo = resultInfo;
	}

	public void setResultInfo(JSONObject object) {
		try {
			_resultInfo = ResultInfoEntity.fromJSON(object);
		} catch (JSONException e) {
			_resultInfo = new ResultInfoEntity();
		}
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject obj = new JSONObject();

		try {
			obj.put("ID", getID());
			obj.put("I_RENWUBH", getRENWUBH());
			obj.put("S_CID", getCID());
			obj.put("S_SHUIBIAOTXM", getSHUIBIAOTXM());
			obj.put("S_ST", getST());
			obj.put("S_KOUJING", getKOUJING());
			obj.put("S_BIAOXINGID", getBIAOXINGID());
			obj.put("S_SHUIBIAOCJID", getSHUIBIAOCJID());
			obj.put("S_HM", getHM());
			obj.put("S_DZ", getDZ());
			obj.put("I_BIAOWUGDLX", getBIAOWUGDLX());
			obj.put("I_ZT", getZT());
			obj.put("D_DENGJIRQ", getDENGJIRQ());
			obj.put("S_DENGJIR", getDENGJIR());
			obj.put("I_DAYINCS", getDAYINCS());
			obj.put("D_SHIGONGRQ", getSHIGONGRQ());
			obj.put("S_SHIGONGR", getSHIGONGR());
			obj.put("D_FUHERQ", getFUHERQ());
			obj.put("S_FUHER", getFUHER());
			obj.put("S_BEIZHU", getBEIZHU());
			obj.put("I_JIEGUO", getJIEGUO());
			obj.put("D_HUITIANRQ", getHUITIANRQ());
			obj.put("S_HUITIANR", getHUITIANR());
			obj.put("S_LIANXIR", getLIANXIR());
			obj.put("D_YUYUERQ", getYUYUERQ());
			obj.put("S_LIANXIDH", getLIANXIDH());
			obj.put("D_SHANGCIHBRQ", getSHANGCIHBRQ());
			obj.put("I_KID", getKID());
			obj.put("S_CAOZUOR", getCAOZUOR());
			obj.put("D_CAOZUOSJ", getCAOZUOSJ());
			obj.put("I_JIUBIAODS", getJIUBIAODS());
			obj.put("S_XINBIAOTXM", getXINBIAOTXM());
			obj.put("I_XINBIAODS", getXINBIAODS());
			obj.put("I_BIAOWUSQPH", getBIAOWUSQPH());
			obj.put("S_CH", getCH());
			obj.put("S_PAIDANR", getPAIDANR());
			obj.put("D_PAIDANSJ", getPAIDANSJ());
			obj.put("S_JIEDANR", getJIEDANR());
			obj.put("D_ZUIHOUSGRQ", getZUIHOUSGRQ());
			obj.put("S_X1", getX1());
			obj.put("S_Y1", getY1());
			obj.put("S_GONGDANBH", getGONGDANBH());
			obj.put("S_KeHuQM", getKeHuQM());
			obj.put("S_ZhaoPianMC", getZhaoPianMC());

			obj.put("S_GUZHANGYY", getGUZHANGYY());
			obj.put("S_CHAOBIAOZT", getCHAOBIAOZT());
			obj.put("S_BEIZHU1", getBEIZHU1());
			obj.put("S_SHUIBIAOGYH", getSHUIBIAOGYH());
			obj.put("S_XINBIAOGYH", getXINBIAOGYH());
			obj.put("N_QIANFEIJE", getQIANFEIJE());
			obj.put("S_JIANCEJG", getJIANCEJG());
			obj.put("S_CHAIBIAOYY", getCHAIBIAOYY());
			obj.put("S_HETONGH", getHETONGH());
			obj.put("S_SHUIBIAOGG", getSHUIBIAOGG());
			obj.put("S_YONGSHUIXZ", getYONGSHUIXZ());
			obj.put("S_SHENHER", getSHENHER());
			obj.put("D_SHENHERQ", getSHENHERQ());

			obj.put("ResultInfo", getResultInfo().toJSON());
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}

	public static JSONObject toJSON(FormWorkInfoEntity entity)
			throws JSONException {
		JSONObject obj = new JSONObject();

		try {
			obj.put("ID", entity.getID());
			obj.put("I_RENWUBH", entity.getRENWUBH());
			obj.put("S_CID", entity.getCID());
			obj.put("S_SHUIBIAOTXM", entity.getSHUIBIAOTXM());
			obj.put("S_ST", entity.getST());
			obj.put("S_KOUJING", entity.getKOUJING());
			obj.put("S_BIAOXINGID", entity.getBIAOXINGID());
			obj.put("S_SHUIBIAOCJID", entity.getSHUIBIAOCJID());
			obj.put("S_HM", entity.getHM());
			obj.put("S_DZ", entity.getDZ());
			obj.put("I_BIAOWUGDLX", entity.getBIAOWUGDLX());
			obj.put("I_ZT", entity.getZT());
			obj.put("D_DENGJIRQ", entity.getDENGJIRQ());
			obj.put("S_DENGJIR", entity.getDENGJIR());
			obj.put("I_DAYINCS", entity.getDAYINCS());
			obj.put("D_SHIGONGRQ", entity.getSHIGONGRQ());
			obj.put("S_SHIGONGR", entity.getSHIGONGR());
			obj.put("D_FUHERQ", entity.getFUHERQ());
			obj.put("S_FUHER", entity.getFUHER());
			obj.put("S_BEIZHU", entity.getBEIZHU());
			obj.put("I_JIEGUO", entity.getJIEGUO());
			obj.put("D_HUITIANRQ", entity.getHUITIANRQ());
			obj.put("S_HUITIANR", entity.getHUITIANR());
			obj.put("S_LIANXIR", entity.getLIANXIR());
			obj.put("D_YUYUERQ", entity.getYUYUERQ());
			obj.put("S_LIANXIDH", entity.getLIANXIDH());
			obj.put("D_SHANGCIHBRQ", entity.getSHANGCIHBRQ());
			obj.put("I_KID", entity.getKID());
			obj.put("S_CAOZUOR", entity.getCAOZUOR());
			obj.put("D_CAOZUOSJ", entity.getCAOZUOSJ());
			obj.put("I_JIUBIAODS", entity.getJIUBIAODS());
			obj.put("S_XINBIAOTXM", entity.getXINBIAOTXM());
			obj.put("I_XINBIAODS", entity.getXINBIAODS());
			obj.put("I_BIAOWUSQPH", entity.getBIAOWUSQPH());
			obj.put("S_CH", entity.getCH());
			obj.put("S_PAIDANR", entity.getPAIDANR());
			obj.put("D_PAIDANSJ", entity.getPAIDANSJ());
			obj.put("S_JIEDANR", entity.getJIEDANR());
			obj.put("D_ZUIHOUSGRQ", entity.getZUIHOUSGRQ());
			obj.put("S_X1", entity.getX1());
			obj.put("S_Y1", entity.getY1());
			obj.put("S_GONGDANBH", entity.getGONGDANBH());
			obj.put("S_KeHuQM", entity.getKeHuQM());
			obj.put("S_ZhaoPianMC", entity.getZhaoPianMC());

			obj.put("S_GUZHANGYY", entity.getGUZHANGYY());
			obj.put("S_CHAOBIAOZT", entity.getCHAOBIAOZT());
			obj.put("S_BEIZHU1", entity.getBEIZHU1());
			obj.put("S_SHUIBIAOGYH", entity.getSHUIBIAOGYH());
			obj.put("S_XINBIAOGYH", entity.getXINBIAOGYH());
			obj.put("N_QIANFEIJE", entity.getQIANFEIJE());
			obj.put("S_JIANCEJG", entity.getJIANCEJG());
			obj.put("S_CHAIBIAOYY", entity.getCHAIBIAOYY());
			obj.put("S_HETONGH", entity.getHETONGH());
			obj.put("S_SHUIBIAOGG", entity.getSHUIBIAOGG());
			obj.put("S_YONGSHUIXZ", entity.getYONGSHUIXZ());
			obj.put("S_SHENHER", entity.getSHENHER());
			obj.put("D_SHENHERQ", entity.getSHENHERQ());
			obj.put("ResultInfo", entity.getResultInfo().toJSON());
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}

	public static JSONArray toJSONArray(List<FormWorkInfoEntity> list)
			throws JSONException {
		JSONArray array = new JSONArray();

		for (FormWorkInfoEntity entity : list) {
			JSONObject object = toJSON(entity);
			array.put(object);
		}

		return array;
	}

	public static FormWorkInfoEntity fromJSON(JSONObject object)
			throws JSONException {
		FormWorkInfoEntity entity = new FormWorkInfoEntity();

		entity.setID(object.optInt("iD"));
		entity.setRENWUBH(object.optInt("i_RENWUBH"));
		entity.setCID(object.optString("s_CID"));
		entity.setSHUIBIAOTXM(object.optString("s_SHUIBIAOTXM"));
		entity.setST(object.optString("s_ST"));
		entity.setKOUJING(object.optString("s_KOUJING"));
		entity.setBIAOXINGID(object.optString("s_BIAOXINGID"));
		entity.setSHUIBIAOCJID(object.optString("s_SHUIBIAOCJID"));
		entity.setHM(object.optString("s_HM"));
		entity.setDZ(object.optString("s_DZ"));
		entity.setBIAOWUGDLX(object.optInt("i_BIAOWUGDLX"));
		entity.setZT(object.optInt("i_ZT"));
		entity.setDENGJIRQ(object.optLong("d_DENGJIRQ"));
		entity.setDENGJIR(object.optString("s_DENGJIR"));
		entity.setDAYINCS(object.optInt("i_DAYINCS"));
		entity.setSHIGONGRQ(object.optLong("d_SHIGONGRQ"));
		entity.setSHIGONGR(object.optString("s_SHIGONGR"));
		entity.setFUHERQ(object.optLong("d_FUHERQ"));
		entity.setFUHER(object.optString("s_FUHER"));
		entity.setBEIZHU(object.optString("s_BEIZHU"));
		entity.setJIEGUO(object.optInt("i_JIEGUO"));
		entity.setHUITIANRQ(object.optLong("d_HUITIANRQ"));
		entity.setHUITIANR(object.optString("s_HUITIANR"));
		entity.setLIANXIR(object.optString("s_LIANXIR"));
		entity.setYUYUERQ(object.optLong("d_YUYUERQ"));
		entity.setLIANXIDH(object.optString("s_LIANXIDH"));
		entity.setSHANGCIHBRQ(object.optLong("d_SHANGCIHBRQ"));
		entity.setKID(object.optInt("i_KID"));
		entity.setCAOZUOR(object.optString("s_CAOZUOR"));
		entity.setCAOZUOSJ(object.optLong("d_CAOZUOSJ"));
		entity.setJIUBIAODS(object.optInt("i_JIUBIAODS"));
		entity.setXINBIAOTXM(object.optString("s_XINBIAOTXM"));
		entity.setXINBIAODS(object.optInt("i_XINBIAODS"));
		entity.setBIAOWUSQPH(object.optInt("i_BIAOWUSQPH"));
		entity.setCH(object.optString("s_CH"));
		entity.setPAIDANR(object.optString("s_PAIDANR"));
		entity.setPAIDANSJ(object.optLong("d_PAIDANSJ"));
		entity.setJIEDANR(object.optString("s_JIEDANR"));
		entity.setZUIHOUSGRQ(object.optLong("d_ZUIHOUSGRQ"));
		entity.setX1(object.optString("s_X1"));
		entity.setY1(object.optString("s_Y1"));
		entity.setGONGDANBH(object.optString("s_GONGDANBH"));
		entity.setKeHuQM(object.optString("s_KeHuQM"));
		entity.setZhaoPianMC(object.optString("s_ZhaoPianMC"));

		entity.setGUZHANGYY(object.optString("s_GUZHANGYY"));
		entity.setCHAOBIAOZT(object.optString("s_CHAOBIAOZT"));
		entity.setBEIZHU1(object.optString("s_BEIZHU1"));
		entity.setSHUIBIAOGYH(object.optString("s_SHUIBIAOGYH"));
		entity.setXINBIAOGYH(object.optString("s_XINBIAOGYH"));
		entity.setQIANFEIJE(object.optDouble("n_QIANFEIJE"));
		entity.setJIANCEJG(object.optString("s_JIANCEJG"));
		entity.setCHAIBIAOYY(object.optString("s_CHAIBIAOYY"));
		entity.setHETONGH(object.optString("s_HETONGH"));
		entity.setSHUIBIAOGG(object.optString("s_SHUIBIAOGG"));
		entity.setYONGSHUIXZ(object.optString("s_YONGSHUIXZ"));
		entity.setSHENHER(object.optString("s_SHENHER"));
		entity.setSHENHERQ(object.optLong("d_SHENHERQ"));

		entity.setResultInfo(object.optJSONObject("result"));

		return entity;
	}

	public static List<FormWorkInfoEntity> fromJSONArray(JSONArray array)
			throws JSONException {
		List<FormWorkInfoEntity> list = new ArrayList<FormWorkInfoEntity>();

		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			FormWorkInfoEntity entity = FormWorkInfoEntity.fromJSON(object);
			list.add(entity);
		}

		return list;
	}
}
