package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ZhangWuCLCommonSearchEntity {

    public ZhangWuCLCommonSearchEntity() {

    }

    /// 站点号
    public String S_ST;

    /// 账号
    public String S_CID;

    /// 抄表年
    public String I_CHAOBIAON;

    /// 抄表月
    public String I_CHAOBIAOY;

    /// 账务类型(已销、未销)，关联词语表中的ZhangWuLX
    public int ZhangWuLX;

    public String getS_ST() {
        return S_ST;
    }

    public void setS_ST(String s_ST) {
        S_ST = s_ST;
    }

    public String getS_CID() {
        return S_CID;
    }

    public void setS_CID(String s_CID) {
        S_CID = s_CID;
    }

    public String getI_CHAOBIAON() {
        return I_CHAOBIAON;
    }

    public void setI_CHAOBIAON(String i_CHAOBIAON) {
        I_CHAOBIAON = i_CHAOBIAON;
    }

    public String getI_CHAOBIAOY() {
        return I_CHAOBIAOY;
    }

    public void setI_CHAOBIAOY(String i_CHAOBIAOY) {
        I_CHAOBIAOY = i_CHAOBIAOY;
    }

    public int getZhangWuLX() {
        return ZhangWuLX;
    }

    public void setZhangWuLX(int zhangWuLX) {
        ZhangWuLX = zhangWuLX;
    }

    public static ZhangWuCLCommonSearchEntity fromJSON(JSONObject object) {
        ZhangWuCLCommonSearchEntity zw = new ZhangWuCLCommonSearchEntity();
        zw.setS_CID(object.optString("s_CID"));
        zw.setS_ST(object.optString("s_ST"));
        zw.setI_CHAOBIAON(object.optString("i_CHAOBIAON"));
        zw.setI_CHAOBIAOY(object.optString("i_CHAOBIAOY"));
        zw.setZhangWuLX(object.optInt("zhangWuLX"));
        return zw;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<ZhangWuCLCommonSearchEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ZhangWuCLCommonSearchEntity> list = new ArrayList<ZhangWuCLCommonSearchEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ZhangWuCLCommonSearchEntity entity = ZhangWuCLCommonSearchEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<ZhangWuCLCommonSearchEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (ZhangWuCLCommonSearchEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(ZhangWuCLCommonSearchEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_ST", entity.getS_ST());
            obj.put("S_CID", entity.getS_CID());
            obj.put("I_CHAOBIAON", entity.getI_CHAOBIAON());
            obj.put("I_CHAOBIAOY", entity.getI_CHAOBIAOY());
            obj.put("ZhangWuLX", entity.getZhangWuLX());
        } catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_ST", getS_ST());
            obj.put("S_CID", getS_CID());
            obj.put("I_CHAOBIAON", getI_CHAOBIAON());
            obj.put("I_CHAOBIAOY", getI_CHAOBIAOY());
            obj.put("ZhangWuLX", getZhangWuLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
