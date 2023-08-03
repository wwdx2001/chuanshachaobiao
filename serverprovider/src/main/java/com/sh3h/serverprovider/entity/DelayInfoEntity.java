package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DelayInfoEntity {
    // 编号
    private long _ID;
    // 任务编号
    private int _RENWUBH;
    // 抄表ID
    private int _CHAOBIAOID;
    // 客户号
    private String _CID;
    // 抄见水量
    private int _ChaoJianSL;
    // 上次抄码
    private int _ShangCiCM;
    // 抄回抄码
    private int _CHAOHUICM;
    // 抄表状态编码
    private int _ZHUANGTAIBM;
    // 抄表状态中文名称
    private String _ZHUANGTAIMC;
    // 量高量低编码
    private int _LIANGGAOLDBM;
    // 抄表日期
    private long _ChaoBiaoRQ;
    // 抄表年
    private int _ChaoBiaoN;
    // 抄表月
    private int _ChaoBiaoY;
    // 抄表员
    private String _CHAOBIAOY;
    // 抄表方式编码（FK，抄表方式表0：手工抄表 1：手掌机抄表 2：远程表 3：IC卡表）
    private int _FANGSHIBM;
    // 抄表状态（FK，抄表类型表0：正常抄表 1：加抄 2：特殊抄表）
    private int _CHAOBIAOZT;
    // 上次抄表日期
    private long _SHANGCICBRQ;
    // 站点
    private String _ST;
    // 册本号
    private String _CH;
    // 册内序号
    private int _CeNeiXH;
    // 旧表抄码
    private int _JIUBIAOCM;
    // 新表底码
    private int _XINBIAODM;
    // 换表日期
    private long _HUANBIAORQ;
    // 换表回填时间
    private long _HUANBIAOHTSJ;
    // 登记时间
    private long _DENGJISJ;
    // -1:作废0：登记 1：不抄 2：回填（抄到）
    private int _ZHUANGTAI;
    // 延迟员编号
    private String _YANCHIYBH;
    // 回填员编号
    private String _HUITIANYBH;
    // 回填时间
    private long _HUITIANSJ;
    // 处理情况(在回填时如果作废则必须填写此内容)
    private String _CHULIQK;
    // 操作人
    private String _CAOZUOR;
    // 操作时间
    private long _CAOZUOSJ;
    // 换表方式
    private int _HUANBIAOFS;
    // 抄表备注
    private String _CHAOBIAOBZ;
    // 水表条形码
    private String _SHUIBIAOTXM;
    // 延迟原因
    private String _YANCHIYY;
    private int I_ZHENSHICM;
    // 0用水量说明(系统词语表41)
    private int _LINYONGSLSM;
    // 经度
    private String _X;
    // 纬度
    private String _Y;
    // 延迟类型(0:延迟,1:延迟外复)
    private int _YANCHILX;
    // 延迟单编号
    private String _YANCHIBH;
    private int I_CHAOBIAOBZ;
    private int I_ShangChuanBZ;
    private int I_KaiZhangBZ;
    /// <summary>
    /// 简号
    /// </summary>
    private String _JH;
    /// <summary>
    /// 量高水量
    /// </summary>
    private int _LIANGGAOSL;
    /// <summary>
    /// 量低水量
    /// </summary>
    private int _LIANGDISL;
    /// <summary>
    /// 前三月平均量
    /// </summary>
    private int _PINGJUNL1;
    /// <summary>
    /// 上次水量
    /// </summary>
    private int _SHANGCISL;
    /// <summary>
    /// 开帐金额
    /// </summary>
    private int _JE;
    private String S_JIETITS;
    private String S_ShangCiZTBM;
    private int I_SHANGGEDBZQTS;
    private long D_SHANGSHANGGYCBRQ;

    public DelayInfoEntity() { }

    public DelayInfoEntity(long _ID, int _RENWUBH, int _CHAOBIAOID, String _CID, int _ChaoJianSL,
                           int _ShangCiCM, int _CHAOHUICM, int _ZHUANGTAIBM, String _ZHUANGTAIMC,
                           int _LIANGGAOLDBM, long _ChaoBiaoRQ, int _ChaoBiaoN, int _ChaoBiaoY,
                           String _CHAOBIAOY, int _FANGSHIBM, int _CHAOBIAOZT, long _SHANGCICBRQ,
                           String _ST, String _CH, int _CeNeiXH, int _JIUBIAOCM, int _XINBIAODM,
                           long _HUANBIAORQ, long _HUANBIAOHTSJ, long _DENGJISJ, int _ZHUANGTAI,
                           String _YANCHIYBH, String _HUITIANYBH, long _HUITIANSJ, String _CHULIQK,
                           String _CAOZUOR, long _CAOZUOSJ, int _HUANBIAOFS, String _CHAOBIAOBZ,
                           String _SHUIBIAOTXM, String _YANCHIYY, int i_ZHENSHICM, int _LINYONGSLSM,
                           String _X, String _Y, int _YANCHILX, String _YANCHIBH, int i_CHAOBIAOBZ,
                           int i_ShangChuanBZ, int i_KaiZhangBZ, String _JH, int _LIANGGAOSL,
                           int _LIANGDISL, int _PINGJUNL1, int _SHANGCISL, int _JE,
                           String s_JIETITS, String s_ShangCiZTBM, int i_SHANGGEDBZQTS,
                           long d_SHANGSHANGGYCBRQ) {
        this._ID = _ID;
        this._RENWUBH = _RENWUBH;
        this._CHAOBIAOID = _CHAOBIAOID;
        this._CID = _CID;
        this._ChaoJianSL = _ChaoJianSL;
        this._ShangCiCM = _ShangCiCM;
        this._CHAOHUICM = _CHAOHUICM;
        this._ZHUANGTAIBM = _ZHUANGTAIBM;
        this._ZHUANGTAIMC = _ZHUANGTAIMC;
        this._LIANGGAOLDBM = _LIANGGAOLDBM;
        this._ChaoBiaoRQ = _ChaoBiaoRQ;
        this._ChaoBiaoN = _ChaoBiaoN;
        this._ChaoBiaoY = _ChaoBiaoY;
        this._CHAOBIAOY = _CHAOBIAOY;
        this._FANGSHIBM = _FANGSHIBM;
        this._CHAOBIAOZT = _CHAOBIAOZT;
        this._SHANGCICBRQ = _SHANGCICBRQ;
        this._ST = _ST;
        this._CH = _CH;
        this._CeNeiXH = _CeNeiXH;
        this._JIUBIAOCM = _JIUBIAOCM;
        this._XINBIAODM = _XINBIAODM;
        this._HUANBIAORQ = _HUANBIAORQ;
        this._HUANBIAOHTSJ = _HUANBIAOHTSJ;
        this._DENGJISJ = _DENGJISJ;
        this._ZHUANGTAI = _ZHUANGTAI;
        this._YANCHIYBH = _YANCHIYBH;
        this._HUITIANYBH = _HUITIANYBH;
        this._HUITIANSJ = _HUITIANSJ;
        this._CHULIQK = _CHULIQK;
        this._CAOZUOR = _CAOZUOR;
        this._CAOZUOSJ = _CAOZUOSJ;
        this._HUANBIAOFS = _HUANBIAOFS;
        this._CHAOBIAOBZ = _CHAOBIAOBZ;
        this._SHUIBIAOTXM = _SHUIBIAOTXM;
        this._YANCHIYY = _YANCHIYY;
        I_ZHENSHICM = i_ZHENSHICM;
        this._LINYONGSLSM = _LINYONGSLSM;
        this._X = _X;
        this._Y = _Y;
        this._YANCHILX = _YANCHILX;
        this._YANCHIBH = _YANCHIBH;
        I_CHAOBIAOBZ = i_CHAOBIAOBZ;
        I_ShangChuanBZ = i_ShangChuanBZ;
        I_KaiZhangBZ = i_KaiZhangBZ;
        this._JH = _JH;
        this._LIANGGAOSL = _LIANGGAOSL;
        this._LIANGDISL = _LIANGDISL;
        this._PINGJUNL1 = _PINGJUNL1;
        this._SHANGCISL = _SHANGCISL;
        this._JE = _JE;
        S_JIETITS = s_JIETITS;
        S_ShangCiZTBM = s_ShangCiZTBM;
        I_SHANGGEDBZQTS = i_SHANGGEDBZQTS;
        D_SHANGSHANGGYCBRQ = d_SHANGSHANGGYCBRQ;
    }

    public long getID() {
        return _ID;
    }

    public void setID(long ID) {
        _ID = ID;
    }

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public int getCHAOBIAOID() {
        return _CHAOBIAOID;
    }

    public void setCHAOBIAOID(int CHAOBIAOID) {
        _CHAOBIAOID = CHAOBIAOID;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public int getChaoJianSL() {
        return _ChaoJianSL;
    }

    public void setChaoJianSL(int ChaoJianSL) {
        _ChaoJianSL = ChaoJianSL;
    }

    public int getShangCiCM() {
        return _ShangCiCM;
    }

    public void setShangCiCM(int ShangCiCM) {
        _ShangCiCM = ShangCiCM;
    }

    public int getCHAOHUICM() {
        return _CHAOHUICM;
    }

    public void setCHAOHUICM(int CHAOHUICM) {
        _CHAOHUICM = CHAOHUICM;
    }

    public int getZHUANGTAIBM() {
        return _ZHUANGTAIBM;
    }

    public void setZHUANGTAIBM(int ZHUANGTAIBM) {
        _ZHUANGTAIBM = ZHUANGTAIBM;
    }

    public String getZHUANGTAIMC() {
        return _ZHUANGTAIMC;
    }

    public void setZHUANGTAIMC(String ZHUANGTAIMC) {
        _ZHUANGTAIMC = ZHUANGTAIMC;
    }

    public int getLIANGGAOLDBM() {
        return _LIANGGAOLDBM;
    }

    public void setLIANGGAOLDBM(int LIANGGAOLDBM) {
        _LIANGGAOLDBM = LIANGGAOLDBM;
    }

    public long getChaoBiaoRQ() {
        return _ChaoBiaoRQ;
    }

    public void setChaoBiaoRQ(long ChaoBiaoRQ) {
        _ChaoBiaoRQ = ChaoBiaoRQ;
    }

    public int getChaoBiaoN() {
        return _ChaoBiaoN;
    }

    public void setChaoBiaoN(int ChaoBiaoN) {
        _ChaoBiaoN = ChaoBiaoN;
    }

    public int getChaoBiaoY() {
        return _ChaoBiaoY;
    }

    public void setChaoBiaoY(int ChaoBiaoY) {
        _ChaoBiaoY = ChaoBiaoY;
    }

    public String getCHAOBIAOY() {
        return _CHAOBIAOY;
    }

    public void setCHAOBIAOY(String CHAOBIAOY) {
        _CHAOBIAOY = CHAOBIAOY;
    }

    public int getFANGSHIBM() {
        return _FANGSHIBM;
    }

    public void setFANGSHIBM(int FANGSHIBM) {
        _FANGSHIBM = FANGSHIBM;
    }

    public int getCHAOBIAOZT() {
        return _CHAOBIAOZT;
    }

    public void setCHAOBIAOZT(int CHAOBIAOZT) {
        _CHAOBIAOZT = CHAOBIAOZT;
    }

    public long getSHANGCICBRQ() {
        return _SHANGCICBRQ;
    }

    public void setSHANGCICBRQ(long SHANGCICBRQ) {
        _SHANGCICBRQ = SHANGCICBRQ;
    }

    public String getST() {
        return _ST;
    }

    public void setST(String ST) {
        _ST = ST;
    }

    public String getCH() {
        return _CH;
    }

    public void setCH(String CH) {
        _CH = CH;
    }

    public int getCeNeiXH() {
        return _CeNeiXH;
    }

    public void setCeNeiXH(int CeNeiXH) {
        _CeNeiXH = CeNeiXH;
    }

    public int getJIUBIAOCM() {
        return _JIUBIAOCM;
    }

    public void setJIUBIAOCM(int JIUBIAOCM) {
        _JIUBIAOCM = JIUBIAOCM;
    }

    public int getXINBIAODM() {
        return _XINBIAODM;
    }

    public void setXINBIAODM(int XINBIAODM) {
        _XINBIAODM = XINBIAODM;
    }

    public long getHUANBIAORQ() {
        return _HUANBIAORQ;
    }

    public void setHUANBIAORQ(long HUANBIAORQ) {
        _HUANBIAORQ = HUANBIAORQ;
    }

    public long getHUANBIAOHTSJ() {
        return _HUANBIAOHTSJ;
    }

    public void setHUANBIAOHTSJ(long HUANBIAOHTSJ) {
        _HUANBIAOHTSJ = HUANBIAOHTSJ;
    }

    public long getDENGJISJ() {
        return _DENGJISJ;
    }

    public void setDENGJISJ(long DENGJISJ) {
        _DENGJISJ = DENGJISJ;
    }

    public int getZHUANGTAI() {
        return _ZHUANGTAI;
    }

    public void setZHUANGTAI(int ZHUANGTAI) {
        _ZHUANGTAI = ZHUANGTAI;
    }

    public String getYANCHIYBH() {
        return _YANCHIYBH;
    }

    public void setYANCHIYBH(String YANCHIYBH) {
        _YANCHIYBH = YANCHIYBH;
    }

    public String getHUITIANYBH() {
        return _HUITIANYBH;
    }

    public void setHUITIANYBH(String HUITIANYBH) {
        _HUITIANYBH = HUITIANYBH;
    }

    public long getHUITIANSJ() {
        return _HUITIANSJ;
    }

    public void setHUITIANSJ(long HUITIANSJ) {
        _HUITIANSJ = HUITIANSJ;
    }

    public String getCHULIQK() {
        return _CHULIQK;
    }

    public void setCHULIQK(String CHULIQK) {
        _CHULIQK = CHULIQK;
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

    public int getHUANBIAOFS() {
        return _HUANBIAOFS;
    }

    public void setHUANBIAOFS(int HUANBIAOFS) {
        _HUANBIAOFS = HUANBIAOFS;
    }

    public String getCHAOBIAOBZ() {
        return _CHAOBIAOBZ;
    }

    public void setCHAOBIAOBZ(String CHAOBIAOBZ) {
        _CHAOBIAOBZ = CHAOBIAOBZ;
    }

    public String getSHUIBIAOTXM() {
        return _SHUIBIAOTXM;
    }

    public void setSHUIBIAOTXM(String SHUIBIAOTXM) {
        _SHUIBIAOTXM = SHUIBIAOTXM;
    }

    public String getYANCHIYY() {
        return _YANCHIYY;
    }

    public void setYANCHIYY(String YANCHIYY) {
        _YANCHIYY = YANCHIYY;
    }

    public int getI_ZHENSHICM() {
        return I_ZHENSHICM;
    }

    public void setI_ZHENSHICM(int i_ZHENSHICM) {
        I_ZHENSHICM = i_ZHENSHICM;
    }

    public int getLINYONGSLSM() {
        return _LINYONGSLSM;
    }

    public void setLINYONGSLSM(int LINYONGSLSM) {
        _LINYONGSLSM = LINYONGSLSM;
    }

    public String getX() {
        return _X;
    }

    public void setX(String X) {
        _X = X;
    }

    public String getY() {
        return _Y;
    }

    public void setY(String Y) {
        _Y = Y;
    }

    public int getYANCHILX() {
        return _YANCHILX;
    }

    public void setYANCHILX(int YANCHILX) {
        _YANCHILX = YANCHILX;
    }

    public String getYANCHIBH() {
        return _YANCHIBH;
    }

    public void setYANCHIBH(String YANCHIBH) {
        _YANCHIBH = YANCHIBH;
    }

    public int getI_CHAOBIAOBZ() {
        return I_CHAOBIAOBZ;
    }

    public void setI_CHAOBIAOBZ(int i_CHAOBIAOBZ) {
        I_CHAOBIAOBZ = i_CHAOBIAOBZ;
    }

    public int getI_ShangChuanBZ() {
        return I_ShangChuanBZ;
    }

    public void setI_ShangChuanBZ(int i_ShangChuanBZ) {
        I_ShangChuanBZ = i_ShangChuanBZ;
    }

    public int getI_KaiZhangBZ() {
        return I_KaiZhangBZ;
    }

    public void setI_KaiZhangBZ(int i_KaiZhangBZ) {
        I_KaiZhangBZ = i_KaiZhangBZ;
    }

    public String getJH() {
        return _JH;
    }

    public void setJH(String JH) {
        _JH = JH;
    }

    public int getLIANGGAOSL() {
        return _LIANGGAOSL;
    }

    public void setLIANGGAOSL(int LIANGGAOSL) {
        _LIANGGAOSL = LIANGGAOSL;
    }

    public int getLIANGDISL() {
        return _LIANGDISL;
    }

    public void setLIANGDISL(int LIANGDISL) {
        _LIANGDISL = LIANGDISL;
    }

    public int getPINGJUNL1() {
        return _PINGJUNL1;
    }

    public void setPINGJUNL1(int PINGJUNL1) {
        _PINGJUNL1 = PINGJUNL1;
    }

    public int getSHANGCISL() {
        return _SHANGCISL;
    }

    public void setSHANGCISL(int SHANGCISL) {
        _SHANGCISL = SHANGCISL;
    }

    public int getJE() {
        return _JE;
    }

    public void setJE(int JE) {
        _JE = JE;
    }

    public String getS_JIETITS() {
        return S_JIETITS;
    }

    public void setS_JIETITS(String s_JIETITS) {
        S_JIETITS = s_JIETITS;
    }

    public String getS_ShangCiZTBM() {
        return S_ShangCiZTBM;
    }

    public void setS_ShangCiZTBM(String s_ShangCiZTBM) {
        S_ShangCiZTBM = s_ShangCiZTBM;
    }

    public int getI_SHANGGEDBZQTS() {
        return I_SHANGGEDBZQTS;
    }

    public void setI_SHANGGEDBZQTS(int i_SHANGGEDBZQTS) {
        I_SHANGGEDBZQTS = i_SHANGGEDBZQTS;
    }

    public long getD_SHANGSHANGGYCBRQ() {
        return D_SHANGSHANGGYCBRQ;
    }

    public void setD_SHANGSHANGGYCBRQ(long d_SHANGSHANGGYCBRQ) {
        D_SHANGSHANGGYCBRQ = d_SHANGSHANGGYCBRQ;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("iD", getID());
            obj.put("i_RENWUBH", getRENWUBH());
            obj.put("i_CHAOBIAOID", getCHAOBIAOID());
            obj.put("s_CID", getCID());
            obj.put("i_ChaoJianSL", getChaoJianSL());
            obj.put("i_ShangCiCM", getShangCiCM());
            obj.put("i_CHAOHUICM", getCHAOHUICM());
            obj.put("i_ZHUANGTAIBM", getZHUANGTAIBM());
            obj.put("s_ZHUANGTAIMC", getZHUANGTAIMC());
            obj.put("i_LIANGGAOLDBM", getLIANGGAOLDBM());
            obj.put("d_ChaoBiaoRQ", getChaoBiaoRQ());
            obj.put("i_ChaoBiaoN", getChaoBiaoN());
            obj.put("i_ChaoBiaoY", getChaoBiaoY());
            obj.put("s_CHAOBIAOY", getCHAOBIAOY());
            obj.put("i_FANGSHIBM", getFANGSHIBM());
            obj.put("i_CHAOBIAOZT", getCHAOBIAOZT());
            obj.put("d_SHANGCICBRQ", getSHANGCICBRQ());
            obj.put("s_ST", getST());
            obj.put("s_CH", getCH());
            obj.put("i_CeNeiXH", getCeNeiXH());
            obj.put("i_JIUBIAOCM", getJIUBIAOCM());
            obj.put("i_XINBIAODM", getXINBIAODM());
            obj.put("d_HUANBIAORQ", getHUANBIAORQ());
            obj.put("d_HUANBIAOHTSJ", getHUANBIAOHTSJ());
            obj.put("d_DENGJISJ", getDENGJISJ());
            obj.put("i_ZHUANGTAI", getZHUANGTAI());
            obj.put("s_YANCHIYBH", getYANCHIYBH());
            obj.put("s_HUITIANYBH", getHUITIANYBH());
            obj.put("d_HUITIANSJ", getHUITIANSJ());
            obj.put("s_CHULIQK", getCHULIQK());
            obj.put("s_CAOZUOR", getCAOZUOR());
            obj.put("d_CAOZUOSJ", getCAOZUOSJ());
            obj.put("i_HUANBIAOFS", getHUANBIAOFS());
            obj.put("s_CHAOBIAOBZ", getCHAOBIAOBZ());
            obj.put("s_SHUIBIAOTXM", getSHUIBIAOTXM());
            obj.put("s_YANCHIYY", getYANCHIYY());
            obj.put("i_ZHENSHICM", getI_ZHENSHICM());
            obj.put("i_LINYONGSLSM", getLINYONGSLSM());
            obj.put("s_X", getX());
            obj.put("s_Y", getY());
            obj.put("i_YANCHILX", getYANCHILX());
            obj.put("s_YANCHIBH", getYANCHIBH());
            obj.put("i_CHAOBIAOBZ", getCHAOBIAOBZ());
            obj.put("i_ShangChuanBZ", getI_ShangChuanBZ());
            obj.put("i_KaiZhangBZ", getI_KaiZhangBZ());
            obj.put("s_JH", getJH());
            obj.put("i_LIANGGAOSL", getLIANGGAOSL());
            obj.put("i_LIANGDISL", getLIANGDISL());
            obj.put("i_PINGJUNL1", getPINGJUNL1());
            obj.put("i_SHANGCISL", getSHANGCISL());
            obj.put("n_JE", getJE());
            obj.put("S_JIETITS", getS_JIETITS());
            obj.put("S_ShangCiZTBM", getS_ShangCiZTBM());
            obj.put("I_SHANGGEDBZQTS", getI_SHANGGEDBZQTS());
            obj.put("D_SHANGSHANGGYCBRQ", getD_SHANGSHANGGYCBRQ());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONObject toJSON(DelayInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("ID", entity.getID());
            obj.put("I_RENWUBH", entity.getRENWUBH());
            obj.put("I_CHAOBIAOID", entity.getCHAOBIAOID());
            obj.put("S_CID", entity.getCID());
            obj.put("I_ChaoJianSL", entity.getChaoJianSL());
            obj.put("I_ShangCiCM", entity.getShangCiCM());
            obj.put("I_CHAOHUICM", entity.getCHAOHUICM());
            obj.put("I_ZHUANGTAIBM", entity.getZHUANGTAIBM());
            obj.put("S_ZHUANGTAIMC", entity.getZHUANGTAIMC());
            obj.put("I_LIANGGAOLDBM", entity.getLIANGGAOLDBM());
            obj.put("D_ChaoBiaoRQ", entity.getChaoBiaoRQ());
            obj.put("I_ChaoBiaoN", entity.getChaoBiaoN());
            obj.put("I_ChaoBiaoY", entity.getChaoBiaoY());
            obj.put("S_CHAOBIAOY", entity.getCHAOBIAOY());
            obj.put("I_FANGSHIBM", entity.getFANGSHIBM());
            obj.put("I_CHAOBIAOZT", entity.getCHAOBIAOZT());
            obj.put("D_SHANGCICBRQ", entity.getSHANGCICBRQ());
            obj.put("S_ST", entity.getST());
            obj.put("S_CH", entity.getCH());
            obj.put("I_CeNeiXH", entity.getCeNeiXH());
            obj.put("I_JIUBIAOCM", entity.getJIUBIAOCM());
            obj.put("I_XINBIAODM", entity.getXINBIAODM());
            obj.put("D_HUANBIAORQ", entity.getHUANBIAORQ());
            obj.put("D_HUANBIAOHTSJ", entity.getHUANBIAOHTSJ());
            obj.put("D_DENGJISJ", entity.getDENGJISJ());
            obj.put("I_ZHUANGTAI", entity.getZHUANGTAI());
            obj.put("S_YANCHIYBH", entity.getYANCHIYBH());
            obj.put("S_HUITIANYBH", entity.getHUITIANYBH());
            obj.put("D_HUITIANSJ", entity.getHUITIANSJ());
            obj.put("S_CHULIQK", entity.getCHULIQK());
            obj.put("S_CAOZUOR", entity.getCAOZUOR());
            obj.put("D_CAOZUOSJ", entity.getCAOZUOSJ());
            obj.put("I_HUANBIAOFS", entity.getHUANBIAOFS());
            obj.put("S_CHAOBIAOBZ", entity.getCHAOBIAOBZ());
            obj.put("S_SHUIBIAOTXM", entity.getSHUIBIAOTXM());
            obj.put("S_YANCHIYY", entity.getYANCHIYY());
            obj.put("I_ZHENSHICM", entity.getI_ZHENSHICM());
            obj.put("I_LINYONGSLSM", entity.getLINYONGSLSM());
            obj.put("S_X", entity.getX());
            obj.put("S_Y", entity.getY());
            obj.put("I_YANCHILX", entity.getYANCHILX());
            obj.put("S_YANCHIBH", entity.getYANCHIBH());
            obj.put("I_CHAOBIAOBZ", entity.getCHAOBIAOBZ());
            obj.put("I_ShangChuanBZ", entity.getI_ShangChuanBZ());
            obj.put("I_KaiZhangBZ", entity.getI_KaiZhangBZ());
            obj.put("S_JH", entity.getJH());
            obj.put("I_LIANGGAOSL", entity.getLIANGGAOSL());
            obj.put("I_LIANGDISL", entity.getLIANGDISL());
            obj.put("I_PINGJUNL1", entity.getPINGJUNL1());
            obj.put("I_SHANGCISL", entity.getSHANGCISL());
            obj.put("N_JE", entity.getJE());
            obj.put("S_JIETITS", entity.getS_JIETITS());
            obj.put("S_ShangCiZTBM", entity.getS_ShangCiZTBM());
            obj.put("I_SHANGGEDBZQTS", entity.getI_SHANGGEDBZQTS());
            obj.put("D_SHANGSHANGGYCBRQ", entity.getD_SHANGSHANGGYCBRQ());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONArray toJSONArray(List<DelayInfoEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (DelayInfoEntity entity : list) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }

        return array;
    }

    public static DelayInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        DelayInfoEntity entity = new DelayInfoEntity();

        entity.setID(object.optInt("iD"));
        entity.setRENWUBH(object.optInt("i_RENWUBH"));
        entity.setCHAOBIAOID(object.optInt("i_CHAOBIAOID"));
        entity.setCID(object.optString("s_CID"));
        entity.setChaoJianSL(object.optInt("i_ChaoJianSL"));
        entity.setShangCiCM(object.optInt("i_ShangCiCM"));
        entity.setCHAOHUICM(object.optInt("i_CHAOHUICM"));
        entity.setZHUANGTAIBM(object.optInt("i_ZHUANGTAIBM"));
        entity.setZHUANGTAIMC(object.optString("s_ZHUANGTAIMC"));
        entity.setLIANGGAOLDBM(object.optInt("i_LIANGGAOLDBM"));
        entity.setChaoBiaoRQ(object.optLong("d_ChaoBiaoRQ"));
        entity.setChaoBiaoN(object.optInt("i_ChaoBiaoN"));
        entity.setChaoBiaoY(object.optInt("i_ChaoBiaoY"));
        entity.setCHAOBIAOY(object.optString("s_CHAOBIAOY"));
        entity.setFANGSHIBM(object.optInt("i_FANGSHIBM"));
        entity.setCHAOBIAOZT(object.optInt("i_CHAOBIAOZT"));
        entity.setSHANGCICBRQ(object.optLong("d_SHANGCICBRQ"));
        entity.setST(object.optString("s_ST"));
        entity.setCH(object.optString("s_CH"));
        entity.setCeNeiXH(object.optInt("i_CeNeiXH"));
        entity.setJIUBIAOCM(object.optInt("i_JIUBIAOCM"));
        entity.setXINBIAODM(object.optInt("i_XINBIAODM"));
        entity.setHUANBIAORQ(object.optLong("d_HUANBIAORQ"));
        entity.setHUANBIAOHTSJ(object.optLong("d_HUANBIAOHTSJ"));
        entity.setDENGJISJ(object.optLong("d_DENGJISJ"));
        entity.setZHUANGTAI(object.optInt("i_ZHUANGTAI"));
        entity.setYANCHIYBH(object.optString("s_YANCHIYBH"));
        entity.setHUITIANYBH(object.optString("s_HUITIANYBH"));
        entity.setHUITIANSJ(object.optLong("d_HUITIANSJ"));
        entity.setCHULIQK(object.optString("s_CHULIQK"));
        entity.setCAOZUOR(object.optString("s_CAOZUOR"));
        entity.setCAOZUOSJ(object.optLong("d_CAOZUOSJ"));
        entity.setHUANBIAOFS(object.optInt("i_HUANBIAOFS"));
        entity.setCHAOBIAOBZ(object.optString("s_CHAOBIAOBZ"));
        entity.setSHUIBIAOTXM(object.optString("s_SHUIBIAOTXM"));
        entity.setYANCHIYY(object.optString("s_YANCHIYY"));
        entity.setI_ZHENSHICM(object.optInt("i_ZHENSHICM"));
        entity.setLINYONGSLSM(object.optInt("i_LINYONGSLSM"));
        entity.setX(object.optString("s_X"));
        entity.setY(object.optString("s_Y"));
        entity.setYANCHILX(object.optInt("i_YANCHILX"));
        entity.setYANCHIBH(object.optString("s_YANCHIBH"));
        entity.setI_CHAOBIAOBZ(object.optInt("i_CHAOBIAOBZ"));
        entity.setI_ShangChuanBZ(object.optInt("i_ShangChuanBZ"));
        entity.setI_KaiZhangBZ(object.optInt("i_KaiZhangBZ"));
        entity.setJH(object.optString("s_JH"));
        entity.setLIANGGAOSL(object.optInt("i_LIANGGAOSL"));
        entity.setLIANGDISL(object.optInt("i_LIANGDISL"));
        entity.setPINGJUNL1(object.optInt("i_PINGJUNL1"));
        entity.setSHANGCISL(object.optInt("i_SHANGCISL"));
        entity.setJE(object.optInt("n_JE"));
        entity.setS_JIETITS(object.optString("s_JIETITS"));
        entity.setS_ShangCiZTBM(object.optString("s_ShangCiZTBM"));
        entity.setI_SHANGGEDBZQTS(object.optInt("i_SHANGGEDBZQTS"));
        entity.setD_SHANGSHANGGYCBRQ(object.optLong("d_SHANGSHANGGYCBRQ"));
        return entity;
    }

    public static List<DelayInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<DelayInfoEntity> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            DelayInfoEntity entity = DelayInfoEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
