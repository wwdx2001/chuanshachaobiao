package com.sh3h.meterreading.ui.urge.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dataprovider3.entity.CuijiaoEntity;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.InspectionInput.lr.MyFragmentPagerAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.Const;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class CallForPaymentOrderBackActivity extends ParentActivity implements ViewPager.OnPageChangeListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    public ViewPager mViewPager;
    private MenuItem menuItem;

    private MyFragmentPagerAdapter mPagerAdapter;
    private List<ParentFragment> mFragmentList;

    private OrderBackHandleFragment handleFragment;
    private OrderBackMultiMediaFragment multiMediaFragment;

    private CuijiaoEntity mCuijiaoEntity;
    private int position;
    private String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline_order_back);
        userName = getIntent().getStringExtra(Const.USERNAME);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.arrow);
        mToolbar.setTitle("退单申请");
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mCuijiaoEntity = getIntent().getParcelableExtra(Const.BEAN);
        position = getIntent().getIntExtra(Const.POSITION, 0);


        mFragmentList = new ArrayList<>();

        handleFragment = OrderBackHandleFragment.newInstance(mCuijiaoEntity, userName);
        multiMediaFragment = OrderBackMultiMediaFragment.newInstance("", "");
        mFragmentList.add(handleFragment);
        mFragmentList.add(multiMediaFragment);

        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("退单申请");
        mTabLayout.getTabAt(1).setText("多媒体");

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_text, menu);
        menuItem = menu.findItem(R.id.item_menu_text);
        menuItem.setTitle("提交");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_text:
                if (multiMediaFragment.isError()) {
                    commitMultiMediaData();
                } else {
                    handleFragment.commitData(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(String o) {
                            // 提交图片
                            commitMultiMediaData();
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void commitMultiMediaData() {
        multiMediaFragment.commitMultiMediaData(mCuijiaoEntity.getS_CID(), new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showShort(s);
                Intent intent = new Intent();
                intent.putExtra(Const.POSITION, position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
