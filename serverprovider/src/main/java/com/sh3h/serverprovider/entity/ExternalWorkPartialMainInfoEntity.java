package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ExternalWorkPartialMainInfoEntity {
    /// <summary>
    /// 任务编号
    /// </summary>
    private int _RENWUBH;
    /// <summary>
    /// 用户号
    /// </summary>
    private String _CID;
    /// <summary>
    /// 工单类型
    /// </summary>
    private int _SUBTYPE;
    ///申请人核实人口数【人口性质变更；】
    /// </summary>
    private int _HESHIRKS;
    ///申请人核实蹲位数【公测审批；】
    /// </summary>
    private int _HESHIDWS;
    /// <summary>
    /// 施工日期
    /// </summary>
    private long _SHIGONGRQ;
    /// <summary>
    /// 施工人(派单接收人）
    /// </summary>
    private String _SHIGONGR;
    /// <summary>
    /// 核查情况【水表自转核查】
    /// </summary>
    private String _HECHAQK;
    /// <summary>
    /// 审核意见【用水比例调整；水表自转核查】
    /// </summary>
    private String _SHENHEYJ;
    /// <summary>
    /// 1:同意,2:不同意，【用水性质变更用-复核意见；用水比例调整-审批意见；调减数量-复核意见】
    /// </summary>
    private int _SHENPIJG;
    /// <summary>
    /// 复核意见原因【调减水量-】
    /// </summary>
    private String _FUHEYJYY;
    /// <summary>
    /// 来源编号
    /// </summary>
    private int _LAIYUANBH;
    /// <summary>
    /// 客户签名（图片名称）
    /// </summary>
    private String _KeHuQM;
    /// <summary>
    /// 照片名称（name1,name2,...）
    /// </summary>
    private String _ZhaoPianMC;
    // x坐标
    private String _X1;
    // y坐标
    private String _Y1;
    // 备注
    private String _BEIZHU;

    public ExternalWorkPartialMainInfoEntity() {

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

    public int getSUBTYPE() {
        return _SUBTYPE;
    }

    public void setSUBTYPE(int SUBTYPE) {
        _SUBTYPE = SUBTYPE;
    }

    public int getHESHIRKS() {
        return _HESHIRKS;
    }

    public void setHESHIRKS(int HESHIRKS) {
        _HESHIRKS = HESHIRKS;
    }

    public int getHESHIDWS() {
        return _HESHIDWS;
    }

    public void setHESHIDWS(int HESHIDWS) {
        _HESHIDWS = HESHIDWS;
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

    public String getHECHAQK() {
        return _HECHAQK;
    }

    public void setHECHAQK(String HECHAQK) {
        _HECHAQK = HECHAQK;
    }

    public String getSHENHEYJ() {
        return _SHENHEYJ;
    }

    public void setSHENHEYJ(String SHENHEYJ) {
        _SHENHEYJ = SHENHEYJ;
    }

    public int getSHENPIJG() {
        return _SHENPIJG;
    }

    public void setSHENPIJG(int SHENPIJG) {
        _SHENPIJG = SHENPIJG;
    }

    public String getFUHEYJYY() {
        return _FUHEYJYY;
    }

    public void setFUHEYJYY(String FUHEYJYY) {
        _FUHEYJYY = FUHEYJYY;
    }

    public int getLAIYUANBH() {
        return _LAIYUANBH;
    }

    public void setLAIYUANBH(int LAIYUANBH) {
        _LAIYUANBH = LAIYUANBH;
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

    public String getBEIZHU() {
        return _BEIZHU;
    }

    public void setBEIZHU(String BEIZHU) {
        _BEIZHU = BEIZHU;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", getRENWUBH());
            obj.put("S_CID", getCID());
            obj.put("I_SUBTYPE", getSUBTYPE());
            obj.put("I_HESHIRKS", getHESHIRKS());
            obj.put("I_HESHIDWS", getHESHIDWS());
            obj.put("D_SHIGONGRQ", getSHIGONGRQ());
            obj.put("S_SHIGONGR", getSHIGONGR());
            obj.put("S_HECHAQK", getHECHAQK());
            obj.put("S_SHENHEYJ", getSHENHEYJ());
            obj.put("I_SHENPIJG", getSHENPIJG());
            obj.put("S_FUHEYJYY", getFUHEYJYY());
            obj.put("I_LAIYUANBH", getLAIYUANBH());
            obj.put("S_KeHuQM", getKeHuQM());
            obj.put("S_ZhaoPianMC", getZhaoPianMC());
            obj.put("S_X1", getX1());
            obj.put("S_Y1", getY1());
            obj.put("S_BEIZHU", getBEIZHU());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONObject toJSON(ExternalWorkPartialMainInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", entity.getRENWUBH());
            obj.put("S_CID", entity.getCID());
            obj.put("I_SUBTYPE", entity.getSUBTYPE());
            obj.put("I_HESHIRKS", entity.getHESHIRKS());
            obj.put("I_HESHIDWS", entity.getHESHIDWS());
            obj.put("D_SHIGONGRQ", entity.getSHIGONGRQ());
            obj.put("S_SHIGONGR", entity.getSHIGONGR());
            obj.put("S_HECHAQK", entity.getHECHAQK());
            obj.put("S_SHENHEYJ", entity.getSHENHEYJ());
            obj.put("I_SHENPIJG", entity.getSHENPIJG());
            obj.put("S_FUHEYJYY", entity.getFUHEYJYY());
            obj.put("I_LAIYUANBH", entity.getLAIYUANBH());
            obj.put("S_KeHuQM", entity.getKeHuQM());
            obj.put("S_ZhaoPianMC", entity.getZhaoPianMC());
            obj.put("S_X1", entity.getX1());
            obj.put("S_Y1", entity.getY1());
            obj.put("S_BEIZHU", entity.getBEIZHU());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONArray toJSONArray(List<ExternalWorkPartialMainInfoEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (ExternalWorkPartialMainInfoEntity entity : list) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }

        return array;
    }
}
