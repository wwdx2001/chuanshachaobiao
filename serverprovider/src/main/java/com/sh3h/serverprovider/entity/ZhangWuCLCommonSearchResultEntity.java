package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ZhangWuCLCommonSearchResultEntity {

    public ZhangWuCLCommonSearchResultEntity() {

    }

    public int ID;
    public String S_TableName;
    public String S_CID;
    public String S_HM;
    public String S_DZ;
    public String DL_KAIZHANG;
    public String S_ZHUANGTAIMC;
    public int I_KAIZHANGSL;
    public double N_SHUIFEI;
    public double N_PAISHUIF;
    public double N_KAIZHANGJE;
    public int I_SHANGCICM;
    public int I_FEEID;
    private String S_JH;
    private int I_TIAOJIAH;

    private int I_CHULI;
    private int I_CHULILX;
    private int I_KID;
    private String S_SHUIBIAOFL;
    private int I_HUAZHANGBH;

    public int getI_KID() {
        return I_KID;
    }

    public void setI_KID(int i_KID) {
        I_KID = i_KID;
    }

    public String getS_SHUIBIAOFL() {
        return S_SHUIBIAOFL;
    }

    public void setS_SHUIBIAOFL(String s_SHUIBIAOFL) {
        S_SHUIBIAOFL = s_SHUIBIAOFL;
    }

    public int getI_HUAZHANGBH() {
        return I_HUAZHANGBH;
    }

    public void setI_HUAZHANGBH(int i_HUAZHANGBH) {
        I_HUAZHANGBH = i_HUAZHANGBH;
    }

    public int getI_CHULI() {
        return I_CHULI;
    }

    public void setI_CHULI(int i_CHULI) {
        I_CHULI = i_CHULI;
    }

    public int getI_CHULILX() {
        return I_CHULILX;
    }

    public void setI_CHULILX(int i_CHULILX) {
        I_CHULILX = i_CHULILX;
    }

    public String getS_JH() {
        return S_JH;
    }

    public void setS_JH(String s_JH) {
        S_JH = s_JH;
    }

    public int getI_TIAOJIAH() {
        return I_TIAOJIAH;
    }

    public void setI_TIAOJIAH(int i_TIAOJIAH) {
        I_TIAOJIAH = i_TIAOJIAH;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getS_TableName() {
        return S_TableName;
    }

    public void setS_TableName(String s_TableName) {
        S_TableName = s_TableName;
    }

    public int getI_FEEID() {
        return I_FEEID;
    }

    public void setI_FEEID(int i_FEEID) {
        I_FEEID = i_FEEID;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
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

    public String getDL_KAIZHANG() {
        return DL_KAIZHANG;
    }

    public void setDL_KAIZHANG(String DL_KAIZHANG) {
        this.DL_KAIZHANG = DL_KAIZHANG;
    }

    public String getS_ZHUANGTAIMC() {
        return S_ZHUANGTAIMC;
    }

    public void setS_ZHUANGTAIMC(String s_ZHUANGTAIMC) {
        S_ZHUANGTAIMC = s_ZHUANGTAIMC;
    }

    public int getI_KAIZHANGSL() {
        return I_KAIZHANGSL;
    }

    public void setI_KAIZHANGSL(int i_KAIZHANGSL) {
        I_KAIZHANGSL = i_KAIZHANGSL;
    }

    public double getN_SHUIFEI() {
        return N_SHUIFEI;
    }

    public void setN_SHUIFEI(double n_SHUIFEI) {
        N_SHUIFEI = n_SHUIFEI;
    }

    public double getN_PAISHUIF() {
        return N_PAISHUIF;
    }

    public void setN_PAISHUIF(double n_PAISHUIF) {
        N_PAISHUIF = n_PAISHUIF;
    }

    public double getN_KAIZHANGJE() {
        return N_KAIZHANGJE;
    }

    public void setN_KAIZHANGJE(double n_KAIZHANGJE) {
        N_KAIZHANGJE = n_KAIZHANGJE;
    }

    public int getI_SHANGCICM() {
        return I_SHANGCICM;
    }

    public void setI_SHANGCICM(int i_SHANGCICM) {
        I_SHANGCICM = i_SHANGCICM;
    }

    public static ZhangWuCLCommonSearchResultEntity fromJSON(JSONObject object) {
        ZhangWuCLCommonSearchResultEntity zwcl = new ZhangWuCLCommonSearchResultEntity();
        zwcl.setID(object.optInt("iD"));
        zwcl.setS_TableName(object.optString("s_TableName"));
        zwcl.setS_CID(object.optString("s_CID"));
        zwcl.setS_HM(object.optString("s_HM"));
        zwcl.setS_DZ(object.optString("s_DZ"));
        zwcl.setDL_KAIZHANG(object.optString("dL_KAIZHANG"));
        zwcl.setS_ZHUANGTAIMC(object.optString("s_ZHUANGTAIMC"));
        zwcl.setI_KAIZHANGSL(object.optInt("i_KAIZHANGSL"));
        zwcl.setN_SHUIFEI(object.optDouble("n_SHUIFEI"));
        zwcl.setN_PAISHUIF(object.optDouble("n_PAISHUIF"));
        zwcl.setN_KAIZHANGJE(object.optDouble("n_KAIZHANGJE"));
        zwcl.setI_SHANGCICM(object.optInt("i_SHANGCICM"));
        zwcl.setI_FEEID(object.optInt("i_FEEID"));
        zwcl.setI_TIAOJIAH(object.optInt("i_TIAOJIAH"));
        zwcl.setS_JH(object.optString("s_JH"));
        zwcl.setI_CHULI(object.optInt("i_CHULI"));
        zwcl.setI_CHULILX(object.optInt("i_CHULILX"));
        zwcl.setS_JH(object.optString("s_SHUIBIAOFL"));
        zwcl.setI_CHULI(object.optInt("i_KID"));
        zwcl.setI_CHULILX(object.optInt("i_HUAZHANGBH"));
        return zwcl;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<ZhangWuCLCommonSearchResultEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ZhangWuCLCommonSearchResultEntity> list = new ArrayList<ZhangWuCLCommonSearchResultEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ZhangWuCLCommonSearchResultEntity entity = ZhangWuCLCommonSearchResultEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<ZhangWuCLCommonSearchResultEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (ZhangWuCLCommonSearchResultEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(ZhangWuCLCommonSearchResultEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_CID", entity.getS_CID());
            obj.put("S_HM", entity.getS_HM());
            obj.put("S_DZ", entity.getS_DZ());
            obj.put("ID", entity.getID());
            obj.put("S_TableName", entity.getS_TableName());
            obj.put("DL_KAIZHANG", entity.getDL_KAIZHANG());
            obj.put("S_ZHUANGTAIMC", entity.getS_ZHUANGTAIMC());
            obj.put("I_KAIZHANGSL", entity.getI_KAIZHANGSL());
            obj.put("N_SHUIFEI", entity.getN_SHUIFEI());
            obj.put("N_PAISHUIF", entity.getN_PAISHUIF());
            obj.put("N_KAIZHANGJE", entity.getN_KAIZHANGJE());
            obj.put("I_SHANGCICM", entity.getI_SHANGCICM());
            obj.put("I_FEEID", entity.getI_FEEID());
            obj.put("S_JH", entity.getS_JH());
            obj.put("I_TIAOJIAH", entity.getI_TIAOJIAH());
            obj.put("I_CHULI", entity.getI_CHULI());
            obj.put("I_CHULILX", entity.getI_CHULILX());
            obj.put("I_KID", entity.getI_KID());
            obj.put("I_HUAZHANGBH", entity.getI_HUAZHANGBH());
            obj.put("S_SHUIBIAOFL", entity.getS_SHUIBIAOFL());
        } catch (JSONException e) {
            return null;
        }

        return obj;
    }

}
