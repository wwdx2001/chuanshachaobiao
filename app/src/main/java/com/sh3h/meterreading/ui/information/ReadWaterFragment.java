package com.sh3h.meterreading.ui.information;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.SmoothProgressBar;
import com.sh3h.dataprovider.schema.ChaoBiaoSJColumns;
import com.sh3h.datautil.data.entity.DUChaoBiaoJL;
import com.sh3h.datautil.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sh3h.meterreading.ui.information.CustomerInformationActivity.IS_LOCAL_SEARCH;


/**
 * Created by xulongjun on 2016/2/16.
 * 近期抄表
 */
public class ReadWaterFragment extends ParentFragment implements ReadWaterMvpView {
    public static final String TAG = "ReadWaterFragment";

    @Inject
    Bus mEventBus;

    @Bind(R.id.lv_jinqicblist)
    RecyclerView _jinQiCBlist;

    @Bind(R.id.loading_process)
    SmoothProgressBar loadingProcess;

    @Inject
    ReadWaterPresenter mReadWaterPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readwater, container, false);
        LogUtil.i("ReadWaterFragment", "onCreateView");

        ButterKnife.bind(this, view);
        mReadWaterPresenter.attachView(this);
        init();
        mEventBus.register(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mReadWaterPresenter.detachView();
    }

    private void init() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ChaoBiaoSJColumns.CID))) {
            return;
        }
        DUChaoBiaoJLInfo duChaoBiaoJLInfo = new DUChaoBiaoJLInfo();
        duChaoBiaoJLInfo.setFilterType(DUChaoBiaoJLInfo.FilterType.ONE);
        duChaoBiaoJLInfo.setCustomerId(_bundle.getString(ChaoBiaoSJColumns.CID));
        mReadWaterPresenter.getChaoBiaoJL(duChaoBiaoJLInfo, _bundle.getBoolean(IS_LOCAL_SEARCH, true), getContext());
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onError(String message) {
        loadingProcess.setVisibility(View.INVISIBLE);
        LogUtil.e(TAG, message);
    }

    @Override
    public void onGetChaoBiaoJLFinish(List<DUChaoBiaoJL> duChaoBiaoJLs) {
        loadingProcess.setVisibility(View.INVISIBLE);

        if (duChaoBiaoJLs != null) {
            _jinQiCBlist.setLayoutManager(new LinearLayoutManager(_jinQiCBlist.getContext()));
            SimpleStringRecyclerViewAdapter mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), duChaoBiaoJLs);
            _jinQiCBlist.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void onCustomerInformationUpdating(UIBusEvent.CustomerInformationUpdating customerInformationUpdating) {
        LogUtil.i(TAG, "---onCustomerInformationUpdating---");
        init();
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<DUChaoBiaoJL> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public final View mView;
            public final TextView mChaoBiaoRQN;
            public final TextView mChaoBiaoRQYR;
            public final TextView mShangCiCM;
            public final TextView mBenCiCM;
            public final TextView mChaoJianSL;
            public final TextView mChaoBiaoZT;
            public final TextView mBeiZhu;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                mChaoBiaoRQN = (TextView) view.findViewById(R.id.tv_chaobiaorqn);
                mChaoBiaoRQYR = (TextView) view.findViewById(R.id.tv_chaobiaorqyr);
                mShangCiCM = (TextView) view.findViewById(R.id.tv_shangcicm);
                mBenCiCM = (TextView) view.findViewById(R.id.tv_bencicm);
                mChaoJianSL = (TextView) view.findViewById(R.id.tv_chaojiansl);
                mChaoBiaoZT = (TextView) view.findViewById(R.id.tv_chaobiaozt);
                mBeiZhu = (TextView) view.findViewById(R.id.tv_beizhu);
            }

        }

        public DUChaoBiaoJL getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<DUChaoBiaoJL> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_jinqicb, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mChaoBiaoRQN
                    .setText(TextUtil.format(mValues.get(position).getChaobiaorq(), TextUtil.FORMAT_DATE_YEAR));
            holder.mChaoBiaoRQYR.setText(TextUtil.format(mValues.get(position).getChaobiaorq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH));
            holder.mShangCiCM.setText(String.valueOf(mValues.get(position).getShangcicm()));
            holder.mBenCiCM.setText(String.valueOf(mValues.get(position).getBencicm()));
            holder.mChaoJianSL.setText(String.valueOf(mValues.get(position).getChaojiansl()));
            holder.mChaoBiaoZT.setText(mValues.get(position).getChaobiaozt().trim());

            if (mValues.get(position).getChaobiaobz() == null
                    || mValues.get(position).getChaobiaobz().equalsIgnoreCase(TextUtil.EMPTY)) {
                holder.mView.findViewById(R.id.tableRow2).setVisibility(View.GONE);
            } else {
                holder.mView.findViewById(R.id.tableRow2).setVisibility(View.VISIBLE);
                holder.mBeiZhu.setText(mValues.get(position).getChaobiaobz());
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
