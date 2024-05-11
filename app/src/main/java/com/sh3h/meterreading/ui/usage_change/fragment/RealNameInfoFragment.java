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
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.custom_view.SelectSpinnerView;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

/**
 * 实名制信息收集
 */
public class RealNameInfoFragment extends ParentFragment {

    private SelectSpinnerView mRealNameUserTypeSpinner;
    private EditText mRealNameContactPersonEt;
    private EditText mRealNamePhoneNumEt;
    private EditText mRealNameEmailEt;
    private EditText mRealNameRemarksEt;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_real_name_info;
    }

    @Override
    protected void initView1(View view) {
        super.initView1(view);
        mRealNameUserTypeSpinner = view.findViewById(R.id.real_name_user_type_spinner);
        mRealNameContactPersonEt = view.findViewById(R.id.real_name_contact_person_et);
        mRealNamePhoneNumEt = view.findViewById(R.id.real_name_phone_num_et);
        mRealNameEmailEt = view.findViewById(R.id.real_name_email_et);
        mRealNameRemarksEt = view.findViewById(R.id.real_name_Remarks_et);
    }

    @Override
    protected void initData1() {
        super.initData1();
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<String> strings = (List<String>) bundle.getSerializable(Const.STRINGS);
            RealNameWholeEntity entity = (RealNameWholeEntity) bundle.getParcelable(Const.BEAN);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mRealNameUserTypeSpinner.setAdapter(adapter);

            mRealNameContactPersonEt.setText(entity != null ? entity.getContactPerson() : "");
            mRealNamePhoneNumEt.setText(entity != null ? entity.getPhoneNum() : "");
            mRealNameEmailEt.setText(entity != null ? entity.getEmail() : "");
            mRealNameRemarksEt.setText(entity != null ? entity.getRemarks() : "");
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

    public RealNameWholeEntity getFillBack() {
        String userType = (String) mRealNameUserTypeSpinner.getSelectedItem();
        String contactPerson = mRealNameContactPersonEt.getText().toString();
        String phoneNum = mRealNamePhoneNumEt.getText().toString();

        if (TextUtil.isNullOrEmpty(userType)
                && TextUtil.isNullOrEmpty(contactPerson)
                && TextUtil.isNullOrEmpty(phoneNum)) {
            ToastUtils.showLong("请填写必要信息");
            return null;
        }
        String email = mRealNameEmailEt.getText().toString();
        String remarks = mRealNameRemarksEt.getText().toString();

        RealNameWholeEntity realNameWholeEntity = new RealNameWholeEntity();
        realNameWholeEntity.setUserType(userType);
        realNameWholeEntity.setContactPerson(contactPerson);
        realNameWholeEntity.setPhoneNum(phoneNum);
        realNameWholeEntity.setEmail(TextUtil.isNullOrEmpty(email) ? "" : email);
        realNameWholeEntity.setRemarks(TextUtil.isNullOrEmpty(remarks) ? "" : remarks);

        return realNameWholeEntity;
    }
}