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
import com.sh3h.datautil.data.entity.DUJiaoFeiXX;
import com.sh3h.datautil.data.entity.DUJiaoFeiXXInfo;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sh3h.meterreading.ui.information.CustomerInformationActivity.IS_LOCAL_SEARCH;

/**
 * Created by xulongjun on 2016/2/16.
 * 近期缴费
 */
public class PayWaterFragment extends ParentFragment implements PayWaterMvpView {
    public static final String TAG = "PayWaterFragment";

    @Inject
    Bus mEventBus;

    @BindView(R.id.lv_jinqijflist)
    RecyclerView _jinQiJFlist;

    @BindView(R.id.loading_process)
    SmoothProgressBar loadingProcess;

    @Inject
    PayWaterPresenter mPayWaterPresenter;

    private PayWaterFragment mPayWaterFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paywater, container, false);
        LogUtil.i("PayWaterFragment", "onCreateView");

        ButterKnife.bind(this, view);
        mPayWaterPresenter.attachView(this);
        mPayWaterFragment = PayWaterFragment.this;
        init();
        mEventBus.register(this);

        return view;
    }

    private void init() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ChaoBiaoSJColumns.CID))) {
            return;
        }
        DUJiaoFeiXXInfo duJiaoFeiXXInfo = new DUJiaoFeiXXInfo();
        duJiaoFeiXXInfo.setFilterType(DUJiaoFeiXXInfo.FilterType.ONE);
        duJiaoFeiXXInfo.setCustomerId(_bundle.getString(ChaoBiaoSJColumns.CID));
        mPayWaterPresenter.getJiaoFeiXXs(duJiaoFeiXXInfo, _bundle.getBoolean(IS_LOCAL_SEARCH, true), getContext());
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

//        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mPayWaterPresenter.detachView();
    }

    @Override
    public void onError(String message) {
        loadingProcess.setVisibility(View.INVISIBLE);
        LogUtil.e(TAG, message);
    }

    @Override
    public void onGetJiaoFeiXXs(List<DUJiaoFeiXX> duJiaoFeiXXes) {
        loadingProcess.setVisibility(View.INVISIBLE);

        if (duJiaoFeiXXes != null) {
            _jinQiJFlist.setLayoutManager(new LinearLayoutManager(_jinQiJFlist.getContext()));
            SimpleStringRecyclerViewAdapter mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), duJiaoFeiXXes, mPayWaterFragment);
            _jinQiJFlist.setAdapter(mAdapter);
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
        private List<DUJiaoFeiXX> mValues;
        private PayWaterFragment mPayWaterFragment;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public final View mView;

            public final TextView mJiaofeiRQN;
            public final TextView mJiaofeiRQYR;
            public final TextView mZhangwuNY;
            public final TextView mFeeid;
            public final TextView mZhangdanJE;
            public final TextView mWeiYueJE;
            public final TextView mShishouJE;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                mJiaofeiRQN = (TextView) view.findViewById(R.id.tv_jiaofeirqn);
                mJiaofeiRQYR = (TextView) view.findViewById(R.id.tv_jiaofeirqyr);
                mZhangwuNY = (TextView) view.findViewById(R.id.tv_zhangwuny);
                mFeeid = (TextView) view.findViewById(R.id.tv_feeid);
                mZhangdanJE = (TextView) view.findViewById(R.id.tv_zhangdanje);
                mWeiYueJE = (TextView) view.findViewById(R.id.tv_weiyueje);
                mShishouJE = (TextView) view.findViewById(R.id.tv_shishouje);
            }

        }

        public DUJiaoFeiXX getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<DUJiaoFeiXX> items, PayWaterFragment payWaterFragment) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            mPayWaterFragment = payWaterFragment;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_jinqijf, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mJiaofeiRQN.setText(TextUtil.format(mValues.get(position).getShoufeirq(),
                    TextUtil.FORMAT_DATE_YEAR));
            holder.mJiaofeiRQYR.setText(TextUtil.format(mValues.get(position).getShoufeirq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH));
            holder.mZhangwuNY.setText(String.valueOf(mValues.get(position).getZhangwuny()));
            holder.mFeeid.setText(String.valueOf(mValues.get(position).getFeeid()));
            holder.mZhangdanJE.setText(holder.mView.getResources().getString(R.string.label_renminbi)
                    + mPayWaterFragment._f.format(mValues.get(position).getJe()));
            holder.mWeiYueJE.setText(holder.mView.getResources().getString(R.string.label_renminbi)
                    + mPayWaterFragment._f.format(mValues.get(position).getShishouwyj()));
            holder.mShishouJE.setText(holder.mView.getResources().getString(R.string.label_renminbi)
                    + mPayWaterFragment._f.format(mValues.get(position).getShishouzje()));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
