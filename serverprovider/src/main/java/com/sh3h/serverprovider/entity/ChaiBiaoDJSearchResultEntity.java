package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChaiBiaoDJSearchResultEntity {

    public ChaiBiaoDJSearchResultEntity() {

    }

    public String S_KEHUID;    //客户编号
    public String S_CID;   // 	账号
    public String S_CH;    //	册本号
    public String S_HM;        //	户名
    public String S_DZ;        //	地址
    public String S_DIANHUA;    //	电话
    public String S_MINGCHENG;//	联系人
    public int I_GUZHANGYY;    //	故障原因
    public long D_YUYUERQ;        //	预约日期
    public int I_BENCICM;        //	上次抄码
    public int I_CHAOJIANSL;    //	上期水量
    public int I_PINGJUNL1;    //	平均水量
    public int I_GONGDANLX;    //	工单类型
    public int i_kid;            //	流程ID
    public int id;            //	主键ID
    public int I_YANGHUGDLX;    //	养护工单类型
    public String S_GONGDANBH;    //	工单编号
    public String s_ST;    //	站点

    public String getS_KEHUID() {
        return S_KEHUID;
    }

    public void setS_KEHUID(String s_KEHUID) {
        S_KEHUID = s_KEHUID;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getS_CH() {
        return S_CH;
    }

    public void setS_CH(String s_CH) {
        S_CH = s_CH;
    }

    public String getS_HM() {
        return S_HM;
    }

    public void setS_HM(String s_HM) {
        S_HM = s_HM;
    }

    public String getS_DZ() {
        return S_DZ;
    }

    public void setS_DZ(String s_DZ) {
        S_DZ = s_DZ;
    }

    public String getS_DIANHUA() {
        return S_DIANHUA;
    }

    public void setS_DIANHUA(String s_DIANHUA) {
        S_DIANHUA = s_DIANHUA;
    }

    public String getS_MINGCHENG() {
        return S_MINGCHENG;
    }

    public void setS_MINGCHENG(String s_MINGCHENG) {
        S_MINGCHENG = s_MINGCHENG;
    }

    public int getI_GUZHANGYY() {
        return I_GUZHANGYY;
    }

    public void setI_GUZHANGYY(int i_GUZHANGYY) {
        I_GUZHANGYY = i_GUZHANGYY;
    }

    public long getD_YUYUERQ() {
        return D_YUYUERQ;
    }

    public void setD_YUYUERQ(long d_YUYUERQ) {
        D_YUYUERQ = d_YUYUERQ;
    }

    public int getI_BENCICM() {
        return I_BENCICM;
    }

    public void setI_BENCICM(int i_BENCICM) {
        I_BENCICM = i_BENCICM;
    }

    public int getI_CHAOJIANSL() {
        return I_CHAOJIANSL;
    }

    public void setI_CHAOJIANSL(int i_CHAOJIANSL) {
        I_CHAOJIANSL = i_CHAOJIANSL;
    }

    public int getI_PINGJUNL1() {
        return I_PINGJUNL1;
    }

    public void setI_PINGJUNL1(int i_PINGJUNL1) {
        I_PINGJUNL1 = i_PINGJUNL1;
    }

    public int getI_GONGDANLX() {
        return I_GONGDANLX;
    }

    public void setI_GONGDANLX(int i_GONGDANLX) {
        I_GONGDANLX = i_GONGDANLX;
    }

    public int getI_kid() {
        return i_kid;
    }

    public void setI_kid(int i_kid) {
        this.i_kid = i_kid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getI_YANGHUGDLX() {
        return I_YANGHUGDLX;
    }

    public void setI_YANGHUGDLX(int i_YANGHUGDLX) {
        I_YANGHUGDLX = i_YANGHUGDLX;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public String getS_ST() {
        return s_ST;
    }

    public void setS_ST(String s_ST) {
        this.s_ST = s_ST;
    }

    public static ChaiBiaoDJSearchResultEntity fromJSON(JSONObject object) {
        ChaiBiaoDJSearchResultEntity chaoBiaoDJResult = new ChaiBiaoDJSearchResultEntity();
        chaoBiaoDJResult.setS_KEHUID(object.optString("s_KEHUID"));
        chaoBiaoDJResult.setS_CID(object.optString("s_CID"));
        chaoBiaoDJResult.setS_CH(object.optString("s_CH"));
        chaoBiaoDJResult.setS_HM(object.optString("s_HM"));
        chaoBiaoDJResult.setS_DZ(object.optString("s_DZ"));
        chaoBiaoDJResult.setS_DIANHUA(object.optString("s_DIANHUA"));
        chaoBiaoDJResult.setS_MINGCHENG(object.optString("s_MINGCHENG"));
        chaoBiaoDJResult.setI_GUZHANGYY(object.optInt("i_GUZHANGYY"));
        chaoBiaoDJResult.setD_YUYUERQ(object.optLong("d_YUYUERQ"));
        chaoBiaoDJResult.setI_BENCICM(object.optInt("i_BENCICM"));
        chaoBiaoDJResult.setI_CHAOJIANSL(object.optInt("i_CHAOJIANSL"));
        chaoBiaoDJResult.setI_PINGJUNL1(object.optInt("i_PINGJUNL1"));
        chaoBiaoDJResult.setI_GONGDANLX(object.optInt("i_GONGDANLX"));
        chaoBiaoDJResult.setI_kid(object.optInt("i_kid"));
        chaoBiaoDJResult.setId(object.optInt("id"));
        chaoBiaoDJResult.setI_YANGHUGDLX(object.optInt("i_YANGHUGDLX"));
        chaoBiaoDJResult.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        chaoBiaoDJResult.setS_ST(object.optString("s_ST"));
        return chaoBiaoDJResult;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<ChaiBiaoDJSearchResultEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ChaiBiaoDJSearchResultEntity> list = new ArrayList<ChaiBiaoDJSearchResultEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ChaiBiaoDJSearchResultEntity entity = ChaiBiaoDJSearchResultEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }
}
