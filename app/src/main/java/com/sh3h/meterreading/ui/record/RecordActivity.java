package com.sh3h.meterreading.ui.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.information.CustomerInformationActivity;
import com.sh3h.meterreading.ui.urge.CallForPaymentOrderDetailActivity;
import com.sh3h.meterreading.util.Const;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抄表录入
 */
public class RecordActivity extends ParentActivity implements View.OnClickListener,
        MenuItem.OnMenuItemClickListener {
    private static final String TAG = "RecordActivity";

    @Inject
    Bus mEventBus;

    @BindView(R.id.record_lr)
    RadioButton mRecordLRRadioButton;

    @BindView(R.id.record_detail_info)
    RadioButton mDetailInfoRadioButton;

    @BindView(R.id.record_viewPager)
    ViewPager mViewPager;

    //private List<Fragment> mRecordFragmentList;
    //private List<Fragment> mDetailInfoFragmentList;
    //private RecordLRFragment mRecordLRFragment;
    //private DetailInfoFragment mDetailInfoFragment;
    private MyPagerAdapter mMyPagerAdapter;
    //private CommonFragementPagerAdapter mDetailInfoAdapter;

    private MenuItem mImageMenuItem;
    private MenuItem mTextMenuItem;

    //    private String _sT;
    private String _cH;
    private String _cID;
    private int _renWuBH;
    private int mCeNeiXH;
    private int mStartXH;
    private int mEndXH;
    private int _mLastReadingChild;
    private int _yiChaoShu;
    private boolean mIsFromTask;
    private int mRenwuId;
//    private int mDataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_record_lr);
        ButterKnife.bind(this);
        setActionBarBackButtonEnable();
        mEventBus.register(this);

        getIntentData(savedInstanceState);
        //initFragmentList();
        initViewPager();
        initOnclickListener();

        LogUtil.i(TAG, "---onCreate---");
    }

    private void getIntentData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            _cH = savedInstanceState.getString(ConstDataUtil.S_CH);
            _cID = savedInstanceState.getString(ConstDataUtil.S_CID);
            _renWuBH = savedInstanceState.getInt(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = savedInstanceState.getInt(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = savedInstanceState.getInt(ConstDataUtil.STARTXH, 0);
            mEndXH = savedInstanceState.getInt(ConstDataUtil.ENDXH, 0);
            _mLastReadingChild = savedInstanceState.getInt(ConstDataUtil.I_LASTREADINGCHILD, 0);
            _yiChaoShu = savedInstanceState.getInt(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = savedInstanceState.getBoolean(ConstDataUtil.FROM_TASK, false);
            mRenwuId = savedInstanceState.getInt(Const.RENWUID, 0);
            if (intent != null) {
                intent.putExtra(ConstDataUtil.S_CID, _cID);
                intent.putExtra(ConstDataUtil.I_CENEIXH, mCeNeiXH);
            }

            LogUtil.i(TAG, "getIntentData savedInstanceState != null" + _cID);
        } else if (intent != null) {
            _cH = intent.getStringExtra(ConstDataUtil.S_CH);
            _cID = intent.getStringExtra(ConstDataUtil.S_CID);
            _renWuBH = intent.getIntExtra(ConstDataUtil.I_RENWUBH, 0);
            mCeNeiXH = intent.getIntExtra(ConstDataUtil.I_CENEIXH, 0);
            mStartXH = intent.getIntExtra(ConstDataUtil.STARTXH, 0);
            mEndXH = intent.getIntExtra(ConstDataUtil.ENDXH, 0);
            _mLastReadingChild = intent.getIntExtra(ConstDataUtil.I_LASTREADINGCHILD, 0);
            _yiChaoShu = intent.getIntExtra(ConstDataUtil.YICHAOSHU, 0);
            mIsFromTask = intent.getBooleanExtra(ConstDataUtil.FROM_TASK, false);
            mRenwuId = intent.getIntExtra(Const.RENWUID, 0);
            LogUtil.i(TAG, "getIntentData intent != null" + _cID);
        } else {
            LogUtil.i(TAG, "getIntentData error!!!");
        }
    }

//    private void initFragmentList() {
////        Bundle bundle = getBundle();
////        mRecordFragmentList = new ArrayList<>();
////        mRecordLRFragment = new RecordLRFragment();
////        mRecordLRFragment.setArguments(bundle);
////        mRecordFragmentList.add(mRecordLRFragment);
////        LogUtil.i(TAG, "---initFragmentList---" + mRecordLRFragment);
//
////        mDetailInfoFragmentList = new ArrayList<>();
////        mDetailInfoFragment = new DetailInfoFragment();
////        mDetailInfoFragment.setArguments(bundle);
////        mDetailInfoFragmentList.add(mDetailInfoFragment);
//    }

    private void initViewPager() {
//        if ((mRecordFragmentList == null)
//                /*|| (mDetailInfoFragmentList == null)*/) {
//            return;
//        }

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mMyPagerAdapter);
        //mViewPager.setCurrentItem(0);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        setRadioButtonChecked(true, false);
//                        break;
//                    case 1:
//                        setRadioButtonChecked(false, true);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

//        mDetailInfoAdapter = new CommonFragementPagerAdapter(getSupportFragmentManager(),
//                mDetailInfoFragmentList);
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
//        bundle.putString(ChaoBiaoSJColumns.ST, _sT);
        bundle.putString(ConstDataUtil.S_CH, _cH);
        bundle.putString(ConstDataUtil.S_CID, _cID);
        bundle.putInt(ConstDataUtil.I_RENWUBH, _renWuBH);
        bundle.putInt(ConstDataUtil.I_CENEIXH, mCeNeiXH);
        bundle.putInt(ConstDataUtil.STARTXH, mStartXH);
        bundle.putInt(ConstDataUtil.ENDXH, mEndXH);
//        bundle.putString("PreActivity", mPreActivity);
        bundle.putInt(ConstDataUtil.I_LASTREADINGCHILD, _mLastReadingChild);
        bundle.putInt(ConstDataUtil.YICHAOSHU, _yiChaoShu);
        bundle.putBoolean(ConstDataUtil.FROM_TASK, mIsFromTask);
        return bundle;
    }

    private void initOnclickListener() {
        mRecordLRRadioButton.setOnClickListener(this);
        mDetailInfoRadioButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.i(TAG, "---onActivityResult---1");
        if (resultCode == RESULT_OK) {
            Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
            if (object instanceof RecordLRFragment) {
                RecordLRFragment recordLRFragment = (RecordLRFragment) object;
                LogUtil.i(TAG, "---onActivityResult---" + recordLRFragment);
                recordLRFragment.processResult(requestCode, resultCode, data);
            }
            LogUtil.i(TAG, "---onActivityResult---2");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "---onDestroy---");

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
    }

    private void setRadioButtonChecked(boolean isRecodLRChecked, boolean isDetailInfoChecked) {
        mRecordLRRadioButton.setChecked(isRecodLRChecked);
        mDetailInfoRadioButton.setChecked(isDetailInfoChecked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_lr, menu);

        mImageMenuItem = menu.findItem(R.id.action_image);
        mTextMenuItem = menu.findItem(R.id.action_text);
        mImageMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LogUtil.i(TAG, "---onCreateOptionsMenu 1---");
                Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
                if (object instanceof RecordLRFragment) {
                    LogUtil.i(TAG, "---onCreateOptionsMenu---" + object);
                    RecordLRFragment recordLRFragment = (RecordLRFragment) object;
                    recordLRFragment.loadImages();
                }
                LogUtil.i(TAG, "---onCreateOptionsMenu 2---");
                return true;
            }
        });

        showTextMenuButton();
        mTextMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mRenwuId != 0) {
                    Intent intent = new Intent(RecordActivity.this, CallForPaymentOrderDetailActivity.class);
                    intent.putExtra(Const.S_CID, _cID);
                    intent.putExtra(Const.RENWUID, String.valueOf(mRenwuId));
                    startActivity(intent);
                }
                return true;
            }
        });

        return true;
    }

    private void showTextMenuButton() {
        if (mRenwuId != 0) {
            mTextMenuItem.setVisible(true);
        } else {
            mTextMenuItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_lr:
                //mImageMenuItem.setVisible(true);
                //setRadioButtonChecked(true, false);
                //mRecordViewPager.setCurrentItem(0);
                //mViewPager.setAdapter(mRecordAdapter);
                break;
            case R.id.record_detail_info:
                //mImageMenuItem.setVisible(false);
                //setRadioButtonChecked(false, true);
                //mRecordViewPager.setCurrentItem(1);
                //mViewPager.setAdapter(mDetailInfoAdapter);
                Intent intent = new Intent(this, CustomerInformationActivity.class);
                intent.putExtra(CustomerInformationActivity.S_CID, TextUtil.getString(_cID));
                intent.putExtra(CustomerInformationActivity.S_CH, TextUtil.getString(_cH));
                intent.putExtra(CustomerInformationActivity.S_TASKID, _renWuBH);
                startActivity(intent);
                setRadioButtonChecked(true, false);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                if (mRecordLRRadioButton.isChecked()) {
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 1---");
                    Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
                    if (object instanceof RecordLRFragment) {
                        LogUtil.i(TAG, "---KEYCODE_VOLUME_UP---" + object);
                        RecordLRFragment recordLRFragment = (RecordLRFragment) object;
                        recordLRFragment.switchPreviousOne();
                    }
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_UP 2---");
                    return true;
                }
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                if (mRecordLRRadioButton.isChecked()) {
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 1---");
                    Object object = mMyPagerAdapter.instantiateItem(mViewPager, 0);
                    if (object instanceof RecordLRFragment) {
                        LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN---" + object);
                        RecordLRFragment recordLRFragment = (RecordLRFragment) object;
                        recordLRFragment.switchNextOne();
                    }
                    LogUtil.i(TAG, "---KEYCODE_VOLUME_DOWN 2---");
                    return true;
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(ChaoBiaoSJColumns.ST, _sT);
        outState.putString(ConstDataUtil.S_CH, _cH);
        outState.putString(ConstDataUtil.S_CID, _cID);
        outState.putInt(ConstDataUtil.I_RENWUBH, _renWuBH);
        outState.putInt(ConstDataUtil.I_LASTREADINGCHILD, _mLastReadingChild);
//        outState.putString("PreActivity", mPreActivity);
//        outState.putBoolean("isfinished", mIsfinished);
        outState.putInt(ConstDataUtil.I_CENEIXH, mCeNeiXH);
        outState.putInt(ConstDataUtil.STARTXH, mStartXH);
        outState.putInt(ConstDataUtil.ENDXH, mEndXH);
//        outState.putInt(CeBenListActivity.DATA_TYPE, mDataType);
        outState.putInt(ConstDataUtil.YICHAOSHU, _yiChaoShu);
        outState.putBoolean(ConstDataUtil.FROM_TASK, mIsFromTask);
        outState.putInt(Const.RENWUID, mRenwuId);
        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    @Subscribe
    public void onUpdateRecord(UIBusEvent.UpdateRecord updateRecord) {
        LogUtil.i(TAG, "---onUpdateRecord---");

        String customerId = updateRecord.getCustomerId();
        if (!TextUtil.isNullOrEmpty(customerId)) {
            _cID = customerId;
        }

        mCeNeiXH = updateRecord.getOrderNumber();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager mFragmentManager;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
        }

        @Override
        public Fragment getItem(int position) {
            RecordLRFragment fragment = new RecordLRFragment();
            fragment.setButtonListener(new ShowMenuButtonListener() {
                @Override
                public void showCallForPayButton(int renwuId) {
                    mRenwuId = renwuId;
                    showTextMenuButton();
                }
            });
            fragment.setArguments(getBundle());
            LogUtil.i(TAG, "---getItem---" + fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
