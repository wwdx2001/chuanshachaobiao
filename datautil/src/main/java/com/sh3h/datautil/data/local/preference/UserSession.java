package com.sh3h.datautil.data.local.preference;


import android.content.Context;
import android.content.SharedPreferences;

import com.sh3h.mobileutil.util.TextUtil;

import java.util.Date;
import java.util.UUID;

public class UserSession {
    /**
     * 共享存储名称
     */
    private static final String FILE_SESSION = "shanghai3h_user_session";

    /**
     * session id
     */
    private static final String KEY_SESSION_ID = "session_id";

    /**
     * 存储用户名称
     */
    private static final String KEY_USER_NAME = "user_name";
    /**
     * 存储用户名称
     */
    private static final String KEY_USER_ACCOUNT = "user_account";
    /**
     * 存储用户编号名称
     */
    private static final String KEY_USER_ID = "user_id";
	/**
	 * 存储部门编号名称
	 */
	private static final String KEY_DEPARTMENT_ID = "department_id";
	/**
	 * 存储部门编号名称
	 */
	private static final String KEY_DEPARTMENT_NAME = "department_name";
    /**
     * 存储时间名称
     */
    private static final String KEY_LOGIN_TIME = "login_time";
	/**
	 * 存储状态编号名称
	 */
	private static final String KEY_LOGIN_MODE = "login_mode";
    /**
     * 存储登陆状态名称
     */
    private static final String KEY_LOGIN_STATE = "loginState";

    /**
     * 存储是否首次登录
     */
    private static final String KEY_LOGIN_FIRST = "loginFirst";

    /**
     * password
     */
    private static final String KEY_PASSWORD = "password";

    /**
     * recovery
     */
    private static final String KEY_RECOVERY = "RECOVERY";

    /**
     * 用户id
     */
    private int _userId = 0;

    /**
     * 用户账号
     */
    private String _account = null;

    /**
     * 用户姓名
     */
    private String _userName = null;

    /**
     * 用户部门id
     */
    private int _departmentId = 0;

    /**
     * 用户部门
     */
    private String _departmentName = null;

    /**
     * 登陆时间
     */
    private Date _loginTime = null;

    /**
     * 网络状态
     */
    private int _loginMode = 0;

    /**
     * 登陆状态
     */
    private int _loginState = 0;

    /**
     * 是否首次登录
     */
    private boolean _loginFirst = true;

    /**
     *
     */
    private String _sessionId = "-1";

    /**
     *
     */
    private boolean _recovery = false;

    /**
     *
     */
    private String _password = null;

    /**
     *
     */
    private Context mContext;

    public UserSession(Context context) {
        mContext = context;
        readSharedPreferences();
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public boolean getRecovery() {
        return _recovery;
    }

    public void setRecovery(boolean recovery) {
        _recovery = recovery;
    }

    public boolean isLoginFirst() {
        return _loginFirst;
    }

    public void setLoginFirst(boolean loginFirst) {
        _loginFirst = loginFirst;
    }

    public String getSessionId() {
        return _sessionId;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int userId) {
        _userId = userId;
    }

    public String getAccount() {
        return _account;
    }

    public void setAccount(String account) {
        _account = account;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public int getDepartmentId() {
        return _departmentId;
    }

    public void setDepartmentId(int departmentId) {
        _departmentId = departmentId;
    }

    public String getDepartmentName() {
        return _departmentName;
    }

    public void setDepartmentName(String departmentName) {
        _departmentName = departmentName;
    }

    public Date getLoginTime() {
        return _loginTime;
    }

    public void setLoginTime(Date loginTime) {
        _loginTime = loginTime;
    }

    public int getLoginMode() {
        return _loginMode;
    }

    public void setLoginMode(int loginMode) {
        _loginMode = loginMode;
    }

    public int getLoginState() {
        return _loginState;
    }

    public void setLoginState(int loginState) {
        _loginState = loginState;
    }

    private void readSharedPreferences() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        if (sp != null) {
            _sessionId = sp.getString(KEY_SESSION_ID, "-1");
            //this.setUserId(sp.getInt(KEY_USER_ID, StateService.UNKNOW_USER_ID));
            setUserId(sp.getInt(KEY_USER_ID, 0));
            setUserName(sp.getString(KEY_USER_NAME, TextUtil.EMPTY));
            setLoginTime(new Date(sp.getLong(KEY_LOGIN_TIME, 0)));
            setLoginState(sp.getInt(KEY_LOGIN_STATE, 0));
            setAccount(sp.getString(KEY_USER_ACCOUNT, TextUtil.EMPTY));
            setLoginFirst(sp.getBoolean(KEY_LOGIN_FIRST, true));
            set_password(sp.getString(KEY_PASSWORD, TextUtil.EMPTY));
            setRecovery(sp.getBoolean(KEY_RECOVERY, false));
        }

        if (_sessionId.equals("-1")) {
            _sessionId = UUID.randomUUID().toString();
        }
    }

    public boolean save() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_SESSION_ID, this._sessionId);
        // 记录用户名称
        if (TextUtil.isNullOrEmpty(_userName)) {
            editor.putString(KEY_USER_NAME, "");
        } else {
            editor.putString(KEY_USER_NAME, _userName);
        }
        // 记录用户account
        if (TextUtil.isNullOrEmpty(_account)) {
            editor.putString(KEY_USER_ACCOUNT, "");
        } else {
            editor.putString(KEY_USER_ACCOUNT, _account);
        }
        // 记录用户编号
        editor.putInt(KEY_USER_ID, _userId);
        // 记录登录时间
        if (_loginTime == null) {
            editor.putLong(KEY_LOGIN_TIME, new Date().getTime());
        } else {
            editor.putLong(KEY_LOGIN_TIME, _loginTime.getTime());
        }
        // 记录登陆状态
        editor.putInt(KEY_LOGIN_STATE, _loginState);
        //记录是否是第一次登录
        editor.putBoolean(KEY_LOGIN_FIRST, _loginFirst);
        //记录密码
        if (TextUtil.isNullOrEmpty(_password)) {
            editor.putString(KEY_PASSWORD, "");
        } else {
            editor.putString(KEY_PASSWORD, _password);
        }
        editor.putBoolean(KEY_RECOVERY, _recovery);
        return editor.commit();
    }

    public void clearSessionId() {
        _sessionId = "-1";
    }

    public boolean clearUserSession() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        return editor.clear().commit();
    }

    // public void updateLoginState(Context context, int loginState) {
    // SharedPreferences sp = context.getSharedPreferences(FILE_SESSION,
    // Context.MODE_PRIVATE);
    // Editor editor = sp.edit();
    // editor.putInt(KEY_LOGIN_STATE, loginState);
    // editor.putString(KEY_SESSION_ID, "-1");
    // editor.commit();
    // }
}
