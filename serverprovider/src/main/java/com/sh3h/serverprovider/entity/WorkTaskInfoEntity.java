package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// ����������Ϣ��
public class WorkTaskInfoEntity {
    // ���
    private int _id;
    // ����Ա
    private String _account;
    // ������
    private int _renwubh;
    // ��������
    private int _type;
    // ������
    private int _subtype;
    // ���񴴽�ʱ��
    private long _create;
    // �����ɷ�ʱ��
    private long _paifasj;
    // �������ʱ��
    private long _huitian;
    // ����״̬(-1:ȡ��;0:����;1:�ɷ�;2:���
    private long _status;

    public WorkTaskInfoEntity()
    {
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getAccount() {
        return _account;
    }

    public void setAccount(String account) {
        _account = account;
    }

    public int getRenwubh() {
        return _renwubh;
    }

    public void setRenwubh(int renwubh) {
        _renwubh = renwubh;
    }

    public int getType() {
        return _type;
    }

    public void setType(int type) {
        _type = type;
    }

    public int getSubtype() {
        return _subtype;
    }

    public void setSubtype(int subtype) {
        _subtype = subtype;
    }

    public long getCreate() {
        return _create;
    }

    public void setCreate(long create) {
        _create = create;
    }

    public long getPaifasj() {
        return _paifasj;
    }

    public void setPaifasj(long paifasj) {
        _paifasj = paifasj;
    }

    public long getHuitian() {
        return _huitian;
    }

    public void setHuitian(long huitian) {
        _huitian = huitian;
    }

    public long getStatus() {
        return _status;
    }

    public void setStatus(long status) {
        _status = status;
    }

    public static WorkTaskInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        WorkTaskInfoEntity entity = new WorkTaskInfoEntity();

        entity.setId(object.optInt("iD"));
        entity.setAccount(object.optString("account"));
        entity.setRenwubh(object.optInt("i_RENWUBH"));
        entity.setType(object.optInt("i_TYPE"));
        entity.setSubtype(object.optInt("i_SUBTYPE"));
        entity.setCreate(object.optLong("d_CREATE"));
        entity.setPaifasj(object.optLong("d_PAIFASJ"));
        entity.setHuitian(object.optLong("d_HUITIAN"));
        entity.setStatus(object.optLong("i_STATUS"));

        return entity;
    }

    public static List<WorkTaskInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<WorkTaskInfoEntity> list = new ArrayList<WorkTaskInfoEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            WorkTaskInfoEntity entity = WorkTaskInfoEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
