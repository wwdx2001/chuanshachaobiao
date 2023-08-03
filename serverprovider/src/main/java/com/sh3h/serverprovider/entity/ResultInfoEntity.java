package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultInfoEntity {
    /// <summary>
    /// 是否成功
    /// </summary>
    private boolean _isResult;
    /// <summary>
    /// 失败结果描述
    /// </summary>
    private String _errorMsg;

    public ResultInfoEntity() {
        _isResult = false;
        _errorMsg = "";
    }

    public void setIsResult(boolean isResult) {
        _isResult = isResult;
    }

    public boolean getIsResult() {
        return _isResult;
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
            obj.put("ErrorMsg", getErrorMsg());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static ResultInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        ResultInfoEntity entity = new ResultInfoEntity();

        entity.setIsResult(object.optBoolean("isResult"));
        entity.setErrorMsg(object.optString("errorMsg"));

        return  entity;
    }
}
