package com.sh3h.meterreading.ui.InspectionInput;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.meterreading.ui.base.ParentPresenter;

import javax.inject.Inject;

public class RemoteLRPresenter extends ParentPresenter<RemoteLRMvpView> {
    private static final String TAG = "InspectionInputPresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public RemoteLRPresenter(DataManager dataManager, PreferencesHelper mPreferencesHelper, ConfigHelper mConfigHelper) {
        super(dataManager);
        this.mPreferencesHelper = mPreferencesHelper;
        this.mConfigHelper = mConfigHelper;

    }
}