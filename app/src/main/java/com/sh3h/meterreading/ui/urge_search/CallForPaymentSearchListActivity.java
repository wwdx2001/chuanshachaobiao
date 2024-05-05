package com.sh3h.meterreading.ui.urge_search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CallForPaymentSearchBean;
import com.example.dataprovider3.entity.CallForPaymentTaskBean;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.urge.adapter.CallForPaymentSearchListAdapter;
import com.sh3h.meterreading.ui.urge.contract.CallForPaymentSearchListContract;
import com.sh3h.meterreading.ui.urge.presenter.CallForPaymentSearchListPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.RecyclerSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CallForPaymentSearchListActivity extends ParentActivity implements CallForPaymentSearchListContract.View{

//    @BindView(R.id.call_for_pay_search_rv)
    RecyclerView mSearchRv;

    private MenuItem mItem1;
    private MenuItem mItem2;
    private CallForPaymentSearchListPresenterImpl mPresenter;
    private CallForPaymentSearchListAdapter mAdapter;

    private List<CallForPaymentSearchBean> mSearchList;
    private String mCid;
    private String mAddress;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_for_payment_search_list;
    }

    @Override
    protected void initView1() {
        super.initView1();
        setActionBarBackButtonEnable();
        mSearchRv = (RecyclerView) findViewById(R.id.call_for_pay_search_rv);
        mSearchRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mSearchRv.addItemDecoration(new RecyclerSpacingItemDecoration(0, 6, 0, 0));
    }

    @Override
    protected void initData1() {
        super.initData1();
        mPresenter = new CallForPaymentSearchListPresenterImpl(this);

        Intent intent = getIntent();
        mCid = intent.getStringExtra(Const.S_CID);
        mAddress = intent.getStringExtra(Const.S_DZ);

        mSearchList = new ArrayList<>();
        mAdapter = new CallForPaymentSearchListAdapter(mSearchList);
        mSearchRv.setAdapter(mAdapter);
    }

    @Override
    protected void requestData1() {
        super.requestData1();
        mPresenter.getSearchListData(mCid, mAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right_text, menu);
        mItem1 = menu.findItem(R.id.menu_wenti_shangbao);
        mItem1.setVisible(true);
        mItem1.setTitle("提交");
        mItem2 = menu.findItem(R.id.menu_right_text);
        mItem2.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_wenti_shangbao:
                mPresenter.submitData(mAdapter.getData());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<CallForPaymentSearchBean> data = (List<CallForPaymentSearchBean>) o;
                mSearchList.clear();
                mSearchList.addAll(data);
                mAdapter.setNewData(mSearchList);
            }
        });
    }

    @Override
    public void failed(String result) {
        ToastUtils.showLong(result);
    }

    @Override
    public void error(String s) {

    }
}