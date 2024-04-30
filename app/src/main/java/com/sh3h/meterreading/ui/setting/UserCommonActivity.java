package com.sh3h.meterreading.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.UserCommonAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.sort.SortStatusActivity;
import com.sh3h.meterreading.util.ApplicationsUtil;
import com.sh3h.meterreading.util.ConstDataUtil;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xulongjun on 2016/2/25.
 */
public class UserCommonActivity extends ParentActivity implements UserCommonMvpView,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "UserCommonActivity";
    @Inject
    UserCommonPresenter mUserCommonPresenter;

    @BindView(R.id.chaobiao_dialog_list1)
    ListView mDaLeiList;

    @BindView(R.id.chaobiao_dialog_list2)
    ListView mXiaoLeiList;

    @BindView(R.id.btnSubmit)
    Button mBtnSubmit;

    @BindView(R.id.btnCancel)
    Button mBtnCancel;

    @BindView(R.id.btnSort)
    Button mBtnSort;

    private List<DUChaoBiaoZTFL> mChaoBiaoZTFLList;
    private List<DUChaoBiaoZT> mChaoBiaoZTList;

    private MyListAdapter mMyListAdapter;
    private UserCommonAdapter mXiaoLeiAdapter;
    private int mIndex;


    public UserCommonActivity() {
        mIndex = 0;
        mDaLeiList = null;
        mChaoBiaoZTFLList = null;
        mChaoBiaoZTList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercommon);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mUserCommonPresenter.attachView(this);
        setActionBarBackButtonEnable();
        LogUtil.i(TAG, "---onCreate---");

        initListView();

        mBtnSubmit.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnSort.setOnClickListener(this);
    }

    private void initListView() {
        mChaoBiaoZTFLList = new ArrayList<>();
        mChaoBiaoZTList = new ArrayList<>();

        mDaLeiList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mMyListAdapter = new MyListAdapter(this,
                R.layout.item_userciommon_parent, mChaoBiaoZTFLList);
        mDaLeiList.setAdapter(mMyListAdapter);
        mDaLeiList.setOnItemClickListener(this);

        mUserCommonPresenter.loadAllChaobiaoZTFL();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int fenLeiBM = mChaoBiaoZTFLList.get(position).getFenleibm();
        mXiaoLeiAdapter.setDaleiId(fenLeiBM);
        mIndex = position;
        mMyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        if (ApplicationsUtil.isFastClick())
            return;

        int id = v.getId();

        switch (id) {

            case R.id.btnSubmit:
                // ok保存在配置文件中
                List<ChaoBiaoZT> chaoBiaoZTList = this.mXiaoLeiAdapter
                        .getUserChangYong();

                String zhuangTaBM = "";
                for (ChaoBiaoZT item : chaoBiaoZTList) {
                    zhuangTaBM = zhuangTaBM + item.getI_ZHUANGTAIBM() + ",";
                }
                if (!zhuangTaBM.equals("")) {
                    zhuangTaBM = zhuangTaBM.substring(0, zhuangTaBM.length() - 1);
                }

                mUserCommonPresenter.saveUserChangYong(zhuangTaBM);
                break;
            case R.id.btnCancel:
                // 取消
                finish();
                break;
            case R.id.btnSort:
                Intent intent = new Intent(UserCommonActivity.this, SortStatusActivity.class);
                intent.putExtra(ConstDataUtil.S_SETTING_READING_OR_IMAGE, true);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            finish();
        }
    }

    private class MyListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private int mResource;
        private List<DUChaoBiaoZTFL> mListData;

        public MyListAdapter(Context context, int resource,
                             List<DUChaoBiaoZTFL> listData) {
            mResource = resource;
            mListData = listData;
            mInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<DUChaoBiaoZTFL> list) {
            mListData = list;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView == null ? mInflater
                    .inflate(mResource, null) : convertView;
            DUChaoBiaoZTFL chaoBiaoZTFL = mListData.get(position);
            TextView tv = (TextView) view.findViewById(R.id.tv_parent);
            tv.setText(chaoBiaoZTFL.getFenleimc());
            if (mIndex == position) {
                tv.setBackgroundResource(R.drawable.item_bk_selected);
                tv.setTextColor(getResources().getColor(R.color.text_white));
            } else {
                tv.setBackgroundResource(R.drawable.item_bk);
                tv.setTextColor(getResources().getColor(R.color.text_content));
            }
            tv.setPadding(0, 0, 20, 0);
            return view;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
        mUserCommonPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }


    @Override
    public void loadAllChaobiaoZTFL(List<DUChaoBiaoZTFL> duChaoBiaoZTFLs) {

        if (duChaoBiaoZTFLs == null || duChaoBiaoZTFLs.size() <= 0) {
            return;
        }
        mChaoBiaoZTFLList = duChaoBiaoZTFLs;
        mMyListAdapter.setDataList(mChaoBiaoZTFLList);
        mMyListAdapter.notifyDataSetChanged();

        mUserCommonPresenter.loadAllChaobiaoZT();
    }

    private void xiaoLeiSetAdapter(int fenLeiBM) {
        mXiaoLeiAdapter = new UserCommonAdapter(this, fenLeiBM,
                mChaoBiaoZTList, mUserCommonPresenter.getUserChangYong());

        mXiaoLeiList.setAdapter(mXiaoLeiAdapter);
        mXiaoLeiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                CheckBox user_checkbox = (CheckBox) arg1.findViewById(R.id.user_checkbox);
                if (user_checkbox.isChecked()) {
                    user_checkbox.setChecked(false);
                } else {
                    user_checkbox.setChecked(true);
                }
            }
        });
    }

    @Override
    public void loadAllChaobiaozt(List<DUChaoBiaoZT> duChaoBiaoZTs) {

        if (duChaoBiaoZTs == null || duChaoBiaoZTs.size() <= 0) {
            return;
        }

        mChaoBiaoZTList = duChaoBiaoZTs;
        xiaoLeiSetAdapter(1);
    }

    @Override
    public void onError(String message) {
        ApplicationsUtil.showMessage(this, message);
    }

    @Override
    public void showMessage(String message) {
        ApplicationsUtil.showMessage(this, message);
    }

    @Override
    public void showMessage(int message) {
        ApplicationsUtil.showMessage(this, message);
        finish();
    }

}
