package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolumeTrackEntity {
    /// <summary>
    /// id
    /// </summary>
    private int _id;
    /// <summary>
    /// 册本号
    /// </summary>
    private String _ch;
    /// <summary>
    /// 册内序号
    /// </summary>
    private int _ceneixh;
    /// <summary>
    /// 经度
    /// </summary>
    private String _x;
    /// <summary>
    /// 维度
    /// </summary>
    private String _y;

    public VolumeTrackEntity() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getCh() {
        return _ch;
    }

    public void setCh(String ch) {
        _ch = ch;
    }

    public int getCeneixh() {
        return _ceneixh;
    }

    public void setCeneixh(int ceneixh) {
        _ceneixh = ceneixh;
    }

    public String getX() {
        return _x;
    }

    public void setX(String x) {
        _x = x;
    }

    public String getY() {
        return _y;
    }

    public void setY(String y) {
        _y = y;
    }

    public static VolumeTrackEntity fromJSON(JSONObject object)
            throws JSONException {
        VolumeTrackEntity entity = new VolumeTrackEntity();

        entity.setId(object.optInt("i_ID"));
        entity.setCh(object.optString("s_CH"));
        entity.setCeneixh(object.optInt("i_CeNeiXH"));
        entity.setX(object.optString("s_X"));
        entity.setY(object.optString("s_Y"));

        return entity;
    }

    public static List<VolumeTrackEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<VolumeTrackEntity> list = new ArrayList<VolumeTrackEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            VolumeTrackEntity entity = VolumeTrackEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
