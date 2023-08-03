package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginInfo2Entity {

    public LoginInfo2Entity() {

    }

    /// 用户账号，登录账号
    private String Account;

    /// 站点号
    private String Station;

    // 站点名称
    private String StationName;

    /// 用户名
    private String UserName;

    public void reset() {
        Account = null;
        Station = null;
        StationName = null;
        UserName = null;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public static LoginInfo2Entity fromJSON(JSONObject object) {
        LoginInfo2Entity loginInfo2Entity = new LoginInfo2Entity();
        loginInfo2Entity.setAccount(object.optString("account"));
        loginInfo2Entity.setStation(object.optString("station"));
        loginInfo2Entity.setStationName(object.optString("stationName"));
        loginInfo2Entity.setUserName(object.optString("UserName"));
        return loginInfo2Entity;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<LoginInfo2Entity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<LoginInfo2Entity> list = new ArrayList<LoginInfo2Entity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            LoginInfo2Entity entity = LoginInfo2Entity.fromJSON(object);
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
    public JSONArray toJSONArray(List<LoginInfo2Entity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (LoginInfo2Entity cbsjentity : list) {
            JSONObject object = toJSON(cbsjentity);
            array.put(object);
        }

        return array;
    }

    public static JSONObject toJSON(LoginInfo2Entity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Account", entity.getAccount());
            obj.put("Station", entity.getStation());
            obj.put("StationName", entity.getStationName());
            obj.put("UserName", entity.getUserName());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Account", getAccount());
            obj.put("Station", getStation());
            obj.put("StationName", getStationName());
            obj.put("UserName", getUserName());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
