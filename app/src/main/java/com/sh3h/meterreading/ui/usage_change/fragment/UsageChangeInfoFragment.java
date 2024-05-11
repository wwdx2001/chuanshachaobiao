package com.sh3h.meterreading.ui.usage_change.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.custom_view.SelectSpinnerView;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

/**
 * 用水性质变更
 */
public class UsageChangeInfoFragment extends ParentFragment {
    private SelectSpinnerView mUsageChangeUploadJhSpinner;
    private SelectSpinnerView mUsageChangeUploadCodeSpinner;
    private EditText mUsageChangeUploadRemarksEt;




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_usage_change_upload;
    }

    @Override
    protected void initView1(View view) {
        super.initView1(view);
        mUsageChangeUploadJhSpinner = view.findViewById(R.id.usage_change_upload_jh_spinner);
        mUsageChangeUploadCodeSpinner = view.findViewById(R.id.usage_change_upload_code_spinner);
        mUsageChangeUploadRemarksEt = view.findViewById(R.id.usage_change_upload_remarks_et);
    }

    @Override
    protected void initData1() {
        super.initData1();
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<String> strings = (List<String>) bundle.getSerializable(Const.STRINGS);
            List<String> jianHaoList = (List<String>) bundle.getSerializable(Const.JIANHAOS);
            RealNameWholeEntity entity = (RealNameWholeEntity) bundle.getParcelable(Const.BEAN);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mUsageChangeUploadCodeSpinner.setAdapter(adapter);

            ArrayAdapter<String> jianhaoAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, jianHaoList);
            jianhaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mUsageChangeUploadJhSpinner.setAdapter(jianhaoAdapter);

            mUsageChangeUploadRemarksEt.setText(entity != null ? entity.getRemarks() : "");
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Activity currentActivity;

    @Override
    public void onAttach(Context context) {
        currentActivity = getActivity() == null ? ActivityUtils.getTopActivity() : getActivity();
        super.onAttach(context);
    }

    public UsageChangeUploadWholeEntity getFillBack() {
        String code = (String) mUsageChangeUploadCodeSpinner.getSelectedItem();
        String jh = (String) mUsageChangeUploadJhSpinner.getSelectedItem();

        if (TextUtil.isNullOrEmpty(code)
                && TextUtil.isNullOrEmpty(jh)) {
            ToastUtils.showLong("请填写必要信息");
            return null;
        }
        String remarks = mUsageChangeUploadRemarksEt.getText().toString();

        UsageChangeUploadWholeEntity entity = new UsageChangeUploadWholeEntity();
        entity.setJh(jh);
        entity.setSsdm(code);
        entity.setRemarks(TextUtil.isNullOrEmpty(remarks) ? "" : remarks);

        return entity;
    }
}