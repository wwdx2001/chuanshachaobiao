package com.sh3h.meterreading.ui.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.ui.information.ArrearsWaterFragment;
import com.sh3h.meterreading.ui.information.BasicInformationFragment;
import com.sh3h.meterreading.ui.information.ChangeWaterFragment;
import com.sh3h.meterreading.ui.information.PayWaterFragment;
import com.sh3h.meterreading.ui.information.ReadWaterFragment;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liurui on 2016/2/23.
 */
public class DetailInfoFragment extends ParentFragment {
    public static final String TAG = "DetailInfoFragment";

    @Bind(R.id.aci_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.aci_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.aci_viewPager)
    ViewPager mViewPage;

    private List<Fragment> fragmentList;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        LogUtil.i(TAG, "---DetailInfoFragment onCreate---");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer_information, container, false);
        LogUtil.e("DetailInfoFragment", "onCreateView");
        ButterKnife.bind(this, view);

        mToolbar.setVisibility(View.GONE);

        initViewPager();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void initViewPager() {
        Bundle bundle = getArguments();

        fragmentList = new ArrayList<>();

        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());

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


        mViewPage.setOffscreenPageLimit(1);
        mViewPage.setAdapter(myAdapter);
//        viewPager.setCurrentItem(0);

        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mTabLayout.setupWithViewPager(mViewPage);
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
