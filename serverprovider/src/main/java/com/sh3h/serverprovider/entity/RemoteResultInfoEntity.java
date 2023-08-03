package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoteResultInfoEntity {
    /// <summary>
    /// 是否成功
    /// </summary>
    private boolean _isResult;
    /// <summary>
    /// 开帐金额
    /// </summary>
    private double _JE;
    /// <summary>
    /// 失败结果描述
    /// </summary>
    private String _errorMsg;

    public RemoteResultInfoEntity() {
        _isResult = false;
        _errorMsg = "";
        _JE = 0.0;
    }

    public void setIsResult(boolean isResult) {
        _isResult = isResult;
    }

    public boolean getIsResult() {
        return _isResult;
    }

    public void setJE(double JE) {
        _JE = JE;
    }

    public double getJE() {
        return _JE;
    }

    public void setErrorMsg(String errorMsg) {
        _errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return _errorMsg;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("IsResult", getIsResult());
            obj.put("N_JE", getJE());
            obj.put("ErrorMsg", getErrorMsg());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static RemoteResultInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        RemoteResultInfoEntity entity = new RemoteResultInfoEntity();

        entity.setIsResult(object.optBoolean("isResult"));
        entity.setJE(object.getDouble("n_JE"));
        entity.setErrorMsg(object.optString("errorMsg"));

        return  entity;
    }
}
