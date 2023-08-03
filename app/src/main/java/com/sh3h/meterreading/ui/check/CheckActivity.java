package com.sh3h.meterreading.ui.check;

import android.os.Bundle;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;

import butterknife.ButterKnife;

/**
 * Created by liurui on 2016/2/17.
 */
public class CheckActivity extends ParentActivity implements CheckMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        setActionBarBackButtonEnable();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onError(String message) {

    }
}
