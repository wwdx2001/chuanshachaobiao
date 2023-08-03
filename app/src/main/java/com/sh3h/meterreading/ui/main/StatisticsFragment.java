package com.sh3h.meterreading.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.meterreading.util.ApplicationsUtil;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liurui on 2016/1/27.
 */
public class StatisticsFragment extends ParentFragment implements ViewPager.OnPageChangeListener, View.OnClickListener, StatisticsMvpView {
    private static final String TAG = "StatisticsFragment";

    @Bind(R.id.ly_page1)
    RelativeLayout ly_page1;

    @Bind(R.id.ly_page2)
    RelativeLayout ly_page2;

    @Bind(R.id.tv_page1)
    TextView tv_page1;

    @Bind(R.id.tv_page2)
    TextView tv_page2;

    @Bind(R.id.viewPager_statitics)
    ViewPager viewPager;

    @Bind(R.id.imabtnPrev)
    ImageButton imabtnPrev;

    @Bind(R.id.imabtnNext)
    ImageButton imabtnNext;

    @Bind(R.id.cebenhao_data)
    TextView cebenhaoData;

    @Bind(R.id.total_water_data)
    TextView totalWaterData;

    @Inject
    StatisticsPresenter mStatisticsPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    //private List<Fragment> fragmentList;

    private StatisticsActivity mStatisticsActivity;
    private MyPagerAdapter mMyPagerAdapter;

    private List<HashMap<String, Object>> mCHRenWuBHList;

    private int _chIndex;

    private boolean isPrepared;
    private boolean isInited;

    private List<DUTask> duTasks;

    public StatisticsFragment() {
        _chIndex = -1;
        isInited = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatisticsActivity = (StatisticsActivity) getActivity();
        mStatisticsActivity.getActivityComponent().inject(this);
        LogUtil.i(TAG, "---onCreate---" + this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistcs, container, false);

        ButterKnife.bind(this, rootView);
        mStatisticsPresenter.attachView(this);

        isPrepared = true;
        lazyLoad();

        LogUtil.i(TAG, "---onCreateView---" + this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isPrepared = false;
        ButterKnife.unbind(this);
        mStatisticsPresenter.detachView();
        LogUtil.i(TAG, "---onDestroyView---");
    }

    private void init() {
        imabtnPrev.setOnClickListener(this);
        imabtnNext.setOnClickListener(this);

        ly_page1.setOnClickListener(this);
        ly_page2.setOnClickListener(this);

        mMyPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(mMyPagerAdapter);
//        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case MyPagerAdapter.BAR_CHART_FRAGMENT:
                        changeTypeStyle(0);
//                        barCharFragment.refreshChart();
                        break;
                    case MyPagerAdapter.PIE_CHART_FRAGMENT:
                        changeTypeStyle(1);
//                        pieChartFragment.refreshChart();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        isInited = true;

//        mStatisticsPresenter.getAllTasks(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_page1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ly_page2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.imabtnNext:
                if (mCHRenWuBHList == null) {
                    ApplicationsUtil.showMessage(mStatisticsActivity, R.string.text_nocebendata);
                    return;
                }
                if (mCHRenWuBHList.size() == 0) {
                    return;
                }
                if (_chIndex == mCHRenWuBHList.size() - 1) {
                    ApplicationsUtil.showMessage(mStatisticsActivity, R.string.text_lastceben);
                    return;
                }
                _chIndex++;
                updateCeBenData(_chIndex);
//                ApplicationsUtil.showMessage(mMainActivity, "imabtnNext");
                break;
            case R.id.imabtnPrev:
                if (mCHRenWuBHList == null) {
                    ApplicationsUtil.showMessage(mStatisticsActivity, R.string.text_nocebendata);
                    return;
                }
                if (mCHRenWuBHList.size() == 0) {
                    return;
                }
                if (_chIndex == 0) {
                    ApplicationsUtil.showMessage(mStatisticsActivity, R.string.text_firstceben);
                    return;
                }
                _chIndex--;
                updateCeBenData(_chIndex);
//                ApplicationsUtil.showMessage(mMainActivity, "imabtnPrev");
                break;

        }
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

    private void changeTypeStyle(int which) {
        switch (which) {
            case 0:
                ly_page1.setBackgroundResource(R.drawable.rectangle_left_select);
                tv_page1.setTextColor(getResources().getColor(R.color.text_white));
                ly_page2.setBackgroundResource(R.drawable.rectangle_right);
                tv_page2.setTextColor(getResources().getColor(R.color.text_darkGrey));
                break;
            case 1:
                ly_page1.setBackgroundResource(R.drawable.rectangle_left);
                tv_page1.setTextColor(getResources().getColor(R.color.text_darkGrey));
                ly_page2.setBackgroundResource(R.drawable.rectangle_right_select);
                tv_page2.setTextColor(getResources().getColor(R.color.text_white));
                break;
        }
    }
//    public void refreshChart(){
//        switch (viewPager.getCurrentItem()){
//            case 0:
//                barCharFragment.refreshChart();
//                break;
//            case 1:
//                pieChartFragment.refreshChart();
//                break;
//        }
//    }

    @Override
    protected void lazyLoad() {
        LogUtil.i(TAG, "---lazyLoad 1---");
        if (!isPrepared || !isVisible ) {
            LogUtil.i(TAG, "---lazyLoad 2---");
            return;
        }

        if (!isInited) {
            init();
            changeTypeStyle(0);
        }

        mStatisticsPresenter.getAllTasks(true);


        LogUtil.i(TAG, "---lazyLoad 3---");
    }

    @Override
    public void onError(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(mStatisticsActivity, message);
        }
    }

    @Override
    public void onGetTask(DUTask duTask) {
//        mEventPosterHelper.postEventSafely(new UIBusEvent.StatisticDataFinish(duTask));
    }

    @Override
    public void onGetAllTasks(List<DUTask> duTaskList) {
        if (duTaskList == null || duTaskList.size() == 0) {
            return;
        }

        mCHRenWuBHList = new ArrayList<>();
        for (DUTask duTask : duTaskList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("RenWuBH", duTask.getRenWuBH());
            map.put("CH", duTask.getcH());

            mCHRenWuBHList.add(map);
        }

        cebenhaoData.setText(duTaskList.get(0).getcH());

        _chIndex = 0;

        duTasks = duTaskList;

        if (duTasks.size() != 0) {
            mEventPosterHelper.postEventSafely(new UIBusEvent.StatisticDataFinish(duTasks.get(_chIndex)));
        }

        mStatisticsPresenter.getChaoJianS(duTasks.get(_chIndex).getRenWuBH(),
                duTasks.get(_chIndex).getcH());
    }

    @Override
    public void onGetChaoJianS(Integer integer) {
        LogUtil.i(TAG, "---onGetChaoJianS---");

        double chaoJianL = 0;
        int totalCount = duTasks.get(_chIndex).getZongShu();
        if (totalCount <= 0) {
            chaoJianL = 0;
        } else {
            chaoJianL = 100.0 * integer / totalCount;
        }

        String str = String.format(ConstDataUtil.LOCALE, "%.2f%%", chaoJianL);
        totalWaterData.setText(str);
    }

    private void updateCeBenData(int _chIndex) {
        cebenhaoData.setText(duTasks.get(_chIndex).getcH());
        mEventPosterHelper.postEventSafely(new UIBusEvent.StatisticDataFinish(duTasks.get(_chIndex)));
        mStatisticsPresenter.getChaoJianS(duTasks.get(_chIndex).getRenWuBH(),
                duTasks.get(_chIndex).getcH());
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public static final int BAR_CHART_FRAGMENT = 0;
        public static final int PIE_CHART_FRAGMENT = 1;
        public static final int FRAGMENT_COUNT = 2;

        private FragmentManager mFragmentManager;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case BAR_CHART_FRAGMENT:
                    fragment = new BarChartFragment();
                    break;
                case PIE_CHART_FRAGMENT:
                    fragment = new PieChartFragment();
                    break;
                default:
                    fragment = new BarChartFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
