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
import com.sh3h.datautil.data.entity.DUQianFeiXX;
import com.sh3h.datautil.data.entity.DUQianFeiXXInfo;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.event.UIBusEvent;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sh3h.meterreading.ui.information.CustomerInformationActivity.IS_LOCAL_SEARCH;

/**
 * Created by xulongjun on 2016/2/16.
 * 欠费信息
 */
public class ArrearsWaterFragment extends ParentFragment implements ArrearsWaterMvpView {
    public static final String TAG = "ArrearsWaterFragment";

    @Inject
    Bus mEventBus;

    @Bind(R.id.lv_qianfeixxlist)
    RecyclerView _qianFeiXXlist;

    @Bind(R.id.loading_process)
    SmoothProgressBar loadingProcess;

    @Inject
    ArrearsWaterPresenter mArrearsWaterPresenter;

    public ArrearsWaterFragment mArrearsWaterFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mArrearsWaterFragment = ArrearsWaterFragment.this;
        LogUtil.i(TAG, "---ArrearsWaterFragment onCreate---");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arrearswater, container, false);
        LogUtil.i("ArrearsWaterFragment", "onCreateView");

        ButterKnife.bind(this, view);
        mArrearsWaterPresenter.attachView(this);
        init();
        mEventBus.register(this);
        List<DUQianFeiXX> duQianFeiXXList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            DUQianFeiXX duQianFeiXX = new DUQianFeiXX("A6011", "2343234234", 2019, 9, 201909,
//                    System.currentTimeMillis(), 1, 2231, 1000, 3999, 2800);
//            duQianFeiXXList.add(duQianFeiXX);
//        }
        if (duQianFeiXXList != null) {
            _qianFeiXXlist.setLayoutManager(new LinearLayoutManager(_qianFeiXXlist.getContext()));
            SimpleStringRecyclerViewAdapter mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), duQianFeiXXList, mArrearsWaterFragment);
            _qianFeiXXlist.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mEventBus.unregister(this);
        mArrearsWaterPresenter.detachView();
    }

    @Subscribe
    public void onCustomerInformationUpdating(UIBusEvent.CustomerInformationUpdating customerInformationUpdating) {
        LogUtil.i(TAG, "---onCustomerInformationUpdating---");
        init();
    }

    private void init() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ChaoBiaoSJColumns.CID))) {
            return;
        }
        DUQianFeiXXInfo duQianFeiXXInfo = new DUQianFeiXXInfo();
        duQianFeiXXInfo.setFilterType(DUQianFeiXXInfo.FilterType.ONE);
        duQianFeiXXInfo.setCustomerId(_bundle.getString(ChaoBiaoSJColumns.CID));
        mArrearsWaterPresenter.getQianFeiXXs(duQianFeiXXInfo, _bundle.getBoolean(IS_LOCAL_SEARCH, true), getContext());
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onError(String message) {
        loadingProcess.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetQianFeiXXs(List<DUQianFeiXX> duQianFeiXXList) {
        loadingProcess.setVisibility(View.INVISIBLE);
        if (duQianFeiXXList != null) {
            _qianFeiXXlist.setLayoutManager(new LinearLayoutManager(_qianFeiXXlist.getContext()));
            SimpleStringRecyclerViewAdapter mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), duQianFeiXXList, mArrearsWaterFragment);
            _qianFeiXXlist.setAdapter(mAdapter);
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<DUQianFeiXX> mValues;
        private ArrearsWaterFragment mArrearsWaterFragment;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public final View mView;
            public final TextView mZhangWuNY;
            public final TextView mChaoCI;
            public final TextView mShuiLiang;
            public final TextView mJE;
            public final TextView mJE2;
            public final TextView mJE3;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                mZhangWuNY = (TextView) view.findViewById(R.id.tv_zhangwuny);
                mChaoCI = (TextView) view.findViewById(R.id.tv_chaoci);
                mShuiLiang = (TextView) view.findViewById(R.id.tv_shuiliang);
                mJE = (TextView) view.findViewById(R.id.tv_je);
                mJE2 = (TextView) view.findViewById(R.id.tv_je2);
                mJE3 = (TextView) view.findViewById(R.id.tv_je3);
            }

        }

        public DUQianFeiXX getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<DUQianFeiXX> items, ArrearsWaterFragment arrearsWaterFragment) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            mArrearsWaterFragment = arrearsWaterFragment;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_qianfeixx, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mZhangWuNY.setText(String.valueOf(mValues.get(position).getZhangwuny()));
            holder.mChaoCI.setText(String.valueOf(mValues.get(position).getChaoci()));
            holder.mShuiLiang.setText(String.valueOf(mValues.get(position).getKaizhangsl()));
            holder.mJE.setText(String.format("%s%s", holder.mView.getResources().getString(R.string.label_renminbi), mArrearsWaterFragment._f.format(mValues.get(position).getShuifei())));
            holder.mJE2.setText(String.format("%s%s", holder.mView.getResources().getString(R.string.label_renminbi), mArrearsWaterFragment._f.format(mValues.get(position).getPaishuif())));
            holder.mJE3.setText(String.format("%s%s", holder.mView.getResources().getString(R.string.label_renminbi), mArrearsWaterFragment._f.format(mValues.get(position).getJe())));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

}
