package com.sh3h.meterreading.ui.usage_change;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.UsageChangeUploadWholeEntity;
import com.sh3h.dataprovider.entity.JianHaoMX;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.lr.MyFragmentPagerAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.usage_change.contract.UsageChangeUploadContract;
import com.sh3h.meterreading.ui.usage_change.fragment.UsageChangeInfoFragment;
import com.sh3h.meterreading.ui.usage_change.fragment.UsageChangeMediaFragment;
import com.sh3h.meterreading.ui.usage_change.presenter.UsageChangeUploadPresenterImpl;
import com.sh3h.meterreading.util.Const;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsageChangeUploadActivity extends ParentActivity implements UsageChangeUploadContract.View {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private boolean isHistory;
    private MenuItem mItem1;
    private MenuItem mItem2;

    private MyFragmentPagerAdapter mPagerAdapter;
    private List<ParentFragment> mFragmentList;
    private UsageChangeInfoFragment usageChangeInfoFragment;
    private UsageChangeMediaFragment usageChangeMediaFragment;
    private UsageChangeUploadPresenterImpl mPresenter;
    private Bundle mBundle;

    private String mS_cid;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name_detail;
    }

    @Override
    public void initView1() {
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        setActionBarBackButtonEnable();

        mFragmentList = new ArrayList<>();
        usageChangeInfoFragment = new UsageChangeInfoFragment();
        usageChangeMediaFragment = new UsageChangeMediaFragment();
    }

    private void bindFragment() {
        usageChangeInfoFragment.setArguments(mBundle);
        usageChangeMediaFragment.setArguments(mBundle);
        mFragmentList.add(usageChangeInfoFragment);
        mFragmentList.add(usageChangeMediaFragment);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(mPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.getTabAt(0).setText("用水性质信息");
        tablayout.getTabAt(1).setText("附件信息");
    }

    @Override
    protected void initData1() {
        super.initData1();
        mPresenter = new UsageChangeUploadPresenterImpl(this);

        mBundle = new Bundle();

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            mS_cid = bundle1.getString(Const.S_CID);
            mPresenter.getSaveData(mS_cid);
            List<JianHaoMX> jianHaoMXList = (List<JianHaoMX>) bundle1.getSerializable(Const.LIST);
            mBundle.putString(Const.S_CID, mS_cid);
        }
        mPresenter.getJianhaoList();
        mPresenter.getCode("涉水对象");
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
        UsageChangeUploadWholeEntity wholeEntity = usageChangeInfoFragment.getFillBack();
        if (wholeEntity == null) {
            return;
        }
        wholeEntity.setS_CID(mS_cid);
        wholeEntity.setID((mS_cid).hashCode());
        UsageChangeUploadWholeEntity newWholeEntity = usageChangeMediaFragment.getImagesInfo(wholeEntity);
        if (newWholeEntity == null) {
            return;
        }
        mPresenter.saveOrUpload(isSave, newWholeEntity);
    }




    @Override
    public void getJianhaoList(List<String> list) {
        mBundle.putSerializable(Const.JIANHAOS, (Serializable) list);
    }

    @Override
    public void getCode(List<String> strings) {
        mBundle.putSerializable(Const.STRINGS, (Serializable) strings);
        bindFragment();
    }

    @Override
    public void getSaveData(UsageChangeUploadWholeEntity entity) {
        mBundle.putParcelable(Const.BEAN, entity);
    }

    @Override
    public void uploadSuccess(String s) {
    }

    @Override
    public void result(String s) {
        if (TextUtil.isNullOrEmpty(s)) {
            ToastUtils.showLong(getResources().getString(R.string.text_upload_success));
            finish();
        } else {
            ToastUtils.showLong(s);
        }
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
