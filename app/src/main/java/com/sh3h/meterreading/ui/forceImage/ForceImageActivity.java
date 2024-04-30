package com.sh3h.meterreading.ui.forceImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.adapter.UserCommonAdapter;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForceImageActivity extends ParentActivity implements ForceImageMvpView,
        AdapterView.OnItemClickListener{

    private static final String TAG = "ForceImageActivity";
    @Inject
    ForceImagePresenter mForceImagePresenter;

    @BindView(R.id.chaobiao_dialog_list1)
    ListView mDaLeiList;

    @BindView(R.id.chaobiao_dialog_list2)
    ListView mXiaoLeiList;

    private List<DUChaoBiaoZTFL> mChaoBiaoZTFLList;
    private List<DUChaoBiaoZT> mChaoBiaoZTList;

    private ForceImageActivity.MyListAdapter mMyListAdapter;
    private UserCommonAdapter mXiaoLeiAdapter;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_image);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        mForceImagePresenter.attachView(this);
        setActionBarBackButtonEnable();
        LogUtil.i(TAG, "---onCreate---");

        initListView();
    }

    private void initListView() {
        mChaoBiaoZTFLList = new ArrayList<>();
        mChaoBiaoZTList = new ArrayList<>();

        mDaLeiList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mMyListAdapter = new ForceImageActivity.MyListAdapter(this,
                R.layout.item_userciommon_parent, mChaoBiaoZTFLList);
        mDaLeiList.setAdapter(mMyListAdapter);
        mDaLeiList.setOnItemClickListener(this);

        mForceImagePresenter.loadAllChaobiaoZTFL();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int fenLeiBM = mChaoBiaoZTFLList.get(position).getFenleibm();
        mXiaoLeiAdapter.setDaleiId(fenLeiBM);
        mIndex = position;
        mMyListAdapter.notifyDataSetChanged();
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

        MyListAdapter(Context context, int resource, List<DUChaoBiaoZTFL> listData) {
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
        mForceImagePresenter.detachView();
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

        mForceImagePresenter.loadAllChaobiaoZT();
    }

    private void xiaoLeiSetAdapter(int fenLeiBM) {
        mXiaoLeiAdapter = new UserCommonAdapter(this, fenLeiBM,
                mChaoBiaoZTList, mForceImagePresenter.getForceImage());
        mXiaoLeiAdapter.setEnable(false);
        mXiaoLeiList.setAdapter(mXiaoLeiAdapter);
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
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
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

