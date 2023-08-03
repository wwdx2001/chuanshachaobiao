package com.sh3h.meterreading.ui.check;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;

import javax.inject.Inject;

/**
 * Created by liurui on 2016/2/17.
 */
public class CheckPresenter extends ParentPresenter<CheckMvpView> {

    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    
    @Inject
    public CheckPresenter(DataManager dataManager,
                          PreferencesHelper preferencesHelper,
                          ConfigHelper configHelper) {
        super(dataManager);
        mPreferencesHelper = preferencesHelper;
        mConfigHelper = configHelper;
    }
}
