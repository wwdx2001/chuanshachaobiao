package com.sh3h.serverprovider.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class DiMaTZEntity {
    /// 账号
    private String S_CID;

    /// 工单编号
    private String S_GongDanBH;

    /// 当前抄码
    private String I_BENCICM;

    /// 新表底码
    private String I_XINBIAODM;

    /// 旧表抄码
    private String I_JIUBIAOCM;

    /// 原因
    private String YuanYin;

    /// 站点
    private String S_ST;

    public DiMaTZEntity() {

    }

    public void setS_CID(String cid) {
        S_CID = cid;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_GongDanBH(String gongDanBH) {
        S_GongDanBH= gongDanBH;
    }

    public String getS_GongDanBH() {
        return S_GongDanBH;
    }

    public void setI_BENCICM(String bencicm) {
        I_BENCICM = bencicm;
    }

    public String getI_BENCICM() {
        return I_BENCICM;
    }

    public void setI_XINBIAODM(String xinbiaodm) {
        I_XINBIAODM = xinbiaodm;
    }

    public String getI_XINBIAODM() {
        return I_XINBIAODM;
    }

    public void setI_JIUBIAOCM(String jiubiaocm) {
        I_JIUBIAOCM = jiubiaocm;
    }

    public String getI_JIUBIAOCM() {
        return I_JIUBIAOCM;
    }

    public void setYuanYin(String yuanYin) {
        YuanYin = yuanYin;
    }

    public String getYuanYin() {
        return YuanYin;
    }

    public void setS_ST(String st) {
        S_ST = st;
    }

    public String getS_ST() {
        return S_ST;
    }

    public static JSONObject toJSON(DiMaTZEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_CID", entity.getS_CID());
            obj.put("S_GongDanBH", entity.getS_GongDanBH());
            obj.put("I_BENCICM", entity.getI_BENCICM());
            obj.put("I_XINBIAODM", entity.getI_XINBIAODM());
            obj.put("I_JIUBIAOCM", entity.getI_JIUBIAOCM());
            obj.put("YuanYin", entity.getYuanYin());
            obj.put("S_ST", entity.getS_ST());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_CID", getS_CID());
            obj.put("S_GongDanBH", getS_GongDanBH());
            obj.put("I_BENCICM", getI_BENCICM());
            obj.put("I_XINBIAODM", getI_XINBIAODM());
            obj.put("I_JIUBIAOCM", getI_JIUBIAOCM());
            obj.put("YuanYin", getYuanYin());
            obj.put("S_ST", getS_ST());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }
}
