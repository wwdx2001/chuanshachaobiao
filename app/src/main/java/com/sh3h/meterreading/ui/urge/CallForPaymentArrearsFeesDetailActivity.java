package com.sh3h.meterreading.ui.urge;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentArrearsFeesDetailBean;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.custom_view.grid_view.DataGridView;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentArrearsFeesDetailContract;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentArrearsFeesDetailPresenterImpl;
import com.sh3h.meterreading.util.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 费用详情
 */
public class CallForPaymentArrearsFeesDetailActivity extends ParentActivity
  implements CallForPaymentArrearsFeesDetailContract.View {

  private RecyclerView mCallForPayArrearsFeesDeatilRv;
  private TableLayout mCallForPayArrearsFeesDeatilTl;
  @BindView(R.id.call_for_pay_arrears_fees_deatil_dv)
  DataGridView mCallForPayArrearsFeesDeatilDv;

  CallForPaymentArrearsFeesDetailPresenterImpl mPresenter;

  private MenuItem mItem1;
  private TableRow row;

  private List<CallForPaymentArrearsFeesDetailBean> mBeans;
  private String mS_RENWUID;
  private String mS_CID;

  private int[] mHeaderContent = new int[]{R.string.header_text1, R.string.header_text2, R.string.header_text3,
    R.string.header_text4, R.string.header_text5, R.string.header_text6};
  private String[] mFieldNames = new String[]{"I_ZHANGWUNY", "N_SHUIFEI", "N_PAISHUIF", "N_JE", "N_WEIYUEJ", "D_KAIZHANG"};
  private float[] mColunmWeight = new float[]{1, 1, 1, 1, 1, 1};
  private Class[] mCellContentViews = new Class[]{TextView.class, TextView.class, TextView.class, TextView.class, TextView.class, TextView.class};

  @Override
  protected int getLayoutId() {
    return R.layout.activity_call_for_payment_arrears_fees_detail;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.menu_right_text, menu);
    mItem1 = menu.findItem(R.id.menu_right_text);
    mItem1.setTitle(R.string.refresh_text);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_right_text:
        mPresenter.getArrearsFeesDetail(mS_RENWUID, mS_CID);
//        TextView textView = new TextView(this);
//        textView.setText("132456");
//        textView.setTextSize(20);
//        row.addView(textView);
//        mCallForPayArrearsFeesDeatilTl.addView(row);
        break;

    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void initView1() {
//    row = new TableRow(this);
//    mCallForPayArrearsFeesDeatilTl = findViewById(R.id.call_for_pay_arrears_fees_deatil_tl);

//    TextView textView = new TextView(this);
//    textView.setText("132456");
//    textView.setTextSize(20);
//    row.addView(textView);
//    mCallForPayArrearsFeesDeatilTl.addView(row);
//    mCallForPayArrearsFeesDeatilTl.setStretchAllColumns(true);
//    mCallForPayArrearsFeesDeatilRv = findViewById(R.id.call_for_pay_arrears_fees_deatil_rv);
    ButterKnife.bind(this);
    getSupportActionBar().setTitle(getResources().getString(R.string.call_for_pay_arrears_fees_detail_title));
  }

  private void initDataGridView() {
    mCallForPayArrearsFeesDeatilDv.setHeaderHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    mCallForPayArrearsFeesDeatilDv.setRowHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    mCallForPayArrearsFeesDeatilDv.setHeaderTextSize(14);
    mCallForPayArrearsFeesDeatilDv.setRowTextSize(12);
    mCallForPayArrearsFeesDeatilDv.setColunms(6);
    mCallForPayArrearsFeesDeatilDv.setHeaderContentByStringId(mHeaderContent);
    mCallForPayArrearsFeesDeatilDv.setFieldNames(mFieldNames);
    mCallForPayArrearsFeesDeatilDv.setColunmWeight(mColunmWeight);
    mCallForPayArrearsFeesDeatilDv.setCellContentView(mCellContentViews);
    mCallForPayArrearsFeesDeatilDv.setDataSource(mBeans);
    mCallForPayArrearsFeesDeatilDv.initDataGridView();
  }

  @Override
  protected void initData1() {
    super.initData1();
    mBeans = new ArrayList<>();
    getActivityComponent().inject(this);
    mPresenter = new CallForPaymentArrearsFeesDetailPresenterImpl(this);
    mPresenter.attachView(this);

    Intent intent = getIntent();
    mS_RENWUID = intent.getStringExtra(Const.S_RENWUID);
    mS_CID = intent.getStringExtra(Const.S_CID);

  }

  @Override
  protected void requestData1() {
    super.requestData1();
    mPresenter.getArrearsFeesDetail(mS_RENWUID, mS_CID);
    initDataGridView();
  }

  @Override
  public void success(Object o) {
    runOnUiThread(new Runnable() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void run() {
        mBeans.clear();
        List<CallForPaymentArrearsFeesDetailBean> detailBean = (List<CallForPaymentArrearsFeesDetailBean>) o;
        List<CallForPaymentArrearsFeesDetailBean> dataSource = mCallForPayArrearsFeesDeatilDv.getDataSource();
        if (dataSource != null && dataSource.size() > 0) {
          for (CallForPaymentArrearsFeesDetailBean bean : detailBean) {
            for (CallForPaymentArrearsFeesDetailBean bean1 : dataSource) {
              if (!bean.getD_KAIZHANG().equals(bean1.getD_KAIZHANG())
                || !bean.getI_ZHANGWUNY().equals(bean1.getI_ZHANGWUNY())
                || !bean.getN_JE().equals(bean1.getN_JE())
                || !bean.getN_PAISHUIF().equals(bean1.getN_PAISHUIF())
                || !bean.getN_SHUIFEI().equals(bean.getN_SHUIFEI())
                || !bean.getN_WEIYUEJ().equals(bean1.getN_WEIYUEJ())) {
                mBeans.add(bean);
              }
            }
          }
        } else {
          mBeans.addAll(detailBean);
        }

        mCallForPayArrearsFeesDeatilDv.setDataSource(mBeans);
        mCallForPayArrearsFeesDeatilDv.updateAll();
      }
    });
  }

  @Override
  public void failed(String result) {
    if (result != null) {
      ToastUtils.showLong(result);
    }
    ToastUtils.showLong(R.string.get_local_data_is_null_retry_refresh_text);
  }

  @Override
  public void error(String s) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.detachView();
  }
}
