package com.sh3h.dataprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultInfo {

	// / <summary>
	// / 是否成功
	// / </summary>
	private boolean _isResult;
	// / <summary>
	// / 失败结果描述
	// / </summary>
	private String _errorMsg;

	public ResultInfo() {
		_isResult = false;
		_errorMsg = "";
	}

	public ResultInfo(boolean isResult, String errorMsg) {
		_isResult = isResult;
		_errorMsg = errorMsg;
	}

	public boolean getIsResult() {
		return _isResult;
	}

	public void setIsResult(boolean isResult) {
		_isResult = isResult;
	}

	public String getErrorMsg() {
		return _errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		_errorMsg = errorMsg;
	}

	/*	public JSONObject toJSON() {
            JSONObject obj = new JSONObject();
            try {
                obj.put("IsResult", is_isResult());
                obj.put("ErrorMsg", get_errorMsg());
            } catch (JSONException e) {
                return null;
            }

            return obj;
        }

        public static ResultInfo fromResultInfoJSON(JSONObject object) {
            try {
                ResultInfo entity = new ResultInfo();
                entity.set_isResult(object.optBoolean("isResult"));
                entity.set_errorMsg(object.optString("errorMsg"));
                return entity;
            } catch (Exception e) {
                return null;
            }
        }
    */
	public static ResultInfo getResultInfo(boolean isResult, String errorMsg) {
		return new ResultInfo(isResult, errorMsg);
	}
}
