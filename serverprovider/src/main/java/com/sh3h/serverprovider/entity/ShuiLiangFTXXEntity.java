package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShuiLiangFTXXEntity {
    /// <summary>
    /// 用户号
    /// </summary>
    private String _CID;
    /// <summary>
    /// 简号
    /// </summary>
    private String _JIANHAO;
    /// <summary>
    /// 分摊方式
    /// </summary>
    private int _FenTanFS;
    /// <summary>
    /// 分摊量
    /// </summary>
    private double _FenTanL;
    /// <summary>
    /// 排序
    /// </summary>
    private int _PaiXu;

    public ShuiLiangFTXXEntity() {

    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public String getCID() {
        return _CID;
    }

    public void setJIANHAO(String JIANHAO) {
        _JIANHAO = JIANHAO;
    }

    public String getJIANHAO() {
        return _JIANHAO;
    }

    public void setFenTanFS(int FenTanFS) {
        _FenTanFS = FenTanFS;
    }

    public int getFenTanFS() {
        return _FenTanFS;
    }

    public void setFenTanL(double FenTanL) {
        _FenTanL = FenTanL;
    }

    public double getFenTanL() {
        return _FenTanL;
    }

    public void setPaiXu(int PaiXu) {
        _PaiXu = PaiXu;
    }

    public int getPaiXu() {
        return _PaiXu;
    }

    public static ShuiLiangFTXXEntity fromJSON(JSONObject object) throws JSONException {
        ShuiLiangFTXXEntity entity = new ShuiLiangFTXXEntity();

        entity.setCID(object.optString("s_CID"));
        entity.setJIANHAO(object.optString("s_JIANHAO"));
        entity.setFenTanFS(object.optInt("i_FenTanFS"));
        entity.setFenTanL(object.optDouble("n_FenTanL"));
        entity.setPaiXu(object.optInt("i_PaiXu"));

        return entity;
    }

    public static List<ShuiLiangFTXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ShuiLiangFTXXEntity> list = new ArrayList<ShuiLiangFTXXEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ShuiLiangFTXXEntity entity = ShuiLiangFTXXEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
