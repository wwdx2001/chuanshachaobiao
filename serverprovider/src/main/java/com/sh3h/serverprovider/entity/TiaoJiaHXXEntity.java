package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiaoJiaHXXEntity {
    private int ID;
    private int I_TIAOJIAH;
    private long D_SHENGXIAORQ;
    private long D_TIAOJIARQ;
    private String S_CAOZUOR;
    private String S_BEIZHU;
    private long D_CAOZUOSJ;

    public TiaoJiaHXXEntity() {

    }

    public void setID(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setI_TIAOJIAH(int i_tiaojiah) {
        I_TIAOJIAH = i_tiaojiah;
    }

    public int getI_TIAOJIAH() {
        return I_TIAOJIAH;
    }

    public void setD_SHENGXIAORQ(long d_shengxiaorq) {
        D_SHENGXIAORQ = d_shengxiaorq;
    }

    public long getD_SHENGXIAORQ() {
        return D_SHENGXIAORQ;
    }

    public void setD_TIAOJIARQ(long d_tiaojiarq) {
        D_TIAOJIARQ = d_tiaojiarq;
    }

    public long getD_TIAOJIARQ() {
        return D_TIAOJIARQ;
    }

    public void setS_CAOZUOR(String s_caozuor) {
        S_CAOZUOR = s_caozuor;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_BEIZHU(String s_beizhu) {
        S_BEIZHU = s_beizhu;
    }

    public String getS_BEIZHU() {
        return S_BEIZHU;
    }

    public void setD_CAOZUOSJ(long d_caozuosj) {
        D_CAOZUOSJ = d_caozuosj;
    }

    public long getD_CAOZUOSJ() {
        return D_CAOZUOSJ;
    }

    public static TiaoJiaHXXEntity fromJSON(JSONObject object) {
        TiaoJiaHXXEntity entity = new TiaoJiaHXXEntity();
        entity.setID(object.optInt("iD"));
        entity.setI_TIAOJIAH(object.optInt("i_TIAOJIAH"));
        entity.setD_SHENGXIAORQ(object.optLong("d_SHENGXIAORQ"));
        entity.setD_TIAOJIARQ(object.optLong("d_TIAOJIARQ"));
        entity.setS_CAOZUOR(object.optString("s_CAOZUOR"));
        entity.setS_BEIZHU(object.optString("s_BEIZHU"));
        entity.setD_CAOZUOSJ(object.optLong("s_CAOZUOSJ"));
        return entity;
    }

    public static List<TiaoJiaHXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<TiaoJiaHXXEntity> list = new ArrayList<TiaoJiaHXXEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            TiaoJiaHXXEntity entity = TiaoJiaHXXEntity.fromJSON(object);
            list.add(entity);
        }

        return list;

    }
}
