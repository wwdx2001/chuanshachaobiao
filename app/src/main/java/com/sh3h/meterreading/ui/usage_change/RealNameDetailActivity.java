package com.sh3h.meterreading.ui.usage_change;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dataprovider3.entity.RealNameWholeEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.lr.MyFragmentPagerAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.usage_change.contract.RealNameDetailContract;
import com.sh3h.meterreading.ui.usage_change.fragment.RealNameInfoFragment;
import com.sh3h.meterreading.ui.usage_change.fragment.RealNameMediaFragment;
import com.sh3h.meterreading.ui.usage_change.presenter.RealNameDetailPresenterImpl;
import com.sh3h.meterreading.util.Const;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealNameDetailActivity extends ParentActivity implements RealNameDetailContract.View {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private boolean isHistory;
    private MenuItem mItem1;
    private MenuItem mItem2;

    private MyFragmentPagerAdapter mPagerAdapter;
    private List<ParentFragment> mFragmentList;
    private RealNameInfoFragment realNameInfoFragment;
    private RealNameMediaFragment realNameMediaFragment;
    private RealNameDetailPresenterImpl mPresenter;
    private Bundle bundle;

    private String mS_cid;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name_detail;
    }

    @Override
    public void initView1() {
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
//        initToolBar();

        mFragmentList = new ArrayList<>();
        realNameInfoFragment = new RealNameInfoFragment();
        realNameMediaFragment = new RealNameMediaFragment();
        bindFragment();
    }

    private void bindFragment() {
        realNameInfoFragment.setArguments(bundle);
        realNameMediaFragment.setArguments(bundle);
        mFragmentList.add(realNameInfoFragment);
        mFragmentList.add(realNameMediaFragment);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(mPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.getTabAt(0).setText("具名制信息");
        tablayout.getTabAt(1).setText("附件信息");
    }

    @Override
    protected void initData1() {
        super.initData1();
        mPresenter = new RealNameDetailPresenterImpl(this);

        mPresenter.getUserType("");

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            mS_cid = bundle1.getString(Const.S_CID);
            mPresenter.getSaveData(mS_cid);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right_text, menu);
        mItem1 = menu.findItem(R.id.menu_wenti_shangbao);
        mItem1.setVisible(true);
        mItem1.setTitle("保存");
        mItem2 = menu.findItem(R.id.menu_right_text);
        mItem2.setTitle("上传");
        mItem2.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_wenti_shangbao:
                saveCommitData(true);
                break;
            case R.id.menu_right_text:
                saveCommitData(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCommitData(boolean isSave) {
        RealNameWholeEntity wholeEntity = realNameInfoFragment.getFillBack();
        if (wholeEntity == null) {
            return;
        }
        wholeEntity.setS_CID(mS_cid);
        wholeEntity.setID((mS_cid).hashCode());
        RealNameWholeEntity newWholeEntity = realNameMediaFragment.getImagesInfo(wholeEntity);
        mPresenter.saveOrUpload(isSave, newWholeEntity);
    }


    @Override
    public void getUserType(List<String> strings) {
        bundle = new Bundle();
        bundle.putSerializable(Const.STRINGS, (Serializable) strings);
    }

    @Override
    public void getSaveData(RealNameWholeEntity bean) {
        bundle.putParcelable(Const.BEAN, bean);
        bindFragment();
    }

    @Override
    public void uploadSuccess(String s) {
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
