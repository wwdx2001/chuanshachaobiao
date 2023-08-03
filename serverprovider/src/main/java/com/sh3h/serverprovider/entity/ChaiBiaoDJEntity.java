package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChaiBiaoDJEntity {

    public ChaiBiaoDJEntity() {

    }

    /// 主键ID
    public int ID;

    /// 站点
    public String S_ST;

    /// 户名
    public String S_HM;

    /// 地址
    public String S_DZ;

    /// 账号
    public String S_CID;

    /// 拆表原因
    public int I_CHAIBIAOYY;

    /// 流程类别
    public int I_LiuChengLB;

    /// 反映类别
    public String S_FANYINGLB;

    /// 反映内容
    public String S_FANYINGNR;

    /// 陪标标识
    public String S_PEIBIAO;

    /// 是否免赔 0：免赔 1：不免赔
    public int I_MIANPEI;

    /// 施工约时
    public String SHIGONGYS;

    /// 受理备注
    public String SHOULIBZ;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getS_ST() {
        return S_ST;
    }

    public void setS_ST(String s_ST) {
        S_ST = s_ST;
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

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public int getI_CHAIBIAOYY() {
        return I_CHAIBIAOYY;
    }

    public void setI_CHAIBIAOYY(int i_CHAIBIAOYY) {
        I_CHAIBIAOYY = i_CHAIBIAOYY;
    }

    public int getI_LiuChengLB() {
        return I_LiuChengLB;
    }

    public void setI_LiuChengLB(int i_LiuChengLB) {
        I_LiuChengLB = i_LiuChengLB;
    }

    public String getS_FANYINGLB() {
        return S_FANYINGLB;
    }

    public void setS_FANYINGLB(String s_FANYINGLB) {
        S_FANYINGLB = s_FANYINGLB;
    }

    public String getS_FANYINGNR() {
        return S_FANYINGNR;
    }

    public void setS_FANYINGNR(String s_FANYINGNR) {
        S_FANYINGNR = s_FANYINGNR;
    }

    public String getS_PEIBIAO() {
        return S_PEIBIAO;
    }

    public void setS_PEIBIAO(String s_PEIBIAO) {
        S_PEIBIAO = s_PEIBIAO;
    }

    public int getI_MIANPEI() {
        return I_MIANPEI;
    }

    public void setI_MIANPEI(int i_MIANPEI) {
        I_MIANPEI = i_MIANPEI;
    }

    public String getSHIGONGYS() {
        return SHIGONGYS;
    }

    public void setSHIGONGYS(String SHIGONGYS) {
        this.SHIGONGYS = SHIGONGYS;
    }

    public String getSHOULIBZ() {
        return SHOULIBZ;
    }

    public void setSHOULIBZ(String SHOULIBZ) {
        this.SHOULIBZ = SHOULIBZ;
    }

    public static ChaiBiaoDJEntity fromJSON(JSONObject object) {
        ChaiBiaoDJEntity chaoBiaoDJ = new ChaiBiaoDJEntity();

        chaoBiaoDJ.setID(object.optInt("iD"));
        chaoBiaoDJ.setS_ST(object.optString("s_ST"));
        chaoBiaoDJ.setS_HM(object.optString("s_HM"));
        chaoBiaoDJ.setS_DZ(object.optString("s_DZ"));
        chaoBiaoDJ.setS_CID(object.optString("s_CID"));
        chaoBiaoDJ.setI_CHAIBIAOYY(object.optInt("i_CHAIBIAOYY"));
        chaoBiaoDJ.setI_LiuChengLB(object.optInt("i_LiuChengLB"));
        chaoBiaoDJ.setS_FANYINGLB(object.optString("s_FANYINGLB"));
        chaoBiaoDJ.setS_FANYINGNR(object.optString("s_FANYINGNR"));
        chaoBiaoDJ.setS_PEIBIAO(object.optString("s_PEIBIAO"));
        chaoBiaoDJ.setI_MIANPEI(object.optInt("i_MIANPEI"));
        chaoBiaoDJ.setSHIGONGYS(object.optString("sHIGONGYS"));
        chaoBiaoDJ.setSHOULIBZ(object.optString("sHOULIBZ"));
        return chaoBiaoDJ;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<ChaiBiaoDJEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ChaiBiaoDJEntity> list = new ArrayList<ChaiBiaoDJEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ChaiBiaoDJEntity entity = ChaiBiaoDJEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<ChaiBiaoDJEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (ChaiBiaoDJEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(ChaiBiaoDJEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", entity.getID());
            obj.put("S_ST", entity.getS_ST());
            obj.put("S_HM", entity.getS_HM());
            obj.put("S_DZ", entity.getS_DZ());
            obj.put("S_CID", entity.getS_CID());
            obj.put("I_CHAIBIAOYY", entity.getI_CHAIBIAOYY());
            obj.put("I_LiuChengLB", entity.getI_LiuChengLB());
            obj.put("S_FANYINGLB", entity.getS_FANYINGLB());
            obj.put("S_FANYINGNR", entity.getS_FANYINGNR());
            obj.put("S_PEIBIAO", entity.getS_PEIBIAO());
            obj.put("I_MIANPEI", entity.getI_MIANPEI());
            obj.put("SHIGONGYS", entity.getSHIGONGYS());
            obj.put("SHOULIBZ", entity.getSHOULIBZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", getID());
            obj.put("S_ST", getS_ST());
            obj.put("S_HM", getS_HM());
            obj.put("S_DZ", getS_DZ());
            obj.put("S_CID", getS_CID());
            obj.put("I_CHAIBIAOYY", getI_CHAIBIAOYY());
            obj.put("I_LiuChengLB", getI_LiuChengLB());
            obj.put("S_FANYINGLB", getS_FANYINGLB());
            obj.put("S_FANYINGNR", getS_FANYINGNR());
            obj.put("S_PEIBIAO", getS_PEIBIAO());
            obj.put("I_MIANPEI", getI_MIANPEI());
            obj.put("SHIGONGYS", getSHIGONGYS());
            obj.put("SHOULIBZ", getSHOULIBZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
