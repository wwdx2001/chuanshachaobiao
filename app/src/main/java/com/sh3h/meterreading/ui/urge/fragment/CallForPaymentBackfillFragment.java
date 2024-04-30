package com.sh3h.meterreading.ui.urge.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentBackFillDataBean;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.custom_view.SelectSpinnerView;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentBackfillContract;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentBackfillPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;
import com.sh3h.serverprovider.entity.XJXXWordBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class CallForPaymentBackfillFragment extends ParentFragment implements CallForPaymentBackfillContract.View {

  @Inject
  CallForPaymentBackfillPresenterImpl mPresenter;

//  private RecyclerView mCallForPayBackFillSpinnerRv;
  private EditText mCallForPayBackFillEt;
  private SelectSpinnerView mSpinnerValue1, mSpinnerValue2, mSpinnerValue3, mSpinnerValue4,
    mSpinnerValue5, mSpinnerValue6, mSpinnerValue7;
  private LinearLayout mCallForPayBackFillContactsLl, mCallForPayBackFillPhoneLl;
  private EditText mCallForPayBackFillContactsEt, mCallForPayBackFillPhoneEt;

  //  private CallForPaymentBackFillAdapter mAdapter;
  private HashMap<String, String> mDataValue;
  private ArrayList<String> mQfyyList2, mQfyyList2Sign, mQfyyList1Sign, data1, data3;
  private CallForPaymentBackFillDataBean mBackFillData;
  private boolean isFirst;

  private final String[] keys = {"1级欠费原因：", "2级欠费原因：", "　现场是否用水：", "　是否能联系到用户：",
    "主体是否存在：", "是否愿意付费：", "是否信息更变："};

  public CallForPaymentBackfillFragment() {
    // Required empty public constructor
  }


  @Override
  protected int getLayoutId() {
    return R.layout.fragment_call_for_payment_backfill;
  }

  @Override
  protected void initView1(View view) {

    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
//    mCallForPayBackFillSpinnerRv = view.findViewById(R.id.call_for_pay_back_fill_spinner_rv);
    mSpinnerValue1 = view.findViewById(R.id.spinner_value1);
    mSpinnerValue2 = view.findViewById(R.id.spinner_value2);
    mSpinnerValue3 = view.findViewById(R.id.spinner_value3);
    mSpinnerValue4 = view.findViewById(R.id.spinner_value4);
    mSpinnerValue5 = view.findViewById(R.id.spinner_value5);
    mSpinnerValue6 = view.findViewById(R.id.spinner_value6);
    mSpinnerValue7 = view.findViewById(R.id.spinner_value7);
    mCallForPayBackFillEt = view.findViewById(R.id.call_for_pay_back_fill_et);
    mCallForPayBackFillContactsLl = view.findViewById(R.id.call_for_pay_back_fill_contacts_ll);
    mCallForPayBackFillContactsEt = view.findViewById(R.id.call_for_pay_back_fill_contacts_et);
    mCallForPayBackFillPhoneLl = view.findViewById(R.id.call_for_pay_back_fill_phone_ll);
    mCallForPayBackFillPhoneEt = view.findViewById(R.id.call_for_pay_back_fill_phone_et);

//    mCallForPayBackFillSpinnerRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
  }

  @Override
  protected void initData1() {
    super.initData1();
    if (getArguments() != null && getArguments().getParcelable(Const.CALLFORPAYMENTBACKFILLDATABEAN) != null) {
      mBackFillData = getArguments().getParcelable(Const.CALLFORPAYMENTBACKFILLDATABEAN);
    } else {
      mBackFillData = new CallForPaymentBackFillDataBean();
    }

    List<XJXXWordBean> qfyyList1 = mPresenter.getQFYYWordData("欠费原因1级", null);

    mDataValue = new HashMap();
    data1 = new ArrayList<>();
    mQfyyList2 = new ArrayList<>();
    mQfyyList2Sign = new ArrayList<>();
    mQfyyList1Sign = new ArrayList<>();
    data3 = new ArrayList<>();

    for (XJXXWordBean bean : qfyyList1) {
      data1.add(bean.getMNAME());
      mDataValue.put(bean.getMNAME(), bean.getMVALUE());
      mQfyyList1Sign.add(bean.getMVALUE().split("-")[0]);
    }

    data3.add("否");
    data3.add("是");


  }

  @Override
  protected void requestData() {
    super.requestData();
    isFirst = true;
    setSpinnerData(mSpinnerValue1, data1, mBackFillData.getV_QIANFEIYY1_SELECT_POSITION());
    getQFYY2List(mDataValue.get(mSpinnerValue1.getSelectedItem().toString()));
    setSpinnerData(mSpinnerValue2, mQfyyList2, mBackFillData.getV_QIANFEIYY2_SELECT_POSITION());
    setSpinnerData(mSpinnerValue3, data3, mBackFillData.getV_ISYONGSHUI_POSITION());
    setSpinnerData(mSpinnerValue4, data3, mBackFillData.getV_ISLIANXIYH_POSITION());
    setSpinnerData(mSpinnerValue5, data3, mBackFillData.getV_ISZHUTICZ_POSITION());
    setSpinnerData(mSpinnerValue6, data3, mBackFillData.getV_ISYYFF_POSITION());
    setSpinnerData(mSpinnerValue7, data3, mBackFillData.getV_ISXINXIBG_POSITION());
    mCallForPayBackFillContactsEt.setText(mBackFillData.getV_LIANXIR());
    mCallForPayBackFillPhoneEt.setText(mBackFillData.getV_LIANXIDH());
    mCallForPayBackFillEt.setText(mBackFillData.getV_BEIZHU());
  }

  private void setSpinnerData(SelectSpinnerView spinnerValue, ArrayList<String> data, int position) {
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerValue.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    spinnerValue.setSelection(position, true);
  }

  private void setSpinnerData(SelectSpinnerView spinnerValue, ArrayList<String> data) {
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerValue.setAdapter(adapter);
  }

  private void getQFYY2List(String value) {
    List<XJXXWordBean> qfyyList2 = mPresenter.getQFYYWordData("欠费原因2级", value);

    mQfyyList2.clear();
    mQfyyList2Sign.clear();

    for (XJXXWordBean bean : qfyyList2) {
      mQfyyList2.add(bean.getMNAME());
      mQfyyList2Sign.add(bean.getMVALUE().split("-")[1]);
    }
  }

  @Override
  protected void initListener1() {
    super.initListener1();
    mSpinnerValue1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mBackFillData.setV_QIANFEIYY1_POSITION(Integer.parseInt(mQfyyList1Sign.get(position)));
        mBackFillData.setV_QIANFEIYY1_SELECT_POSITION(position);

        if (!isFirst) {
          String value = mDataValue.get(mSpinnerValue1.getSelectedItem().toString());

          getQFYY2List(value);

          setSpinnerData(mSpinnerValue2, mQfyyList2);
        } else {
          isFirst = false;
        }
      }


      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mQfyyList2Sign.size() > 0) {
          try {
            mBackFillData.setV_QIANFEIYY2_SELECT_POSITION(position);
            mBackFillData.setV_QIANFEIYY2_POSITION(Integer.parseInt(mQfyyList2Sign.get(position)));
          } catch (Exception e) {
            mBackFillData.setV_QIANFEIYY2_POSITION(0);
            mBackFillData.setV_QIANFEIYY2_SELECT_POSITION(0);
          }
        } else {
          mBackFillData.setV_QIANFEIYY2_POSITION(0);
          mBackFillData.setV_QIANFEIYY2_SELECT_POSITION(0);
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBackFillData.setV_ISYONGSHUI_POSITION(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBackFillData.setV_ISLIANXIYH_POSITION(position);

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBackFillData.setV_ISZHUTICZ_POSITION(position);

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBackFillData.setV_ISYYFF_POSITION(position);

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mSpinnerValue7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBackFillData.setV_ISXINXIBG_POSITION(position);
        if (position == 1) {
          mCallForPayBackFillContactsLl.setVisibility(View.VISIBLE);
          mCallForPayBackFillPhoneLl.setVisibility(View.VISIBLE);
        } else {
          mCallForPayBackFillContactsLl.setVisibility(View.GONE);
          mCallForPayBackFillPhoneLl.setVisibility(View.GONE);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  public CallForPaymentBackFillDataBean getBackFillData() {
    String selectedText = (String) mSpinnerValue7.getSelectedItem();
    String s = mCallForPayBackFillEt.getText().toString();
    String contactsText = mCallForPayBackFillContactsEt.getText().toString();
    String phoneText = mCallForPayBackFillPhoneEt.getText().toString();

    if (selectedText.equals("是")) {
      if (TextUtil.isNullOrEmpty(contactsText)) {
        ToastUtils.showLong("请填写要修改的联系人");
        return null;
      } else {
        mBackFillData.setV_LIANXIR(contactsText);
      }

      if (TextUtil.isNullOrEmpty(phoneText)) {
        ToastUtils.showLong("请填写要修改的联系人电话");
        return null;
      } else {
        if (phoneText.length() != 11) {
          ToastUtils.showLong("请填写正确的联系电话");
        }
        mBackFillData.setV_LIANXIDH(phoneText);
      }
    }
    if (TextUtil.isNullOrEmpty(s)) {
      mBackFillData.setV_BEIZHU("");
    } else {
      mBackFillData.setV_BEIZHU(s);
    }


    return mBackFillData;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.detachView();
  }

  @Override
  protected void lazyLoad() {

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
