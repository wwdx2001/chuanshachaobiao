package com.sh3h.meterreading.ui.usage_change;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCombined;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.usage_change.adapter.UsageChangeSearchListAdapter;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeContract;
import com.sh3h.meterreading.ui.usage_change.presenter.UsageChangeBasicPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.RecyclerSpacingItemDecoration;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsageChangeSearchListActivity extends ParentActivity implements UsageChangeContract.View, BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mUsageChangeListRv;
    private SearchView mSearchView;

    @Inject
    UsageChangeBasicPresenterImpl mBasicPresenter;

    private UsageChangeSearchListAdapter mAdapter;

    private List<DUCard> mDUCardList;
    private DUCard mDUCard;
    private String mS_cid, mAddress;
    private int mItemPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_usage_change_search_list;
    }

    @Override
    protected void initView1() {
        super.initView1();
        setActionBarBackButtonEnable();
        mUsageChangeListRv = (RecyclerView) findViewById(R.id.usage_change_list_rv);
        mUsageChangeListRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mUsageChangeListRv.addItemDecoration(new RecyclerSpacingItemDecoration(0, 6, 0, 0));
    }

    @Override
    protected void initData1() {
        super.initData1();
        getActivityComponent().inject(this);
        mBasicPresenter.attachView(this);

        Intent intent = getIntent();
        mS_cid = intent.getStringExtra(Const.S_CID);
        mAddress = intent.getStringExtra(Const.S_DZ);
        mDUCardList = new ArrayList<>();
        mAdapter = new UsageChangeSearchListAdapter(mDUCardList);
        mUsageChangeListRv.setAdapter(mAdapter);
    }

    @Override
    protected void initListener1() {
        super.initListener1();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasicPresenter.detachView();
    }

    @Override
    protected void requestData1() {
        super.requestData1();
        DUCombinedInfo duCombinedInfo = new DUCombinedInfo();
        DUCombined duCombined = new DUCombined();
        duCombined.set_cid(mS_cid);
        duCombined.set_dizhi(mAddress);
        duCombinedInfo.setDuCombined(duCombined);
        mBasicPresenter.getCombinedResults(duCombinedInfo, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置输入框提示语
        mSearchView.setQueryHint("输入用户号或地址查询");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBasicPresenter.searchData(query, mDUCardList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtil.isNullOrEmpty(newText)) {
                    searchDataNotify(mDUCardList);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mItemPosition = position;
        mDUCard = mDUCardList.get(position);
        if (mDUCard.getI_DingESL() > 0) {
            ToastUtils.showLong("当前表卡正在其他流程中，不能进行操作");
            return;
        }
        mBasicPresenter.getianHaoMX(mDUCard.getJianhao());
    }

    @Override
    public void onGetJianHaoMXFinish(List<JianHaoMX> list) {
        Intent intent = new Intent(this, UsageChangeBasicInformationActivity.class);
        intent.putExtra(Const.BEAN, mDUCard);
        intent.putExtra(Const.JIANHAOMX, (Serializable) list);
        startActivity(intent);
    }

    @Override
    public void searchDataNotify(List<DUCard> duCard) {
        mAdapter.setNewData(duCard);
    }

    @Override
    public void success(Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<DUCard> data = (List<DUCard>) o;
                mDUCardList.clear();
                mDUCardList.addAll(data);
                mAdapter.setNewData(mDUCardList);
            }
        });
    }

    @Override
    public void failed(String result) {
        ToastUtils.showLong(result);
    }

    @Override
    public void error(String s) {
        ToastUtils.showLong(s);
    }
}
