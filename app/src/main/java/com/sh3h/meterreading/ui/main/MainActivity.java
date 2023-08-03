package com.sh3h.meterreading.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

//import com.sh3h.meterreading.injection.component.ActivityComponent2;
//import com.sh3h.meterreading.injection.component.DaggerActivityComponent2;

public class MainActivity extends ParentActivity implements MainMvpView,
        View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String POSITIVE = "POSITIVE";
    public static final String YICHAO = "已抄";
    public static final String WEICHAO = "未抄";
    public static final String GUCHAO = "估算";

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    Bus mEventBus;

    @Bind(R.id.parent_work)
    RadioButton mWorkRadioButton;

    @Bind(R.id.parent_statistics)
    RadioButton mStatisticsRadioButton;

    @Bind(R.id.parent_setting)
    RadioButton mSettingRadioButton;

    @Bind(R.id.main_viewPager)
    ViewPager mMainViewPager;

    private long mExitTime;

    private SwipeBackLayout mSwipeBackLayout;
    private MyPagerAdapter mMyPagerAdapter;

    //private List<Fragment> fragmentList;
    //private MyWorkFragment myWorkFragment;
    //private StatisticsFragment mWorkStatisticsFragment;
    //private SettingFragment mSettingFragment;

    private MenuItem refreshBtn;
    private boolean isManualCheckingVersion;

    private Intent intentKeepAlive;

    public MainActivity() {
        isManualCheckingVersion = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);
        //mEventBus.register(this);

        initView();

        initSwipeBackLayout();

        //initFragmentList();

        initViewPager();

        boolean isFromLogin;
        if (savedInstanceState != null) {
            isFromLogin = savedInstanceState.getBoolean(ConstDataUtil.FROM_LOGIN, false);
            LogUtil.i(TAG, "---onCreate savedInstanceState---" + isFromLogin);
        } else {
            Intent intent = getIntent();
            isFromLogin = intent.getBooleanExtra(ConstDataUtil.FROM_LOGIN, false);
            LogUtil.i(TAG, "---onCreate intent---" + isFromLogin);
        }

        needRefresh = false;
//        if (checkPermissions()) {
//            initConfig();
//        }
    }

    private void initView() {
        mWorkRadioButton.setOnClickListener(this);
        mStatisticsRadioButton.setOnClickListener(this);
        mSettingRadioButton.setOnClickListener(this);
    }

    private void initSwipeBackLayout() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEnableGesture(false);
    }

//    private void initFragmentList() {
//        fragmentList = new ArrayList<>();
//
//        myWorkFragment = new MyWorkFragment();
//        fragmentList.add(myWorkFragment);
//
//        mWorkStatisticsFragment = new StatisticsFragment();
//        fragmentList.add(mWorkStatisticsFragment);
//
//        mSettingFragment = new SettingFragment();
//        fragmentList.add(mSettingFragment);
//    }

    private void initViewPager() {
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mMainViewPager.setOffscreenPageLimit(1);
        mMainViewPager.setAdapter(mMyPagerAdapter);
        mMainViewPager.setCurrentItem(0);
        mMainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case MyPagerAdapter.MY_WORK_FRAGMENT:
                        mWorkRadioButton.setChecked(true);
                        mStatisticsRadioButton.setChecked(false);
                        mSettingRadioButton.setChecked(false);
                        changeRefreshBtnState(true);
                        break;
//                    case MyPagerAdapter.STATISTICS_FRAGMENT:
//                        mWorkRadioButton.setChecked(false);
//                        mStatisticsRadioButton.setChecked(true);
//                        mSettingRadioButton.setChecked(false);
//                        changeRefreshBtnState(false);
//                        break;
//                    case MyPagerAdapter.SETTING_FRAGMENT:
//                        mWorkRadioButton.setChecked(false);
//                        mStatisticsRadioButton.setChecked(false);
//                        mSettingRadioButton.setChecked(true);
//                        changeRefreshBtnState(false);
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void startVersionService() {
        LogUtil.i(TAG, "---startVersionService---");
        needDialog = true;
        //startService(VersionService.getStartIntent(this));
    }

    @Override
    public void startSyncService() {
        LogUtil.i(TAG, "---startSyncService---");
        needDialog = true;
        //uploadAndDownloadAllData();
    }

    @Override
    public void startKeepAliveService() {
        LogUtil.i(TAG, "---startKeepAliveService---");
        //startService(KeepAliveService.getStartIntent(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (needRefresh) {
            needRefresh = false;
            //refreshMyWorkFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "---onDestroy---");

        ButterKnife.unbind(this);
        //mEventBus.unregister(this);
        //stopService(VersionService.getStartIntent(this));
        //stopService(SyncService.getStartIntent(this));
        //stopService(KeepAliveService.getStartIntent(this));
        mMainPresenter.destroy();
    }

    @Override
    public void onExit(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }

        mMainPresenter.detachView();
        System.exit(0);
    }

    @Override
    public void initSubTitle(String subTitle) {
        setActionBarSubTitle(subTitle);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.parent_work:
                mMainViewPager.setCurrentItem(0);
                changeRefreshBtnState(true);
                break;
            case R.id.parent_statistics:
                mMainViewPager.setCurrentItem(1);
                changeRefreshBtnState(false);
                break;
            case R.id.parent_setting:
                mMainViewPager.setCurrentItem(2);
                changeRefreshBtnState(false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ApplicationsUtil.showMessage(this,
                        "再按一次退出程序");
                mExitTime = System.currentTimeMillis();

            } else {
                boolean leftOrRight = mConfigHelper.isLeftOrRightOperation();
                new MaterialDialog.Builder(this)
                        .title(R.string.text_prompt)
                        .content(R.string.main_question_quit)
                        .cancelable(false)
                        .positiveText(R.string.text_ok)
                        .negativeText(R.string.text_cancel)
                        .buttonsGravity(leftOrRight)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.i(TAG, which.name());
                                if (which.name().equals(POSITIVE)) {
                                    finish();
                                }
                            }
                        })
                        .show(leftOrRight);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        refreshBtn = menu.findItem(R.id.mm_action_update);
//        refreshBtn.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Subscribe
    public void onInitConfigResult(UIBusEvent.InitConfigResult initConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initConfigResult.isSuccess());
        if (initConfigResult.isSuccess()) {
            initUserConfig();
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

    @Subscribe
    public void onInitUserConfigResult(UIBusEvent.InitUserConfigResult initUserConfigResult) {
        LogUtil.i(TAG, "---onInitResult---" + initUserConfigResult.isSuccess());
        if (initUserConfigResult.isSuccess()) {
            mMainPresenter.init(true);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_init_failure);
        }
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mm_action_update:
//                //isManualRefresh = true;
//                //startSyncService();
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstDataUtil.FROM_LOGIN, false);
        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    public void checkVersion() {
        isManualCheckingVersion = true;
        startVersionService();
    }

    private void changeRefreshBtnState(boolean flag) {
        if (refreshBtn != null) {
            refreshBtn.setVisible(flag);
        }
    }

    private void refreshMyWorkFragment() {
        MyWorkFragment myWorkFragment = getMyWorkFragment();
        if ((myWorkFragment != null) && (!myWorkFragment.isDestroyed())) {
            myWorkFragment.refresh();
        }
    }

    private MyWorkFragment getMyWorkFragment() {
        Object object = mMyPagerAdapter.instantiateItem(mMainViewPager, MyPagerAdapter.MY_WORK_FRAGMENT);
        if (object instanceof MyWorkFragment) {
            return (MyWorkFragment) object;
        }

        return null;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public static final int MY_WORK_FRAGMENT = 0;
        //        public static final int STATISTICS_FRAGMENT = 1;
//        public static final int SETTING_FRAGMENT = 2;
        public static final int FRAGMENT_COUNT = 1;

        private FragmentManager mFragmentManager;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case MY_WORK_FRAGMENT:
                    fragment = new MyWorkFragment();
                    break;
//                case STATISTICS_FRAGMENT:
//                    fragment = new StatisticsFragment();
//                    break;
//                case SETTING_FRAGMENT:
//                    fragment = new SettingFragment();
//                    break;
                default:
                    fragment = new MyWorkFragment();
                    break;
            }

            LogUtil.i(TAG, "---getItem---" + fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
