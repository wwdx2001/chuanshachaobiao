package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiMaTZXXEntity {
    private String S_CID;       //--账号
    private String S_CH;        // --册号
    private String S_ST;        // --站点
    private String S_HM;        // --户名
    private String S_DZ;        // --用水地址
    private int I_CHAOBIAON;    // --抄表年
    private int I_CHAOBIAOY;    // --抄表月
    private int I_CC;           // --抄次
    private long D_CHAOBIAORQ;      // --抄表日期
    private String S_KUAIJIEJ_PC;   // --抄表状态
    private int I_CHAOJIANSL;       // --抄见水量
    private long D_XIAZAISJ;        // --下载时间
    private String S_KEHUID;        // --客户编号
    private int I_JIUBIAOCM;        // --旧表抄码
    private int I_XINBIAODM;        // --新表底码
    private int I_BENCICM;          // --当前抄码

    public DiMaTZXXEntity() {

    }

    public void setS_CID(String cid) {
        S_CID = cid;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CH(String ch) {
        S_CH = ch;
    }

    public String getS_CH() {
        return S_CH;
    }

    public void setS_ST(String st) {
        S_ST = st;
    }

    public String getS_ST() {
        return S_ST;
    }

    public void setS_HM(String hm) {
        S_HM = hm;
    }

    public String getS_HM() {
        return S_HM;
    }

    public void setS_DZ(String dz) {
        S_DZ = dz;
    }

    public String getS_DZ() {
        return S_DZ;
    }

    public void setI_CHAOBIAON(int i_chaobiaon) {
        I_CHAOBIAON = i_chaobiaon;
    }

    public int getI_CHAOBIAON() {
        return I_CHAOBIAON;
    }

    public void setI_CHAOBIAOY(int i_chaobiaoy) {
        I_CHAOBIAOY = i_chaobiaoy;
    }

    public int getI_CHAOBIAOY() {
        return I_CHAOBIAOY;
    }

    public void setI_CC(int i_cc) {
        I_CC = i_cc;
    }

    public int getI_CC() {
        return I_CC;
    }

    public void setD_CHAOBIAORQ(long d_chaobiaorq) {
        D_CHAOBIAORQ = d_chaobiaorq;
    }

    public long getD_CHAOBIAORQ() {
        return D_CHAOBIAORQ;
    }

    public void setS_KUAIJIEJ_PC(String s_kuaijiej_pc) {
        S_KUAIJIEJ_PC = s_kuaijiej_pc;
    }

    public String getS_KUAIJIEJ_PC() {
        return S_KUAIJIEJ_PC;
    }

    public void setI_CHAOJIANSL(int i_chaojiansl) {
        I_CHAOJIANSL = i_chaojiansl;
    }

    public int getI_CHAOJIANSL() {
        return I_CHAOJIANSL;
    }

    public void setD_XIAZAISJ(long d_xiazaisj) {
        D_XIAZAISJ = d_xiazaisj;
    }

    public long getD_XIAZAISJ() {
        return D_XIAZAISJ;
    }

    public void setS_KEHUID(String s_kehuid) {
        S_KEHUID = s_kehuid;
    }

    public String getS_KEHUID() {
        return S_KEHUID;
    }

    public void setI_JIUBIAOCM(int i_jiubiaocm) {
        I_JIUBIAOCM = i_jiubiaocm;
    }

    public int getI_JIUBIAOCM() {
        return I_JIUBIAOCM;
    }

    public void setI_XINBIAODM(int i_xinbiaodm) {
        I_XINBIAODM = i_xinbiaodm;
    }

    public int getI_XINBIAODM() {
        return I_XINBIAODM;
    }

    public void setI_BENCICM(int i_bencicm) {
        I_BENCICM = i_bencicm;
    }

    public int getI_BENCICM() {
        return I_BENCICM;
    }

    public static DiMaTZXXEntity fromJSON(JSONObject object) {
        DiMaTZXXEntity entity = new DiMaTZXXEntity();
        entity.setS_CID(object.optString("s_CID"));
        entity.setS_CH(object.optString("s_CH"));
        entity.setS_ST(object.optString("s_ST"));
        entity.setS_HM(object.optString("s_HM"));
        entity.setS_DZ(object.optString("s_DZ"));
        entity.setI_CHAOBIAON(object.optInt("i_CHAOBIAON"));
        entity.setI_CHAOBIAOY(object.optInt("i_CHAOBIAOY"));
        entity.setI_CC(object.optInt("i_CC"));
        entity.setD_CHAOBIAORQ(object.optLong("s_CHAOBIAORQ"));
        entity.setS_KUAIJIEJ_PC(object.optString("s_KUAIJIEJ_PC"));
        entity.setI_CHAOJIANSL(object.optInt("i_CHAOJIANSL"));
        entity.setD_XIAZAISJ(object.optLong("d_XIAZAISJ"));
        entity.setS_KEHUID(object.optString("s_KEHUID"));
        entity.setI_JIUBIAOCM(object.optInt("i_JIUBIAOCM"));
        entity.setI_XINBIAODM(object.optInt("i_XINBIAODM"));
        entity.setI_BENCICM(object.optInt("i_BENCICM"));
        return entity;
    }

    public static List<DiMaTZXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<DiMaTZXXEntity> list = new ArrayList<DiMaTZXXEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            DiMaTZXXEntity entity = DiMaTZXXEntity.fromJSON(object);
            list.add(entity);
        }

        return list;

    }
}
