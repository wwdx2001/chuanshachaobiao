package com.sh3h.meterreading.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2016/11/22.
 */

public class SettingActivity extends ParentActivity {
    private static final String TAG = "SettingActivity";

    @Inject
    Bus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getActivityComponent().inject(this);
        setActionBarBackButtonEnable();
        mEventBus.register(this);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            initParams(savedInstanceState);
        } else if (intent != null) {
            initParams(intent.getExtras());
        } else {
            initParams(null);
        }

        if (checkPermissions()) {
            initConfig();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
        LogUtil.i(TAG, "---onDestroy---");
    }


    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initConfigResult.isSuccess());
        if (initConfigResult.isSuccess()) {
            initUserConfig();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Subscribe
    public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
        if (initUserConfigResult.isSuccess()) {
//            mTaskListPresenter.loadTasks();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

}
