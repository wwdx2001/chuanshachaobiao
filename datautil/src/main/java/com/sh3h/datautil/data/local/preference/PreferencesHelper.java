package com.sh3h.datautil.data.local.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "android_shanghai3h_pref_file";

    private final Context mContext;
    private UserSession mUserSession;
    //private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        //mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mContext = context;
        mUserSession = new UserSession(mContext);
    }

    public void clear() {
        //mPref.edit().clear().apply();
    }

    public UserSession getUserSession() {
        return mUserSession;
    }

    public boolean clearUserSession() {
        return mUserSession.clearUserSession();
    }

    public boolean isRecovery() {
        return mUserSession.getRecovery();
    }

    public boolean setRecovery(boolean isRecovery) {
        mUserSession.setRecovery(isRecovery);
        return mUserSession.save();
    }
}
