package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DingQiHBSearchResultEntity {
    private String S_KEHUID;    //--客户编号
    private String S_CID;       //    --账号
    private String S_CH;        //     --册本号
    private String S_HM;        //     --户名
    private String S_DZ;        //     --地址
    private String S_DIANHUA;   //  --电话
    private String S_MINGCHENG;    //--联系人
    private int I_GUZHANGYY;    // --故障原因
    private long D_YUYUERQ;       //   --预约日期
    private int I_BENCICM;       // --上次抄码
    private int I_CHAOJIANSL;    //--上期水量
    private int I_PINGJUNL1;     //  --平均水量
    private int I_GONGDANLX;     //   --工单类型
    private int I_KID;           //   --流程ID
    private int ID;              //  --主键ID
    private int I_YANGHUGDLX;    //  --养护工单类型
    private String S_GONGDANBH;     //   --工单编号
    private String S_ST;            //  --站点

    public DingQiHBSearchResultEntity() {

    }

    public void setS_KEHUID(String kehuid) {
        S_KEHUID = kehuid;
    }

    public String getS_KEHUID() {
        return S_KEHUID;
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

    public void setS_DIANHUA(String dianhua) {
        S_DIANHUA = dianhua;
    }

    public String getS_DIANHUA() {
        return S_DIANHUA;
    }

    public void setS_MINGCHENG(String mingcheng) {
        S_MINGCHENG = mingcheng;
    }

    public String getS_MINGCHENG() {
        return S_MINGCHENG;
    }

    public void setI_GUZHANGYY(int guzhangyy) {
        I_GUZHANGYY = guzhangyy;
    }

    public int getI_GUZHANGYY() {
        return I_GUZHANGYY;
    }

    public void setD_YUYUERQ(long yuyuerq) {
        D_YUYUERQ = yuyuerq;
    }

    public long getD_YUYUERQ() {
        return D_YUYUERQ;
    }

    public void setI_BENCICM(int bencicm) {
        I_BENCICM = bencicm;
    }

    public int getI_BENCICM() {
        return I_BENCICM;
    }

    public void setI_CHAOJIANSL(int chaojiansl) {
        I_CHAOJIANSL = chaojiansl;
    }

    public int getI_CHAOJIANSL() {
        return I_CHAOJIANSL;
    }

    public void setI_PINGJUNL1(int pingjunl1) {
        I_PINGJUNL1 = pingjunl1;
    }

    public int getI_PINGJUNL1() {
        return I_PINGJUNL1;
    }

    public void setI_GONGDANLX(int gongdanlx) {
        I_GONGDANLX = gongdanlx;
    }

    public int getI_GONGDANLX() {
        return I_GONGDANLX;
    }

    public void setI_KID(int kid) {
        I_KID = kid;
    }

    public int getI_KID() {
        return I_KID;
    }

    public void setID(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setI_YANGHUGDLX(int yanghugdlx) {
        I_YANGHUGDLX = yanghugdlx;
    }

    public int getI_YANGHUGDLX() {
        return I_YANGHUGDLX;
    }

    public void setS_GONGDANBH(String gongdanbh) {
        S_GONGDANBH = gongdanbh;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_ST(String st) {
        S_ST = st;
    }

    public String getS_ST() {
        return S_ST;
    }

    public static JSONObject toJSON(DingQiHBSearchResultEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_KEHUID", entity.getS_KEHUID());
            obj.put("S_CID", entity.getS_CID());
            obj.put("S_CH", entity.getS_CH());
            obj.put("S_HM", entity.getS_HM());
            obj.put("S_DZ", entity.getS_DZ());
            obj.put("S_DIANHUA", entity.getS_DIANHUA());
            obj.put("S_MINGCHENG", entity.getS_MINGCHENG());
            obj.put("I_GUZHANGYY", entity.getI_GUZHANGYY());
            obj.put("D_YUYUERQ", entity.getD_YUYUERQ());
            obj.put("I_BENCICM", entity.getI_BENCICM());
            obj.put("I_CHAOJIANSL", entity.getI_CHAOJIANSL());
            obj.put("I_PINGJUNL1", entity.getI_PINGJUNL1());
            obj.put("I_GONGDANLX", entity.getI_GONGDANLX());
            obj.put("I_KID", entity.getI_KID());
            obj.put("ID", entity.getID());
            obj.put("I_YANGHUGDLX", entity.getI_YANGHUGDLX());
            obj.put("S_GONGDANBH", entity.getS_GONGDANBH());
            obj.put("S_ST", entity.getS_ST());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_KEHUID", getS_KEHUID());
            obj.put("S_CID", getS_CID());
            obj.put("S_CH", getS_CH());
            obj.put("S_HM", getS_HM());
            obj.put("S_DZ", getS_DZ());
            obj.put("S_DIANHUA", getS_DIANHUA());
            obj.put("S_MINGCHENG", getS_MINGCHENG());
            obj.put("I_GUZHANGYY", getI_GUZHANGYY());
            obj.put("D_YUYUERQ", getD_YUYUERQ());
            obj.put("I_BENCICM", getI_BENCICM());
            obj.put("I_CHAOJIANSL", getI_CHAOJIANSL());
            obj.put("I_PINGJUNL1", getI_PINGJUNL1());
            obj.put("I_GONGDANLX", getI_GONGDANLX());
            obj.put("I_KID", getI_KID());
            obj.put("ID", getID());
            obj.put("I_YANGHUGDLX", getI_YANGHUGDLX());
            obj.put("S_GONGDANBH", getS_GONGDANBH());
            obj.put("S_ST", getS_ST());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public static DingQiHBSearchResultEntity fromJSON(JSONObject object) {
        DingQiHBSearchResultEntity entity = new DingQiHBSearchResultEntity();
        entity.setS_KEHUID(object.optString("s_KEHUID"));
        entity.setS_CID(object.optString("s_CID"));
        entity.setS_CH(object.optString("s_CH"));
        entity.setS_HM(object.optString("s_HM"));
        entity.setS_DZ(object.optString("s_DZ"));
        entity.setS_DIANHUA(object.optString("s_DIANHUA"));
        entity.setS_MINGCHENG(object.optString("s_MINGCHENG"));
        entity.setI_GUZHANGYY(object.optInt("i_GUZHANGYY"));
        entity.setD_YUYUERQ(object.optLong("d_YUYUERQ"));
        entity.setI_BENCICM(object.optInt("i_BENCICM"));
        entity.setI_CHAOJIANSL(object.optInt("i_CHAOJIANSL"));
        entity.setI_PINGJUNL1(object.optInt("i_PINGJUNL1"));
        entity.setI_GONGDANLX(object.optInt("i_GONGDANLX"));
        entity.setI_KID(object.optInt("i_KID"));
        entity.setID(object.optInt("iD"));
        entity.setI_YANGHUGDLX(object.optInt("i_YANGHUGDLX"));
        entity.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        entity.setS_ST(object.optString("s_ST"));

        return entity;
    }

    public static List<DingQiHBSearchResultEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<DingQiHBSearchResultEntity> list = new ArrayList<DingQiHBSearchResultEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            DingQiHBSearchResultEntity entity = DingQiHBSearchResultEntity.fromJSON(object);
            list.add(entity);
        }
        return list;

    }
}
