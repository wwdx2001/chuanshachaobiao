package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExternalWorkMainInfoEntity {
    /// <summary>
    /// 任务编号
    /// </summary>
    private int _RENWUBH;
    /// <summary>
    /// 工单类型
    /// </summary>
    private int _SUBTYPE;
    /// <summary>
    /// 客户号
    /// </summary>
    private String _CID;
    /// <summary>
    /// 册号
    /// </summary>
    private String _CH;
    /// <summary>
    /// 户名
    /// </summary>
    private String _HM;
    /// <summary>
    /// 用水地址
    /// </summary>
    private String _DZ;
    /// <summary>
    /// 电话号码
    /// </summary>
    private String _DIANHUAHM;
    /// <summary>
    /// 原用水性质
    /// </summary>
    private String _JHOLD;
    /// <summary>
    /// 新用水性质
    /// </summary>
    private String _JHNEW;
    /// <summary>
    /// 提交日期
    /// </summary>
    private long _TIJIAORQ;
    /// <summary>
    /// 申请人
    /// </summary>
    private String _SHENQINGR;
    /// <summary>
    /// 申请原因
    /// </summary>
    private String _SHENQINGYY;
    /// <summary>
    /// 申请人口数
    /// </summary>
    private int _RENKOUS;
    /// <summary>
    /// 优惠水量
    /// </summary>
    private int _YOUHUISL;
    /// <summary>
    /// 蹲位数
    /// </summary>
    private int _DUNWEIS;
    /// <summary>
    /// 是否全额
    /// </summary>
    private boolean _QUANER;
    /// <summary>
    /// 来源编号
    /// </summary>
    private int _LAIYUANBH;
    /// <summary>
    /// 水表号
    /// </summary>
    private String _TIAOXINGM;
    /// <summary>
    /// 操作人
    /// </summary>
    private String _CAOZUOR;
    /// <summary>
    /// 客户签名（图片名称）
    /// </summary>
    private String _KeHuQM;
    /// 照片名称（name1,name2,...）
    /// </summary>
    private String _ZhaoPianMC;
    // x坐标
    private String _X1;
    // y坐标
    private String _Y1;
    // 返回结果
    private ResultInfoEntity _Result;

    public ExternalWorkMainInfoEntity() {}

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public int getSUBTYPE() {
        return _SUBTYPE;
    }

    public void setSUBTYPE(int SUBTYPE) {
        _SUBTYPE = SUBTYPE;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public String getCH() {
        return _CH;
    }

    public void setCH(String CH) {
        _CH = CH;
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

    public String getDIANHUAHM() {
        return _DIANHUAHM;
    }

    public void setDIANHUAHM(String DIANHUAHM) {
        _DIANHUAHM = DIANHUAHM;
    }

    public String getJHOLD() {
        return _JHOLD;
    }

    public void setJHOLD(String JHOLD) {
        _JHOLD = JHOLD;
    }

    public String getJHNEW() {
        return _JHNEW;
    }

    public void setJHNEW(String JHNEW) {
        _JHNEW = JHNEW;
    }

    public long getTIJIAORQ() {
        return _TIJIAORQ;
    }

    public void setTIJIAORQ(long TIJIAORQ) {
        _TIJIAORQ = TIJIAORQ;
    }

    public String getSHENQINGR() {
        return _SHENQINGR;
    }

    public void setSHENQINGR(String SHENQINGR) {
        _SHENQINGR = SHENQINGR;
    }

    public String getSHENQINGYY() {
        return _SHENQINGYY;
    }

    public void setSHENQINGYY(String SHENQINGYY) {
        _SHENQINGYY = SHENQINGYY;
    }

    public int getRENKOUS() {
        return _RENKOUS;
    }

    public void setRENKOUS(int RENKOUS) {
        _RENKOUS = RENKOUS;
    }

    public int getYOUHUISL() {
        return _YOUHUISL;
    }

    public void setYOUHUISL(int YOUHUISL) {
        _YOUHUISL = YOUHUISL;
    }

    public int getDUNWEIS() {
        return _DUNWEIS;
    }

    public void setDUNWEIS(int DUNWEIS) {
        _DUNWEIS = DUNWEIS;
    }

    public boolean getQUANER() {
        return _QUANER;
    }

    public void setQUANER(boolean QUANER) {
        _QUANER = QUANER;
    }

    public int getLAIYUANBH() {
        return _LAIYUANBH;
    }

    public void setLAIYUANBH(int LAIYUANBH) {
        _LAIYUANBH = LAIYUANBH;
    }

    public String getTIAOXINGM() {
        return _TIAOXINGM;
    }

    public void setTIAOXINGM(String TIAOXINGM) {
        _TIAOXINGM = TIAOXINGM;
    }

    public String getCAOZUOR() {
        return _CAOZUOR;
    }

    public void setCAOZUOR(String CAOZUOR) {
        _CAOZUOR = CAOZUOR;
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

    public ResultInfoEntity getResultInfo() {
        return _Result;
    }

    public void setResultInfo(ResultInfoEntity result) {
        _Result = result;
    }

    public void setResultInfo(JSONObject object) {
        try {
            _Result = ResultInfoEntity.fromJSON(object);
        }
        catch (JSONException e) {
            _Result = new ResultInfoEntity();
        }
    }

    public static ExternalWorkMainInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        ExternalWorkMainInfoEntity entity = new ExternalWorkMainInfoEntity();

        entity.setRENWUBH(object.optInt("i_RENWUBH"));
        entity.setSUBTYPE(object.optInt("i_SUBTYPE"));
        entity.setCID(object.optString("s_CID"));
        entity.setCH(object.optString("s_CH"));
        entity.setHM(object.optString("s_HM"));
        entity.setDZ(object.optString("s_DZ"));
        entity.setDIANHUAHM(object.optString("s_DIANHUAHM"));
        entity.setJHOLD(object.optString("s_JH_OLD"));
        entity.setJHNEW(object.optString("s_JH_NEW"));
        entity.setTIJIAORQ(object.optLong("d_TIJIAORQ"));
        entity.setSHENQINGR(object.optString("s_SHENQINGR"));
        entity.setSHENQINGYY(object.optString("s_SHENQINGYY"));
        entity.setRENKOUS(object.optInt("i_RENKOUS"));
        entity.setYOUHUISL(object.optInt("i_YOUHUISL"));
        entity.setDUNWEIS(object.optInt("i_DUNWEIS"));
        entity.setQUANER(object.optBoolean("b_QUANER"));
        entity.setLAIYUANBH(object.optInt("i_LAIYUANBH"));
        entity.setTIAOXINGM(object.optString("s_TIAOXINGM"));
        entity.setCAOZUOR(object.optString("s_CAOZUOR"));
        entity.setKeHuQM(object.optString("s_KeHuQM"));
        entity.setZhaoPianMC(object.optString("s_ZhaoPianMC"));
        entity.setX1(object.optString("s_X1"));
        entity.setY1(object.optString("s_Y1"));

        entity.setResultInfo(object.optJSONObject("result"));

        return entity;
    }

    public static List<ExternalWorkMainInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ExternalWorkMainInfoEntity> list = new ArrayList<ExternalWorkMainInfoEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ExternalWorkMainInfoEntity entity = ExternalWorkMainInfoEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
