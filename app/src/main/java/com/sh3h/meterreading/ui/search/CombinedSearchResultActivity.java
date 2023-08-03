package com.sh3h.meterreading.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.datautil.data.entity.DUCard;
import com.sh3h.datautil.data.entity.DUCombined;
import com.sh3h.datautil.data.entity.DUCombinedInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.ParentActivity;
import com.sh3h.meterreading.ui.information.CustomerInformationActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xulongjun on 2016/2/2.
 */
public class CombinedSearchResultActivity extends ParentActivity implements CombinedSearchResultMvpView, View.OnClickListener {
    private static final String TAG = "SelectResultActivity";
    public final static String CEBENHAO = "cebenhao";
    public final static String KEHUBH = "kehubh";
    public final static String HUMING = "huming";
    public final static String DIZHI = "dizhi";
    public final static String DIANHUA = "dianhua";
    public final static String JIANHAO = "jianhao";
    public final static String BIAOHAO = "biaohao";
    public final static String KOUJINMIN = "koujinmin";
    public final static String KOUJINMAX = "koujinmax";
    public final static String QIANFEIBS = "qianfeibs";
    public final static String QIANFEIJE = "qianfeije";
    public final static String HUANBIAORQ = "huanbiaorq";

    @Inject
    CombinedSearchResultPresenter mCombinedSearchResultPresenter;
    @Bind(R.id.lv_searchlist) ListView mSearchList;
    @Bind(R.id.loading_process) SmoothProgressBar mSmoothProgressBar;
    private boolean isLocalSearch = true;
    private MyListAdapter mMyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mCombinedSearchResultPresenter.attachView(this);
        setActionBarBackButtonEnable();
        getDataAndSetListView();
    }

    private void getDataAndSetListView() {
        List<DUCard> duCardList = new ArrayList<>();
        mMyListAdapter = new MyListAdapter(CombinedSearchResultActivity.this,
                R.layout.item_search_result, duCardList);
        mSearchList.setAdapter(mMyListAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            DUCombinedInfo duCombinedInfo = new DUCombinedInfo();
            Bundle bundle = intent.getExtras();
            duCombinedInfo.setDuCombined(queryConditions(bundle));
            mCombinedSearchResultPresenter.getCombinedResults(duCombinedInfo, isLocalSearch);
        }
    }

    public DUCombined queryConditions(Bundle data) {
        DUCombined info = new DUCombined();
        info.set_ch(data.getString(CombinedSearchResultActivity.CEBENHAO));
        info.set_cid(data.getString(CombinedSearchResultActivity.KEHUBH));
        info.set_huming(data.getString(CombinedSearchResultActivity.HUMING));
        info.set_dizhi(data.getString(CombinedSearchResultActivity.DIZHI));
        info.set_lianxidh(data.getString(CombinedSearchResultActivity.DIANHUA));
        info.set_jianhao(data.getString(CombinedSearchResultActivity.JIANHAO));
        info.set_biaohao(data.getString(CombinedSearchResultActivity.BIAOHAO));
        info.set_koujingmin(data.getDouble(CombinedSearchResultActivity.KOUJINMIN, 999999999));
        info.set_koujingmax(data.getDouble(CombinedSearchResultActivity.KOUJINMAX, -1));
        info.set_qianfeibs(data.getInt(CombinedSearchResultActivity.QIANFEIBS, -1));
        info.set_qianfeije(data.getDouble(CombinedSearchResultActivity.QIANFEIJE, -1));
        info.set_huanbiaorq(data.getLong(CombinedSearchResultActivity.HUANBIAORQ));
        return info;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mCombinedSearchResultPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onCompleted(List<DUCard> duCards) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if ((duCards == null) || (duCards.size() <= 0)) {
            return;
        }

        mMyListAdapter.setDataList(duCards);
        mMyListAdapter.notifyDataSetChanged();

        setActionBarSubTitle(String.format(Locale.CHINA, "%d%s", duCards.size(),
                getResources().getString(R.string.text_number)));
    }

    @Override
    public void onError(String message) {
        mSmoothProgressBar.setVisibility(View.INVISIBLE);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mResource;
        private List<DUCard> mListData;

        public MyListAdapter(Context context, int resource,
                             List<DUCard> listData) {
            mResource = resource;
            mListData = listData;
            mInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<DUCard> list) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyListHolder myListHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(mResource, null);
                myListHolder = new MyListHolder();
                myListHolder.tvCeBenHao = (TextView) convertView.findViewById(R.id.tv_cebenhao);
                myListHolder.tvHuMing = (TextView) convertView.findViewById(R.id.tv_huming);
                myListHolder.tvBiaoKaBH = (TextView) convertView.findViewById(R.id.tv_biaokabh);
                myListHolder.tvKeHuDZ = (TextView) convertView.findViewById(R.id.tv_kehudz);
                myListHolder.cardView = (CardView) convertView.findViewById(R.id.isr_cv_style);
                convertView.setTag(myListHolder);
            } else {
                myListHolder = (MyListHolder) convertView.getTag();
            }

            // 获取数据
            final DUCard biaoKaXX = mListData.get(position);
            myListHolder.tvCeBenHao.setText(TextUtil.getString(biaoKaXX.getCh()));
            myListHolder.tvBiaoKaBH.setText(TextUtil.getString(biaoKaXX.getCid()));
            myListHolder.tvHuMing.setText(TextUtil.getString(biaoKaXX.getKehumc()));
            myListHolder.tvKeHuDZ.setText(TextUtil.getString(biaoKaXX.getDizhi()));
            myListHolder.cardView.setTag(position);
            myListHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag();
                    if (object instanceof Integer) {
                        int index = (Integer) object;
                        if ((index >= 0) && (index < mListData.size())) {
                            DUCard duCard = mListData.get(index);
                            Intent intent = new Intent(CombinedSearchResultActivity.this,
                                    CustomerInformationActivity.class);
                            intent.putExtra(CustomerInformationActivity.S_CID,
                                    TextUtil.getString(duCard.getCid()));
                            intent.putExtra(CustomerInformationActivity.S_CH,
                                    TextUtil.getString(duCard.getCh()));
                            intent.putExtra(CustomerInformationActivity.IS_LOCAL_SEARCH, true); // modify, 2017/10/10, isLocalSearch
                            intent.putExtra(CustomerInformationActivity.DUCARD, biaoKaXX);
                            startActivity(intent);
                        }
                    }
                }
            });

            return convertView;
        }
    }

    private class MyListHolder {
        private TextView tvCeBenHao;
        private TextView tvBiaoKaBH;
        private TextView tvHuMing;
        private TextView tvKeHuDZ;
        private CardView cardView;
    }
}
