package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExternalWorkDetailInfoEntity {
    /// <summary>
    /// 任务编号
    /// </summary>
    private int _RENWUBH;
    /// <summary>
    /// 序号
    /// </summary>
    private int _XH;
    /// <summary>
    /// 简号
    /// </summary>
    private String _JH;
    /// <summary>
    /// 简号比率
    /// </summary>
    private double _BILI;
    /// <summary>
    /// 简号类型(0:原简号 1：新简号)
    /// </summary>
    private int _JHTYPE;
    /// <summary>
    /// 客户号
    /// </summary>
    private String _CID;
    /// <summary>
    /// 抄表年月
    /// </summary>
    private int _CHAOBIAONY;
    /// <summary>
    /// 栋单元房
    /// </summary>
    private String _DZ;
    /// <summary>
    /// 水表号
    /// </summary>
    private String _TIAOXINGM;
    /// <summary>
    /// 原水量
    /// </summary>
    private int _YONGSHUIL;
    /// <summary>
    /// 调减水量
    /// </summary>
    private int _TIAOJIANSL;
    /// <summary>
    /// 调整金额
    /// </summary>
    private double _TIAOZHENGJE;
    /// <summary>
    /// 申请缘由
    /// </summary>
    private String _SHENQINGYY;
    /// <summary>
    /// 备注
    /// </summary>
    private String _BEIZHU;
    /// <summary>
    /// 操作人
    /// </summary>
    private String _CAOZUOR;
    /// <summary>
    /// 原抄码
    /// </summary>
    private int _YUANCHAOM;

    public ExternalWorkDetailInfoEntity() {

    }

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public int getXH() {
        return _XH;
    }

    public void setXH(int XH) {
        _XH = XH;
    }

    public String getJH() {
        return _JH;
    }

    public void setJH(String JH) {
        _JH = JH;
    }

    public double getBILI() {
        return _BILI;
    }

    public void setBILI(double BILI) {
        _BILI = BILI;
    }

    public int getJHTYPE() {
        return _JHTYPE;
    }

    public void setJHTYPE(int JHTYPE) {
        _JHTYPE = JHTYPE;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public int getCHAOBIAONY() {
        return _CHAOBIAONY;
    }

    public void setCHAOBIAONY(int CHAOBIAONY) {
        _CHAOBIAONY = CHAOBIAONY;
    }

    public String getDZ() {
        return _DZ;
    }

    public void setDZ(String DZ) {
        _DZ = DZ;
    }

    public String getTIAOXINGM() {
        return _TIAOXINGM;
    }

    public void setTIAOXINGM(String TIAOXINGM) {
        _TIAOXINGM = TIAOXINGM;
    }

    public int getYONGSHUIL() {
        return _YONGSHUIL;
    }

    public void setYONGSHUIL(int YONGSHUIL) {
        _YONGSHUIL = YONGSHUIL;
    }

    public int getTIAOJIANSL() {
        return _TIAOJIANSL;
    }

    public void setTIAOJIANSL(int TIAOJIANSL) {
        _TIAOJIANSL = TIAOJIANSL;
    }

    public double getTIAOZHENGJE() {
        return _TIAOZHENGJE;
    }

    public void setTIAOZHENGJE(double TIAOZHENGJE) {
        _TIAOZHENGJE = TIAOZHENGJE;
    }

    public String getSHENQINGYY() {
        return _SHENQINGYY;
    }

    public void setSHENQINGYY(String SHENQINGYY) {
        _SHENQINGYY = SHENQINGYY;
    }

    public String getBEIZHU() {
        return _BEIZHU;
    }

    public void setBEIZHU(String BEIZHU) {
        _BEIZHU = BEIZHU;
    }

    public String getCAOZUOR() {
        return _CAOZUOR;
    }

    public void setCAOZUOR(String CAOZUOR) {
        _CAOZUOR = CAOZUOR;
    }

    public int getYUANCHAOM() {
        return _YUANCHAOM;
    }

    public void setYUANCHAOM(int YUANCHAOM) {
        _YUANCHAOM = YUANCHAOM;
    }

    public static ExternalWorkDetailInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        ExternalWorkDetailInfoEntity entity = new ExternalWorkDetailInfoEntity();

        entity.setRENWUBH(object.optInt("i_RENWUBH"));
        entity.setXH(object.optInt("i_XH"));
        entity.setJH(object.optString("s_JH"));
        entity.setBILI(object.optDouble("n_BILI"));
        entity.setJHTYPE(object.optInt("i_JHTYPE"));
        entity.setCID(object.optString("s_CID"));
        entity.setCHAOBIAONY(object.optInt("i_CHAOBIAONY"));
        entity.setDZ(object.optString("s_DZ"));
        entity.setTIAOXINGM(object.optString("s_TIAOXINGM"));
        entity.setYONGSHUIL(object.optInt("i_YONGSHUIL"));
        entity.setTIAOJIANSL(object.optInt("i_TIAOJIANSL"));
        entity.setTIAOZHENGJE(object.optDouble("n_TIAOZHENGJE"));
        entity.setSHENQINGYY(object.optString("s_SHENQINGYY"));
        entity.setBEIZHU(object.optString("s_BEIZHU"));
        entity.setCAOZUOR(object.optString("s_CAOZUOR"));
        entity.setYUANCHAOM(object.optInt("i_YUANCHAOM"));

        return entity;
    }

    public static List<ExternalWorkDetailInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ExternalWorkDetailInfoEntity> list = new ArrayList<ExternalWorkDetailInfoEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ExternalWorkDetailInfoEntity entity = ExternalWorkDetailInfoEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
