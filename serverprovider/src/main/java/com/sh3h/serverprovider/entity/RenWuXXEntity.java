package com.sh3h.serverprovider.entity;


import org.json.JSONObject;

public class RenWuXXEntity {
    public static final String SUCCESS_MESSAGE = "success";
    public static final String FAILURE_MESSAGE = "failure";

    private String _message;
    private String _renwus;
    private String _chaobiaojbh;

    public RenWuXXEntity() {
        _message = FAILURE_MESSAGE;
        _renwus = "";
        _chaobiaojbh = "";
    }

    public RenWuXXEntity(String _message, String _renwus) {
        this._message = _message;
        this._renwus = _renwus;
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public String get_renwus() {
        return _renwus;
    }

    public void set_renwus(String _renwus) {
        this._renwus = _renwus;
    }

    public String get_chaobiaojbh() {
        return _chaobiaojbh;
    }

    public void set_chaobiaojbh(String _chaobiaojbh) {
        this._chaobiaojbh = _chaobiaojbh;
    }

    public static RenWuXXEntity fromJSON(JSONObject object) {
        RenWuXXEntity renWuXXEntity = new RenWuXXEntity();
        try {
            renWuXXEntity.set_message(object.optString("s_MESSAGE"));
            renWuXXEntity.set_renwus(object.optString("s_RENWUS"));
            renWuXXEntity.set_chaobiaojbh(object.optString("i_CHAOBIAOJBH"));
        } catch (Exception e) {
            e.printStackTrace();
            renWuXXEntity.set_message(FAILURE_MESSAGE);
            renWuXXEntity.set_renwus("");
        }

        return renWuXXEntity;
    }
}
