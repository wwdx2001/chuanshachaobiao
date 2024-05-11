package com.sh3h.meterreading.ui.urge.delay;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.tools.PickerViewUtils;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.custom_view.UnderlineEditText;
import com.sh3h.meterreading.util.URL;
import com.sh3h.serverprovider.entity.ResultBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDelayHandleFragment extends ParentFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CuijiaoEntity mCuijiaoEntity;
    private String userName;

    private AppCompatEditText tvYqyyValue, tvYqbzValue;
    private UnderlineEditText tvYqscValue;


    public static OrderDelayHandleFragment newInstance(CuijiaoEntity param1, String param2) {
        OrderDelayHandleFragment fragment = new OrderDelayHandleFragment();
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
        return R.layout.fragment_order_delay_handle;
    }

    @Override
    protected void initView1(View view) {
        tvYqyyValue = view.findViewById(R.id.tv_yqyy_value);
        tvYqbzValue = view.findViewById(R.id.tv_yqbz_value);
        tvYqscValue = view.findViewById(R.id.tv_yqsc_value);

        tvYqyyValue.setOnTouchListener(onTouchListener);
        tvYqbzValue.setOnTouchListener(onTouchListener);
//        tvYqscValue.setOnTouchListener(onTouchListener);
//        tvYqscValue.addTextChangedListener(watcher);

        tvYqscValue.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    private TextWatcher watcher = new TextWatcher() {

        final String[] outStr = {""}; //这个值存储输入超过两位数时候显示的内容

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String edit = charSequence.toString();
            if (edit.length() == 2 && Integer.parseInt(edit) >= 10) {
                outStr[0] = edit;
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String words = charSequence.toString();
            //首先内容进行非空判断，空内容（""和null）不处理
            if (!StringUtils.isEmpty(words)) {
                //1-100的正则验证
                Pattern p = Pattern.compile("^(100|[1-9]\\d|\\d)$");
                Matcher m = p.matcher(words);
                if (m.find() || ("").equals(words)) {
                    //这个时候输入的是合法范围内的值
                } else {
                    if (words.length() > 2) {
                        //若输入不合规，且长度超过2位，继续输入只显示之前存储的outStr
                        tvYqscValue.setText(outStr[0]);
                        //重置输入框内容后默认光标位置会回到索引0的地方，要改变光标位置
                        tvYqscValue.setSelection(2);
                    }
//                                    ToastUtil.show("请输入范围在1-100之间的整数");
                }
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable editable) {
            //这里的处理是不让输入0开头的值
            String words = editable.toString();
            //首先内容进行非空判断，空内容（""和null）不处理
            if (!StringUtils.isEmpty(words)) {
                try {
                    if (Integer.parseInt(editable.toString()) <= 0) {
                        tvYqscValue.setText("");
                        ToastUtils.showShort("请输入范围在1-48之间的整数");
                    } else if (Integer.parseInt(editable.toString()) > 100) {
                        tvYqscValue.setText(outStr[0]);
                        //将光标移至文字末尾
                        tvYqscValue.setSelection(tvYqscValue.getText().length());
                        ToastUtils.showShort("延期时长最大限定为100天");
                    }
                } catch (NumberFormatException e) {
                    tvYqscValue.setText("");
                }
            }

            String text = tvYqscValue.getText().toString();

        }
    };

    /**
     * 解决editText与NestedScrollView 的滑动冲突
     */
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //这句话是告诉父view，我的事件自己处理
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    protected void commitData(CallBack<String> callBack) {
//        if (StringUtils.isTrimEmpty(tvYqyyValue.getText().toString())) {
//            ToastUtils.showShort("延期原因不能为空");
//            return;
//        }
        if (StringUtils.isTrimEmpty(tvYqbzValue.getText().toString())) {
            ToastUtils.showShort("延期备注不能为空");
            return;
        }
        if (StringUtils.isTrimEmpty(tvYqscValue.getText().toString())) {
            ToastUtils.showShort("延期时长不能为空");
            return;
        }
        com.zhouyou.http.subsciber.IProgressDialog iProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("延期申请中...");
                return progressDialog;
            }
        };
        EasyHttp.post(URL.BASE_URGE_URL1 + URL.CuiJiaoYanQi)
                .params("Gdh", mCuijiaoEntity.getS_RENWUID())
                .params("D_YANQISJ", tvYqscValue.getText().toString())
                .params("S_YANQIBZ", tvYqbzValue.getText().toString())
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
                            callBack.onSuccess("延期申请成功");
                        } else {
                            ToastUtils.showShort("Error：" + baseBean.getMsgInfo());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yqsc_value:
                PickerViewUtils.getInstance().showTimeDialog("请选择延期日期", new OnTimeSelectListener(){
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tvYqscValue.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy/MM/dd")));
                    }
                }, new boolean[]{true, true, true, false, false, false},
                        5, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                break;
        }
    }
}
