package com.sh3h.meterreading.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sh3h.dataprovider.schema.ChaoBiaoSJColumns;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by xulongjun on 2016/2/16.
 */
public class CustomerInformationActivity extends ParentActivity implements CustomerInformationMvpView {
    private static final String TAG = "CustomerInformationActivity";
    public static final String S_TASKID = "S_TASKID"; // 任务号
    public static final String S_CH = "S_CH"; // 册本号
    public static final String S_CID = "S_CID"; // 用户号
    public static final String I_GROUPID = "I_GROUPID"; //组号
    public static final String IS_LOCAL_SEARCH = "IS_LOCAL_SEARCH"; //
    public static final String DUCARD = "DUCARD"; //
    public static final String WHERE = "where"; // 哪里来的

    @Inject
    CustomerInformationPresenter mCustomerInformationPresenter;

    @Inject
    Bus mEventBus;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @BindView(R.id.aci_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.aci_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.aci_viewPager)
    ViewPager mViewPage;

    private int mRenWuBH;
    private String mCh;
    private String mCid;
    private int mGroupId;
    private boolean mIsLocalSearch;
    private int where;
    private DUCard mDUCard;

    public CustomerInformationActivity() {
        mRenWuBH = 0;
        mCh = null;
        mCid = null;
        mGroupId = 0;
        mDUCard = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setActionBarBackButtonEnable();
        mCustomerInformationPresenter.attachView(this);
        mEventBus.register(this);

        needDialog = false;
        initViewPager();
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mCustomerInformationPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    private void initViewPager() {
        Bundle bundle = getBundle();
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());

        BasicInformationFragment basicInformationFragment1 = new BasicInformationFragment();
        basicInformationFragment1.setArguments(bundle);

        myAdapter.addFragment(basicInformationFragment1, getResources().getString(R.string.label_jibenxx));

        ReadWaterFragment readWaterFragment = new ReadWaterFragment();
        readWaterFragment.setArguments(bundle);
        myAdapter.addFragment(readWaterFragment, getResources().getString(R.string.lable_jinqicb));

        PayWaterFragment payWaterFragment = new PayWaterFragment();
        payWaterFragment.setArguments(bundle);
        myAdapter.addFragment(payWaterFragment, getResources().getString(R.string.lable_jinqijf));

        ArrearsWaterFragment arrearsWaterFragment = new ArrearsWaterFragment();
        arrearsWaterFragment.setArguments(bundle);
        myAdapter.addFragment(arrearsWaterFragment, getResources().getString(R.string.lable_qianfeixx));

        ChangeWaterFragment changeWaterFragment = new ChangeWaterFragment();
        changeWaterFragment.setArguments(bundle);
        myAdapter.addFragment(changeWaterFragment, getResources().getString(R.string.lable_huanbiaojl));

        mViewPage.setAdapter(myAdapter);
        mTabLayout.setupWithViewPager(mViewPage);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private Bundle getBundle() {
        Intent intent = getIntent();
        mRenWuBH = intent.getIntExtra(S_TASKID, 0);
        mCh = TextUtil.getString(intent.getStringExtra(S_CH));
        mCid = TextUtil.getString(intent.getStringExtra(S_CID));
        mGroupId = intent.getIntExtra(I_GROUPID, 0);
        mDUCard = (DUCard) intent.getSerializableExtra(DUCARD);
        mIsLocalSearch = intent.getBooleanExtra(IS_LOCAL_SEARCH, true);
        where = intent.getIntExtra(WHERE, 0);

        Bundle bundle = new Bundle();
        bundle.putString(ChaoBiaoSJColumns.CID, mCid);
        bundle.putString(ChaoBiaoSJColumns.CH, mCh);
        bundle.putInt(ChaoBiaoSJColumns.I_RENWUBH, mRenWuBH);
        bundle.putInt(ChaoBiaoSJColumns.I_GROUPID, mGroupId);
        bundle.putBoolean(IS_LOCAL_SEARCH, mIsLocalSearch);
        bundle.putInt(WHERE, where);
        if (mDUCard != null) {
            bundle.putSerializable(CustomerInformationActivity.DUCARD, mDUCard);
        }

        return bundle;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ChaoBiaoSJColumns.CID, mCid);
        outState.putString(ChaoBiaoSJColumns.CH, mCh);
        outState.putInt(ChaoBiaoSJColumns.I_RENWUBH, mRenWuBH);
        outState.putInt(ChaoBiaoSJColumns.I_GROUPID, mGroupId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customer_info, menu);
        MenuItem menuItem = menu.findItem(R.id.mci_action_update);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (where == 1){
                    onCheckForUpdatingCard(true);
                }else {
                    mCustomerInformationPresenter.checkForUpdatingCard(mRenWuBH, mCh);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Subscribe
    public void onSyncDataStart(UIBusEvent.SyncDataStart syncDataStart) {
        LogUtil.i(TAG, "---onSyncDataStart---");
        if (needDialog) {
            showProgress(R.string.dialog_sync_data);
        }
    }

    @Subscribe
    public void onSyncDataEnd(UIBusEvent.SyncDataEnd syncDataEnd) {
        LogUtil.i(TAG, "---onSyncSpecialDataEnd---");
        if (needDialog) {
            needDialog = false;
            hideProgress();
        }
        mEventPosterHelper.postEventSafely(new UIBusEvent.CustomerInformationUpdating());
    }

    @Override
    public void onError(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void onCheckForUpdatingCard(boolean canUpdate) {
        if (canUpdate) {
            needDialog = true;
            if (TextUtil.isNullOrEmpty(mCh)) {
                downloadDetailInfo(0, String.valueOf(mGroupId), mCid);
            } else {
                downloadDetailInfo(0, mCh, mCid);
            }
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_uploadCards_firstly);
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
