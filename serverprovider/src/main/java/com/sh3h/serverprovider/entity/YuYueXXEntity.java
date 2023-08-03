package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YuYueXXEntity {

    public YuYueXXEntity() {

    }
    /// 用户号
    public String S_CID;

    /// 预约联系人
    public String S_YUYUER ;

    /// 预约时间
    public long D_YUYUERQ ;

    /// 操作人
    public String S_CAOZUOR ;

    /// 操作时间
    public long D_CAOZUO ;

    /// 备注
    public String S_BEIZHU ;

    /// 上下午
    public String S_YUYUERQKZ ;

    /// 备用字段2
    public String S_BEIZHU2 ;

    /// 备用字段3
    public String S_BEIZHU3;

    /// 主键
    public long ID;

    /// 预约电话
    public String S_YUYUEDH;

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getS_YUYUER() {
        return S_YUYUER;
    }

    public void setS_YUYUER(String s_YUYUER) {
        S_YUYUER = s_YUYUER;
    }

    public long getD_YUYUERQ() {
        return D_YUYUERQ;
    }

    public void setD_YUYUERQ(long d_YUYUERQ) {
        D_YUYUERQ = d_YUYUERQ;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_CAOZUOR(String s_CAOZUOR) {
        S_CAOZUOR = s_CAOZUOR;
    }

    public long getD_CAOZUO() {
        return D_CAOZUO;
    }

    public void setD_CAOZUO(long d_CAOZUO) {
        D_CAOZUO = d_CAOZUO;
    }

    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setS_BEIZHU(String s_BEIZHU) {
        S_BEIZHU = s_BEIZHU;
    }

    public String getS_YUYUERQKZ() {
        return S_YUYUERQKZ;
    }

    public void setS_YUYUERQKZ(String s_YUYUERQKZ) {
        S_YUYUERQKZ = s_YUYUERQKZ;
    }

    public String getS_BEIZHU2() {
        return S_BEIZHU2;
    }

    public void setS_BEIZHU2(String s_BEIZHU2) {
        S_BEIZHU2 = s_BEIZHU2;
    }

    public String getS_BEIZHU3() {
        return S_BEIZHU3;
    }

    public void setS_BEIZHU3(String s_BEIZHU3) {
        S_BEIZHU3 = s_BEIZHU3;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getS_YUYUEDH() {
        return S_YUYUEDH;
    }

    public void setS_YUYUEDH(String s_YUYUEDH) {
        S_YUYUEDH = s_YUYUEDH;
    }

    public static YuYueXXEntity fromJSON(JSONObject object) {
        YuYueXXEntity chaoBiaoDJ = new YuYueXXEntity();

        chaoBiaoDJ.setS_CID(object.optString("s_CID"));
        chaoBiaoDJ.setS_YUYUER(object.optString("s_YUYUER"));
        chaoBiaoDJ.setD_YUYUERQ(object.optLong("d_YUYUERQ"));
        chaoBiaoDJ.setS_CAOZUOR(object.optString("s_CAOZUOR"));
        chaoBiaoDJ.setD_CAOZUO(object.optLong("d_CAOZUO"));
        chaoBiaoDJ.setS_BEIZHU(object.optString("s_BEIZHU"));
        chaoBiaoDJ.setS_YUYUERQKZ(object.optString("s_YUYUERQKZ"));
        chaoBiaoDJ.setS_BEIZHU2(object.optString("s_BEIZHU2"));
        chaoBiaoDJ.setS_BEIZHU3(object.optString("s_BEIZHU3"));
        chaoBiaoDJ.setID(object.optInt("sD"));
        chaoBiaoDJ.setS_YUYUEDH(object.optString("s_YUYUEDH"));
        return chaoBiaoDJ;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<YuYueXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<YuYueXXEntity> list = new ArrayList<YuYueXXEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            YuYueXXEntity entity = YuYueXXEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<YuYueXXEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (YuYueXXEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(YuYueXXEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", entity.getID());
            obj.put("S_YUYUER", entity.getS_YUYUER());
            obj.put("D_YUYUERQ", entity.getD_YUYUERQ());
            obj.put("S_CAOZUOR", entity.getS_CAOZUOR());
            obj.put("S_CID", entity.getS_CID());
            obj.put("D_CAOZUO", entity.getD_CAOZUO());
            obj.put("S_BEIZHU", entity.getS_BEIZHU());
            obj.put("S_YUYUERQKZ", entity.getS_YUYUERQKZ());
            obj.put("S_BEIZHU2", entity.getS_BEIZHU2());
            obj.put("S_BEIZHU3", entity.getS_BEIZHU3());
            obj.put("S_YUYUEDH", entity.getS_YUYUEDH());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", getID());
            obj.put("S_YUYUER", getS_YUYUER());
            obj.put("D_YUYUERQ", getD_YUYUERQ());
            obj.put("S_CAOZUOR", getS_CAOZUOR());
            obj.put("S_CID", getS_CID());
            obj.put("D_CAOZUO", getD_CAOZUO());
            obj.put("S_BEIZHU", getS_BEIZHU());
            obj.put("S_YUYUERQKZ", getS_YUYUERQKZ());
            obj.put("S_BEIZHU2", getS_BEIZHU2());
            obj.put("S_BEIZHU3", getS_BEIZHU3());
            obj.put("S_YUYUEDH", getS_YUYUEDH());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
