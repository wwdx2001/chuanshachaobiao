package com.sh3h.meterreading.ui.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.mobileutil.view.DatePickerDialog;
import com.sh3h.mobileutil.view.DatePickerListener;
import com.sh3h.mobileutil.widgrt.DatePicker;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CombinedSearchActivity extends ParentActivity implements View.OnClickListener {

    private final static String TAG = "CombinedSearchActivity";

    @BindView(R.id.ed_cebenhao) EditText mEtCeBenH;
    @BindView(R.id.ed_customer_code) EditText mEtCustomerCode;
    @BindView(R.id.ed_huming) EditText mEtHuMing;
    @BindView(R.id.ed_address) EditText mEtDiZhi;
    @BindView(R.id.ed_phone) EditText mEddianhua;
    @BindView(R.id.ed_jianhao) EditText eEdJianHao;
    @BindView(R.id.ed_biaohao) EditText eEdBiaoHao;
    @BindView(R.id.ed_koujing_begin) EditText eEdKouJingBegin;
    @BindView(R.id.ed_koujing_end) EditText eEdKouJingEnd;
    @BindView(R.id.ed_qianfeibs) EditText eEdQianFeiBS;
    @BindView(R.id.ed_qianfeije) EditText eEdQianFeiJE;
    @BindView(R.id.tv_huanbianrq) TextView tTvHuanBiaoRQ;
    @BindView(R.id.btn_select) Button mBtnSelect;
    @BindView(R.id.btn_reset) Button mBtnReset;
    @Inject Bus mEventBus;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mEventBus.register(this);
        setActionBarBackButtonEnable();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
//        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_huanbianrq:
                timePicker();
                break;
            case R.id.btn_select:
                goResultPage();
                break;
            case R.id.btn_reset:
                resetDate();
                break;
        }
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
            tTvHuanBiaoRQ.setOnClickListener(this);
            mBtnSelect.setOnClickListener(this);
            mBtnReset.setOnClickListener(this);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    private void resetDate() {
        mEtCeBenH.setText(TextUtil.EMPTY);
        mEtCustomerCode.setText(TextUtil.EMPTY);
        mEtHuMing.setText(TextUtil.EMPTY);
        mEtDiZhi.setText(TextUtil.EMPTY);
        mEddianhua.setText(TextUtil.EMPTY);
        eEdJianHao.setText(TextUtil.EMPTY);
        eEdBiaoHao.setText(TextUtil.EMPTY);
        eEdKouJingBegin.setText(TextUtil.EMPTY);
        eEdKouJingEnd.setText(TextUtil.EMPTY);
        eEdQianFeiBS.setText(TextUtil.EMPTY);
        eEdQianFeiJE.setText(TextUtil.EMPTY);
        tTvHuanBiaoRQ.setText(TextUtil.EMPTY);
    }

    /**
     * 查看条件
     */
    private void goResultPage() {
        Intent intent = new Intent(CombinedSearchActivity.this,
                CombinedSearchResultActivity.class);
        Bundle bundle = new Bundle();
        boolean isEMPTY = true;
        String text = mEtCeBenH.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.CEBENHAO, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.CEBENHAO, TextUtil.EMPTY);
        }

        text = mEtCustomerCode.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.KEHUBH, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.KEHUBH, TextUtil.EMPTY);
        }

        text = mEtHuMing.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.HUMING, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.HUMING, TextUtil.EMPTY);
        }

        text = mEtDiZhi.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.DIZHI, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.DIZHI, TextUtil.EMPTY);
        }

        text = mEddianhua.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.DIANHUA, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.DIANHUA, TextUtil.EMPTY);
        }

        text = eEdJianHao.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.JIANHAO, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.JIANHAO, TextUtil.EMPTY);
        }

        text = eEdBiaoHao.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putString(CombinedSearchResultActivity.BIAOHAO, text);
            isEMPTY = false;
        } else {
            bundle.putString(CombinedSearchResultActivity.BIAOHAO, TextUtil.EMPTY);
        }

        text = eEdKouJingBegin.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putDouble(CombinedSearchResultActivity.KOUJINMIN, Double.parseDouble(text));
            isEMPTY = false;
        } else {
            bundle.putDouble(CombinedSearchResultActivity.KOUJINMIN, 999999999);
        }

        text = eEdKouJingEnd.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putDouble(CombinedSearchResultActivity.KOUJINMAX, Double.parseDouble(text));
            isEMPTY = false;
        } else {
            bundle.putDouble(CombinedSearchResultActivity.KOUJINMAX, -1);
        }

        text = eEdQianFeiBS.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putInt(CombinedSearchResultActivity.QIANFEIBS, Integer.parseInt(text));
            isEMPTY = false;
        } else {
            bundle.putInt(CombinedSearchResultActivity.QIANFEIBS, -1);
        }

        text = eEdQianFeiJE.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putDouble(CombinedSearchResultActivity.QIANFEIJE, Double.parseDouble(text));
            isEMPTY = false;
        } else {
            bundle.putDouble(CombinedSearchResultActivity.QIANFEIJE, -1);
        }

        text = tTvHuanBiaoRQ.getText().toString().trim();
        if (!TextUtil.isNullOrEmpty(text)) {
            bundle.putLong(CombinedSearchResultActivity.HUANBIAORQ,
                    TextUtil.format(TextUtil.FORMAT_DATE, text).getTime());
            isEMPTY = false;
        } else {
            bundle.putLong(CombinedSearchResultActivity.HUANBIAORQ, 0);
        }

        if (!isEMPTY) {
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            ApplicationsUtil.showMessage(this, "请输入至少一个查询条件");
        }
    }


    private void timePicker() {
        Date currentDate = new Date();
        int end_date = Integer
                .parseInt(TextUtil.format(currentDate, TextUtil.FORMAT_DATE_YEAR));
        int month = Integer.parseInt(TextUtil.format(currentDate, TextUtil.FORMAT_DATE_MONTH));
        int day = Integer.parseInt(TextUtil.format(currentDate, TextUtil.FORMAT_DATE_DAY));
        new DatePickerDialog(CombinedSearchActivity.this, 1990, end_date, month, day,
                new DatePickerListener() {
                    @Override
                    public void getTime(Date date) {
                        tTvHuanBiaoRQ.setText(TextUtil.format(date, TextUtil.FORMAT_DATE));
                    }
        }, DatePicker.AppointTime).show();
    }

}
