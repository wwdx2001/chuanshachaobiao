package com.sh3h.meterreading.ui.urge.back;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.tools.PickerViewUtils;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentBackfillContract;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentBackfillPresenterImpl;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.ResultBean;
import com.sh3h.serverprovider.entity.XJXXWordBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderBackHandleFragment extends ParentFragment implements CallForPaymentBackfillContract.View {

    @Inject
    CallForPaymentBackfillPresenterImpl mPresenter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CuijiaoEntity mCuijiaoEntity;
    private String userName;

    private AppCompatEditText tvTdbzValue;
    private EditText tvTdyyValue;

    private SimpleCallBack<String> mDownloadWordsCallBack;
    private List<String> mTuiDanYYList;

    public static OrderBackHandleFragment newInstance(CuijiaoEntity param1, String param2) {
        OrderBackHandleFragment fragment = new OrderBackHandleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCuijiaoEntity = getArguments().getParcelable(ARG_PARAM1);
            userName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_back_handle;
    }

    @Override
    protected void initView1(View view) {
        tvTdyyValue = view.findViewById(R.id.tv_tdyy_value);
        tvTdbzValue = view.findViewById(R.id.tv_tdbz_value);
        tvTdyyValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTuiDanYYList != null && mTuiDanYYList.size() > 0) {
                    KeyboardUtils.hideSoftInput(getActivity());
                    PickerViewUtils.getInstance().showCustomDialog("请选择退单原因", mTuiDanYYList, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvTdyyValue.setText(mTuiDanYYList.get(options1));
                        }
                    });
                } else {
                    initData1();
                }
            }
        });
    }

    @Override
    protected void initData1() {
        super.initData1();
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);

        List<XJXXWordBean> reasonList = mPresenter.getQFYYWordData("退单原因", null);
        mTuiDanYYList = new ArrayList<>();
        for (XJXXWordBean reason : reasonList) {
            mTuiDanYYList.add(reason.getMNAME());
        }
    }

    @Override
    protected void lazyLoad() {

    }

    protected void commitData(CallBack<String> callBack) {
        if (StringUtils.isTrimEmpty(tvTdyyValue.getText().toString()) ||
                StringUtils.isTrimEmpty(tvTdbzValue.getText().toString())) {
            ToastUtils.showShort("退单原因和退单备注不能为空");
            return;
        }
        IProgressDialog iProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("退单申请中...");
                return progressDialog;
            }
        };
        EasyHttp.post(URL.BASE_URGE_URL1 + URL.CuiJiaoTuiDan)
                .params("Gdh", mCuijiaoEntity.getS_CID())
                .params("S_TUIDANYY", tvTdyyValue.getText().toString())
                .params("S_TUIDANBZ", tvTdbzValue.getText().toString())
                .execute(new ProgressDialogCallBack<String>(iProgressDialog, true, false) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        ToastUtils.showShort("Error：" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        ResultBean baseBean = GsonUtils.fromJson(s, ResultBean.class);
                        if ("true".equals(baseBean.getMsgCode())) {
                            callBack.onSuccess("退单申请成功");
                        } else {
                            ToastUtils.showShort("Error：" + baseBean.getMsgInfo());
                        }
                    }
                });
    }

    @Override
    public void success(Object o) {

    }

    @Override
    public void failed(String result) {

    }

    @Override
    public void error(String s) {

    }
}


