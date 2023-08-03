package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UploadInfoEntity {

    public UploadInfoEntity() {

    }

    /// 编号
    private String S_BIANHAO;

    /// 编号类型
    private String S_BIAONHAOLX;

    /// 上传类型
    private String S_LEIXING;

    /// 操作时间
    private long D_CAOZUOSJ;

    /// 操作人
    private String S_CAOZUOR;

    /// 文件名称
    private String S_WENJIANMC;

    /// 文件路径
    private String S_WENJIANLJ;

    /// 图片类型
    private String S_PICLX;

    public String getS_BIANHAO() {
        return S_BIANHAO;
    }

    public void setS_BIANHAO(String s_BIANHAO) {
        S_BIANHAO = s_BIANHAO;
    }

    public String getS_BIAONHAOLX() {
        return S_BIAONHAOLX;
    }

    public void setS_BIAONHAOLX(String s_BIAONHAOLX) {
        S_BIAONHAOLX = s_BIAONHAOLX;
    }

    public String getS_LEIXING() {
        return S_LEIXING;
    }

    public void setS_LEIXING(String s_LEIXING) {
        S_LEIXING = s_LEIXING;
    }

    public long getD_CAOZUOSJ() {
        return D_CAOZUOSJ;
    }

    public void setD_CAOZUOSJ(long d_CAOZUOSJ) {
        D_CAOZUOSJ = d_CAOZUOSJ;
    }

    public String getS_CAOZUOR() {
        return S_CAOZUOR;
    }

    public void setS_CAOZUOR(String s_CAOZUOR) {
        S_CAOZUOR = s_CAOZUOR;
    }

    public String getS_WENJIANMC() {
        return S_WENJIANMC;
    }

    public void setS_WENJIANMC(String s_WENJIANMC) {
        S_WENJIANMC = s_WENJIANMC;
    }

    public String getS_WENJIANLJ() {
        return S_WENJIANLJ;
    }

    public void setS_WENJIANLJ(String s_WENJIANLJ) {
        S_WENJIANLJ = s_WENJIANLJ;
    }

    public String getS_PICLX() {
        return S_PICLX;
    }

    public void setS_PICLX(String s_PICLX) {
        S_PICLX = s_PICLX;
    }

    public static UploadInfoEntity fromJSON(JSONObject object) {
        UploadInfoEntity uploadInfoEntity = new UploadInfoEntity();
        uploadInfoEntity.setS_BIANHAO(object.optString("s_BIANHAO"));
        uploadInfoEntity.setS_BIAONHAOLX(object.optString("s_BIAONHAOLX"));
        uploadInfoEntity.setS_LEIXING(object.optString("s_LEIXING"));
        uploadInfoEntity.setD_CAOZUOSJ(object.optLong("d_CAOZUOSJ"));
        uploadInfoEntity.setS_CAOZUOR(object.optString("s_CAOZUOR"));
        uploadInfoEntity.setS_WENJIANMC(object.optString("s_WENJIANMC"));
        uploadInfoEntity.setS_WENJIANLJ(object.optString("s_WENJIANLJ"));
        uploadInfoEntity.setS_PICLX(object.optString("s_PICLX"));
        return uploadInfoEntity;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<UploadInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<UploadInfoEntity> list = new ArrayList<UploadInfoEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            UploadInfoEntity entity = UploadInfoEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    /**
     * 转换List<ChaoBiaoSJEntity>对象为JSONArray
     *
     * @param list
     * @return
     */
    public static JSONArray toJSONArray(List<UploadInfoEntity> list)
            throws JSONException {
        if (list == null) {
            return null;
        }

        JSONArray array = new JSONArray();

        for (UploadInfoEntity cbsjentity : list) {
            JSONObject object = toJSON(cbsjentity);
            array.put(object);
        }

        return array;
    }


    public static JSONObject toJSON(UploadInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_BIANHAO", entity.getS_BIANHAO());
            obj.put("S_BIAONHAOLX", entity.getS_BIAONHAOLX());
            obj.put("S_LEIXING", entity.getS_LEIXING());
            obj.put("D_CAOZUOSJ", entity.getD_CAOZUOSJ());
            obj.put("S_CAOZUOR", entity.getS_CAOZUOR());
            obj.put("S_WENJIANMC", entity.getS_WENJIANMC());
            obj.put("S_WENJIANLJ", entity.getS_WENJIANLJ());
            obj.put("S_PICLX", entity.getS_PICLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_BIANHAO", getS_BIANHAO());
            obj.put("S_BIAONHAOLX", getS_BIAONHAOLX());
            obj.put("S_LEIXING", getS_LEIXING());
            obj.put("D_CAOZUOSJ", getD_CAOZUOSJ());
            obj.put("S_CAOZUOR", getS_CAOZUOR());
            obj.put("S_WENJIANMC", getS_WENJIANMC());
            obj.put("S_WENJIANLJ", getS_WENJIANLJ());
            obj.put("S_PICLX", getS_PICLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
